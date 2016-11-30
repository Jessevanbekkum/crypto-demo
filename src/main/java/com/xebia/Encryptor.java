package com.xebia;

public interface Encryptor {

    String encrypt(String plainText);

    String decrypt(String cipherText);

    String encryptAsym(String plainText);

    String decryptAsym(String plainText);
}
