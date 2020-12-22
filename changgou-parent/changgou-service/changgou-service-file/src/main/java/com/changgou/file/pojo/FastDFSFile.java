package com.changgou.file.pojo;

import org.csource.common.NameValuePair;

import java.io.PrintStream;
import java.util.Arrays;

public class FastDFSFile {
    private String fileName;
    private String ext;
    private byte[] content;

    public NameValuePair[] getMeta_list() {
        if (meta_list == null){
            meta_list = new NameValuePair[1];
            meta_list[0] = new NameValuePair("author", author);
        }
        return meta_list;
    }

    public void setMeta_list(NameValuePair[] meta_list) {
        this.meta_list = meta_list;
    }

    private String author;
    private NameValuePair[] meta_list;

    public FastDFSFile() {
    }

    public FastDFSFile(String fileName, String ext, byte[] content, String author) {
        this.fileName = fileName;
        this.ext = ext;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return "FastDFSFile{" +
                "fileName='" + fileName + '\'' +
                ", ext='" + ext + '\'' +
                ", content=" + Arrays.toString(content) +
                ", author='" + author + '\'' +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
