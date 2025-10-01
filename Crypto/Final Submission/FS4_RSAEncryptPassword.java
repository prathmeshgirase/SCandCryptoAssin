import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;

public class FS4_RSAEncryptPassword {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pub = kp.getPublic();
        PrivateKey prv = kp.getPrivate();

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, pub);
        byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        String encB64 = Base64.getEncoder().encodeToString(encrypted);

        cipher.init(Cipher.DECRYPT_MODE, prv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encB64));
        String dec = new String(decrypted, StandardCharsets.UTF_8);

        System.out.println("Password - " + password);
        System.out.println("Encrypted password - " + encB64);
        System.out.println("Decrypted password - " + dec);
    }
}


