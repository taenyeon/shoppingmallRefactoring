package com.shop.myapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
  페이징 처리 객체
 */
public class Pagination {

    private int listSize = 12;
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

        this.endList = page * listSize;
        // 현제 페이지 기준으로 보여줄 페이지 수 계산
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