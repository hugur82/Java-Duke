import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Décrivez votre classe CaesarCipher ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class CaesarCipher
{
    public String encrypt(String input, int key){
        StringBuilder encrypter= new StringBuilder(input);
        
        String alphabet="abcdefghijklmnopqrstuvwxyz";
        String shifted = alphabet.substring(key) + alphabet.substring(0,key);
        String upperAlphabet = alphabet.toUpperCase();
        String upperShifted = shifted.toUpperCase();
        
        for (int i = 0; i < encrypter.length() ; i++){
            char currChar = encrypter.charAt(i);
            int idx = alphabet.indexOf(currChar);
            if(idx != -1){
            char newChar = shifted.charAt(idx);
            encrypter.setCharAt(i,newChar);
            }
            else{
                idx = upperAlphabet.indexOf(currChar);
                if(idx != -1){
                char newChar = upperShifted.charAt(idx);
                encrypter.setCharAt(i,newChar);
                }
            }
        }
        return encrypter.toString();
    }
    
    public String decrypt(String input, int key){
        StringBuilder decrypter = new StringBuilder(input);
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String shifted = alphabet.substring(key) + alphabet.substring(0,key);
        String upperAlphabet = alphabet.toUpperCase();
        String upperShifted = shifted.toUpperCase();
        
        for(int i=0;i < decrypter.length();i++){
            char c = decrypter.charAt(i);
            int idx = shifted.indexOf(c);
            if(idx != -1){
                char newChar = alphabet.charAt(idx);
                decrypter.setCharAt(i,newChar);
            }
            else {
                idx =upperShifted.indexOf(c); 
                if(idx != -1){
                    char newChar = upperAlphabet.charAt(idx);
                    decrypter.setCharAt(i,newChar);
                }
            }
        }
        
        return decrypter.toString();
    }
    
    public String encryptTwoKeys (String input, int key1, int key2){
        StringBuilder encrypted = new StringBuilder(input);
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String lowerKey1 = lowerAlphabet.substring(key1) + lowerAlphabet.substring(0,key1);
        String lowerKey2 = lowerAlphabet.substring(key2) + lowerAlphabet.substring(0,key2);
        String upperAlphabet = lowerAlphabet.toUpperCase();
        String upperKey1 =lowerKey1.toUpperCase();
        String upperKey2 =lowerKey2.toUpperCase();
        
        for (int i = 0; i < encrypted.length();i++){
            char currChar = encrypted.charAt(i);
            int idx = lowerAlphabet.indexOf(currChar);
            
            if(idx!=-1){
                if ( i%2 == 0)
                    encrypted.setCharAt(i,lowerKey1.charAt(idx));
                else
                    encrypted.setCharAt(i,lowerKey2.charAt(idx));
            }
            else {
                idx = upperAlphabet.indexOf(currChar);
                if (idx!=-1){
                    if ( i%2 == 0)
                    encrypted.setCharAt(i,upperKey1.charAt(idx));
                    else
                    encrypted.setCharAt(i,upperKey2.charAt(idx));
                }
                
            }
            
        }
        
        return encrypted.toString();
    }
    
    public String decryptTwoKeys (String input, int key1, int key2){
        StringBuilder encrypted = new StringBuilder(input);
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String lowerKey1 = lowerAlphabet.substring(key1) + lowerAlphabet.substring(0,key1);
        String lowerKey2 = lowerAlphabet.substring(key2) + lowerAlphabet.substring(0,key2);
        String upperAlphabet = lowerAlphabet.toUpperCase();
        String upperKey1 =lowerKey1.toUpperCase();
        String upperKey2 =lowerKey2.toUpperCase();
        
        for (int i = 0; i < encrypted.length();i++){
            char currChar = encrypted.charAt(i);
            int idx = lowerKey1.indexOf(currChar);
            if(idx!=-1){
                if(i%2 != 0)
                    idx = lowerKey2.indexOf(currChar);
                encrypted.setCharAt(i,lowerAlphabet.charAt(idx));
            }
            else {
                idx = upperKey1.indexOf(currChar);
                if(idx!=-1){
                    if(i%2!=0)
                        idx= upperKey2.indexOf(currChar);
                    encrypted.setCharAt(i,upperAlphabet.charAt(idx));
                }
            }
            
        }
        
        return encrypted.toString();
    }
    
    public void testEncrypt2key(){
        String texte= "At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        int key1 =2;
        int key2 =20;
        
        String encrypted = encryptTwoKeys(texte,key1,key2);
        System.out.println("Texte original= "+ texte);
        System.out.println("Texte encrypted= 'Top ncmy qkff vi vguv vbg ycpx'");
        System.out.println("Texte decrypté= "+ decryptTwoKeys("Top ncmy qkff vi vguv vbg ycpx",key1,key2));
        System.out.println("----------Avec file Resource--------");
        FileResource fr = new FileResource();
        texte = fr.asString();
        encrypted = encryptTwoKeys(texte,key1,key2);
        System.out.println("Texte original= "+ texte);
        System.out.println("Texte encrypted= "+ encrypted);
        System.out.println("Texte decrypté= "+ decryptTwoKeys(encrypted,key1,key2));
        
    }
    
    public void testEncrypt(){
        int key = 15;
        String texte ="At noon be in the conference room with your hat on for a surprise party. YELL LOUD!";
        System.out.println("texte is \""+ texte + "\"");
        String encryptedTxt =encrypt(texte,key) ;
        System.out.println("Encrypt is \""+ encryptedTxt + "\"");
        System.out.println("-----------Decrypt-----------");
        String decryptTxt = decrypt(encryptedTxt,key);
        System.out.println("After decrypting  is \""+ decryptTxt + "\"");
        
        FileResource fr = new FileResource();
        texte = fr.asString();
        System.out.println("texte is \""+ texte + "\"");
        encryptedTxt =encrypt(texte,key) ;
        System.out.println("Encrypt is \""+ encryptedTxt + "\"");
        System.out.println("-----------Decrypt-----------");
        decryptTxt = decrypt(encryptedTxt,key);
        System.out.println("After decrypting  is \""+ decryptTxt + "\"");
        
    }
}