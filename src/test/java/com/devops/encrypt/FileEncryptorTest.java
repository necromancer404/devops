package com.devops.encrypt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileEncryptorTest {

    // Use project-relative path for portability
    private final String testFilePath = "testfile.txt";
    private final String password = "1234567812345678"; // 16 bytes

    @Before
    public void setUp() throws Exception {
        // Create a test file with some content
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("This is a test file.");
        }

        // Encrypt the file before tests
        FileEncryptorCLI.encryptFile(testFilePath, password);
    }

    @Test
    public void testEncryptFile() {
        File encryptedFile = new File(testFilePath + ".enc");
        assertTrue("Encrypted file should exist", encryptedFile.exists());
    }

    @Test
    public void testDecryptFile() throws Exception {
        String[] args = {"decrypt", testFilePath + ".enc", password};
        FileEncryptorCLI.main(args);

        File decryptedFile = new File(testFilePath);
        System.out.println("Checking file: " + decryptedFile.getAbsolutePath() + " - exists: " + decryptedFile.exists());

        // Wait briefly if needed for file system sync in Docker
        Thread.sleep(200);

        assertTrue("Decrypted file should exist", decryptedFile.exists());
    }

    @After
    public void tearDown() {
        File original = new File(testFilePath);
        if (original.exists()) {
            original.delete();
        }

        File encrypted = new File(testFilePath + ".enc");
        if (encrypted.exists()) {
            encrypted.delete();
        }
    }
}
