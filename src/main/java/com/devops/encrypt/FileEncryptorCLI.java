package com.devops.encrypt;

import java.io.*;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.CipherOutputStream;
import java.util.Scanner;

public class FileEncryptorCLI {

    private static final String HOME_DIRECTORY = System.getProperty("user.home");
    private static Scanner scanner = new Scanner(System.in);
    private static String selectedFilePath = "";

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("decrypt")) {
            // For test automation
            decryptFromArgs(args);
            return;
        }

        System.out.println("Welcome to the File Encryptor/Decryptor CLI");
        while (true) {
            listFilesAndDirectories(HOME_DIRECTORY);
            System.out.println("\nEnter a directory or file path (or 'exit' to quit): ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equals("exit")) {
                System.out.println("Exiting...");
                break;
            } else if (new File(userInput).isDirectory()) {
                listFilesAndDirectories(userInput);
            } else if (new File(userInput).isFile()) {
                selectedFilePath = userInput;
                System.out.println("You selected file: " + selectedFilePath);
                promptAction();
            } else {
                System.out.println("Invalid path! Please try again.");
            }
        }
    }

    private static void decryptFromArgs(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid args. Usage: decrypt <file> <password>");
            return;
        }
        String file = args[1];
        String password = args[2];
        try {
            decryptFile(file, password);
            System.out.println("Decryption done!");
        } catch (Exception e) {
            System.out.println("Error decrypting: " + e.getMessage());
        }
    }

    private static void listFilesAndDirectories(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            System.out.println("\nFiles and directories in " + path + ":");
            for (File file : files) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }

    private static void promptAction() {
        System.out.println("\nDo you want to:");
        System.out.println("1. Encrypt the file");
        System.out.println("2. Decrypt the file");
        System.out.print("Enter your choice (1/2): ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter a password for encryption: ");
                String encryptionPassword = scanner.nextLine();
                try {
                    encryptFile(selectedFilePath, encryptionPassword);
                    System.out.println("File encrypted successfully!");
                } catch (Exception e) {
                    System.out.println("Error encrypting file: " + e.getMessage());
                }
                break;
            case "2":
                System.out.print("Enter a password for decryption: ");
                String decryptionPassword = scanner.nextLine();
                try {
                    decryptFile(selectedFilePath, decryptionPassword);
                    System.out.println("File decrypted successfully!");
                } catch (Exception e) {
                    System.out.println("Error decrypting file: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice! Please try again.");
        }
    }

    public static void encryptFile(String inputFile, String password) throws Exception {
        Key key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        File input = new File(inputFile);
        File output = new File(inputFile + ".enc");

        try (FileInputStream fileInputStream = new FileInputStream(input);
             FileOutputStream fileOutputStream = new FileOutputStream(output);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void decryptFile(String inputFile, String password) throws Exception {
        Key key = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        File input = new File(inputFile);
        File output = new File(inputFile.replace(".enc", ""));

        try (FileInputStream fileInputStream = new FileInputStream(input);
             FileOutputStream fileOutputStream = new FileOutputStream(output);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("Decrypted file created at: " + output.getAbsolutePath());
    }
}
