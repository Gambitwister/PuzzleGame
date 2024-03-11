package ui;

import javax.swing.*;

public class RegisterJFrame extends JFrame {
    public RegisterJFrame() {
        initJFrame();
    }

    private void initJFrame() {
        this.setSize(603, 680);
        this.setVisible(true);
        // set the game page in the middle of the screen
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setTitle("Puzzle Game v1.0");
    }
}
