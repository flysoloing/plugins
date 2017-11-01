package ${groupId}.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

/**
 * 应用的运行时异常<br>
 * User: laitao <br>
 * Date: 13-11-10 <br>
 * Time: 下午7:09
 */
public class AppRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1540851313130932011L;
    /**
     * 用来反应业务异常的错误码，一般会结合配置文件
     */
    private String errorCode;

    /**
     * 用来反应业务异常信息的错误信息，一般会结合配置来说明<code>errorCode</code>
     */
    private String errorMessage;

    /**
     * 异常信息描述，用来描述<code>cause</code>
     */
    private String showMessage;

    /**
     * 扩展业务信息
     */
    private Map<String, String> extBizInfo;

    /**
     * 触发该异常的原因
     */
    private Throwable cause;

    public AppRuntimeException() {
    }

    public AppRuntimeException(Map extBizInfo) {
        this.extBizInfo = extBizInfo;
    }

    public AppRuntimeException(String showMessage) {
        super(showMessage);
        this.showMessage = showMessage;
    }

    public AppRuntimeException(Throwable cause) {
        super(cause);
    }

    public AppRuntimeException(String errorCode, String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public AppRuntimeException(String errorCode, String errorMessage, Map<String, String> extBizInfo) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.extBizInfo = extBizInfo;
    }

    public AppRuntimeException(String errorCode, String errorMessage, String showMessage) {
        super(showMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.showMessage = showMessage;
    }

    public AppRuntimeException(String showMessage, Throwable cause) {
        super(showMessage, cause);
        this.showMessage = showMessage;
        this.cause = cause;
    }

    public AppRuntimeException(String errorCode, String errorMessage, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.cause = cause;
        this.errorMessage = errorMessage;
    }

    public AppRuntimeException(String errorCode, String errorMessage, String showMessage, Throwable cause) {
        super(showMessage, cause);
        this.cause = cause;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.showMessage = showMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getShowMessage() {
        if (this.showMessage == null) {
            return super.getMessage();
        }
        return showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, String> getExtBizInfo() {
        return extBizInfo;
    }

    public void setExtBizInfo(Map<String, String> extBizInfo) {
        this.extBizInfo = extBizInfo;
    }

    /**
     * Description:获取异常信息
     *
     * @return
     */
    public String getMessage() {
        String msg = super.getMessage();
        String causeMsg = null;
        if (this.cause != null) {
            causeMsg = this.cause.getMessage();
        }
        if (msg != null) {
            if (causeMsg != null) {
                return msg + " caused by: " + causeMsg;
            }
            return msg;
        }
        return causeMsg;
    }

    /**
     * Description:获取异常堆栈信息
     *
     * @return
     */
    public String getErrorStack() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bo);
        printStackTrace(ps);
        return new String(bo.toByteArray());
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        if (this.cause == null) {
            return super.toString();
        }
        return super.toString() + " caused by: " + this.cause.toString();
    }
}
