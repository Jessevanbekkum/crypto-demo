package com.xebia;

import java.io.Serializable;
import javax.crypto.SealedObject;
import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;

public class KeyCzarEncryptor implements Encryptor {

    private final Crypter symCrypter;
    private final Crypter asymCrypter;

    KeyCzarEncryptor() {
        try {
            symCrypter = new Crypter(this.getClass().getResource("/keyczarkeys/sym").getFile());
            asymCrypter = new Crypter(this.getClass().getResource("/keyczarkeys/asym").getFile());
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(final String plainText) {
        try {
            return symCrypter.encrypt(plainText);
        } catch (KeyczarException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

    @Override
    public SealedObject seal(final Serializable o) {
        // TODO Implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Object unseal(final SealedObject so) {
        // TODO Implement
        throw new UnsupportedOperationException();
    }
}
