package packt.java9.network.connect.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Encryptor {

    public static byte[] encrypt(Key key, byte[] message)
        throws NoSuchPaddingException, NoSuchAlgorithmException,
        InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message);
    }

    public static byte[] decrypt(Key key, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(encrypted);
    }

    public static KeyPair buildKeyPair(String algorithm, final int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    public static void saveKeyPair(String path, KeyPair keyPair) throws IOException {
        PublicKey publicKey = keyPair.getPublic();
        savePublicKey(path + "/public.key", publicKey);

        PrivateKey privateKey = keyPair.getPrivate();
        savePrivateKey(path + "/private.key", privateKey);
    }

    public static void savePrivateKey(String fileName, PrivateKey privateKey) throws IOException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(pkcs8EncodedKeySpec.getEncoded());
        }
    }

    public static void savePublicKey(String fileName, PublicKey publicKey) throws IOException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(x509EncodedKeySpec.getEncoded());
        }
    }

    public static KeyPair loadKeyPair(String path, String algorithm)
        throws IOException, NoSuchAlgorithmException,
        InvalidKeySpecException {
        PublicKey publicKey = loadPublicKey(path + "/public.key", algorithm);
        PrivateKey privateKey = loadPrivateKey(path + "/private.key", algorithm);

        return new KeyPair(publicKey, privateKey);
    }

    public static PrivateKey loadPrivateKey(String fileName, String algorithm)
        throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        final byte[] encodedPrivateKey;
        try (final FileInputStream fis = new FileInputStream(fileName)) {
            encodedPrivateKey = fis.readAllBytes();
            fis.read(encodedPrivateKey);
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        <
    }

    public static PublicKey loadPublicKey(String fileName, String algorithm)
        throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        final byte[] encodedPublicKey;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            encodedPublicKey = fis.readAllBytes();
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
        return keyFactory.generatePublic(publicKeySpec);
    }
}
