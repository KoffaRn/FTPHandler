package org.koffa;

import org.koffa.ftp.FTPManager;
import org.koffa.ftp.FTPUploader;
import org.koffa.interfaces.FileUploader;

public class Main {
    public static void main(String[] args) {
        FileUploader uploader = new FTPUploader(new FTPManager("localhost", 21, "test", "!test"));
        uploader.uploadFile("test.txt");
    }
}