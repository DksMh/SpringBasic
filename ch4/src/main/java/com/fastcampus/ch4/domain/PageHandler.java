package com.fastcampus.ch4.domain;

import org.springframework.web.util.UriComponentsBuilder;

// 페이지 내비게이션바
public class PageHandler {
//    private int page; //현재 페이지
//    private int pageSize; //한 페이지의 크기
//    private String option;
//    private String keyword;
    private SearchCondition sc; // 위 4개를 묶은 것

    private int totalCnt; //총 게시물 개수
    private int naviSize = 10; //페이지 내비게이션의 크기 ( ex)) 1~10 페이지 까지 )
    private int totalPage; //전체 페이지의 개수
    private int beginPage; //내비게이션의 첫번째 페이지
    private int endPage; //내비게이션의 마지막 페이지
    private boolean showPrev; //이전 페이지로 이동하는 링크를 보여줄 것이지의 여부 ( ex)) < (아이콘))
    private boolean showNext; //다음 페이지로 이동하는 링크를 보여줄 것인지의 여부 ( ex)) > (아이콘))

    public PageHandler(int totalCnt, SearchCondition sc){
        this.totalCnt = totalCnt;
        this.sc = sc;
        doPaging(totalCnt,sc);
    }

//    public PageHandler(int totalCnt, int page){
//        this(totalCnt, page, 10);
//    }

    public void doPaging(int totalCnt, SearchCondition sc) { // PageHandler(int totalCnt, int page, int pageSize)에서 바꿈
        this.totalCnt = totalCnt;
        totalPage = (int)Math.ceil(totalCnt / (double)sc.getPageSize());
        beginPage = (sc.getPage()-1) / naviSize * naviSize + 1;
        endPage = Math.min(beginPage + naviSize - 1, totalPage);
        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
//        this.page = page;
//        this.pageSize = pageSize;
//
//        totalPage = (int)Math.ceil(totalCnt / (double)pageSize);
//        beginPage = (page-1) / naviSize * naviSize + 1;
//        endPage = Math.min(beginPage + naviSize - 1, totalPage);
//        showPrev = beginPage != 1;
//        showNext = endPage != totalPage;
    }

    public SearchCondition getSc() {
        return sc;
    }

    public void setSc(SearchCondition sc) {
        this.sc = sc;
    }
    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    /* public int getPageSize() {return pageSize;}
    public void setPageSize(int pageSize) {this.pageSize = pageSize;} */

    public int getNaviSize() {
        return naviSize;
    }

    public void setNaviSize(int naviSize) {
        this.naviSize = naviSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /* public int getPage() {return page;}
    public void setPage(int page) {this.page = page;}  */

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    void print() {
        System.out.println("page = " + sc.getPage());
        System.out.print(showPrev ? "[PREV] " : "");
        for (int i = beginPage; i <= endPage; i++) {
            System.out.print(i+" ");
        }
        System.out.println(showNext ? " [NEXT] " : "");
    }

    @Override
    public String toString() {
        return "PageHandler{" +
                "sc=" + sc +
                ", totalCnt=" + totalCnt +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }

    /* 기존 toString
    @Override
    public String toString() {
        return "PageHandler{" +
                "totalCnt=" + totalCnt +
                ", pageSize=" + pageSize +
                ", naviSize=" + naviSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }*/
}