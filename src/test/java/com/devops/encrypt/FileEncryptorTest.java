package com.devops.encrypt;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileEncryptorTest {

    private final String testFilePath = "/home/hamza/eclipse-workspace/encrypt/testfile.txt";
    private final String password = "1234567812345678"; // 16 bytes

    @Before
    public void setUp() throws Exception {
        // Create a test file
        FileWriter writer = new FileWriter(testFilePath);
        writer.write("This is a test file.");
        writer.close();

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

        Thread.sleep(500);
        assertTrue("Decrypted file should exist", decryptedFile.exists());
    }

    @After
    public void tearDown() {
        new File(testFilePath).delete();
        new File(testFilePath + ".enc").delete();
    }
}
