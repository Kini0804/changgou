package com.changgou.file.service.impl;

import com.changgou.file.pojo.FastDFSFile;
import com.changgou.file.service.FileService;
import com.changgou.file.utils.FastDFSUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException, MyException {
        FastDFSFile fastDFSFile = new FastDFSFile();
        fastDFSFile.setFileName(multipartFile.getOriginalFilename());
        fastDFSFile.setContent(multipartFile.getBytes());
        fastDFSFile.setExt(StringUtils.getFilenameExtension(multipartFile.getOriginalFilename()));
        fastDFSFile.setAuthor(multipartFile.getContentType());
        String[] results = FastDFSUtils.updateFile(fastDFSFile);
        if (results == null){
            return null;
        }else {
            return FastDFSUtils.getTrackerUrl() + "/" + results[0] + "/" + results[1];
        }
    }
}
