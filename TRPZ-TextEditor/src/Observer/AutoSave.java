package Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AutoSave implements Observer {
    private static final String FILE_NAME = "autosave.txt";

    @Override
    public void update(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(data);
            System.out.println("Text auto-saved to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving text: " + e.getMessage());
        }
    }
}

