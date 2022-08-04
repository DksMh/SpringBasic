package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

import static java.lang.Math.*;
import static java.util.Objects.requireNonNullElse;

public class SearchCondition {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String  keyword = "";
    private String  option = "";
    // private Integer  offset = 0; // page와 pageSize로 계산된 값

    public static final int MIN_PAGE_SIZE = 5;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 50;

    public SearchCondition(){}

    public SearchCondition(Integer page, Integer pageSize) {
        this(page, pageSize, "", "");
    }

    public SearchCondition(Integer page, Integer pageSize, String option, String keyword) {
        this.page = page;
        this.pageSize = pageSize;
        this.option = option;
        this.keyword = keyword;
    }

    public String getQueryString(Integer page) {
        // ?page=10&pageSize=10&option=A&keyword=title 을 만들어주는 메서드
        return UriComponentsBuilder.newInstance()
                .queryParam("page", page)  // 지정된 페이지로 페이지 셋팅
                .queryParam("pageSize", pageSize)
                .queryParam("option", option)
                .queryParam("keyword", keyword)
                .build().toString();
    }

    public String getQueryString() {
        return getQueryString(page);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE);

        // MIN_PAGE_SIZE <= pageSize <= MAX_PAGE_SIZE
        this.pageSize = max(MIN_PAGE_SIZE, min(this.pageSize, MAX_PAGE_SIZE));
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getOffset() { // boardMapper.xml -> LIMIT #{offset} 에서 사용
        return (page-1) *  pageSize;
    }

//    public void setOffset(Integer offset) {
//        this.offset = offset;
//    }


    @Override
    public String toString() {
        return "SearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                ", offset=" + getOffset() +
                '}';
    }

}