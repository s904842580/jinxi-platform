package com.jinxi.platform.dto.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PageDTO implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private long page;
    private long pageSize;
    private String operator;

    public void setPageSize(long pageSize){
        if (pageSize == -1){
            this.pageSize = Integer.MAX_VALUE;
            this.page = 1;
        }else{
            this.pageSize = pageSize;
        }
    }
    public void setPage(long page){
        if (page < 0){
            this.page = 1;
        }else{
            this.page = page;
        }
    }
}

