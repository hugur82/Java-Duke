import edu.duke.*;
import java.util.*;

/**
 * Décrivez votre classe tester ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class tester
{
    public void testCaesarCypher(){
        FileResource fr = new FileResource();
        String text = fr.asString();
        int key=5;
        CaesarCipher cc = new CaesarCipher(key);
        System.out.println("Voici le texte de base: " + text); 
        String encrypted = cc.encrypt(text);
        System.out.println("Voici le texte de encrypté: " + encrypted);
        String decrypted =cc.decrypt(encrypted);
        System.out.println("Voici le texte de decrypté: " + decrypted);  
        
    }
    
    public void testCaesarCracker(){
        CaesarCracker cc = new CaesarCracker('a');
        FileResource fr = new FileResource();
        String text = fr.asString();
        int key = cc.getKey(text);
        System.out.println("Ceci est le message decrypter: "+ cc.decrypt(text));
        System.out.println("Ceci est la clé: "+ key);
    }
    
    public void testVigenereCipher(){
        VigenereCipher vc = new VigenereCipher(new int[]{17,14,12,4});
        FileResource fr = new FileResource();
        String text = fr.asString();
        String encrypted = vc.encrypt(text);
        String decrypted = vc.decrypt(encrypted);
        System.out.println("Voici le texte de encrypté: " + encrypted);
        System.out.println("Voici le texte de decrypté: " + decrypted);  
    }
    
    public void testVigenereBreaker(){
        VigenereBreaker vb = new VigenereBreaker();
        String text;
        text = vb.sliceString("abcdefghijklm", 0, 3);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 1, 3);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 2, 3);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 0, 4);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 1,4);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 2,4);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 3,4);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 0, 5);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 1, 5);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 2, 5);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 3, 5);
        System.out.println("result " +text);
        text = vb.sliceString("abcdefghijklm", 4, 5);
        System.out.println("result " +text);
    }
    
    public void testTryKeyLength(){
        FileResource fr =new FileResource();
        String text = fr.asString();
        VigenereBreaker vb = new VigenereBreaker();
        int[] result = vb.tryKeyLength(text,5,'e');
        System.out.println("Key found: " + Arrays.toString(result));
    }
    
    public void testBreakVigenere(){
        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();
    }
}