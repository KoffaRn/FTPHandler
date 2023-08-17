package org.koffa.ftp;

import org.koffa.interfaces.FileUploader;

public class FTPUploader implements FileUploader {
    FTPManager ftpManager;

    public FTPUploader(FTPManager ftpManager) {
        this.ftpManager = ftpManager;
    }

    @Override
    public void uploadFile(String fileName) {
        ftpManager.uploadFile(fileName);
    }
}
