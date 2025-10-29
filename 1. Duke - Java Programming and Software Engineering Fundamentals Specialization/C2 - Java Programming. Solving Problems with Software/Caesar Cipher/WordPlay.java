import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Décrivez votre classe WordPlay ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class WordPlay
{
    public boolean isVowel(char ch){
        String vowel="aeiouAEIOU";
        if (vowel.indexOf(ch) == -1) return false;
        
        return true;
    }
    
    public void testVowel(){
        boolean resp=true;
        for(char c='A';c<='z';c++){
            resp =isVowel(c);
            if (resp)
                System.out.println(c +" est une voyelle: " +resp);
        }
        
    }
    
    public String replaceVowels (String phrase, char ch){
        
        StringBuilder replaced=new StringBuilder(phrase);
        for (int i=0;i<replaced.length();i++){
            char currChar = replaced.charAt(i);
            if (isVowel(currChar)) replaced.setCharAt(i,ch);
        }
        
        return replaced.toString();
    }
    
    public void testReplaceVowel(){
        String texte= "Salut MOIS toi voyelles ionic";
        System.out.println("Voici le texte          :" + texte);
        System.out.println("Voici le texte remplacer:" + replaceVowels(texte,'¢'));
    }
    
    public String emphasize (String phrase, char ch){
        StringBuilder emphasized = new StringBuilder(phrase);
        for (int i = 0 ; i < emphasized.length(); i++){
            char currChar = emphasized.charAt(i);
            
            if (currChar==Character.toUpperCase(ch) || currChar == ch)
            {
                if(i%2==0)
                    emphasized.setCharAt(i,'*');
                else
                    emphasized.setCharAt(i,'+');
            }
            
        }
        
        return emphasized.toString();
    }
    
    public void testEmphasized(){
        String texte = "dna ctgaaactga";
        char c = 'a';
        System.out.println("texte= "+texte+ " et char="+c);
        System.out.println("result= "+emphasize(texte,c));
        texte = "Mary Bella Abracadabra";
        System.out.println("texte= "+texte+ " et char="+c);
        System.out.println("result= "+emphasize(texte,c));
    }
}