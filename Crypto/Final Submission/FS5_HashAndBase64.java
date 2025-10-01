import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;

public class FS5_HashAndBase64 {
    private static String hashHex(String input, String algo) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algo);
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter text: ");
        String text = sc.nextLine();

        String md5 = hashHex(text, "MD5");
        String sha256 = hashHex(text, "SHA-256");
        String b64enc = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        String b64dec = new String(Base64.getDecoder().decode(b64enc), StandardCharsets.UTF_8);

        System.out.println("MD5  : " + md5);
        System.out.println("SHA-256: " + sha256);
        System.out.println("Base64 Enc: " + b64enc);
        System.out.println("Base64 Dec: " + b64dec);
    }
}


