package com.mtbadi39.commons.utils;

import com.mtbadi39.commons.logging.LogUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.security.Key;
import java.util.StringTokenizer;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtil {

    private static final String algo = "Blowfish";
    private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);

    public void crypteFileToFile(String password, String entree, String sortie) {
        try {
            byte[] passwordInBytes = password.getBytes("UTF-8");
            Key clef = new SecretKeySpec(passwordInBytes, algo);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, clef);

            byte[] texteClaire = ouvrirFichier(entree);
            byte[] texteCrypte = cipher.doFinal(texteClaire);
            sauverFichier(sortie, texteCrypte);
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("CryptoUtil", "crypteFileToFile", e));
        }
    }

    public void crypteTextToFile(String password, String text, String filename) {
        try {
            byte[] passwordInBytes = password.getBytes("UTF-8");
            Key clef = new SecretKeySpec(passwordInBytes, algo);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, clef);

            byte[] texteClaire = text.getBytes();
            byte[] texteCrypte = cipher.doFinal(texteClaire);
            sauverFichier(filename, texteCrypte);
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("CryptoUtil", "crypteTextToFile", e));
        }
    }

    public void decrypteFileToFile(String password, String entree, String sortie) {
        try {
            byte[] passwordInBytes = password.getBytes("UTF-8");
            Key clef = new SecretKeySpec(passwordInBytes, algo);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, clef);

            byte[] texteCrypte = ouvrirFichier(entree);
            byte[] texteClaire = cipher.doFinal(texteCrypte);
            String sTexteClaire = new String(texteClaire);
            StringTokenizer st = new StringTokenizer(sTexteClaire);

            //while (st.hasMoreTokens()) {
            System.out.println("=>" + st.nextToken("\n"));
            System.out.println("=>" + st.nextToken("\n"));
            System.out.println("=>" + st.nextToken("\n"));
            System.out.println("=>" + st.nextToken("\n"));
            //}

            sauverFichier(sortie, texteClaire);
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("CryptoUtil", "decrypteFileToFile", e));
        }
    }

    public String decrypteFileToText(String password, String filename) {
        String sTexteClaire = null;
        try {
            //ISO-8859-2
            byte[] passwordInBytes = password.getBytes("UTF-8");
            Key clef = new SecretKeySpec(passwordInBytes, algo);
            Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, clef);

            byte[] texteCrypte = ouvrirFichier(filename);
            byte[] texteClaire = cipher.doFinal(texteCrypte);
            sTexteClaire = new String(texteClaire);
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("CryptoUtil", "decrypteFileToText", e));
        }
        return sTexteClaire;
    }

    private byte[] ouvrirFichier(String filename) {
        try {
            File fichier = new File(filename);
            byte[] result = new byte[(int) fichier.length()];
            FileInputStream in = new FileInputStream(filename);
            in.read(result);
            in.close();
            return result;
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("CryptoUtil", "ouvrirFichier", e));
            return null;
        }
    }

    private void sauverFichier(String filename, byte[] data) throws Exception {
        try {
            File file = new File(filename);
            File dir = file.getParentFile();
            if (dir != null) {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(data);
            out.close();
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("CryptoUtil", "sauverFichier", e));
            throw new Exception(e);
            /*
             if (ex.getCause() != null) {
             logger.error("Probleme lors de la sauvegarde du fichier: "
             + ex + " --- " + ex.getCause());
             } else {
             logger.error("Probleme lors de la sauvegarde du fichier: " + ex);
             }*/
        }
    }

    public void copieFile(String source, String destination) {
        FileChannel in = null; // canal d'entrée
        FileChannel out = null; // canal de sortie

        try {
            // Init
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(destination).getChannel();

            // Copie depuis le in vers le out
            in.transferTo(0, in.size(), out);
        } catch (Exception e) {
            logger.error(LogUtils.resolveException("CryptoUtil", "copieFile", e));
            /*
             if (ex.getCause() != null) {
             logger.error("Erreur lors de la copie depuis le in vers le out"
             + ex + " --- " + ex.getCause());
             } else {
             logger.error("Erreur lors de la copie depuis le in vers le out" + ex);
             }*/
            //ex.printStackTrace(); // n'importe quelle exception

        } finally { // finalement on ferme
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error(LogUtils.resolveException("CryptoUtil", "copieFile", e));
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    logger.error(LogUtils.resolveException("CryptoUtil", "copieFile", e));
                }
            }
        }
    }
}
