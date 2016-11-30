package com.xebia;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class SodiumEncryptorTest {
    @Test
    public void shouldEncryptSymmetric() {
        SodiumEncryptor sodiumEncryptor = new SodiumEncryptor();
        String cipherText = sodiumEncryptor.encrypt("Super secret message");

        assertThat(cipherText, is(not("super secret message")));
    }

    @Test
    public void shouldDecryptSymmetric() {
        SodiumEncryptor sodiumEncryptor = new SodiumEncryptor();
        String cipherText = sodiumEncryptor.encrypt("Super secret message");
        sodiumEncryptor.decrypt(cipherText);
        assertThat(cipherText, is("super secret message"));
    }
}
