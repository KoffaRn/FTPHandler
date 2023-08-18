package org.koffa;

import org.koffa.ftp.FTPManager;
import org.koffa.interfaces.FileDownloader;
import org.koffa.interfaces.FileUploader;

import java.util.Scanner;

public class ConsoleMenu {
    FileUploader uploader;
    FileDownloader downloader;
    boolean connected = false;
    boolean running = true;

    public ConsoleMenu() {
        while(running) {
            start();
        }
    }
    private void start() {
        if(!connected) connect();
        if(connected) mainMenu();
    }

    private void mainMenu() {
        System.out.println("1. Upload file");
        System.out.println("2. Download file");
        System.out.println("3. Exit");
        int choice = getInputInt("Enter choice:");
        switch (choice) {
            case 1 -> {

                String fileName = getInput("Enter file name:");
                uploader.upload(fileName);
            }
            case 2 -> {
                String fileName2 = getInput("Enter file name:");
                downloader.download(fileName2);
            }
            case 3 -> running = false;
            default -> System.out.println("Invalid input");
        }
    }

    private void connect() {
        String host = getInput("Enter host:");
        int port = getInputInt("Enter port:");
        String username = getInput("Enter username:");
        String password = getInput("Enter password:");
        try{
            uploader = new FTPManager(host, port, username, password);
            downloader = (FileDownloader) uploader;
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
