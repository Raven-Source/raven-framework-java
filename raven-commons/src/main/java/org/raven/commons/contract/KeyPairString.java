package org.raven.commons.contract;

import lombok.Data;

/**
 * @author yanfeng
 * date 2021.06.16
 */
@Data
public class KeyPairString {

    private String publicKey;
    private String privateKey;

    public KeyPairString(String publicKeyString, String privateKeyString) {

        this.publicKey = publicKeyString;
        this.privateKey = privateKeyString;
    }

    public static KeyPairString of(String publicKeyString, String privateKeyString) {
        return new KeyPairString(publicKeyString, privateKeyString);
    }
}
