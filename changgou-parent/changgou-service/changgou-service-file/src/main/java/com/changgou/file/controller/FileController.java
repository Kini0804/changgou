package com.changgou.file.controller;

import com.changgou.file.service.FileService;
import entity.Result;
import entity.StatusCode;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping
    public String updateFile(@RequestParam("file") MultipartFile multipartFile) throws IOException, MyException {
        return fileService.uploadFile(multipartFile);
    }

    @GetMapping
    public String testGet(){
        return "fileGet";
    }
}
