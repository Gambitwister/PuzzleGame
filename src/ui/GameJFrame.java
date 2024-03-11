package ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    Random r = new Random();

    // this array stores the indexes of the images that will be loaded
    int[][] imgArr = new int[4][4];

    //this array stores the completed image indexes
    int[][] completeArr = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    // record the steps that the user has made
    int count = 0;

    // x and y is the index of the blank image
    int x;
    int y;

    // initialize the image path
    String path = "image/animal/animal3/";

    // initialize the menu items
    JMenuItem newGameItem = new JMenuItem("New game");
    JMenuItem reLoginGameItem = new JMenuItem("Re-login");
    JMenuItem closeItem = new JMenuItem("Close game");
    JMenuItem QRCodeItem = new JMenuItem("QR code");
    JMenuItem ladyItem = new JMenuItem("Lady");
    JMenuItem animalItem = new JMenuItem("Animal");
    JMenuItem sportItem = new JMenuItem("Sport");

    // this is the main game page
    public GameJFrame() {
        initJFrame();
        initJMenuBar();
        initData();
        initImage();

        // make sure to show the gameJFrame at last
        this.setVisible(true);
    }

    private void initData() {
        // this array stores all the image indexes
        int[] tempArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        // shuffle tempArr
        int temp;
        for (int i = 0; i < tempArr.length; i++) {
            int randIndex = r.nextInt(tempArr.length);
            temp = tempArr[i];
            tempArr[i] = tempArr[randIndex];
            tempArr[randIndex] = temp;
        }

        // convert tempArr to a 2-D array
        for (int i = 0; i < tempArr.length; i++) {
            imgArr[i / 4][i % 4] = tempArr[i];
        }

        // set the last image to blank
        imgArr[3][3] = 0;
    }

    private void initImage() {
        // reset the image page whenever this function is called
        this.getContentPane().removeAll();

        JLabel cntJLabel = new JLabel("step count: " + count);
        cntJLabel.setBounds(20, 0, 100, 50);
        this.getContentPane().add(cntJLabel);

        JLabel aJLabel = new JLabel("Press 'a' to show the full image");
        aJLabel.setBounds(20, 20, 300, 50);
        this.getContentPane().add(aJLabel);

        JLabel wJLabel = new JLabel("Press 'w' to win the game");
        wJLabel.setBounds(20, 40, 300, 50);
        this.getContentPane().add(wJLabel);

        // show the information "You Win!" when the user completes all images
        if (win()) {
            JLabel winJLabel = new JLabel(new ImageIcon("image/win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }

        // initialize the image
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // record the index of the blank image
                if (imgArr[i][j] == 0) {
                    x = i;
                    y = j;
                }

                JLabel jLabel = new JLabel(new ImageIcon(path + imgArr[i][j] + ".jpg"));
                // the size of each image icon is 105*105
                // use offset, e.g., 83 and 134 to centralize the image page
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                // set bevel borders for the image
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                this.getContentPane().add(jLabel);
            }
        }

        // initialize the background
        // NOTE: initialize the background at last
        JLabel bgJLabel = new JLabel(new ImageIcon("image/background.png"));
        bgJLabel.setBounds(40, 40, 508, 560);
        this.getContentPane().add(bgJLabel);

        // reload the new image page
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu funcJMenu = new JMenu("Function");
        JMenu aboutMenu = new JMenu("About us");
        JMenu newImage = new JMenu("New image");

        funcJMenu.add(newImage);
        funcJMenu.add(newGameItem);
        funcJMenu.add(reLoginGameItem);
        funcJMenu.add(closeItem);
        aboutMenu.add(QRCodeItem);
        newImage.add(ladyItem);
        newImage.add(animalItem);
        newImage.add(sportItem);

        newGameItem.addActionListener(this);
        reLoginGameItem.addActionListener(this);
        closeItem.addActionListener(this);
        QRCodeItem.addActionListener(this);
        ladyItem.addActionListener(this);
        animalItem.addActionListener(this);
        sportItem.addActionListener(this);

        jMenuBar.add(funcJMenu);
        jMenuBar.add(aboutMenu);

        this.setJMenuBar(jMenuBar);
    }

    // initialize the main game page
    private void initJFrame() {
        this.setSize(603, 680);
        // set the game page in the middle of the screen
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setTitle("Puzzle Game v1.0");
        // we do not need the default "centralized component" layout
        this.setLayout(null);
        this.addKeyListener(this);
    }

    private boolean win() {
        for (int i = 0; i < imgArr.length; i++) {
            for (int j = 0; j < imgArr[i].length; j++) {
                if (imgArr[i][j] != completeArr[i][j]) {
                    return false;
                }
            }
        }
        // if imgArr is the same as completeArr, the use will win the game
        return true;
    }

    private int subFolderCnt(String folder) {
        File file = new File(folder);
        int cnt = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // stop the game when the user wins
        if (win()) {
            return;
        }

        int keyCode = e.getKeyCode();
        // a: 65
        if (keyCode == 65) {
            // show the complete image to the user
            this.getContentPane().removeAll();
            JLabel jLabel = new JLabel(new ImageIcon(path + "all.jpg"));
            jLabel.setBounds(83, 134, 420, 420);
            this.getContentPane().add(jLabel);

            // initialize the background
            // NOTE: initialize the background at last
            JLabel bgJLabel = new JLabel(new ImageIcon("image/background.png"));
            bgJLabel.setBounds(40, 40, 508, 560);
            this.getContentPane().add(bgJLabel);

            // reload the new image page
            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // stop the game when the user wins
        if (win()) {
            return;
        }

        int keyCode = e.getKeyCode();

        // w: 87
        // when the user presses 'w', he will win the game directly :)
        if (keyCode == 87) {
            // set imgArr to the completed image
            for (int i = 0; i < 15; i++) {
                imgArr[i / 4][i % 4] = i + 1;
            }
            // set the last image as blank
            imgArr[3][3] = 0;
        }

        // left: 37 up: 38 right: 39 down: 40
        else if (keyCode == 37) {
            // exit when the blank image is at the far right
            if (y == 3) {
                return;
            }
            // move the image to the left
            imgArr[x][y] = imgArr[x][y + 1];
            imgArr[x][y + 1] = 0;
            y++;
            count++;
        } else if (keyCode == 38) {
            // exit when the blank image is at the bottom
            if (x == 3) {
                return;
            }
            // move the image upwards
            imgArr[x][y] = imgArr[x + 1][y];
            imgArr[x + 1][y] = 0;
            x++;
            count++;
        } else if (keyCode == 39) {
            // exit when the blank image is at the far left
            if (y == 0) {
                return;
            }
            // move the image to the right
            imgArr[x][y] = imgArr[x][y - 1];
            imgArr[x][y - 1] = 0;
            y--;
            count++;
        } else if (keyCode == 40) {
            // exit when the blank image is at the top
            if (x == 0) {
                return;
            }
            // move the image downwards
            imgArr[x][y] = imgArr[x - 1][y];
            imgArr[x - 1][y] = 0;
            x--;
            count++;
        }
        initImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String folder = "";

        if (source == ladyItem) {
            folder = "image/girl";
            int folderCnt = subFolderCnt(folder);
            int randFolderIndex = r.nextInt(folderCnt) + 1;
            path = folder + "/girl" + randFolderIndex + "/";
            // reset step count
            count = 0;
            initData();
            initImage();
        }
        else if (source == animalItem) {
            folder = "image/animal";
            int folderCnt = subFolderCnt(folder);
            int randFolderIndex = r.nextInt(folderCnt) + 1;
            path = folder + "/animal" + randFolderIndex + "/";
            // reset step count
            count = 0;
            initData();
            initImage();
        }
        else if (source == sportItem) {
            folder = "image/sport";
            int folderCnt = subFolderCnt(folder);
            int randFolderIndex = r.nextInt(folderCnt) + 1;
            path = folder + "/sport" + randFolderIndex + "/";
            // reset step count
            count = 0;
            initData();
            initImage();
        }
        else if (source == newGameItem) {
            count = 0;
            initData();
            initImage();
        }
        else if (source == reLoginGameItem) {
            count = 0;
            this.setVisible(false);
            new LoginJFrame();
        }
        else if (source == closeItem) {
            // turn off VM directly
            System.exit(0);
        }
        else if (source == QRCodeItem) {
            JDialog jDialog = new JDialog();
            JLabel jLabel = new JLabel(new ImageIcon("image/QR_code.png"));
            jLabel.setBounds(0, 0, 525, 532);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(644,644);
            jDialog.setAlwaysOnTop(true);
            // centralize the jDialog
            jDialog.setLocationRelativeTo(null);
            // user can do nothing without closing the jDialog
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }
    }
}
