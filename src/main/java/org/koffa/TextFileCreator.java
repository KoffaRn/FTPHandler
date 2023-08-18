package org.koffa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class TextFileCreator {
    public TextFileCreator(String fileName, String content) {
        try {
            new File(fileName).createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(content);
            writer.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}