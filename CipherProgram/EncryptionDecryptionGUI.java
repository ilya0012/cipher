package CipherProgram;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Base64;

public class EncryptionDecryptionGUI {
    private JFrame frame;
    private JTextField keyField;
    private JTextField ivField;
    private JTextField inputFileField;
    private JTextField outputFileField;
    private JButton encryptButton;
    private JButton decryptButton;
    private JFileChooser fileChooser;

    private Encryption encryption = new Encryption();
    private Decryption decryption = new Decryption();

    public EncryptionDecryptionGUI() {
        frame = new JFrame("Encryption/Decryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(6, 2));

        fileChooser = new JFileChooser();

        inputFileField = new JTextField();
        outputFileField = new JTextField();
        keyField = new JTextField();
        ivField = new JTextField();

        JButton inputFileButton = new JButton("Select Input File");
        inputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    inputFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JButton outputFileButton = new JButton("Select Output File");
        outputFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    outputFileField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptFile();
            }
        });

        decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptFile();
            }
        });

        frame.add(new JLabel("Input File:"));
        frame.add(inputFileField);
        frame.add(inputFileButton);

        frame.add(new JLabel("Output File:"));
        frame.add(outputFileField);
        frame.add(outputFileButton);

        frame.add(new JLabel("Secret Key (Base64):"));
        frame.add(keyField);

        frame.add(new JLabel("IV (Base64):"));
        frame.add(ivField);

        frame.add(encryptButton);
        frame.add(decryptButton);

        frame.setVisible(true);
    }

    private void encryptFile() {
        try {
            SecretKey key = encryption.generateSecretKey();
            IvParameterSpec iv = encryption.generateIv();

            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            String encodedIv = Base64.getEncoder().encodeToString(iv.getIV());

            keyField.setText(encodedKey);
            ivField.setText(encodedIv);

            encryption.encryptFile(inputFileField.getText(), outputFileField.getText(), key, iv);

            JOptionPane.showMessageDialog(frame, "File encrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error encrypting file: " + e.getMessage());
        }
    }

    private void decryptFile() {
        try {
            String encodedKey = keyField.getText();
            String encodedIv = ivField.getText();

            SecretKey key = decryption.decodeSecretKey(encodedKey);
            IvParameterSpec iv = decryption.decodeIv(encodedIv);

            decryption.decryptFile(inputFileField.getText(), outputFileField.getText(), key, iv);

            JOptionPane.showMessageDialog(frame, "File decrypted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error decrypting file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new EncryptionDecryptionGUI();
    }
}