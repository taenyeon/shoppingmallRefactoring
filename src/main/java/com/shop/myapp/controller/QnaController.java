package com.shop.myapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.shop.myapp.dto.MemberSession;
import com.shop.myapp.dto.QnaBoard;
import com.shop.myapp.service.QnaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("/list")
    public String getList(@RequestParam String itemCode, Model model) {
        /*, @RequestParam(required = false, defaultValue = "1") int page*/
        List<QnaBoard> qnaList = qnaService.getList(itemCode);
        model.addAttribute("qnaList",qnaList);
        model.addAttribute("itemCode",itemCode);
        return "modal/qnaView";
    }

    @ResponseBody
    @PostMapping("/write")
    public ResponseEntity<Object> write(QnaBoard qna, HttpServletRequest request) {
        MemberSession mSession = (MemberSession) request.getSession().getAttribute("member");
        qna.setMemberId(mSession.getMemberId());
        int result = qnaService.insertWrite(qna);
        if (result != 0) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(400).build();
    }

    @ResponseBody
    @PostMapping("/reply")
    public ResponseEntity<Object> reply(QnaBoard qna) {
        int result = qnaService.reply(qna);
        return ResponseEntity.ok().build();
    }

}
