package com.changgou.file.utils;

import com.changgou.file.pojo.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.logging.Logger;

public class FastDFSUtils {

    static {
        String filePath = new ClassPathResource("fastdfs.conf").getPath();
        try {
            ClientGlobal.init(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TrackerServer getTracerServer() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        return trackerClient.getConnection();
    }

    private static StorageClient getStorageClient() throws IOException {
        TrackerServer trackerServer = getTracerServer();
        return new StorageClient(trackerServer, null);
    }

    public static String[] updateFile(FastDFSFile fastDFSFile) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        return storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), fastDFSFile.getMeta_list());
    }

    public static void deleteFile(String groupName, String remote_filename) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        storageClient.delete_file(groupName, remote_filename);
    }

    public static FileInfo getFileInfo(String groupName, String remote_filename) throws IOException, MyException {
        StorageClient storageClient = getStorageClient();
        NameValuePair[] metadata = storageClient.get_metadata(groupName, remote_filename);
        return storageClient.get_file_info(groupName, remote_filename);
    }

    public static String getTrackerUrl() throws IOException {
        TrackerServer trackerServer = getTracerServer();
        Logger.getGlobal().info(String.valueOf(trackerServer.getInetSocketAddress()));
        return "http://" + trackerServer.getInetSocketAddress().getHostString() + ":" + ClientGlobal.getG_tracker_http_port();
    }


    public static void main(String[] args) throws IOException, MyException {
        String trackerUrl = getTrackerUrl();
        System.out.println(getFileInfo("group1", "M00/00/00/CtM3A1-RPzOAHffoADKkqnFk_4k114.JPG"));
    }


}
