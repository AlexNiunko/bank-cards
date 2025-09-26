package com.example.bankcards;

import com.example.bankcards.exception.EncryptionException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    public static void main(String[] args) {
//        var encoder = new BCryptPasswordEncoder();
//        String pass1 = "123";
//        String pass2 = "456";
//        String pass3 = "789";
//        System.out.println(encoder.encode(pass1));
//        System.out.println(encoder.encode(pass2));
//        System.out.println(encoder.encode(pass3));

        String one = "1234567891012134";
        String two = "1111222234567889";
        String three = "9999888877771111";
        String four = "8587455621543256";
        String five = "3298456521238789";
        EncryptionService encryptionService = new EncryptionService();
        System.out.println(encryptionService.encrypt(one));
        System.out.println(encryptionService.encrypt(two));
        System.out.println(encryptionService.encrypt(three));
        System.out.println(encryptionService.encrypt(four));
        System.out.println(encryptionService.encrypt(five));

    }

//    ('VISA', '2026-11-29T14:00:00', 'EUR', '1234567891012134', 2,100.0,'ACTIVE'),
//        ('MASTERCARD', '2026-09-15T16:00:00', 'USD', '1111222234567889', 2,200.0,'BLOCKED'),
//        ('MASTERCARD', '2026-12-27T17:00:00', 'RUB', '9999888877771111', 2,150.0,'ACTIVE'),
//        ('VISA', '2027-10-17T12:00:00', 'USD', '8587455621543256', 3,50.0,'BLOCKED'),
//        ('MIR', '2027-10-17T18:00:00', 'RUB', '3298456521238789', 3,50.0,'ACTIVE');


    static class EncryptionService {

        EncryptionService() {

        }

        private String algorithm = "AES";

        private String secretKey = "QMULq8kR3pVbV0w9q8e5j7nC2vF4hXyD";

        private SecretKeySpec keySpec;


        {
            keySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
        }

        public String encrypt(String raw) {
            try {
                Cipher cipher = Cipher.getInstance(algorithm);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                byte[] encrypted = cipher.doFinal(raw.getBytes());
                return Base64.getEncoder().encodeToString(encrypted);
            } catch (Exception e) {
               throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            }
        }
    }

}
