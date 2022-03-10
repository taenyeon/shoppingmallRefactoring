package com.shop.myapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaBoard {
	private String boardId;
	private String itemCode;
	private String memberId;
	private String boardTitle;
	private String boardContent;
	private String boardReply;
}
