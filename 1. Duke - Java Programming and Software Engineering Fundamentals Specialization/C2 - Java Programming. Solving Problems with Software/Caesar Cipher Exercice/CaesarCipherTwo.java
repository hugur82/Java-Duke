
/**
 * Décrivez votre classe CaesarCipherTwo ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class CaesarCipherTwo
{
    private String alphabet ;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainKey1;
    private int mainKey2;
    
    public CaesarCipherTwo(int key1, int key2){
        this.alphabet = "abcdefghijklmnopqrstuvwxyz";
        this.mainKey1 = key1;
        this.mainKey2 = key2;
        this.shiftedAlphabet1 = alphabet.substring(key1) + alphabet.substring(0,key1);
        this.shiftedAlphabet2 = alphabet.substring(key2) + alphabet.substring(0,key2);
    }
    
    public String encrypt(String input){
        StringBuilder sb = new StringBuilder(input);
        for(int i = 0; i<sb.length();i++){
            char currChar = sb.charAt(i);
            boolean isLower = Character.isLowerCase(currChar);
            char ch = Character.toLowerCase(currChar);
            int idx = alphabet.indexOf(ch);
            if (idx!=-1)
            {
                String shiftedAlphabet = (i%2 ==0)? shiftedAlphabet1: shiftedAlphabet2;
                char newChar = shiftedAlphabet.charAt(idx);
                if(!isLower) newChar = Character.toUpperCase(newChar);
                sb.setCharAt(i,newChar);
            }
        }
        return sb.toString();
    }
    
    public String decrypt(String input){
        CaesarCipherTwo cc = new CaesarCipherTwo(26 - mainKey1, 26 - mainKey2);
        return cc.encrypt(input);
    }
}