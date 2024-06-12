package CipherProgram;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;
import java.util.Scanner;   

public class Text {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Do you want to (1) Encrypt or (2) Decrypt?");
        int choice = input.nextInt();
        input.nextLine();

        if (choice == 1) {
            // Encryption process
            Encryption encryption = new Encryption();

            System.out.println("Enter the path of the file to encrypt:");
            String inputFile = input.nextLine();

            System.out.println("Enter the path to save the encrypted file:");
            String outputFile = input.nextLine();

            try {
                SecretKey key = encryption.generateSecretKey();
                IvParameterSpec iv = encryption.generateIv();

                
                String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
                String encodedIv = Base64.getEncoder().encodeToString(iv.getIV());

                System.out.println("Encoded Key: " + encodedKey);
                System.out.println("Encoded IV: " + encodedIv);

                encryption.encryptFile(inputFile, outputFile, key, iv);

                System.out.println("File encrypted successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (choice == 2) {
            // Decryption process
            Decryption decryption = new Decryption();

            System.out.println("Enter the path of the file to decrypt:");
            String inputFile = input.nextLine();

            System.out.println("Enter the path to save the decrypted file:");
            String outputFile = input.nextLine();

            System.out.println("Enter the Base64 encoded key:");
            String encodedKey = input.nextLine();

            System.out.println("Enter the Base64 encoded IV:");
            String encodedIv = input.nextLine();

            try {
                SecretKey key = decryption.decodeSecretKey(encodedKey); // returns a key
                IvParameterSpec iv = decryption.decodeIv(encodedIv); // returns a vector

                decryption.decryptFile(inputFile, outputFile, key, iv);

                System.out.println("File decrypted successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Choose either 1 or 2.");
            main(args); // launch again
        }

        input.close();
    }
}