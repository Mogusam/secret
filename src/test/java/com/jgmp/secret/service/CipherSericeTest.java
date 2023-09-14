package com.jgmp.secret.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CipherSericeTest {
    @Test
    void generateSecretKey() throws Exception {
        var key = CipherSerice.generateSecretKey();
        var text = """
                If you just want to involve Mockito and don't have to involve Spring, 
                for example, when you just want to use the @Mock / @InjectMocks annotations, 
                then you want to use @ExtendWith(MockitoExtension.class), as it doesn't 
                load in a bunch of unneeded Spring stuff. It replaces the deprecated 
                JUnit4 @RunWith(MockitoJUnitRunner.class).
                               
                               
                """;
        var secretText = CipherSerice.encryptText(text, key);
        System.out.println(secretText);
        var decRiptedText = CipherSerice.decryptText(secretText, key);
        System.out.println("============ decripted below =-=-=-=-=-=-");
        System.out.println(decRiptedText);
        System.out.println(String.valueOf((SecretKeySpec) key));
        assertEquals(decRiptedText, text);
    }


    @Test
    void reGenerateSecretKey() throws Exception {
        var key = CipherSerice.generateSecretKey();
        var secretKeyBytes = key.getEncoded();


        // Encode the byte array to a Base64 string
        String encodedKey = Base64.getEncoder().encodeToString(secretKeyBytes);
        System.out.println("encoded key = " + encodedKey);

        byte[] decodedKeyBytes = Base64.getDecoder().decode(encodedKey);

        SecretKey newSecretKey = new SecretKeySpec(decodedKeyBytes, "AES");


        assertEquals(newSecretKey, key);
    }


}