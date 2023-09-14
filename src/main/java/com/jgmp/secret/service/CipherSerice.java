package com.jgmp.secret.service;


import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherSerice {
    private CipherSerice() {
    }


    private static final String TRANSFORMATION = "AES";

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(TRANSFORMATION);
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(256, secureRandom);
        return keyGenerator.generateKey();
    }

    public static String encryptText(String plaintext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }


    public static String decryptText(String ciphertext, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }


    public static String encodeKey(SecretKey secretKey) {
        return String.valueOf(Base64.getUrlEncoder().encodeToString(secretKey.getEncoded()));
    }

    public static SecretKey decodeToKey(String base64Key) {
        var decodedKeyByte = Base64.getUrlDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKeyByte, TRANSFORMATION);
    }

}
