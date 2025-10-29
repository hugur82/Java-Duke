import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder newMessage = new StringBuilder();
        for(int k = whichSlice; k < message.length(); k+=totalSlices){
            newMessage.append(message.charAt(k));
        }
        return newMessage.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
    
        for (int i = 0; i < klength; i++) {
            // 1️⃣ Récupère la tranche i (toutes les lettres i, i+klength, i+2*klength, etc.)
            String slice = sliceString(encrypted, i, klength);
    
            // 2️⃣ Trouve le décalage de César le plus probable pour cette tranche
            int dkey = cc.getKey(slice);
    
            // 3️⃣ Stocke ce décalage dans le tableau
            key[i] = dkey;
        }
    
        return key;
    }

    public void breakVigenere () {
        //WRITE YOUR CODE HERE
        FileResource fr = new FileResource();
        String text = fr.asString();
        int[] key = tryKeyLength(text, 4, 'e');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypt = vc.decrypt(text);
        System.out.println("Le texte décrypté est " + decrypt);
        System.out.println("Les clés sont " + Arrays.toString(key));
    }
    
}
