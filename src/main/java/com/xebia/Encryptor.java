package com.xebia;

public interface Encryptor {

    String encrypt(String plainText);

    String decrypt(String cipherText);

    String encryptAsync(String plainText);

    String decryptAsync(String plainText);
}
