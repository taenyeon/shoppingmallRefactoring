package com.shop.myapp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.myapp.dto.QnaBoard;
import com.shop.myapp.repository.QnaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class QnaService {
	private final QnaRepository qnaRepository;
	
	public QnaService(@Autowired SqlSession sqlSession) {
		this.qnaRepository = sqlSession.getMapper(QnaRepository.class);
	}
	
	public List<QnaBoard> getList(String itemCode) {
		log.info("QNA List~");
		return qnaRepository.findAll(itemCode);
	}
	
	public int insertWrite(QnaBoard qna) {
		return qnaRepository.insertWrite(qna);
	}

	public int reply(QnaBoard qna) {
		return qnaRepository.reply(qna);
	}


}
