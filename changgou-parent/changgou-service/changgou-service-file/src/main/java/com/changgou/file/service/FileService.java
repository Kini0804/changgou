package com.changgou.file.service;

import org.csource.common.MyException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws IOException, MyException;
}
