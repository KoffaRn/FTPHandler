package org.koffa.ftp;

import org.apache.commons.net.ftp.FTPClient;


import java.io.FileInputStream;
import java.io.IOException;

public class FTPManager {
    String host;
    int port;
    String username;
    String password;

    public FTPManager(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    public void downloadFile(String fileName) {

    }

    public void uploadFile(String fileName) {
        FTPClient ftpClient = connect();
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            ftpClient.changeWorkingDirectory("/upload");
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            disconnect(ftpClient);
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
    private void disconnect(FTPClient ftpClient) {
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}