import java.util.Scanner;

public class Q1_XorHelloWorld {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = sc.nextLine();

        String xoredWithZero = xorStringWithBit(input, 0);
        String xoredWithOne = xorStringWithBit(input, 1);

        System.out.println("Original:      " + input);
        System.out.println("XOR with 0:    " + xoredWithZero);
        System.out.println("XOR with 1:    " + xoredWithOne);
    }

    private static String xorStringWithBit(String text, int bit) {
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int xored = c ^ bit; // XOR each character with bit 0 or 1
            sb.append((char) xored);
        }
        return sb.toString();
    }
}


