import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Apps {

    public static boolean showP = false;
    public static boolean showC = false;

    public static void updateP(String user, JPanel displayP, JPanel controlsP) {
        displayP.removeAll();
        displayP.revalidate();
        displayP.repaint();

        int count = 1;
        int posY = 0;

        String website;
        String username;
        String password;

        String fileName = "src\\files\\"+user+"\\passwords.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts1 = line.split(",");
                website = parts1[0]; username = parts1[1]; password = parts1[2];

                String content = Cryption.decryptPSW(user, website, username, password);
                String[] parts2 = content.split(",");
                website = parts2[0]; username = parts2[1]; password = parts2[2];

                JTextField countF = new JTextField();
                countF.setFont(new Font("Arial", Font.BOLD, 30));
                countF.setHorizontalAlignment(JTextField.CENTER);
                countF.setBounds(0, posY, 40, 50);
                countF.setText(Integer.toString(count));
                displayP.add(countF);

                JTextField websiteF = new JTextField();
                websiteF.setFont(new Font("Arial", Font.PLAIN, 20));
                websiteF.setBounds(40, posY, 153, 50);
                websiteF.setText(website);
                displayP.add(websiteF);

                JTextField usernameF = new JTextField();
                usernameF.setFont(new Font("Arial", Font.PLAIN, 20));
                usernameF.setBounds(193, posY, 550, 50);
                usernameF.setText(username);
                displayP.add(usernameF);

                if (showP) {
                    JTextField passwordF = new JTextField();
                    passwordF.setFont(new Font("Arial", Font.PLAIN, 20));
                    passwordF.setBounds(741, posY, 260, 50);
                    passwordF.setText(password);
                    displayP.add(passwordF);
                } else{
                    JTextField passwordF = new JTextField();
                    passwordF.setFont(new Font("Arial", Font.PLAIN, 20));
                    passwordF.setBounds(741, posY, 260, 50);
                    passwordF.setText(password.replaceAll(".", "●"));
                    displayP.add(passwordF);
                }
                count++;
                posY += 50;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + fileName);
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JLabel label = new JLabel("Count: "+(count-1));
        label.setBounds(400, 65, 200, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        controlsP.add(label);

    }
    public static void addP(String userM, JFrame frame, JPanel displayP, JPanel controlsP) {
        JDialog addTopLevel = new JDialog(frame);
        addTopLevel.setSize(400, 300);
        addTopLevel.setTitle("Add Account");
        addTopLevel.setVisible(true);
        addTopLevel.setResizable(false);

        JTextField websiteF = new JTextField();
        JTextField usernameF = new JTextField();
        JTextField passwordF = new JTextField();

        Runnable addToFile = () -> {
            String site = websiteF.getText();
            String user = usernameF.getText();
            String pass = passwordF.getText();
            String content;

            try {
                content = Cryption.encryptPSW(userM, site, user, pass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            if (site.equals("") || user.equals("") || pass.equals("")) {
                return;
            } else {
                String fileName = "src\\files\\"+userM+"\\passwords.txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                    writer.write(content);
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("Account creation failed");
                    e.printStackTrace();}
                updateP(userM, displayP, controlsP);
            }
        };

        JLabel label = new JLabel("Add password");
        label.setBounds(90, 5, 220, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        addTopLevel.add(label);


        JLabel websiteL = new JLabel("Website");
        websiteL.setBounds(10, 50, 100, 50);
        websiteL.setFont(new Font("Arial", Font.PLAIN, 25));
        addTopLevel.add(websiteL);

        websiteF.setBounds(145, 65, 180, 30);
        websiteF.setFont(new Font("arial", Font.PLAIN, 20));
        addTopLevel.add(websiteF);


        JLabel usernameL = new JLabel("Username");
        usernameL.setBounds(10, 90, 120, 50);
        usernameL.setFont(new Font("Arial", Font.PLAIN, 25));
        addTopLevel.add(usernameL);

        usernameF.setBounds(145, 105, 180, 30);
        usernameF.setFont(new Font("arial", Font.PLAIN, 20));
        addTopLevel.add(usernameF);


        JLabel passwordL = new JLabel("Password");
        passwordL.setBounds(10, 130, 120, 50);
        passwordL.setFont(new Font("Arial", Font.PLAIN, 25));
        addTopLevel.add(passwordL);

        passwordF.setBounds(145, 145, 180, 30);
        passwordF.setFont(new Font("arial", Font.PLAIN, 20));
        addTopLevel.add(passwordF);


        JButton addB = new JButton("Add");
        addB.setBounds(10, 200, 120, 50);
        addB.setFont(new Font("Arial", Font.PLAIN, 30));
        addTopLevel.add(addB);

        ActionListener addBListener = e -> addToFile.run();
        addB.addActionListener(addBListener);

    }
    public static void delP(String userM, JFrame frame, JPanel displayP, JPanel controlsP) {
        JDialog delTopLevel = new JDialog(frame);
        delTopLevel.setSize(400, 200);
        delTopLevel.setTitle("Delete Account");
        delTopLevel.setVisible(true);
        delTopLevel.setResizable(false);

        JTextField numberF = new JTextField();

        Runnable delFromFile = () -> {
            String fileName = "src\\files\\"+userM+"\\passwords.txt";
            int lineToRemove = Integer.parseInt(numberF.getText());

            try {
                File inputFile = new File(fileName);
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String currentLine;
                int lineNumber = 1;

                while ((currentLine = reader.readLine()) != null) {
                    if (lineNumber != lineToRemove) {
                        writer.write(currentLine);
                        writer.newLine();
                    }
                    lineNumber++;
                }

                writer.close();
                reader.close();

                boolean isDeleted = inputFile.delete();
                boolean isRenamed = tempFile.renameTo(inputFile);
            } catch (IOException e) {
                System.out.println("An error occurred while deleting the line from the file.");
                e.printStackTrace();
            }
            updateP(userM, displayP, controlsP);
        };

        JLabel label = new JLabel("Delete account");
        label.setBounds(100, 5, 200, 50);
        label.setFont(new Font("arial", Font.PLAIN, 30));
        delTopLevel.add(label);

        JLabel lineL = new JLabel("Account number");
        lineL.setBounds(10, 50, 200, 50);
        lineL.setFont(new Font("Arial", Font.PLAIN, 25));
        delTopLevel.add(lineL);

        numberF.setBounds(210, 60, 100, 30);
        numberF.setFont(new Font("Arial", Font.PLAIN, 25));
        delTopLevel.add(numberF);

        JButton delB = new JButton("Delete");
        delB.setBounds(10, 100, 363, 35);
        delB.setFont(new Font("Arial", Font.PLAIN, 30));
        delTopLevel.add(delB);

        ActionListener delListener = e -> delFromFile.run();
        delB.addActionListener(delListener);

    }
    public static void editP(String userM, JFrame frame, JPanel displayP, JPanel controlsP) {
        JDialog editTopLevel = new JDialog(frame);
        editTopLevel.setSize(400, 300);
        editTopLevel.setTitle("Edit Account");
        editTopLevel.setVisible(true);
        editTopLevel.setResizable(false);

        JTextField lineF = new JTextField();
        JTextField siteF = new JTextField();
        JTextField userF = new JTextField();
        JTextField passF = new JTextField();

        Runnable load = () -> {
            int lineN = Integer.parseInt(lineF.getText());
            int count = 1;

            String fileName = "src\\files\\"+userM+"\\passwords.txt";

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (lineN == count) {
                        String[] parts1 = line.split(",");
                        String content = Cryption.decryptPSW(userM, parts1[0], parts1[1], parts1[2]);
                        String[] parts2 = content.split(",");
                        siteF.setText(parts2[0]);
                        userF.setText(parts2[1]);
                        passF.setText(parts2[2]);
                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + fileName);
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        Runnable save = () -> {
            int lineN = Integer.parseInt(lineF.getText());

            String fileName = "src\\files\\"+userM+"\\passwords.txt";
            String content;

            try { content = Cryption.encryptPSW(userM, siteF.getText(), userF.getText(), passF.getText()); }
            catch (Exception e) { throw new RuntimeException(e); }

            String[] parts = content.split(",");
            content = parts[0]+","+parts[1]+","+parts[2];

            try {
                File inputFile = new File(fileName);
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String currentLine;
                int lineNumber = 1;

                while ((currentLine = reader.readLine()) != null) {
                    if (lineN == lineNumber) {
                        writer.write(content);
                        writer.newLine();
                    }else {
                        writer.write(currentLine);
                        writer.newLine();
                    }
                    lineNumber++;
                }

                writer.close();
                reader.close();

                boolean isDeleted = inputFile.delete();
                boolean isRenamed = tempFile.renameTo(inputFile);
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + fileName);
                e.printStackTrace();
            }
            updateP(userM, displayP, controlsP);
        };

        JLabel label = new JLabel("Edit Account");
        label.setBounds(100, 5, 220, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        editTopLevel.add(label);

        Font font = new Font("Arial", Font.PLAIN, 25);

        JLabel lineL = new JLabel("Line Number");
        lineL.setBounds(10, 40, 150, 50);
        lineL.setFont(font);
        editTopLevel.add(lineL);

        lineF.setBounds(170, 50, 100, 30);
        lineF.setFont(font);
        editTopLevel.add(lineF);


        JLabel siteL = new JLabel("Website");
        siteL.setBounds(10, 80, 150, 50);
        siteL.setFont(font);
        editTopLevel.add(siteL);

        siteF.setBounds(170, 90, 180, 30);
        siteF.setFont(font);
        editTopLevel.add(siteF);


        JLabel userL = new JLabel("Username");
        userL.setBounds(10, 115, 150, 50);
        userL.setFont(font);
        editTopLevel.add(userL);

        userF.setBounds(170, 125, 180, 30);
        userF.setFont(font);
        editTopLevel.add(userF);


        JLabel passL = new JLabel("Password");
        passL.setBounds(10, 150, 150, 50);
        passL.setFont(font);
        editTopLevel.add(passL);

        passF.setBounds(170, 160, 180, 30);
        passF.setFont(font);
        editTopLevel.add(passF);

        JButton loadB = new JButton("Load");
        loadB.setBounds(10, 200, 175, 50);
        loadB.setFont(new Font("Arial", Font.PLAIN, 30));
        editTopLevel.add(loadB);

        ActionListener loadListener = e -> load.run();
        loadB.addActionListener(loadListener);

        JButton saveB = new JButton("Save");
        saveB.setBounds(195, 200, 175, 50);
        saveB.setFont(new Font("Arial", Font.PLAIN, 30));
        editTopLevel.add(saveB);

        ActionListener saveListener = e -> save.run();
        saveB.addActionListener(saveListener);

    }
    public static void setShowP(String user, JPanel displayP, JButton show, JPanel controlsP) {
        showP = !showP;
        updateP(user, displayP, controlsP);
        if (showP) { show.setText("Hide"); }
        else { show.setText("Show"); }
    }

    public static void password(String user, JPanel controlsP, JPanel displayP, JFrame frame) {
        controlsP.removeAll();
        controlsP.revalidate();
        controlsP.repaint();

        displayP.removeAll();
        displayP.revalidate();
        displayP.repaint();


        JLabel label = new JLabel("Password");
        label.setBounds(375, 10, 250, 35);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        controlsP.add(label);

        JButton addB = new JButton("Add");
        addB.setBounds(10, 10, 95, 50);
        addB.setFont(new Font("Arial", Font.PLAIN, 25));
        controlsP.add(addB);

        ActionListener addPListener = e -> addP(user, frame, displayP, controlsP);
        addB.addActionListener(addPListener);

        JButton delB = new JButton("Delete");
        delB.setBounds(10, 70, 95, 50);
        delB.setFont(new Font("Arial", Font.PLAIN, 19));
        controlsP.add(delB);

        ActionListener delPListener = e -> delP(user, frame, displayP, controlsP);
        delB.addActionListener(delPListener);

        JButton editB = new JButton("Edit");
        editB.setBounds(115, 10, 95, 50);
        editB.setFont(new Font("Arial", Font.PLAIN, 25));
        controlsP.add(editB);

        ActionListener editPListener = e -> editP(user, frame, displayP, controlsP);
        editB.addActionListener(editPListener);

        JButton reloadB = new JButton("Reload");
        reloadB.setBounds(895, 10, 95, 50);
        reloadB.setFont(new Font("Arial", Font.PLAIN, 19));
        controlsP.add(reloadB);

        ActionListener reloadListener = e -> updateP(user, displayP, controlsP);
        reloadB.addActionListener(reloadListener);

        JButton showB = new JButton("Show");
        showB.setBounds(895, 70, 95, 50);
        showB.setFont(new Font("Arial", Font.PLAIN, 19));
        controlsP.add(showB);

        ActionListener showListener = e -> setShowP(user, displayP, showB, controlsP);
        showB.addActionListener(showListener);

        updateP(user, displayP, controlsP);
    }


    public static void updateC(String user, JPanel displayP, JPanel controlsP) {
        displayP.removeAll();
        displayP.revalidate();
        displayP.repaint();

        int count = 1;
        int posY = 0;

        String name;
        String num;
        String date;
        String ccv;

        String fileName = "src\\files\\"+user+"\\cards.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String content = Cryption.decryptCRD(user, parts[0], parts[1], parts[2], parts[3]);
                String[] parts2 = content.split(",");
                name = parts2[0]; num = parts2[1]; date = parts2[2]; ccv = parts2[3];

                JTextField dateF = new JTextField();
                dateF.setFont(new Font("Arial", Font.PLAIN, 25));
                dateF.setBounds(700, posY, 150, 50);
                dateF.setText(date);
                displayP.add(dateF);

                JTextField countF = new JTextField();
                countF.setFont(new Font("Arial", Font.BOLD, 30));
                countF.setHorizontalAlignment(JTextField.CENTER);
                countF.setBounds(0, posY, 40, 50);
                countF.setText(Integer.toString(count));
                displayP.add(countF);

                JTextField nameF = new JTextField();
                nameF.setFont(new Font("Arial", Font.PLAIN, 25));
                nameF.setBounds(40, posY, 320, 50);
                nameF.setText(name);
                displayP.add(nameF);

                if (showC) {
                    JTextField numF = new JTextField();
                    numF.setFont(new Font("Arial", Font.PLAIN, 25));
                    numF.setBounds(360, posY, 340, 50);
                    numF.setText(num);
                    displayP.add(numF);

                    JTextField ccvF = new JTextField();
                    ccvF.setFont(new Font("Arial", Font.PLAIN, 25));
                    ccvF.setBounds(850, posY, 150, 50);
                    ccvF.setText(ccv);
                    displayP.add(ccvF);
                } else{
                    if (num.length() == 19) {
                        JTextField numF = new JTextField();
                        numF.setFont(new Font("Arial", Font.PLAIN, 25));
                        numF.setBounds(360, posY, 340, 50);
                        numF.setText(num.substring(0, num.length()-9) + "●●●● ●●●●");
                        displayP.add(numF);
                    } else if (num.length() == 16) {
                        JTextField numF = new JTextField();
                        numF.setFont(new Font("Arial", Font.PLAIN, 25));
                        numF.setBounds(360, posY, 340, 50);
                        numF.setText(num.substring(0, num.length()-8) + "●●●●●●●●");
                        displayP.add(numF);
                    }

                    JTextField ccvF = new JTextField();
                    ccvF.setFont(new Font("Arial", Font.PLAIN, 25));
                    ccvF.setBounds(850, posY, 150, 50);
                    ccvF.setText(ccv.replaceAll(".", "●"));
                    displayP.add(ccvF);
                }
                count++;
                posY += 50;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + fileName);
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JLabel label = new JLabel("Count: "+(count-1));
        label.setBounds(400, 65, 200, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        controlsP.add(label);

    }
    public static void addC(String userM, JFrame frame, JPanel displayP, JPanel controlsP) {
        JDialog addTopLevel = new JDialog(frame);
        addTopLevel.setSize(400, 350);
        addTopLevel.setTitle("Add Credit/Debit card");
        addTopLevel.setVisible(true);
        addTopLevel.setResizable(false);

        JTextField nameF = new JTextField();
        JTextField numF = new JTextField();
        JTextField dateF = new JTextField();
        JTextField ccvF = new JTextField();

        Runnable addToFile = () -> {
            String name = nameF.getText();
            String num = numF.getText();
            String date = dateF.getText();
            String ccv = ccvF.getText();
            String content;

            try { content = Cryption.encryptCRD(userM, name, num, date, ccv); }
            catch (Exception e) { throw new RuntimeException(e); }

            if (name.equals("") || num.equals("") || date.equals("") || ccv.equals("") ) {
                return;
            } else {
                String fileName = "src\\files\\"+userM+"\\cards.txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                    writer.write(content);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();}
                updateC(userM, displayP, controlsP);
            }
        };

        JLabel label = new JLabel("Add Credit/Debit card");
        label.setBounds(50, 5, 300, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        addTopLevel.add(label);


        JLabel nameL = new JLabel("Name");
        nameL.setBounds(10, 50, 100, 50);
        nameL.setFont(new Font("Arial", Font.PLAIN, 25));
        addTopLevel.add(nameL);

        nameF.setBounds(145, 65, 180, 30);
        nameF.setFont(new Font("arial", Font.PLAIN, 20));
        addTopLevel.add(nameF);


        JLabel numberL = new JLabel("Number");
        numberL.setBounds(10, 90, 120, 50);
        numberL.setFont(new Font("Arial", Font.PLAIN, 25));
        addTopLevel.add(numberL);

        numF.setBounds(145, 105, 180, 30);
        numF.setFont(new Font("arial", Font.PLAIN, 20));
        addTopLevel.add(numF);


        JLabel dateL = new JLabel("Date");
        dateL.setBounds(10, 130, 120, 50);
        dateL.setFont(new Font("Arial", Font.PLAIN, 25));
        addTopLevel.add(dateL);

        dateF.setBounds(145, 145, 180, 30);
        dateF.setFont(new Font("arial", Font.PLAIN, 20));
        addTopLevel.add(dateF);

        JLabel ccvL = new JLabel("CCV");
        ccvL.setBounds(10, 170, 120, 50);
        ccvL.setFont(new Font("Arial", Font.PLAIN, 25));
        addTopLevel.add(ccvL);

        ccvF.setBounds(145, 185, 180, 30);
        ccvF.setFont(new Font("arial", Font.PLAIN, 20));
        addTopLevel.add(ccvF);


        JButton addB = new JButton("Add");
        addB.setBounds(10, 250, 120, 50);
        addB.setFont(new Font("Arial", Font.PLAIN, 30));
        addTopLevel.add(addB);

        ActionListener addBListener = e -> addToFile.run();
        addB.addActionListener(addBListener);

    }
    public static void delC(String userM, JFrame frame, JPanel displayP, JPanel controlsP) {
        JDialog delTopLevel = new JDialog(frame);
        delTopLevel.setSize(400, 200);
        delTopLevel.setTitle("Delete Account");
        delTopLevel.setVisible(true);
        delTopLevel.setResizable(false);

        JTextField numberF = new JTextField();

        Runnable delFromFile = () -> {
            String fileName = "src\\files\\"+userM+"\\cards.txt";
            int lineToRemove = Integer.parseInt(numberF.getText());

            try {
                File inputFile = new File(fileName);
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String currentLine;
                int lineNumber = 1;

                while ((currentLine = reader.readLine()) != null) {
                    if (lineNumber != lineToRemove) {
                        writer.write(currentLine);
                        writer.newLine();
                    }
                    lineNumber++;
                }

                writer.close();
                reader.close();

                boolean isDeleted = inputFile.delete();
                boolean isRenamed = tempFile.renameTo(inputFile);
            } catch (IOException e) {
                System.out.println("An error occurred while deleting the line from the file.");
                e.printStackTrace();
            }
            updateC(userM, displayP, controlsP);
        };

        JLabel label = new JLabel("Delete account");
        label.setBounds(100, 5, 200, 50);
        label.setFont(new Font("arial", Font.PLAIN, 30));
        delTopLevel.add(label);

        JLabel lineL = new JLabel("Account number");
        lineL.setBounds(10, 50, 200, 50);
        lineL.setFont(new Font("Arial", Font.PLAIN, 25));
        delTopLevel.add(lineL);

        numberF.setBounds(210, 60, 100, 30);
        numberF.setFont(new Font("Arial", Font.PLAIN, 25));
        delTopLevel.add(numberF);

        JButton delB = new JButton("Delete");
        delB.setBounds(10, 100, 363, 35);
        delB.setFont(new Font("Arial", Font.PLAIN, 30));
        delTopLevel.add(delB);

        ActionListener delListener = e -> delFromFile.run();
        delB.addActionListener(delListener);

    }
    public static void editC(String userM, JFrame frame, JPanel displayP, JPanel controlsP) {
        JDialog editTopLevel = new JDialog(frame);
        editTopLevel.setSize(400, 350);
        editTopLevel.setTitle("Edit Account");
        editTopLevel.setVisible(true);
        editTopLevel.setResizable(false);

        JTextField lineF = new JTextField();
        JTextField nameF = new JTextField();
        JTextField numF = new JTextField();
        JTextField dateF = new JTextField();
        JTextField ccvF = new JTextField();

        Runnable load = () -> {
            int lineN = Integer.parseInt(lineF.getText());
            int count = 1;

            String fileName = "src\\files\\"+userM+"\\cards.txt";

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (lineN == count) {
                        String[] parts1 = line.split(",");
                        String content = Cryption.decryptCRD(userM, parts1[0], parts1[1], parts1[2], parts1[3]);
                        String[] parts2 = content.split(",");

                        nameF.setText(parts2[0]);
                        numF.setText(parts2[1]);
                        dateF.setText(parts2[2]);
                        ccvF.setText(parts2[3]);
                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + fileName);
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        Runnable save = () -> {
            int lineN = Integer.parseInt(lineF.getText());

            String fileName = "src\\files\\"+userM+"\\cards.txt";
            String content;

            try { content = Cryption.encryptCRD(userM, nameF.getText(), numF.getText(), dateF.getText(), ccvF.getText()); }
            catch (Exception e) { throw new RuntimeException(e); }

            String[] parts = content.split(",");
            content = parts[0]+","+parts[1]+","+parts[2]+","+parts[3];


            try {
                File inputFile = new File(fileName);
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String currentLine;
                int lineNumber = 1;

                while ((currentLine = reader.readLine()) != null) {
                    if (lineN == lineNumber) {
                        writer.write(content);
                        writer.newLine();
                    }else {
                        writer.write(currentLine);
                        writer.newLine();
                    }
                    lineNumber++;
                }

                writer.close();
                reader.close();

                boolean isDeleted = inputFile.delete();
                boolean isRenamed = tempFile.renameTo(inputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateC(userM, displayP, controlsP);
        };

        JLabel label = new JLabel("Edit Card info");
        label.setBounds(100, 5, 220, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        editTopLevel.add(label);

        Font font = new Font("Arial", Font.PLAIN, 25);

        JLabel lineL = new JLabel("Line Number");
        lineL.setBounds(10, 40, 150, 50);
        lineL.setFont(font);
        editTopLevel.add(lineL);

        lineF.setBounds(170, 50, 100, 30);
        lineF.setFont(font);
        editTopLevel.add(lineF);


        JLabel nameL = new JLabel("Name");
        nameL.setBounds(10, 80, 100, 50);
        nameL.setFont(new Font("Arial", Font.PLAIN, 25));
        editTopLevel.add(nameL);

        nameF.setBounds(145, 90, 180, 30);
        nameF.setFont(new Font("arial", Font.PLAIN, 20));
        editTopLevel.add(nameF);


        JLabel numberL = new JLabel("Number");
        numberL.setBounds(10, 115, 120, 50);
        numberL.setFont(new Font("Arial", Font.PLAIN, 25));
        editTopLevel.add(numberL);

        numF.setBounds(145, 125, 180, 30);
        numF.setFont(new Font("arial", Font.PLAIN, 20));
        editTopLevel.add(numF);


        JLabel dateL = new JLabel("Date");
        dateL.setBounds(10, 150, 120, 50);
        dateL.setFont(new Font("Arial", Font.PLAIN, 25));
        editTopLevel.add(dateL);

        dateF.setBounds(145, 160, 180, 30);
        dateF.setFont(new Font("arial", Font.PLAIN, 20));
        editTopLevel.add(dateF);


        JLabel ccvL = new JLabel("CCV");
        ccvL.setBounds(10, 185, 120, 50);
        ccvL.setFont(new Font("Arial", Font.PLAIN, 25));
        editTopLevel.add(ccvL);

        ccvF.setBounds(145, 195, 180, 30);
        ccvF.setFont(new Font("arial", Font.PLAIN, 20));
        editTopLevel.add(ccvF);


        JButton loadB = new JButton("Load");
        loadB.setBounds(10, 250, 175, 50);
        loadB.setFont(new Font("Arial", Font.PLAIN, 30));
        editTopLevel.add(loadB);

        ActionListener loadListener = e -> load.run();
        loadB.addActionListener(loadListener);

        JButton saveB = new JButton("Save");
        saveB.setBounds(195, 250, 175, 50);
        saveB.setFont(new Font("Arial", Font.PLAIN, 30));
        editTopLevel.add(saveB);

        ActionListener saveListener = e -> save.run();
        saveB.addActionListener(saveListener);

    }
    public static void setShowC(String user, JPanel displayP, JButton show, JPanel controlsP){
        showC = !showC;
        updateC(user, displayP, controlsP);
        if (showC) { show.setText("Hide"); }
        else { show.setText("Show"); }
    }

    public static void card(String user, JPanel controlsP, JPanel displayP, JFrame frame) {
        controlsP.removeAll();
        controlsP.revalidate();
        controlsP.repaint();

        displayP.removeAll();
        displayP.revalidate();
        displayP.repaint();


        JLabel label = new JLabel("Card");
        label.setBounds(435, 10, 100, 35);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        controlsP.add(label);

        JButton addB = new JButton("Add");
        addB.setBounds(10, 10, 95, 50);
        addB.setFont(new Font("Arial", Font.PLAIN, 25));
        controlsP.add(addB);

        ActionListener addCListener = e -> addC(user, frame, displayP, controlsP);
        addB.addActionListener(addCListener);

        JButton delB = new JButton("Delete");
        delB.setBounds(10, 70, 95, 50);
        delB.setFont(new Font("Arial", Font.PLAIN, 19));
        controlsP.add(delB);

        ActionListener delCListener = e -> delC(user, frame, displayP, controlsP);
        delB.addActionListener(delCListener);

        JButton editB = new JButton("Edit");
        editB.setBounds(115, 10, 95, 50);
        editB.setFont(new Font("Arial", Font.PLAIN, 25));
        controlsP.add(editB);

        ActionListener editCListener = e -> editC(user, frame, displayP, controlsP);
        editB.addActionListener(editCListener);

        JButton reloadB = new JButton("Reload");
        reloadB.setBounds(895, 10, 95, 50);
        reloadB.setFont(new Font("Arial", Font.PLAIN, 19));
        controlsP.add(reloadB);

        ActionListener reloadListener = e -> updateC(user, displayP, controlsP);
        reloadB.addActionListener(reloadListener);

        JButton showB = new JButton("Show");
        showB.setBounds(895, 70, 95, 50);
        showB.setFont(new Font("Arial", Font.PLAIN, 19));
        controlsP.add(showB);

        ActionListener showListener = e -> setShowC(user, displayP, showB, controlsP);
        showB.addActionListener(showListener);

        updateC(user, displayP, controlsP);
    }


    public static void changePassword(String user, JPanel displayP) {
        displayP.removeAll();
        displayP.revalidate();
        displayP.repaint();

        JTextField pass1F = new JTextField();
        JTextField pass2F = new JTextField();

        Runnable save = () -> {
            String passwordOLD = pass1F.getText();
            String passwordNEW = pass2F.getText();
            String fileName = "src\\files\\logins.txt";

            try {
                File inputFile = new File(fileName);
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(Cryption.encryptMPSW(passwordOLD)+","+user)) { writer.write(Cryption.encryptMPSW(passwordNEW)+","+user); }
                    else { writer.write(line); }
                    writer.newLine();
                }

                writer.close();
                reader.close();

                boolean isDeleted = inputFile.delete();
                boolean isRenamed = tempFile.renameTo(inputFile);

            } catch (IOException e) { e.printStackTrace(); }
        };

        JLabel pass1L = new JLabel("Old password");
        pass1L.setBounds(10, 5, 200, 50);
        pass1L.setFont(new Font("Arial", Font.PLAIN, 30));
        displayP.add(pass1L);

        pass1F.setBounds(215, 15, 210, 35);
        pass1F.setFont(new Font("Arial", Font.PLAIN, 25));
        displayP.add(pass1F);

        JLabel pass2L = new JLabel("New password");
        pass2L.setBounds(10, 45, 200, 50);
        pass2L.setFont(new Font("Arial", Font.PLAIN, 30));
        displayP.add(pass2L);

        pass2F.setBounds(215, 55, 210, 35);
        pass2F.setFont(new Font("Arial", Font.PLAIN, 25));
        displayP.add(pass2F);

        JButton saveB = new JButton("Change password");
        saveB.setBounds(10, 100, 250, 40);
        saveB.setFont(new Font("Arial", Font.PLAIN, 24));
        displayP.add(saveB);

        ActionListener saveListener = e -> save.run();
        saveB.addActionListener(saveListener);
    }
    public static void deleteAccount(String user, JPanel displayP) {
        displayP.removeAll();
        displayP.revalidate();
        displayP.repaint();

        JTextField passF = new JTextField();

        Runnable delete = () -> {
            String fileName = "src\\files\\logins.txt";
            String password = passF.getText();
            password = Cryption.encryptMPSW(password);

            try {
                File inputFile = new File(fileName);
                File tempFile = new File("temp.txt");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(password+","+user)) { continue; }
                    else { writer.write(line); writer.newLine(); }
                }
                writer.close();
                reader.close();

                boolean isDeleted = inputFile.delete();
                boolean isRenamed = tempFile.renameTo(inputFile);

                String folderPath = "src\\files\\"+user;
                File folder = new File(folderPath);
                if (folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    if (files != null) {
                        for (File file : files) { file.delete(); }
                    }
                }
                folder.delete();
            } catch (IOException e) { e.printStackTrace(); }
        };

        JLabel label = new JLabel("Password");
        label.setBounds(10, 5, 230, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        displayP.add(label);

        passF.setBounds(155, 15, 210, 35);
        passF.setFont(new Font("Arial", Font.PLAIN, 25));
        displayP.add(passF);

        JButton delB = new JButton("Delete Account");
        delB.setBounds(10, 70, 250, 40);
        delB.setFont(new Font("Arial", Font.PLAIN, 24));
        displayP.add(delB);

        ActionListener delListener = e -> delete.run();
        delB.addActionListener(delListener);

    }
    public static void twoFA(String user, JPanel displayP) {
        System.out.println("Two factor-authentication");
    }

    public static void idkYet(String user, JPanel displayP) {
        System.out.println("Idk yet");
    }

    public static void settings(String user, JPanel controlsP, JPanel displayP) {
        controlsP.removeAll();
        controlsP.revalidate();
        controlsP.repaint();

        displayP.removeAll();
        displayP.revalidate();
        displayP.repaint();


        JLabel label = new JLabel("Settings");
        label.setBounds(415, 10, 150, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        controlsP.add(label);

        JButton changePswB = new JButton("Change Password");
        changePswB.setBounds(10, 10, 230, 50);
        changePswB.setFont(new Font("Arial", Font.PLAIN, 23));
        controlsP.add(changePswB);

        ActionListener changePswListener = e -> changePassword(user, displayP);
        changePswB.addActionListener(changePswListener);


        JButton deleteAccB = new JButton("Delete Account");
        deleteAccB.setBounds(10, 70, 230, 50);
        deleteAccB.setFont(new Font("Arial", Font.PLAIN, 23));
        controlsP.add(deleteAccB);

        ActionListener deleteAccListener = e -> deleteAccount(user, displayP);
        deleteAccB.addActionListener(deleteAccListener);


        JButton twoFAB = new JButton("2 FA");
        twoFAB.setBounds(760, 10, 230, 50);
        twoFAB.setFont(new Font("Arial", Font.PLAIN, 23));
        controlsP.add(twoFAB);

        ActionListener twoFAListener = e -> twoFA(user, displayP);
        twoFAB.addActionListener(twoFAListener);


        JButton idkYetB = new JButton("IDK YET");
        idkYetB.setBounds(760, 70, 230, 50);
        idkYetB.setFont(new Font("Arial", Font.PLAIN, 23));
        controlsP.add(idkYetB);

        ActionListener idkYetListener = e -> idkYet(user, displayP);
        idkYetB.addActionListener(idkYetListener);
    }
}