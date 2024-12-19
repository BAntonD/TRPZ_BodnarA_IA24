package Observer;

import javax.swing.*;
import java.util.List;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(JTextPane textPane);
}

