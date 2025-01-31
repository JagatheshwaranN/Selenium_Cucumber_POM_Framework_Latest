package com.qa.ctf.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.qa.ctf.constant.TestConstants.*;

/**
 * The EncryptionManager class provides utility methods for encrypting and decrypting data
 * using the AES encryption algorithm with a secret key. This class allows for secure
 * handling of sensitive information through encryption and decryption operations.
 *
 * <p>Features:
 * <ul>
 *   <li>Encryption: Method to encrypt data using the AES algorithm and a secret key.</li>
 *   <li>Decryption: Method to decrypt encrypted data back to its original form.</li>
 *   <li>Key Management: Secret key generation and validation for encryption and decryption
 *   processes.</li>
 * </ul>
 *
 * <p>Exception Handling:
 * <ul>
 *   <li>Custom exceptions from the {@link ExceptionHub} class are thrown to handle invalid
 *      data or encryption/decryption failures.</li>
 *   <li>Detailed logging is provided for both successful encryption/decryption operations
 *      and error scenarios.</li>
 * </ul>
 *
 * <p>Note:
 * This class assumes that the secret key and algorithm used for encryption are valid and
 * securely handled.
 * The AES algorithm with key lengths of 16, 24, or 32 bytes is supported for encryption and
 * decryption.
 *
 * <p>Example:
 * <pre>
 * {@code
 * EncryptionManager encryptionManager = new EncryptionManager();
 * String encryptedData = encryptionManager.encryptData("Sensitive Information");
 * String decryptedData = encryptionManager.decryptData(encryptedData);
 * }
 * </pre>
 *
 * @author Jagatheshwaran N
 * @version 1.0
 */
public class EncryptionManager {

    // Logger instance for the EncryptionManager class to enable logging during the execution
    private static final Logger log = LogManager.getLogger(EncryptionManager.class);

    // Instance of secretKey to store the key used for encryption and decryption
    private final String secretKey;

    // Instance of algorithm to specify the encryption algorithm used, defaulting to "AES"
    private final String algorithm;

    /**
     * Constructs an EncryptionManager instance and initializes it with the default
     * secret key and encryption algorithm.
     * <p>
     * This constructor sets up the necessary dependencies, including the secretKey
     * for encryption/decryption and the algorithm used for the encryption process.
     * The secret key and algorithm are obtained from the constants SECRET_KEY and
     * ALGORITHM, ensuring secure handling of data encryption and decryption.
     * </p>
     *
     */
    public EncryptionManager() {
        this.secretKey = SECRET_KEY;
        this.algorithm = ALGORITHM;
    }

    /**
     * Encrypts the provided data using the specified algorithm and secret key.
     * <p>
     * This method checks if the data is valid (non-null and non-empty), then encrypts
     * it using the AES algorithm and the secret key. The encrypted data is encoded in
     * Base64 for easy storage or transmission.
     * </p>
     *
     * @param data The data to be encrypted. It must not be null or empty.
     * @return The encrypted data in Base64-encoded format.
     * @throws ExceptionHub.InvalidDataException If the provided data is null or empty.
     * @throws ExceptionHub.EncryptionException If an error occurs during the encryption
     *                                          process.
     */
    public String encryptData(String data) {
        if (data == null || data.isEmpty()) {
            log.error("Data - '{}' cannot be null or empty.", data);
            throw new ExceptionHub.InvalidDataException(data);
        }
        SecretKey key = generateSecretKey(secretKey);
        Cipher cipher;
        byte[] encryptedBytes;
        try {
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encryptedBytes = cipher.doFinal(data.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException ex) {
            log.error("Error occurred during the encryption of data");
            throw new ExceptionHub.EncryptionException("Error during encryption", ex);
        }
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the provided encrypted data using the specified algorithm and secret key.
     * <p>
     * This method checks if the encrypted data is valid (non-null and non-empty), then
     * decrypts it using the AES algorithm and the secret key. The decrypted data is
     * returned as a string.
     * </p>
     *
     * @param encryptedData The encrypted data in Base64-encoded format. It must not be null
     *                      or empty.
     * @return The decrypted data as a string.
     * @throws ExceptionHub.InvalidDataException If the provided encrypted data is null or empty.
     * @throws ExceptionHub.EncryptionException If an error occurs during the decryption process.
     */
    public String decryptData(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            log.error("Data - '{}' cannot be null or empty.", encryptedData);
            throw new ExceptionHub.InvalidDataException(encryptedData);
        }
        SecretKey key = generateSecretKey(secretKey);
        Cipher cipher;
        byte[] decodedBytes;
        byte[] decryptedBytes;
        try {
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decodedBytes = Base64.getDecoder().decode(encryptedData);
            decryptedBytes = cipher.doFinal(decodedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException ex) {
            log.error("Error occurred during the decryption of data");
            throw new ExceptionHub.EncryptionException("Error during decryption", ex);
        }
        return new String(decryptedBytes);
    }

    /**
     * Generates a SecretKey instance from the provided key string.
     * <p>
     * This method validates the length of the provided key to ensure it is either
     * 16, 24, or 32 bytes (the valid key lengths for AES). If the key length is invalid,
     * an exception is thrown. The method then creates a SecretKeySpec instance using the
     * provided key and the specified algorithm.
     * </p>
     *
     * @param key The key string used to generate the SecretKey. It must be 16, 24, or 32
     *            bytes in length.
     * @return The generated SecretKey instance.
     * @throws ExceptionHub.InvalidDataException If the provided key length is invalid.
     */
    private SecretKey generateSecretKey(String key) {
        if (key.length() != 16 && key.length() != 24 && key.length() != 32) {
            log.error("Invalid key length. AES supports 16, 24, or 32 bytes.");
            throw new ExceptionHub.InvalidDataException(key);
        }
        byte[] keyBytes = key.getBytes();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm);
    }

}
