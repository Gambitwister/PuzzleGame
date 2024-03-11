package ui;

import others.MyMouseListener;
import others.User;
import others.CodeUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LoginJFrame extends JFrame implements MyMouseListener {
    // create a new ArrayList to store the username and pwd
    static ArrayList<User> list = new ArrayList<>();

    static {
        list.add(new User("zhangsan", "123"));
        list.add(new User("lisi", "1234"));
    }

    String path = "image/login/";

    // initialize the text fields and the buttons
    JTextField username = new JTextField();
    JTextField password = new JTextField();
    JTextField code = new JTextField();
    JButton login = new JButton();
    JButton register = new JButton();

    // generate a random 5-bit validation code
    String codeStr = CodeUtil.getCode();

    public LoginJFrame() {
        initJFrame();
        initView();
        this.setVisible(true);
    }

    public void initView() {
        // refresh the LoginJFrame whenever it's loaded
        this.getContentPane().removeAll();
        JLabel usernameText = new JLabel(new ImageIcon(path + "username.png"));
        usernameText.setBounds(116, 135, 47, 17);
        this.getContentPane().add(usernameText);

        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);

        JLabel passwordText = new JLabel(new ImageIcon(path + "pwd.png"));
        passwordText.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordText);

        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);

        JLabel codeText = new JLabel(new ImageIcon(path + "/valid_code.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);

        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);

        rightCodeJLabel();

        login.setBounds(123, 310, 128, 47);
        login.setIcon(new ImageIcon(path + "login.png"));
        // remove the default button border
        login.setBorderPainted(false);
        // remove the default button background
        login.setContentAreaFilled(false);
        login.addMouseListener(this);
        this.getContentPane().add(login);

        register.setBounds(256, 310, 128, 47);
        register.setIcon(new ImageIcon(path + "register.png"));
        // remove the default button border
        register.setBorderPainted(false);
        // remove the default button background
        register.setContentAreaFilled(false);
        register.addMouseListener(this);
        this.getContentPane().add(register);

        JLabel background = new JLabel(new ImageIcon(path + "background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);

        // reload the new LoginJFrame
        this.getContentPane().repaint();
    }

    private void rightCodeJLabel() {
        JLabel rightCode = new JLabel();
        rightCode.setText(codeStr);
        rightCode.setBounds(300, 256, 50, 30);
        this.getContentPane().add(rightCode);
    }


    public void initJFrame() {
        this.setSize(488, 430);
        this.setTitle("Puzzle Game V1.0 Login");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
    }


    // inform the user that the password is not correct
    public void showJDialog(String content) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(200, 150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        // user can do nothing without closing the dialog
        jDialog.setModal(true);

        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        jDialog.setVisible(true);
    }

    private boolean checkUserAndPwd(String username, String pwd) {
        boolean allCorrect = false;
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                if (user.getPwd().equals(pwd)) {
                    allCorrect = true;
                }
            }
        }
        return allCorrect;
    }

    // check if the username, pwd and code are valid
    public boolean userAndPwdValid() {
        String usernameInput = username.getText();
        String pwdInput = password.getText();
        if (usernameInput.isEmpty() || pwdInput.isEmpty()) {
            return false;
        }
        // check if the username and the password match the data store in the Arraylist
        return checkUserAndPwd(usernameInput, pwdInput);
    }

    public boolean codeValid() {
        String codeInput = code.getText();
        return (codeStr.equalsIgnoreCase(codeInput));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (source == login) {
            login.setIcon(new ImageIcon(path + "login_pressed.png"));
        } else if (source == register) {
            register.setIcon(new ImageIcon(path + "register_pressed.png"));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();
        if (source == login) {
            login.setIcon(new ImageIcon(path + "login.png"));
            if (!codeValid()) {
                showJDialog(("Code is wrong!"));
                // generate a new code
                codeStr = CodeUtil.getCode();
                initView();
            }
            else if (!userAndPwdValid()){
                showJDialog("Wrong Username or Pwd!");
                // generate a new code
                codeStr = CodeUtil.getCode();
                initView();
            }
            else {
                new GameJFrame();
            }
        } else if (source == register) {
            register.setIcon(new ImageIcon(path + "register.png"));
        }
    }
}