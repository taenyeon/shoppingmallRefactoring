package com.shop.myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = {Exception.class})
public class FileService {
    public Map<String,String> boardFileUpload(MultipartFile file,String absolutePath) throws IOException {
        if(!file.isEmpty()){

            Map<String, String> fileInfo = new HashMap<>();
            // 디렉토리 이름
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
            String directory = now.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            String originalName = date + "_" + file.getOriginalFilename();
            System.out.println("originalName = " + originalName);
            fileInfo.put("name", originalName);
            String changedName = "/resources" +"/" + directory + "/" + originalName;
            System.out.println("changedName = " + changedName);
            fileInfo.put("path", changedName);

            // 시분초 포함한 날짜
            // "/board/" 부분은 차후 LocalDate 형식으로 폴더를 구성할 계획
            java.io.File f = new java.io.File(absolutePath+"/" + directory + "/" + originalName);
            // 파일 폴더가 없을경우, 생성
            if (!f.exists()) {
                f.mkdirs();
            }
            file.transferTo(f);
            return fileInfo;

        }
        return null;
    }
}
