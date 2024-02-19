package controllers;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ListInfo {
	
    private static final String AES_Algorithm = "AES/CBC/PKCS5PADDING";
    private static String encryptionKey = "859145623974563257826124";
    
    public static String encrypt(String plainText, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_Algorithm);
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_Algorithm);
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
