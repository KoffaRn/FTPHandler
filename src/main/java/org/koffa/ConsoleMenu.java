package org.koffa;

import org.koffa.ftp.FTPManager;
import org.koffa.interfaces.FileDownloader;
import org.koffa.interfaces.FileUploader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class ConsoleMenu {
    FileUploader uploader;
    FileDownloader downloader;
    boolean connected = false;
    boolean running = true;

    public ConsoleMenu() {
        while(running) {
            if(!connected) connectFtp();
            if(connected) mainMenu();
        }
    }

    private void mainMenu() {
        System.out.println("""
                1. Upload file
                2. Download and display file
                3. Create and upload new text file
                4. Exit""");
        int choice = getInputInt("Enter choice:");
        switch (choice) {
            case 1 -> {
                upload(getInput("Enter file name:"));
            }
            case 2 -> {
                download(getInput("Enter file name:"));
            }
            case 3 -> {
                String fileName = getInput("Enter file name:");
                String content = getInput("Enter file content:");
                try {
                    new TextFileCreator(fileName, content);
                    uploader.upload(fileName);
                    System.out.println("File creation and upload successful");
                } catch (RuntimeException e) {
                    System.out.println("File creation failed: " + e.getMessage());
                }
            }
            case 4 -> running = false;
            default -> System.out.println("Invalid input");
        }
    }

    private void download(String fileName) {
        try {
            downloader.download(fileName);
            displayFile(fileName);
            System.out.println("Download successful");
        }
        catch (RuntimeException e) {
            System.out.println("Download failed: " + e.getMessage());
        }
    }

    private void upload(String fileName) {
        try {
            uploader.upload(fileName);
            System.out.println("Upload successful");
        } catch (RuntimeException e) {
            System.out.println("Upload failed: " + e.getMessage());
        }
    }

    private void displayFile(String fileName) throws RuntimeException {
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void connectFtp() {
        String host = getInput("Enter host:");
        int port = getInputInt("Enter port:");
        String username = getInput("Enter username:");
        String password = getInput("Enter password:");
        try{
            FTPManager ftp = new FTPManager(host, port, username, password);
            uploader = ftp;
            downloader = ftp;
            connected = true;
            System.out.println("Connection successful");
        } catch (RuntimeException e) {
            System.out.println("Connection failed");
        }
    }
    private String getInput(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    private int getInputInt(String prompt) {
        System.out.println(prompt);
        Scanner sc = new Scanner(System.in);
        if(sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            return getInputInt("Invalid input, try again:");
        }
    }
}
