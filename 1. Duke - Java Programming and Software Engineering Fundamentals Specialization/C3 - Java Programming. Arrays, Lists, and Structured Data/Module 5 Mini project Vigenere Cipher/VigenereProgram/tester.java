import edu.duke.*;
import java.util.*;
import java.io.*;

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
    
    public void testReadDictionary(){
        FileResource fr = new FileResource();
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> set = vb.readDictionary(fr);
        //System.out.println("fileressource.asString = "+ fr.asString());
        
        System.out.println("set = "+ set);
        
    }
    
    public void testCountWords() {
        FileResource frDict = new FileResource("dictionaries/English");
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> dict = vb.readDictionary(frDict);

        FileResource frMsg = new FileResource();
        String message = frMsg.asString();

        int valid = vb.countWords(message, dict);
        System.out.println("Number of valid words: " + valid);
    }
    
    public void testBreakForLanguage() {
        // Charger le message chiffré
        FileResource frMsg = new FileResource();
        String encrypted = frMsg.asString();
        VigenereBreaker vb = new VigenereBreaker();
        // Charger le dictionnaire
        FileResource frDict = new FileResource("dictionaries/English");
        HashSet<String> dictionary = vb.readDictionary(frDict);
        
        // Déchiffrer
        String decrypted = vb.breakForLanguage(encrypted, dictionary);
        
        // Afficher un extrait
        System.out.println("Decryption preview:");
        System.out.println(decrypted.substring(0, Math.min(300, decrypted.length())));
    }
    
    public void testMostCommonCharIn(){
        FileResource frDict = new FileResource("dictionaries/English");
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> dict = vb.readDictionary(frDict);
        char mostCommonChar = vb.mostCommonCharIn(dict);
        System.out.println("The most comon char is " + mostCommonChar); 
    }
    
    public void testBreakForAllLangs(){
        VigenereBreaker vb = new VigenereBreaker();
        DirectoryResource dr = new DirectoryResource();
        HashMap<String, HashSet<String>> languages = new HashMap<>();
        
        for( File f : dr.selectedFiles()){
            
            String languageName = f.getName();
            FileResource fr = new FileResource(f);
            HashSet<String> dictionary = new HashSet<> ();
            
            for (String word : fr.words()){
                dictionary.add(word.toLowerCase());
            }
            
            languages.put(languageName, dictionary);
        }
        FileResource frEncrypted = new FileResource();
        String encrypted = frEncrypted.asString();
        vb.breakForAllLangs(encrypted,languages);
    }
}