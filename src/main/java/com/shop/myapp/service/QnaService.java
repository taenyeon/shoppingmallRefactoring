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

	/**
	 * 해당 상품의 QnA 조회
	 * @param itemCode 상품번호
	 * @return 상품에 대한 QnA들
	 */
	public List<QnaBoard> getList(String itemCode) {
		return qnaRepository.findAll(itemCode);
	}

	/**
	 * QnA 작성
	 * @param qna QnA 정보
	 * @return QnA DB 저장 결과
	 */
	public int insertWrite(QnaBoard qna) {
		return qnaRepository.insertWrite(qna);
	}

	/**
	 * 작성된 QnA에 대한 답글 작성
	 * @param qna QnA 정보
	 * @return QnA 답글 DB 저장 결과
	 */
	public int reply(QnaBoard qna) {
		return qnaRepository.reply(qna);
	}


}
