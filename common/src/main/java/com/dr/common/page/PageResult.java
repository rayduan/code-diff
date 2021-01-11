package com.dr.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 *
 * @author jiangdawei on 2018/8/31 11:05.
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = -4106295362336919L;

    // 当前页
    private Integer currentPage = 1;
    // 每页显示的总条数
    private Integer pageSize = 10;
    // 总条数
    private Long totalNum;
    // 是否有下一页
    private Integer isMore;
    // 总页数
    private Integer totalPage;
    // 开始索引
    private Integer startIndex;
    // 分页结果
    private List<T> data;

    public PageResult() {
        super();
    }

    public PageResult(Integer currentPage, Integer pageSize, Long totalNum) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = ((int)(this.totalNum + this.pageSize - 1)) / this.pageSize;
        this.startIndex = (this.currentPage - 1) * this.pageSize;
        this.isMore = this.currentPage >= this.totalPage ? 0 : 1;
    }

    public void setTotalNum(Long totalNum){
        this.totalNum =totalNum;
        this.totalPage = ((int)(this.totalNum + this.pageSize - 1)) / this.pageSize;
    }


}