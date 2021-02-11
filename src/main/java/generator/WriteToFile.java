package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    protected void writeToFile(String outFilePath, String text) {
        File file = new File(outFilePath);

        try {
            if (file.createNewFile()) {
                System.out.println("Successfully created txt file at path " + outFilePath + ".");
            } else {
                System.out.println("File exists. Writing over existing file at path " + outFilePath + ".");
            }

            FileWriter writer = new FileWriter(outFilePath);
            writer.write(text);
            writer.flush();
            writer.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("Unable to write to file.");
        }
    }
}
