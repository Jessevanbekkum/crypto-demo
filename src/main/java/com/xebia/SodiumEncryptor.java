package com.xebia;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SealedObject;
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

    @Override
    public String encrypt(final String plainText) {

        byte[] nonce = random.randomBytes(NONCE_SIZE);
        byte[] result = box.encrypt(nonce, plainText.getBytes());
        byte[] total = concat(nonce, result);
        return encoder.encodeToString(total);
    }

    @Override
    public String decrypt(final String cipherText) {

        byte[] result = box.decrypt(random.randomBytes(24), cipherText.getBytes());
        return encoder.encodeToString(result);
    }

    @Override
    public String encryptAsym(final String plainText) {
        // TODO Implement
        throw new UnsupportedOperationException();
    }

    @Override
    public String decryptAsym(final String plainText) {
        // TODO Implement
        throw new UnsupportedOperationException();
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

    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
