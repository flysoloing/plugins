package ${groupId}.bean;

import java.io.Serializable;

/**
 * 通用返回结果类<br>
 * User: laitao<br>
 * Date: 15-3-18<br>
 * Time: 下午2:54<br>
 */
public class GenericResult<T> extends AbstractResult implements Serializable {
    private static final long serialVersionUID = -1342094832750370935L;

    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
