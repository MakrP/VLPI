package lpnu.vlpi.avpz.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptionHelper {
    @Value("${password.encryprion.algorithm}")
    private String algorithm;

    public String encrypt(String source) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(messageDigest.digest(source.getBytes()));
    }
}
