import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Q4_RegistrationLogin {
    private static final Path DB_PATH = Path.of("users.db");
    private static final int PASSWORD_KEY = 7; // internal fixed key

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Q4_RegistrationLogin().showRegister());
    }

    private void showRegister() {
        JFrame frame = new JFrame("Register");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);
        JPasswordField confirm = new JPasswordField(20);

        JButton refresh = new JButton(new AbstractAction("Refresh") {
            @Override public void actionPerformed(ActionEvent e) {
                username.setText("");
                password.setText("");
                confirm.setText("");
                // Reset carets and focus to ensure fields appear cleared on all LAFs
                username.setCaretPosition(0);
                password.setCaretPosition(0);
                confirm.setCaretPosition(0);
                username.requestFocusInWindow();
            }
        });

        JButton store = new JButton(new AbstractAction("Store") {
            @Override public void actionPerformed(ActionEvent e) {
                String user = username.getText().trim();
                String pass = new String(password.getPassword());
                String conf = new String(confirm.getPassword());
                if (user.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Enter user name");
                    return;
                }
                String validation = validatePassword(pass);
                if (validation != null) {
                    JOptionPane.showMessageDialog(frame, validation);
                    return;
                }
                if (!pass.equals(conf)) {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match");
                    return;
                }
                String enc = ModifiedCaesar.encrypt(pass, PASSWORD_KEY);
                try {
                    saveUser(user, enc);
                    JOptionPane.showMessageDialog(frame, "Registration saved");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving: " + ex.getMessage());
                }
            }
        });

        JButton toLogin = new JButton(new AbstractAction("Login") {
            @Override public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showLogin();
            }
        });

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.add(new JLabel("Enter User name"));
        form.add(username);
        form.add(new JLabel("Enter Password"));
        form.add(password);
        form.add(new JLabel("Confirm Password"));
        form.add(confirm);
        form.add(refresh);
        form.add(store);

        frame.getContentPane().add(form, BorderLayout.CENTER);
        frame.getContentPane().add(toLogin, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showLogin() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(20);

        JButton ok = new JButton(new AbstractAction("OK") {
            @Override public void actionPerformed(ActionEvent e) {
                String user = username.getText().trim();
                String pass = new String(password.getPassword());
                String enc = ModifiedCaesar.encrypt(pass, PASSWORD_KEY);
                boolean success;
                try {
                    success = checkUser(user, enc);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading DB: " + ex.getMessage());
                    return;
                }
                JOptionPane.showMessageDialog(frame, success ? "Login Successful" : "Login failed");
            }
        });

        JButton back = new JButton(new AbstractAction("Back") {
            @Override public void actionPerformed(ActionEvent e) {
                frame.dispose();
                showRegister();
            }
        });

        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Enter User name"));
        form.add(username);
        form.add(new JLabel("Enter Password"));
        form.add(password);
        form.add(back);
        form.add(ok);

        frame.getContentPane().add(form);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String validatePassword(String password) {
        if (password == null || password.length() < 8) return "Password must be at least 8 characters";
        Pattern digit = Pattern.compile(".*\\d.*");
        Pattern upper = Pattern.compile(".*[A-Z].*");
        Pattern special = Pattern.compile(".*[^A-Za-z0-9].*");
        if (!digit.matcher(password).matches()) return "Password needs at least one digit";
        if (!upper.matcher(password).matches()) return "Password needs at least one capital letter";
        if (!special.matcher(password).matches()) return "Password needs at least one special symbol";
        return null;
    }

    private static synchronized void saveUser(String username, String encryptedPassword) throws IOException {
        if (!Files.exists(DB_PATH)) Files.createFile(DB_PATH);
        try (BufferedWriter bw = Files.newBufferedWriter(DB_PATH, java.nio.charset.StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.APPEND)) {
            bw.write(username + ":" + encryptedPassword);
            bw.newLine();
        }
    }

    private static synchronized boolean checkUser(String username, String encryptedPassword) throws IOException {
        if (!Files.exists(DB_PATH)) return false;
        List<String> lines = Files.readAllLines(DB_PATH);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(":", 2);
            if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(encryptedPassword)) {
                return true;
            }
        }
        return false;
    }
}


