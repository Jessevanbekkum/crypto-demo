package com.xebia;

import java.util.Arrays;
import java.util.Base64;
import org.abstractj.kalium.crypto.Random;
import org.abstractj.kalium.crypto.SecretBox;

public class SodiumEncryptor implements Encryptor {
    public static final int NONCE_SIZE = 24;
    private final Random random = new Random();
    private Base64.Encoder encoder = Base64.getEncoder();
    private byte[] key = random.randomBytes();
    private SecretBox box = new SecretBox(key);

    SodiumEncryptor() {

    }

    public String encrypt(final String plainText) {

        byte[] nonce = random.randomBytes(NONCE_SIZE);
        byte[] result = box.encrypt(nonce, plainText.getBytes());
        byte[] total = concat(nonce, result);
        return encoder.encodeToString(total);
    }

    public String decrypt(final String cipherText) {

        byte[] result = box.decrypt(random.randomBytes(24), cipherText.getBytes());
        return encoder.encodeToString(result);
    }

    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
