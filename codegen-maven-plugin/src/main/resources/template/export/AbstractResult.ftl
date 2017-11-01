package ${groupId}.bean;

/**
* 返回结果抽象类<br>
* User: laitao<br>
* Date: 15-3-18<br>
* Time: 下午2:48<br>
*/
public abstract class AbstractResult {

    /**
     * 结果编码
     */
    private String code;
    /**
     * 结果信息
     */
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
