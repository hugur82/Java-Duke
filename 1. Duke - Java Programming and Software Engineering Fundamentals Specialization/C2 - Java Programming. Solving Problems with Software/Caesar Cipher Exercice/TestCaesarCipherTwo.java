import edu.duke.*;
import java.io.*;

public class TestCaesarCipherTwo {

    // Retourne tous les caractères d'une moitié du message (indices start, start+2, ...)
    public String halfOfString(String message, int start) {
    StringBuilder sb = new StringBuilder();
    int count = 0; // compte seulement les lettres
    for (int i = 0; i < message.length(); i++) {
        char ch = message.charAt(i);
        if (Character.isLetter(ch)) {
            if (count % 2 == start) {
                sb.append(ch);
            }
            count++;
        }
    }
    return sb.toString();
}

    // Compte les occurrences de chaque lettre (a-z)
    public int[] countLetters(String message) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for (int i = 0; i < message.length(); i++) {
            char ch = Character.toLowerCase(message.charAt(i));
            int idx = alphabet.indexOf(ch);
            if (idx != -1) counts[idx]++;
        }
        return counts;
    }

    // Retourne l'indice de la valeur maximale dans un tableau
    public int maxIndex(int[] values) {
        int maxIdx = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maxIdx]) maxIdx = i;
        }
        return maxIdx;
    }

    // Détermine la clé probable pour un texte en utilisant l'analyse de fréquence
    // Détermine la clé probable pour un texte
public int getKey(String s) {
    int[] freqs = countLetters(s);
    int maxDex = maxIndex(freqs);

    // Lettres fréquentes en anglais : e, t, a, i, n
    int[] commonLetters = {4, 19, 0, 8, 13};
    int bestKey = 0;
    int bestScore = -1;

    for (int letter : commonLetters) {
        int key = (maxDex - letter + 26) % 26;

        // Déchiffre temporairement avec cette clé
        CaesarCipher cc = new CaesarCipher(key);
        String decrypted = cc.decrypt(s);
        int[] decryptedFreqs = countLetters(decrypted);

        // Score : nombre de 'e' dans le texte déchiffré
        int score = decryptedFreqs[4];

        if (score > bestScore) {
            bestScore = score;
            bestKey = key;
        }
    }
    return bestKey;
}


    // Tente de casser un message chiffré avec 2 clés
    public String breakCaesarCipher(String input) {
        String half1 = halfOfString(input, 0);
        String half2 = halfOfString(input, 1);

        int key1 = getKey(half1);
        int key2 = getKey(half2);

        System.out.println("Detected keys: key1 = " + key1 + ", key2 = " + key2);

        CaesarCipherTwo cc = new CaesarCipherTwo(key1, key2);
        return cc.decrypt(input);
    }

    // Test complet : chiffrement, déchiffrement connu et automatique
    public void simpleTests() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        message="Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCipherTwo cc = new CaesarCipherTwo(21,8);

        String encrypted = cc.encrypt(message);
        System.out.println("Encrypted:\n" + encrypted);

        //String decrypted = cc.decrypt(encrypted);
        String decrypted = cc.decrypt("Hfs cpwewloj loks cd Hoto kyg Cyy.");
        System.out.println("\nDecrypted (known keys):\n" + decrypted);

        String autoDecrypted = breakCaesarCipher("Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!");
        System.out.println("\nAutomatically Decrypted:\n" + autoDecrypted);
    }
}
