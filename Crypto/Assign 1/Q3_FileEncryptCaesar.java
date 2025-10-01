import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Q3_FileEncryptCaesar {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter source file path: ");
        String sourcePath = sc.nextLine().trim();
        System.out.print("Enter destination file path: ");
        String destPath = sc.nextLine().trim();
        System.out.print("Enter key (integer): ");
        int key = Integer.parseInt(sc.nextLine().trim());

        File source = new File(sourcePath);
        if (!source.exists()) {
            System.out.println("Source file does not exist. It will be created empty first.");
            if (!source.createNewFile()) {
                System.err.println("Failed to create source file.");
                return;
            }
        }

        File dest = new File(destPath);
        if (dest.exists()) {
            System.out.print("Destination exists. Overwrite? (y/n): ");
            String ans = sc.nextLine().trim().toLowerCase();
            if (!ans.equals("y")) {
                System.out.println("Aborted.");
                return;
            }
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (!first) content.append('\n');
                content.append(line);
                first = false;
            }
        }

        String cipher = ModifiedCaesar.encrypt(content.toString(), key);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dest))) {
            bw.write(cipher);
        }
        System.out.println("Encrypted content written to: " + dest.getAbsolutePath());
    }
}


