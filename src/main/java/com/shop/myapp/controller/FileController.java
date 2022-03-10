package com.shop.myapp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.FileService;

@RestController
@RequestMapping("/file")
@Auth(role = Auth.Role.SELLER)
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/img/dropUpload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> dropUpload(@RequestParam List<MultipartFile> upload, HttpServletRequest request) throws IOException {
        String absolutePath = request.getSession().getServletContext().getRealPath("/resources/");
        MultipartFile file = upload.get(0);
        System.out.println(file.getName());
        HashMap<String, Object> map = new HashMap<>();
        Map<String, String> fileInfo = fileService.boardFileUpload(file,absolutePath);
        map.put("uploaded", 1);
        map.put("url", fileInfo.get("path"));
        map.put("fileName", fileInfo.get("name"));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
