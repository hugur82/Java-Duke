import edu.duke.*;
import java.io.*;
/**
 * Décrivez votre classe testCaesarCipher ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class testCaesarCipher
{
    public int[] countLetters(String message){
        String alph= "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for(int i = 0; i<message.length();i++){
            char ch = Character.toLowerCase(message.charAt(i));
            int dex = alph.indexOf(ch);
            if (dex != -1) counts[dex]++;
        }
        return counts;
    }
    
    public int maxIndex(int[] values){
        int maxDex = 0;
        for (int i =0; i<values.length;i++){
            if ( values[i] > values[maxDex])
                maxDex=i;
        }
        return maxDex;
    }
    
    public String breakCaesarCipher(String input){
        int[] freqs = countLetters(input);
        int maxDex = maxIndex(freqs);
        int dkey = maxDex - 4; // 'e' est la lettre la plus fréquente en anglais
        if (maxDex < 4) {
            dkey = 26 - (4 - maxDex);
        }
        CaesarCipher cc = new CaesarCipher(dkey);
        return cc.decrypt(input);
        
    }
    
    public void simpleTest(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        message="Can you imagine life WITHOUT the internet AND computers in your pocket?";
        CaesarCipher cc = new CaesarCipher(15);
        
        System.out.println("la source: " + message);
        String encrypted = cc.encrypt(message);
        System.out.println("le message crypté: " + encrypted);
        String decrypt = cc.decrypt(encrypted);
        System.out.println("le message décrypté: " + decrypt);
        
        String autoDecrypt= breakCaesarCipher(encrypted);
        System.out.println("le message décrypté avec l'autoDecrypt: " + autoDecrypt);
    }
}