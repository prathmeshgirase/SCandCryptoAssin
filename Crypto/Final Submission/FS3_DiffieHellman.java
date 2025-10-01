import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class FS3_DiffieHellman {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Diffie-Hellman Key Exchange Demo");
        System.out.print("Enter prime modulus p (blank for random 512-bit prime): ");
        String pIn = sc.nextLine().trim();
        BigInteger p;
        if (pIn.isEmpty()) {
            p = BigInteger.probablePrime(512, new SecureRandom());
        } else {
            p = new BigInteger(pIn);
        }
        System.out.print("Enter generator g (blank for 2): ");
        String gIn = sc.nextLine().trim();
        BigInteger g = gIn.isEmpty() ? BigInteger.valueOf(2) : new BigInteger(gIn);

        SecureRandom rnd = new SecureRandom();
        BigInteger a = new BigInteger(p.bitLength() - 2, rnd).mod(p.subtract(BigInteger.TWO)).add(BigInteger.TWO);
        BigInteger b = new BigInteger(p.bitLength() - 2, rnd).mod(p.subtract(BigInteger.TWO)).add(BigInteger.TWO);

        BigInteger A = g.modPow(a, p);
        BigInteger B = g.modPow(b, p);

        BigInteger s1 = B.modPow(a, p);
        BigInteger s2 = A.modPow(b, p);

        System.out.println("Public parameters:");
        System.out.println("p = " + p);
        System.out.println("g = " + g);
        System.out.println("Alice sends A = g^a mod p = " + A);
        System.out.println("Bob sends B = g^b mod p = " + B);
        System.out.println("Shared secret at Alice = " + s1);
        System.out.println("Shared secret at Bob   = " + s2);
        System.out.println("Equal? " + s1.equals(s2));
    }
}


