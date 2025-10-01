import java.util.*;

public class FS2_TranspositionCiphers {
    // ===== Rail Fence Cipher =====
    public static String railFenceEncrypt(String text, int rails) {
        if (rails <= 1 || text.isEmpty()) return text;
        List<StringBuilder> rows = new ArrayList<>(rails);
        for (int i = 0; i < rails; i++) rows.add(new StringBuilder());
        int row = 0, dir = 1;
        for (int i = 0; i < text.length(); i++) {
            rows.get(row).append(text.charAt(i));
            if (row == 0) dir = 1; else if (row == rails - 1) dir = -1;
            row += dir;
        }
        StringBuilder out = new StringBuilder(text.length());
        for (StringBuilder sb : rows) out.append(sb);
        return out.toString();
    }

    public static String railFenceDecrypt(String cipher, int rails) {
        if (rails <= 1 || cipher.isEmpty()) return cipher;
        int n = cipher.length();
        boolean[][] mark = new boolean[rails][n];
        int row = 0, dir = 1;
        for (int col = 0; col < n; col++) {
            mark[row][col] = true;
            if (row == 0) dir = 1; else if (row == rails - 1) dir = -1;
            row += dir;
        }
        char[][] grid = new char[rails][n];
        int idx = 0;
        for (int r = 0; r < rails; r++) {
            for (int c = 0; c < n; c++) {
                if (mark[r][c]) grid[r][c] = cipher.charAt(idx++);
            }
        }
        StringBuilder out = new StringBuilder(n);
        row = 0; dir = 1;
        for (int col = 0; col < n; col++) {
            out.append(grid[row][col]);
            if (row == 0) dir = 1; else if (row == rails - 1) dir = -1;
            row += dir;
        }
        return out.toString();
    }

    // ===== Columnar Transposition =====
    public static String columnarEncrypt(String text, String key) {
        if (key == null || key.isEmpty()) throw new IllegalArgumentException("Key required");
        int cols = key.length();
        int rows = (int) Math.ceil(text.length() / (double) cols);
        char[][] grid = new char[rows][cols];
        int p = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = p < text.length() ? text.charAt(p++) : '\u0000';
            }
        }
        Integer[] order = columnOrder(key);
        StringBuilder out = new StringBuilder(rows * cols);
        for (int c = 0; c < cols; c++) {
            int col = order[c];
            for (int r = 0; r < rows; r++) {
                if (grid[r][col] != '\u0000') out.append(grid[r][col]);
            }
        }
        return out.toString();
    }

    public static String columnarDecrypt(String cipher, String key) {
        if (key == null || key.isEmpty()) throw new IllegalArgumentException("Key required");
        int cols = key.length();
        int rows = (int) Math.ceil(cipher.length() / (double) cols);
        char[][] grid = new char[rows][cols];
        Integer[] order = columnOrder(key);
        int fullCells = cipher.length();
        int p = 0;
        for (int oc = 0; oc < cols; oc++) {
            int col = order[oc];
            for (int r = 0; r < rows; r++) {
                int idx = r * cols + col;
                if (idx < rows * cols && p < fullCells) {
                    grid[r][col] = cipher.charAt(p++);
                }
            }
        }
        StringBuilder out = new StringBuilder(cipher.length());
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '\u0000') out.append(grid[r][c]);
            }
        }
        return out.toString();
    }

    private static Integer[] columnOrder(String key) {
        int n = key.length();
        List<Integer> indices = new ArrayList<>(n);
        for (int i = 0; i < n; i++) indices.add(i);
        indices.sort(Comparator.comparingInt(i -> key.charAt(i))
                .thenComparingInt(i -> i));
        return indices.toArray(new Integer[0]);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose: 1) RailFence  2) Columnar");
        int choice = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Enter text: ");
        String text = sc.nextLine();
        System.out.print("Encrypt (E) or Decrypt (D)? ");
        boolean enc = sc.nextLine().trim().equalsIgnoreCase("E");
        String result;
        if (choice == 1) {
            System.out.print("Enter rails: ");
            int rails = Integer.parseInt(sc.nextLine().trim());
            result = enc ? railFenceEncrypt(text, rails) : railFenceDecrypt(text, rails);
        } else {
            System.out.print("Enter key: ");
            String key = sc.nextLine();
            result = enc ? columnarEncrypt(text, key) : columnarDecrypt(text, key);
        }
        System.out.println((enc ? "Ciphertext: " : "Plaintext: ") + result);
    }
}


