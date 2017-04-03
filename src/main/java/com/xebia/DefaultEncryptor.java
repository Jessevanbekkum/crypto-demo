package com.xebia;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.primitives.Bytes;

public class DefaultEncryptor implements Encryptor {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultEncryptor.class);
    public static final String AES_CTR_NOPADDING = "AES/CTR/NOPADDING";
    private final SecretKey key;
    private final KeyPair keyPair;

    private static final String ALGORITHM = "AES";
    private static final String salt = "A long, but constant phrase that will be used each time as the salt.";
    private static final int iterations = 2000;
    private static final int keyLength = 128;
    public static final Base64.Encoder encoder = Base64.getEncoder();

    public static final Base64.Decoder decoder = Base64.getDecoder();
    private static final SecureRandom random = new SecureRandom();

    private DefaultEncryptor(SecretKey key, KeyPair keyPair) {
        this.key = key;
        this.keyPair = keyPair;
    }

    public static DefaultEncryptor jce(String secKey) {
        Security.removeProvider("BC");
        return startEncryptor(secKey);
    }

    private static DefaultEncryptor startEncryptor(final String secKey) {
        try {
            SecretKey tmpKey = generateKey(secKey);
            KeyPair tmpKeyPair = generateKeyPair();
            return new DefaultEncryptor(tmpKey, tmpKeyPair);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DefaultEncryptor bouncy(String secKey) {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        return startEncryptor(secKey);
    }

    private static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(final String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, generateIV(cipher), random);
            byte[] params = cipher.getParameters().getEncoded();
            byte[] cipherbytes = cipher.doFinal(plainText.getBytes());
            return encoder.encodeToString(Bytes.concat(params, cipherbytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(final String cipherText) {
        try {
            byte[] decode = decoder.decode(cipherText);
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(ALGORITHM);
            algorithmParameters.init(Arrays.copyOf(decode, 18));

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
            byte[] bytes = cipher.doFinal(Arrays.copyOfRange(decode, 18, decode.length));
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encryptAsym(final String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

            byte[] bytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String decryptAsym(final String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] bytes = cipher.doFinal(decoder.decode(plainText));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException();

        }
    }

    private static SecretKey generateKey(String passphrase) throws Exception {
        /* Derive the key, given password and salt. */
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        return secret;
    }

    private static IvParameterSpec generateIV(Cipher cipher) throws Exception {
        byte[] ivBytes = new byte[cipher.getBlockSize()];
        random.nextBytes(ivBytes);
        return new IvParameterSpec(ivBytes);
    }

    @Override
    public SealedObject seal(Serializable o) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/NOPADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            SealedObject sealedObject = new SealedObject(o, cipher);
            return sealedObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object unseal(SealedObject so) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CTR/NOPADDING");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return so.getObject(cipher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
