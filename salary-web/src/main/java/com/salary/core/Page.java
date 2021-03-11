package com.salary.core;

import com.baomidou.mybatisplus.annotation.TableField;

public class Page {

    @TableField(exist = false)
    private int page;
    @TableField(exist = false)
    private int limit;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
