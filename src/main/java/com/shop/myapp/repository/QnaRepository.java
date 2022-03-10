package com.shop.myapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shop.myapp.dto.QnaBoard;

@Mapper
public interface QnaRepository {

	List<QnaBoard> findAll(String itemCode);
	int insertWrite(QnaBoard qna);
	int reply(QnaBoard qna);
}
