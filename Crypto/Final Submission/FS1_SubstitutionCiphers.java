import java.util.*;

public class FS1_SubstitutionCiphers {
    // ===== Standard Caesar (letters only) =====
    public static String caesarEncrypt(String text, int key) {
        StringBuilder sb = new StringBuilder(text.length());
        int k = ((key % 26) + 26) % 26;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                sb.append((char) ('A' + (c - 'A' + k) % 26));
            } else if (c >= 'a' && c <= 'z') {
                sb.append((char) ('a' + (c - 'a' + k) % 26));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String caesarDecrypt(String text, int key) {
        return caesarEncrypt(text, -key);
    }

    // ===== Modified Caesar (all printable ASCII 32..126) =====
    private static final int ASCII_START = 32;
    private static final int ASCII_END = 126;
    private static final int ASCII_RANGE = ASCII_END - ASCII_START + 1; // 95

    public static String modifiedCaesarEncrypt(String plainText, int key) {
        Objects.requireNonNull(plainText, "plainText");
        int k = key % ASCII_RANGE;
        if (k < 0) k += ASCII_RANGE;
        StringBuilder result = new StringBuilder(plainText.length());
        for (int i = 0; i < plainText.length(); i++) {
            char ch = plainText.charAt(i);
            if (ch >= ASCII_START && ch <= ASCII_END) {
                int shifted = (ch - ASCII_START + k) % ASCII_RANGE;
                result.append((char) (ASCII_START + shifted));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String modifiedCaesarDecrypt(String cipherText, int key) {
        return modifiedCaesarEncrypt(cipherText, -key);
    }

    // ===== Playfair (5x5, I/J combined) =====
    private static class PlayfairKey {
        char[][] table = new char[5][5];
        int[] pos = new int[26]; // maps letter -> r*5+c (for A..Z with J mapped to I)
    }

    private static PlayfairKey buildPlayfairTable(String key) {
        boolean[] used = new boolean[26];
        List<Character> letters = new ArrayList<>(25);
        String k = key == null ? "" : key.toUpperCase(Locale.ROOT);
        for (int i = 0; i < k.length(); i++) {
            char ch = k.charAt(i);
            if (ch < 'A' || ch > 'Z') continue;
            if (ch == 'J') ch = 'I';
            int idx = ch - 'A';
            if (!used[idx]) {
                used[idx] = true;
                if (ch != 'J') letters.add(ch);
            }
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            char add = ch == 'J' ? 'I' : ch;
            int idx = add - 'A';
            if (!used[idx]) {
                used[idx] = true;
                if (add != 'J') letters.add(add);
            }
        }
        PlayfairKey pf = new PlayfairKey();
        int p = 0;
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                char ch = letters.get(p++);
                pf.table[r][c] = ch;
                pf.pos[ch - 'A'] = r * 5 + c;
            }
        }
        // Map J to I position
        pf.pos['J' - 'A'] = pf.pos['I' - 'A'];
        return pf;
    }

    private static List<char[]> playfairPairs(String text) {
        List<char[]> pairs = new ArrayList<>();
        String s = text.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "").replace('J', 'I');
        int i = 0;
        while (i < s.length()) {
            char a = s.charAt(i++);
            char b = 'X';
            if (i < s.length()) {
                b = s.charAt(i);
                if (a == b) {
                    b = 'X';
                } else {
                    i++;
                }
            }
            pairs.add(new char[]{a, b});
        }
        if (!pairs.isEmpty() && pairs.get(pairs.size() - 1)[1] == 0) {
            pairs.get(pairs.size() - 1)[1] = 'X';
        }
        return pairs;
    }

    public static String playfairEncrypt(String text, String key) {
        PlayfairKey pf = buildPlayfairTable(key);
        List<char[]> pairs = playfairPairs(text);
        StringBuilder out = new StringBuilder(pairs.size() * 2);
        for (char[] pr : pairs) {
            int aPos = pf.pos[pr[0] - 'A'];
            int bPos = pf.pos[pr[1] - 'A'];
            int ar = aPos / 5, ac = aPos % 5;
            int br = bPos / 5, bc = bPos % 5;
            if (ar == br) {
                out.append(pf.table[ar][(ac + 1) % 5]);
                out.append(pf.table[br][(bc + 1) % 5]);
            } else if (ac == bc) {
                out.append(pf.table[(ar + 1) % 5][ac]);
                out.append(pf.table[(br + 1) % 5][bc]);
            } else {
                out.append(pf.table[ar][bc]);
                out.append(pf.table[br][ac]);
            }
        }
        return out.toString();
    }

    public static String playfairDecrypt(String text, String key) {
        PlayfairKey pf = buildPlayfairTable(key);
        String s = text.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
        StringBuilder out = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i += 2) {
            char a = s.charAt(i);
            char b = i + 1 < s.length() ? s.charAt(i + 1) : 'X';
            int aPos = pf.pos[a - 'A'];
            int bPos = pf.pos[b - 'A'];
            int ar = aPos / 5, ac = aPos % 5;
            int br = bPos / 5, bc = bPos % 5;
            if (ar == br) {
                out.append(pf.table[ar][(ac + 4) % 5]);
                out.append(pf.table[br][(bc + 4) % 5]);
            } else if (ac == bc) {
                out.append(pf.table[(ar + 4) % 5][ac]);
                out.append(pf.table[(br + 4) % 5][bc]);
            } else {
                out.append(pf.table[ar][bc]);
                out.append(pf.table[br][ac]);
            }
        }
        return out.toString();
    }

    // ===== CLI =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose cipher: 1) Caesar  2) Modified Caesar  3) Playfair");
        int choice = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Enter text: ");
        String text = sc.nextLine();
        System.out.print("Encrypt (E) or Decrypt (D)? ");
        boolean enc = sc.nextLine().trim().equalsIgnoreCase("E");
        String result;
        switch (choice) {
            case 1 -> {
                System.out.print("Enter key (int): ");
                int key = Integer.parseInt(sc.nextLine().trim());
                result = enc ? caesarEncrypt(text, key) : caesarDecrypt(text, key);
            }
            case 2 -> {
                System.out.print("Enter key (int): ");
                int key = Integer.parseInt(sc.nextLine().trim());
                result = enc ? modifiedCaesarEncrypt(text, key) : modifiedCaesarDecrypt(text, key);
            }
            case 3 -> {
                System.out.print("Enter keyword: ");
                String key = sc.nextLine();
                result = enc ? playfairEncrypt(text, key) : playfairDecrypt(text, key);
            }
            default -> throw new IllegalArgumentException("Invalid choice");
        }
        System.out.println((enc ? "Ciphertext: " : "Plaintext: ") + result);
    }
}


