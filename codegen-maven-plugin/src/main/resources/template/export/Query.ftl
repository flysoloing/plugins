package ${groupId}.bean;

import java.io.Serializable;

/**
 * 通用查询类<br>
 * User: laitao<br>
 * Date: 15-3-18<br>
 * Time: 下午3:57<br>
 */
public class Query<T> implements Serializable {
    private static final long serialVersionUID = 4526451054574810264L;

    private T query;

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }
}
