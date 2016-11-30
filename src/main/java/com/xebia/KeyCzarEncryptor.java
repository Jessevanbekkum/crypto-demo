package com.xebia;

import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;

public class KeyCzarEncryptor implements Encryptor {

    private final Crypter symCrypter;
    private final Crypter asymCrypter;

    public KeyCzarEncryptor() throws KeyczarException {
        symCrypter = new Crypter(this.getClass().getResource("/keyczarkeys/sym").getFile());
        asymCrypter = new Crypter(this.getClass().getResource("/keyczarkeys/asym").getFile());
    }

    public String encrypt(final String plainText) {
        try {
            return symCrypter.encrypt(plainText);
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(final String ciphertext) {
        try {
            return symCrypter.decrypt(ciphertext);
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encryptAsym(final String plainText) {
        try {
            return asymCrypter.encrypt(plainText);
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decryptAsym(final String cipherText) {
        try {
            return asymCrypter.decrypt(cipherText);
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }
}
