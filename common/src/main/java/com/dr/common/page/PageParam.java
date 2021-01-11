package com.dr.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParam implements Serializable {

    private static final long serialVersionUID = 841937350585706077L;

    public static final int DEFAULT_PAGE_SIZE = 10;
    /** 分页大小 */
    private Integer pageSize = DEFAULT_PAGE_SIZE;
    /** 页码 */
    private Integer page = 1;

    public Integer getPageSize() {
        return (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public Integer getPage() {
        return (page == null || page <= 0) ? 1 : page;
    }
}
