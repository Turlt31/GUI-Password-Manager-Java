import javax.swing.*;

import java.awt.Font;
import java.awt.*;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class Main {
    public static JFrame frame = new JFrame();
    public static CardLayout cardLayout;

    public static JPanel cardPanel;
    public static JPanel mainPanel = new JPanel();
    public static JPanel loginPanel = new JPanel();
    public static JPanel registerPanel = new JPanel();

    public static void registerScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        // (screenWidth-width)/2 | (screenHeight-height)/2
        // (1266-500)/2 = 383 | (668-250)/2 = 209
        panel.setBounds(383, 209, 500, 250);
        panel.setVisible(true);

        registerPanel.add(panel, BorderLayout.CENTER);

        Font font = new Font("Arial", Font.PLAIN,35);

        JLabel label = new JLabel("Register");
        label.setBounds(185, 10, 280, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        panel.add(label);

        JLabel usernameL = new JLabel("Username");
        usernameL.setBounds(60, 60,180,50);
        usernameL.setFont(font);
        panel.add(usernameL);

        JTextField usernameF = new JTextField();
        usernameF.setBounds(240, 70, 180, 35);
        usernameF.setFont(new Font("Arial", Font.PLAIN, 22));
        panel.add(usernameF);

        JLabel passwordL = new JLabel("Password");
        passwordL.setBounds(60, 100,180,50);
        passwordL.setFont(font);
        panel.add(passwordL);

        JTextField passwordF = new JTextField();
        passwordF.setBounds(240, 110, 180, 35);
        passwordF.setFont(new Font("Arial", Font.PLAIN, 22));
        panel.add(passwordF);

        JButton backB = new JButton("Back");
        backB.setBounds(285, 175, 190, 50);
        backB.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(backB);


        ActionListener backListener = e -> cardLayout.show(cardPanel, "login");
        backB.addActionListener(backListener);

        JButton registerB = new JButton("Register");
        registerB.setBounds(25, 175, 190, 50);
        registerB.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(registerB);

        ActionListener registerListener = e -> register(usernameF.getText(), passwordF.getText());
        registerB.addActionListener(registerListener);
    }
    public static void loginScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        // (1266-500)/2 = 383 | (668-250)/2 = 209
        panel.setBounds(383, 209, 500, 250);
        panel.setVisible(true);

        loginPanel.add(panel, BorderLayout.CENTER);


        Font font = new Font("Arial", Font.PLAIN,35);

        JLabel label = new JLabel("Please login");
        label.setBounds(150, 10, 280, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        panel.add(label);

        JLabel usernameL = new JLabel("Username");
        usernameL.setBounds(60, 60,180,50);
        usernameL.setFont(font);
        panel.add(usernameL);

        JTextField usernameF = new JTextField();
        usernameF.setBounds(240, 70, 180, 35);
        usernameF.setFont(new Font("Arial", Font.PLAIN, 22));
        panel.add(usernameF);

        JLabel passwordL = new JLabel("Password");
        passwordL.setBounds(60, 100,180,50);
        passwordL.setFont(font);
        panel.add(passwordL);

        JTextField passwordF = new JTextField();
        passwordF.setBounds(240, 110, 180, 35);
        passwordF.setFont(new Font("Arial", Font.PLAIN, 22));
        panel.add(passwordF);

        JButton loginB = new JButton("Login");
        loginB.setBounds(25, 175, 190, 50);
        loginB.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(loginB);

        ActionListener loginListener = e -> login(usernameF.getText(), passwordF.getText());

        loginB.addActionListener(loginListener);

        JButton registerB = new JButton("Register");
        registerB.setBounds(285, 175, 190, 50);
        registerB.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(registerB);

        ActionListener registerListener = e -> cardLayout.show(cardPanel, "register");

        registerB.addActionListener(registerListener);
    }
    public static void mainScreen(String user) {
        //cardLayout.show(cardPanel, "main");

        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();

        JLabel userL = new JLabel("Hello "+user+"!");
        userL.setBounds(10, 10, 250, 50);
        userL.setFont(new Font("Arial", Font.PLAIN, 30));
        userL.setForeground(Color.white);
        mainPanel.add(userL);

        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(250, 0, 1016, 130);
        controlPanel.setBackground(Color.gray);

        JPanel displayPanel = new JPanel();
        displayPanel.setBounds(250, 130, 1016, 535);

        controlPanel.setVisible(true);
        displayPanel.setVisible(true);

        controlPanel.setLayout(null);
        displayPanel.setLayout(null);

        mainPanel.add(controlPanel);
        mainPanel.add(displayPanel);

        int heightB = 50;
        int widthB = 230;

        JButton passwordB = new JButton("Passwords");
        passwordB.setBounds(10, 130, widthB, heightB);
        passwordB.setFont(new Font("Arial", Font.PLAIN, 25));
        mainPanel.add(passwordB);

        ActionListener passwordListener = e -> Apps.password(user, controlPanel, displayPanel, frame);
        passwordB.addActionListener(passwordListener);

        JButton cardB = new JButton("Cards");
        cardB.setBounds(10, 190, widthB, heightB);
        cardB.setFont(new Font("Arial", Font.PLAIN, 25));
        mainPanel.add(cardB);

        ActionListener cardListener = e -> Apps.card(user, controlPanel, displayPanel, frame);
        cardB.addActionListener(cardListener);

        JButton settingsB = new JButton("Settings");
        settingsB.setBounds(10, 510, widthB, heightB);
        settingsB.setFont(new Font("Arial", Font.PLAIN, 25));
        mainPanel.add(settingsB);

        ActionListener settingsListener = e -> Apps.settings(user, controlPanel, displayPanel);
        settingsB.addActionListener(settingsListener);

        JButton logoutB =  new JButton("Logout");
        logoutB.setBounds(10, 570, widthB, heightB);
        logoutB.setFont(new Font("Arial", Font.PLAIN, 25));
        mainPanel.add(logoutB);

        ActionListener logoutListener = e -> cardLayout.show(cardPanel, "login");
        logoutB.addActionListener(logoutListener);

        Apps.password(user, controlPanel, displayPanel, frame);
    }

    public static void login(String username, String password) {
        String fileName = "src\\files\\logins.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (Cryption.encryptMPSW(password).equals(parts[0]) && username.equals(parts[1]) ) {
                    mainScreen(username);
                    System.out.println("Logged in with: "+username+" | "+password);
                    cardLayout.show(cardPanel, "main");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + fileName);
            e.printStackTrace();
        }
    }
    public static void register(String username, String password) {
        String fileName = "src\\files\\logins.txt";
        String dirName = "src\\files\\"+username;
        String content = Cryption.encryptMPSW(password)+","+username;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
            System.out.println("Account created: " + username + " | " + password);
        } catch (IOException e) {
            System.out.println("Account creation failed");
            e.printStackTrace();
        }

        File directory = new File(dirName);
        boolean isDirCreated = directory.mkdir();
        if (isDirCreated) {
            try {
                File file1 = new File(directory, "passwords.txt");
                boolean isFile1Created = file1.createNewFile();

                File file2 = new File(directory, "cards.txt");
                boolean isFile2Created = file2.createNewFile();

                File file3 = new File(directory, "key.key");
                boolean isFile3Created = file3.createNewFile();

                Cryption.makeKey(username);

            } catch (IOException e) {
                System.out.println("An error occurred while creating the files.");
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("src/icon/pwm.ico");
        Image image = icon.getImage();

        frame.setSize(1266, 668);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Password manager");
        frame.setResizable(false);
        frame.setIconImage(image);

        mainPanel.setBackground(Color.darkGray);
        loginPanel.setBackground(Color.darkGray);
        registerPanel.setBackground(Color.darkGray);

        loginPanel.setLayout(null);
        mainPanel.setLayout(null);
        registerPanel.setLayout(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(loginPanel, "login");
        cardPanel.add(registerPanel, "register");
        cardPanel.add(mainPanel, "main");

        loginScreen();
        registerScreen();

        //mainScreen("a");

        frame.add(cardPanel);

        frame.setVisible(true);
    }
}