package org.koffa.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.koffa.interfaces.FileDownloader;
import org.koffa.interfaces.FileUploader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPManager implements FileUploader, FileDownloader {
    String host;
    int port;
    String username;
    String password;
    FTPClient ftpClient;

    public FTPManager(String host, int port, String username, String password) throws RuntimeException {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        try {
            this.ftpClient = connect();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(String fileName) throws RuntimeException {
        try {
            saveFile(fileName);
            ftpClient.completePendingCommand();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFile(String fileName) throws IOException {
        try (InputStream inputStream = ftpClient.retrieveFileStream(fileName)) {
            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                byte[] bytesArray = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                    outputStream.write(bytesArray, 0, bytesRead);
                }
            }
        }
    }

    @Override
    public void upload(String fileName) throws RuntimeException {
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private FTPClient connect() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            return ftpClient;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}