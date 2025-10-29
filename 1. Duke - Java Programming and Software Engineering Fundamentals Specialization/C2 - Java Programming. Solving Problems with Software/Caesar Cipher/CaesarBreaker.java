
import edu.duke.*;
/**
 * Décrivez votre classe CaesarBreacker ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */

    public class CaesarBreaker {
 // Returns every other character starting from index 'start'
    public String halfOfString(String message, int start) {
        StringBuilder half = new StringBuilder();
        for (int i = start; i < message.length(); i += 2) {
            half.append(message.charAt(i));
        }
        return half.toString();
    }

    // Counts letter frequencies (same idea as before)
    public int[] countLetters(String message) {
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for (int k = 0; k < message.length(); k++) {
            char ch = Character.toLowerCase(message.charAt(k));
            int dex = alph.indexOf(ch);
            if (dex != -1) {
                counts[dex] += 1;
            }
        }
        return counts;
    }

    // Finds index of maximum frequency
    public int maxIndex(int[] values) {
        int maxDex = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maxDex]) {
                maxDex = i;
            }
        }
        return maxDex;
    }

    // Gets the decryption key based on letter frequency
    public int getKey(String s) {
        int[] freqs = countLetters(s);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4; // assuming 'e' is most common
        if (maxDex < 4) {
            dkey = 26 - (4 - maxDex);
        }
        return dkey;
    }
    
    public String decryptWithKey(String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        int key = getKey(encrypted); // récupère seulement la clé
        return cc.encrypt(encrypted, 26 - key); // déchiffre
    }

    public void testgetKeyDecrypt() {
        CaesarCipher cc = new CaesarCipher();
        String message = "Le chiffrement de Cesar est une methode tres simple mais efficace pour cacher un message sans que personne ne le comprenne facilement.";
        String encrypted = cc.encrypt(message, 5);
        System.out.println("Chiffré : " + encrypted);
    
        String decrypted = decryptWithKey(encrypted); // utilise la méthode séparée
        System.out.println("Déchiffré : " + decrypted);
    }
    
    public String decryptTwoKeys(String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        
        // Split the encrypted message into two halves
        String half1 = halfOfString(encrypted, 0);
        String half2 = halfOfString(encrypted, 1);
        
        // Find the key for each half
        int key1 = getKey(half1);
        int key2 = getKey(half2);
        
        System.out.println("Key 1: " + key1);
        System.out.println("Key 2: " + key2);
        
        // Use encryptTwoKeys from CaesarCipher to decrypt
        String decrypted = cc.encryptTwoKeys(encrypted, 26 - key1, 26 - key2);
        return decrypted;
    }

    // Test the method
    public void testDecryptTwoKeys () {
        CaesarBreaker cb = new CaesarBreaker();
        CaesarCipher cc = new CaesarCipher();

        FileResource fr = new FileResource();
        
        String message = fr.asString();
        //String message  = "Hfs cpwewloj loks cd Hoto kyg Cyy.";
        String encrypted = cc.encryptTwoKeys(message, 1, 2);
        encrypted="Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        System.out.println("Encrypted: " + encrypted);
        String decrypted = cb.decryptTwoKeys(message);
        System.out.println("Decrypted: " + decrypted);
    }
}
