package com.xebia;

import org.junit.Test;
import org.keyczar.exceptions.KeyczarException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class KeyCzarEncryptorTest {
    KeyCzarEncryptor keyCzarEncryptor = new KeyCzarEncryptor();

    public KeyCzarEncryptorTest() throws KeyczarException {
    }

    @Test
    public void shouldEncrypt() {
        System.out.println(keyCzarEncryptor.encrypt("Secret"));
        assertThat(keyCzarEncryptor.encrypt("Secret"), not(is("secret")));
    }


    @Test
    public void shouldDecrypt() {
        String crypted = keyCzarEncryptor.encrypt("Secret");
        String decrypt = keyCzarEncryptor.decrypt(crypted);
        assertThat(decrypt, is("Secret"));
    }


    @Test
    public void shouldEncryptAsym() {
        String cipherText = keyCzarEncryptor.encryptAsym("Secret");
        System.out.println(cipherText);
        assertThat(cipherText, not(is("secret")));
    }

    @Test
    public void shouldDecryptAsym() {
        String crypted = keyCzarEncryptor.encryptAsym("Secret");
        String decrypt = keyCzarEncryptor.decryptAsym(crypted);
        assertThat(decrypt, is("Secret"));
    }
}
