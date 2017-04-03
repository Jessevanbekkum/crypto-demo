package com.xebia;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BCEncryptorTest {

    private Encryptor encryptor = DefaultEncryptor.bouncy("Joshua");

    @Test
    public void testSymmetric() {
        String cyphertext = encryptor.encrypt("abcdef");
        String plaintext = encryptor.decrypt(cyphertext);
        assertEquals("abcdef", plaintext);
    }

    @Test
    public void testAsymmetric() {
        String cyphertext = encryptor.encryptAsym("abcdef");
        String plaintext = encryptor.decryptAsym(cyphertext);
        assertEquals("abcdef", plaintext);
    }
}
