package com.example.climate_backend.global.common.pagination;

import lombok.Getter;

@Getter
public class PageDto {
    private int page;
    private int size;

    public PageDto(int page, int size) {
        this.page = Math.max(page - 1, 0);
        this.size = size;
    }

}
