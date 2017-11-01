package ${groupId}.bean;

import java.io.Serializable;

/**
 * 带分页的返回结果类<br>
 * User: laitao<br>
 * Date: 15-3-18<br>
 * Time: 下午3:53<br>
 */
public class PaginationResult<T> extends ListResult<T> implements Serializable {
    private static final long serialVersionUID = -8701260818087056485L;

    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
