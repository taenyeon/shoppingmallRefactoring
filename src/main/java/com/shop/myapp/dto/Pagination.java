package com.shop.myapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    private int listSize = 12;                //초기값으로 목록개수를 10으로 셋팅
    private int page;
    private int listCnt;
    private int pageCnt;
    private int startPage = 1;
    private int startList;
    private int endList;
    private int endPage;

    public int getStartList() {
        return startList;
    }

    public void pageInfo(int page, int listCnt) {
        this.page = page;
        this.listCnt = listCnt;

        //전체 페이지수
        this.pageCnt = (int) Math.ceil((float) listCnt / listSize);
        System.out.println(pageCnt);
        //게시판 시작번호
        this.startList = (page - 1) * listSize;
        System.out.println(startList);

        this.endList = page * listSize;
        System.out.println(endList);
        if (page >= 3) {
            if (pageCnt - page <= 2){
                this.startPage = pageCnt - 4;
            }else {
            this.startPage = page - 2;
            }
        } else {
            this.startPage = 1;
        }

        if (pageCnt - page >= 2) {
            if (page < 3){
                this.endPage = 5;
            }else {
                this.endPage = page + 2;
            }
        } else {
            this.endPage = pageCnt;
        }
    }
}