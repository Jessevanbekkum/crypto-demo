package com.xebia;

import java.io.Serializable;
import javax.crypto.SealedObject;

public interface Encryptor {

    String encrypt(String plainText);

    String decrypt(String cipherText);

    String encryptAsym(String plainText);

    String decryptAsym(String plainText);

    SealedObject seal(Serializable o);

    Object unseal(SealedObject so);
}
