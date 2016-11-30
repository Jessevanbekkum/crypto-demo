package com.xebia;

import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;

public class KeyCzarEncryptor implements Encryptor {

    private final Crypter crypter;

    public KeyCzarEncryptor() throws KeyczarException {
        crypter = new Crypter("/Users/jvanbekkum/programming/crypto-demo/keys");
    }

    public String encrypt(final String plainText) {
        try {
            return crypter.encrypt(plainText);
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(final String ciphertext) {
        try {
            return crypter.decrypt(ciphertext);
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }
}
