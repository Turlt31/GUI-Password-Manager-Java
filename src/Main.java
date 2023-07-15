import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.nio.ByteBuffer;
import java.time.Instant;

import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.crypto.Mac;
import javax.swing.*;

import org.apache.commons.codec.binary.Base32;

import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.WriterException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.BarcodeFormat;

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
        displayPanel.setBounds(250, 130, 1016, 5000);
        displayPanel.setPreferredSize(new Dimension(1016, 5000));

        JScrollPane scrollPane = new JScrollPane(displayPanel);
        scrollPane.setBounds(250, 130, 1002, 505);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1002, 505));

        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setUnitIncrement(8);


        controlPanel.setVisible(true);
        displayPanel.setVisible(true);

        controlPanel.setLayout(null);
        displayPanel.setLayout(null);

        mainPanel.add(controlPanel);
        mainPanel.add(scrollPane);

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

        JButton notesB = new JButton("Notes");
        notesB.setBounds(10, 250, widthB, heightB);
        notesB.setFont(new Font("Arial", Font.PLAIN, 25));
        mainPanel.add(notesB);

        ActionListener noteListener = e -> Apps.notes(user, controlPanel, displayPanel);
        notesB.addActionListener(noteListener);

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

    private static final int DIGITS = 6;
    private static final int TIME_STEP_SECONDS = 30;

    public static String generateOTP(byte[] keyBytes, long otpTime) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, otpTime);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
            byte[] hash = mac.doFinal(buffer.array());

            int offset = hash[hash.length - 1] & 0xF;
            int truncatedHash = (hash[offset] & 0x7F) << 24
                    | (hash[offset + 1] & 0xFF) << 16
                    | (hash[offset + 2] & 0xFF) << 8
                    | (hash[offset + 3] & 0xFF);

            int otp = truncatedHash % (int) Math.pow(10, DIGITS);
            return String.format("%0" + DIGITS + "d", otp);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String create2FA(String user) {
        byte[] buffer = new byte[20];
        new SecureRandom().nextBytes(buffer);

        Base32 base32 = new Base32();
        String key =  base32.encodeToString(buffer);

        String format = "otpauth://totp/%s:%s?secret=%s&issuer=%s";
        String qrCodeURL = String.format(format, "PM", user, key, "PM");
        String filePath = "src\\files\\"+user+"\\config\\qrCode.png";

        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeURL, BarcodeFormat.QR_CODE, 350, 350, hints);

            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(image, "PNG", new File(filePath));

        } catch (IOException e) { e.printStackTrace(); } catch (WriterException e) { throw new RuntimeException(e); }

        return key;
    }
    public static void login(String username, String password) {
        String fileName = "src\\files\\logins.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (Cryption.encryptMPSW(password).equals(parts[0]) && username.equals(parts[1]) ) {
                    String filePath = "src\\files\\"+username+"\\config\\otp.json";
                    JSONParser jsonParser = new JSONParser();
                    boolean active;
                    String key;
                    try (FileReader fileReader = new FileReader(filePath)) {
                        Object obj = jsonParser.parse(fileReader);
                        JSONObject jsonObject = (JSONObject) obj;
                        active = (boolean) jsonObject.get("active");
                        key = (String) jsonObject.get("key");
                    }
                    catch (ParseException e) { throw new RuntimeException(e); }

                    if (active) {
                        JDialog TFATopLevel = new JDialog(frame);
                        TFATopLevel.setSize(500, 200);
                        TFATopLevel.setTitle("Two Factor-Authentication");
                        TFATopLevel.setVisible(true);
                        TFATopLevel.setResizable(false);

                        JTextField codeF = new JTextField();

                        Runnable checkCode = () -> {
                            long timeWindow = Instant.now().getEpochSecond() / TIME_STEP_SECONDS;
                            byte[] keyBytes = new Base32().decode(key);

                            for (int i = -1; i <= 1; i++) {
                                long otpTime = timeWindow + i;
                                String otp = generateOTP(keyBytes, otpTime);
                                if (codeF.getText().equals(otp)) {
                                    TFATopLevel.dispose();
                                    mainScreen(username); cardLayout.show(cardPanel, "main");
                                }
                            }
                        };

                        JLabel label1 = new JLabel("2 Factor-Authentication");
                        label1.setFont(new Font("Arial", Font.PLAIN, 30));
                        label1.setBounds(90, 10, 330, 40);
                        TFATopLevel.add(label1);

                        JLabel label2 = new JLabel("Code");
                        label2.setBounds(100, 65, 100, 40);
                        label2.setFont(new Font("Arial", Font.PLAIN, 26));
                        TFATopLevel.add(label2);

                        codeF.setBounds(180, 70, 180, 35);
                        codeF.setFont(new Font("Arial", Font.PLAIN, 25));
                        codeF.setHorizontalAlignment(JTextField.CENTER);
                        TFATopLevel.add(codeF);

                        JButton checkB = new JButton("Authenticate");
                        checkB.setBounds(90, 115, 280, 35);
                        checkB.setFont(new Font("Arial", Font.PLAIN, 25));
                        TFATopLevel.add(checkB);

                        ActionListener checkListener = e -> checkCode.run();
                        checkB.addActionListener(checkListener);


                    }
                    else { mainScreen(username); cardLayout.show(cardPanel, "main"); }
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void register(String username, String password) {
        String fileName = "src\\files\\logins.txt";
        String dirName = "src\\files\\"+username;
        String content = Cryption.encryptMPSW(password)+","+username;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content); writer.newLine();
        } catch (IOException e) { e.printStackTrace(); }

        File directory = new File(dirName);
        boolean isDirCreated = directory.mkdir();
        if (isDirCreated) {
            try {
                File file1 = new File(directory, "passwords.txt");
                file1.createNewFile();

                File file2 = new File(directory, "cards.txt");
                file2.createNewFile();

                File file3 = new File(directory, "notes.txt");
                file3.createNewFile();

                File dir = new File(dirName+"\\config");
                dir.mkdir();

                File file4 = new File(directory+"\\config", "key.key");
                file4.createNewFile();

                File file5 = new File(directory+"\\config", "otp.json");
                file5.createNewFile();

                Cryption.makeKey(username);

            } catch (IOException e) {
                System.out.println("An error occurred while creating the files.");
                e.printStackTrace();
            }

        }

        String otpKey = create2FA(username);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("active", false);
        jsonObject.put("key", otpKey);

        try (FileWriter fileWriter = new FileWriter("src\\files\\"+username+"\\config\\otp.json")) {
            fileWriter.write(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        frame.setSize(1266, 668);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Password manager");
        frame.setResizable(false);

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

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}