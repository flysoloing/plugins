package ${groupId}.bean;

import java.io.Serializable;

/**
 * 带分页条件查询类<br>
 * User: laitao<br>
 * Date: 15-3-18<br>
 * Time: 下午3:59<br>
 */
public class PaginationQuery<T> extends Query<T> implements Serializable {
    private static final long serialVersionUID = -1440485676534570713L;

    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
