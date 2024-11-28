package com.green.greengramver2.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component // 빈 등록 : spring 컨테이너한테 객체관리를 맡기는 것
@Slf4j
public class MyFileUtils {
    private final String uploadPath;

    /*
    @Value("${file.directory}") 은
    yaml 파일에 있는 file.directory 속성에 저장된 값을 생성자 호출할 때 값을 넣어준다.
    생성자도 메소드의 한 종류라고 하는데, 특별한 메소드라고 한다.
    그 이유는 생성자는 생성할때, 한번만 호출되기 때문에
     */
    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        log.info("MyFileUtils - 생성자 {}",uploadPath);
        this.uploadPath = uploadPath;
    }

    // path = "ddd/aaa"
    // D:/BHB/download/greengram_ver1/ddd/aaa
    // 디렉토리 생성
    public String makeFolders(String path){
        File file = new File(uploadPath, path);
        // static 아님 >> 객체화하고 주소값.(file.)으로 호출했기 때문에
        // 리턴타입은 boolean >> if()안에서 호출했기 때문에
        // 파라미터는 없음 >> 호출 때 인자를 보내지 않았기 때문에
        // 메소드명은 >> exists 였다.
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    //파일명에서 확장자 추출
    public String getExt(String fileName){
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
    }

    //랜덤파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //랜덤파일명 + 확장자
    public String makeRandomFileName(String originalFileName){
        int lastIdx = originalFileName.lastIndexOf(".");
        return makeRandomFileName() + getExt(originalFileName);
    }

    public String makeRandomFileName(MultipartFile file){
        return makeRandomFileName(file.getOriginalFilename());
    }

    //파일을 원하는 경로에 저장
    public void transferTo(MultipartFile mf,String path) throws IOException {
        File file = new File(uploadPath, path);
        mf.transferTo(file);
    }
}