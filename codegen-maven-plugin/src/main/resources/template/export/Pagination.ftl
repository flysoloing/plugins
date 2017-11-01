package ${groupId}.bean;

import java.io.Serializable;

/**
 * 分页参数类<br>
 * User: laitao<br>
 * Date: 15-3-18<br>
 * Time: 下午3:45<br>
 */
public class Pagination implements Serializable {

    private static final long serialVersionUID = 743475068647030246L;

    private int totalRecord;//总记录条数
    private int pageSize;//每页记录数
    private int currentIndex;//当前页码
    private int totalPage;//总页数

    public Pagination(int totalRecord, int pageSize, int currentIndex) {
        this.totalRecord = totalRecord;
        this.pageSize = pageSize;
        this.currentIndex = currentIndex;
        this.totalPage = (totalRecord + pageSize - 1) / pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
