import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Q2_ModifiedCaesarGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Q2_ModifiedCaesarGUI::createAndShow);
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("Modified Caesar Cipher");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JTextArea inputArea = new JTextArea(8, 40);
        JTextArea outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        JSpinner keySpinner = new JSpinner(new SpinnerNumberModel(3, -1000, 1000, 1));

        JButton encryptBtn = new JButton(new AbstractAction("Encrypt") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputArea.getText();
                int key = (int) keySpinner.getValue();
                outputArea.setText(ModifiedCaesar.encrypt(text, key));
            }
        });

        JButton decryptBtn = new JButton(new AbstractAction("Decrypt") {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputArea.getText();
                int key = (int) keySpinner.getValue();
                String ask = JOptionPane.showInputDialog(frame, "Enter key for decryption", key);
                if (ask != null && !ask.isBlank()) {
                    try {
                        key = Integer.parseInt(ask.trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid key. Using spinner value.");
                    }
                }
                outputArea.setText(ModifiedCaesar.decrypt(text, key));
            }
        });

        JButton clearBtn = new JButton(new AbstractAction("Clear") {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputArea.setText("");
                outputArea.setText("");
            }
        });

        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        keyPanel.add(new JLabel("Key:"));
        keyPanel.add(keySpinner);
        top.add(keyPanel, BorderLayout.SOUTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(encryptBtn);
        buttons.add(decryptBtn);
        buttons.add(clearBtn);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.add(buttons, BorderLayout.NORTH);
        center.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        frame.getContentPane().setLayout(new BorderLayout(8, 8));
        frame.getContentPane().add(top, BorderLayout.NORTH);
        frame.getContentPane().add(center, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


