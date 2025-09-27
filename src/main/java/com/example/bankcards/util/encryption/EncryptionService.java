package com.example.bankcards.util.encryption;

import com.example.bankcards.exception.EncryptionException;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionService {

    @Value("${encoder.algoritm}")
    private String algorithm;

    @Value("${encoder.secret}")
    private  String secretKey;

    private SecretKeySpec keySpec;

    @PostConstruct
    public void init() {
        keySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
    }

    public String encrypt(String raw) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(raw.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new EncryptionException(String.format("Ошибка шифрования %s",raw));
        }
    }

    public String decrypt(String encryptedLine) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = Base64.getDecoder().decode(encryptedLine);
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted);
        } catch (Exception e) {
            throw new EncryptionException(String.format("Ошибка дешифрования %s",encryptedLine));
        }
    }
}
