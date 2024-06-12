package CipherProgram;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Decryption {
    private final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public void decryptFile(String inputFile, String outputFile, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(new File(inputFile));
            fos = new FileOutputStream(new File(outputFile));

            byte[] inputBuffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(inputBuffer)) != -1) {
                byte[] outputBytes = cipher.update(inputBuffer, 0, bytesRead); // Multi-part update
                if (outputBytes != null) {
                    fos.write(outputBytes);
                }
            }

            byte[] finalBytes = cipher.doFinal(); // conclusion of operation
            if (finalBytes != null) {
                fos.write(finalBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SecretKey decodeSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public IvParameterSpec decodeIv(String encodedIv) {
        byte[] decodedIv = Base64.getDecoder().decode(encodedIv);
        return new IvParameterSpec(decodedIv);
    }
}