import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.security.*;
import java.util.Base64;

import java.nio.charset.StandardCharsets;

public class Cryption {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_MODE = "AES/CBC/PKCS5Padding";

    public static void makeKey(String user) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[16];
        secureRandom.nextBytes(keyBytes);

        String fileName = "src\\files\\"+user+"\\config\\key.key";
        String content = Base64.getEncoder().encodeToString(keyBytes);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getKey(String user) {
        String fileName = "src\\files\\"+user+"\\config\\key.key";
        String key = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                key = line;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + fileName);
            e.printStackTrace();
        }
        return key;
    }

    public static String encryptPSW(String userM, String site, String user, String pass)  throws Exception {
        String key = getKey(userM);
        String siteE; String userE; String passE;

        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes1 = cipher.doFinal(site.getBytes(StandardCharsets.UTF_8));
        siteE = Base64.getEncoder().encodeToString(encryptedBytes1);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes2 = cipher.doFinal(user.getBytes(StandardCharsets.UTF_8));
        userE = Base64.getEncoder().encodeToString(encryptedBytes2);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes3 = cipher.doFinal(pass.getBytes(StandardCharsets.UTF_8));
        passE = Base64.getEncoder().encodeToString(encryptedBytes3);

        return siteE+","+userE+","+passE;

    }
    public static String decryptPSW(String userM, String site, String user, String pass ) throws Exception {
        String key = getKey(userM);
        String siteD; String userD; String passD;

        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes1 = Base64.getDecoder().decode(site);
        byte[] decryptedBytes1 = cipher.doFinal(encryptedBytes1);
        siteD = new String(decryptedBytes1, StandardCharsets.UTF_8);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes2 = Base64.getDecoder().decode(user);
        byte[] decryptedBytes2 = cipher.doFinal(encryptedBytes2);
        userD = new String(decryptedBytes2, StandardCharsets.UTF_8);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes3 = Base64.getDecoder().decode(pass);
        byte[] decryptedBytes3 = cipher.doFinal(encryptedBytes3);
        passD = new String(decryptedBytes3, StandardCharsets.UTF_8);

        return siteD+","+userD+","+passD;
    }

    public static String encryptCRD(String userM, String name, String num, String date, String ccv) throws Exception {
        String key = getKey(userM);
        String nameE; String numE; String dateE; String ccvE;

        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes1 = cipher.doFinal(name.getBytes(StandardCharsets.UTF_8));
        nameE = Base64.getEncoder().encodeToString(encryptedBytes1);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes2 = cipher.doFinal(num.getBytes(StandardCharsets.UTF_8));
        numE = Base64.getEncoder().encodeToString(encryptedBytes2);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes3 = cipher.doFinal(date.getBytes(StandardCharsets.UTF_8));
        dateE = Base64.getEncoder().encodeToString(encryptedBytes3);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes4 = cipher.doFinal(ccv.getBytes(StandardCharsets.UTF_8));
        ccvE = Base64.getEncoder().encodeToString(encryptedBytes4);

        return nameE+","+numE+","+dateE+","+ccvE;
    }
    public static String decryptCRD(String userM, String name, String num, String date, String ccv) throws Exception {
        String key = getKey(userM);
        String nameE; String numE; String dateE; String ccvE;

        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes1 = Base64.getDecoder().decode(name);
        byte[] decryptedBytes1 = cipher.doFinal(encryptedBytes1);
        nameE = new String(decryptedBytes1, StandardCharsets.UTF_8);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes2 = Base64.getDecoder().decode(num);
        byte[] decryptedBytes2 = cipher.doFinal(encryptedBytes2);
        numE = new String(decryptedBytes2, StandardCharsets.UTF_8);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes3 = Base64.getDecoder().decode(date);
        byte[] decryptedBytes3 = cipher.doFinal(encryptedBytes3);
        dateE = new String(decryptedBytes3, StandardCharsets.UTF_8);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes4 = Base64.getDecoder().decode(ccv);
        byte[] decryptedBytes4 = cipher.doFinal(encryptedBytes4);
        ccvE = new String(decryptedBytes4, StandardCharsets.UTF_8);

        return nameE+","+numE+","+dateE+","+ccvE;
    }

    public static String encryptNote(String userM, String text) throws Exception {
        String key = getKey(userM);
        String textE;

        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes1 = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        textE = Base64.getEncoder().encodeToString(encryptedBytes1);

        return  textE;
    }
    public static String decryptNote(String userM, String text) throws Exception {
        String key = getKey(userM);
        String textD;

        Cipher cipher = Cipher.getInstance(CIPHER_MODE);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encryptedBytes1 = Base64.getDecoder().decode(text);
        byte[] decryptedBytes1 = cipher.doFinal(encryptedBytes1);
        textD = new String(decryptedBytes1, StandardCharsets.UTF_8);

        return textD;
    }

    public static String encryptMPSW(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();}
        return password;
    }
}
