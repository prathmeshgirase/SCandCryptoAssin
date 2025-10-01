import java.util.Objects;

public final class ModifiedCaesar {
    // Printable ASCII range 32..126 inclusive (95 characters)
    private static final int ASCII_START = 32;
    private static final int ASCII_END = 126;
    private static final int ASCII_RANGE = ASCII_END - ASCII_START + 1; // 95

    private ModifiedCaesar() {}

    public static String encrypt(String plainText, int key) {
        Objects.requireNonNull(plainText, "plainText");
        int normalizedKey = normalizeKey(key);
        StringBuilder result = new StringBuilder(plainText.length());
        for (int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            if (ch >= ASCII_START && ch <= ASCII_END) {
                int shifted = (ch - ASCII_START + normalizedKey) % ASCII_RANGE;
                result.append((char) (ASCII_START + shifted));
            } else {
                // Leave non printable ASCII untouched
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String cipherText, int key) {
        return encrypt(cipherText, -key);
    }

    private static int normalizeKey(int key) {
        int k = key % ASCII_RANGE;
        if (k < 0) k += ASCII_RANGE;
        return k;
    }
}


