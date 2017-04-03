package com.xebia;

import java.io.Serializable;
import javax.crypto.SealedObject;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

public class JasyptEncryptor implements Encryptor {
    private StrongTextEncryptor textEncryptor = new StrongTextEncryptor();


    public JasyptEncryptor(String password) {
        textEncryptor.setPassword(password);
    }

    @Override
    public String encrypt(final String plainText) {
        return textEncryptor.encrypt(plainText);
    }

    @Override
    public String decrypt(final String cipherText) {
        return textEncryptor.decrypt(cipherText);
    }

    @Override
    public String encryptAsym(final String plainText) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String decryptAsym(final String plainText) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SealedObject seal(final Serializable o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object unseal(final SealedObject so) {
        throw new UnsupportedOperationException();
    }
}
