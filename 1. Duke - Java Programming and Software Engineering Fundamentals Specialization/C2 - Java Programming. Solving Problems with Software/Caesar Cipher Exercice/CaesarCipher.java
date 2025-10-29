
/**
 * Décrivez votre classe CaesarCipher ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class CaesarCipher{
   private String alphabet="abcdefghijklmnopqrstuvwxyz";
   private String shiftedAlphabet;
   private String upperAlphabet= alphabet.toUpperCase();
   private String shiftedUpperAlphabet;
   private int mainKey;
   
   public CaesarCipher(int key){
       this.mainKey = key;
       shiftedAlphabet=alphabet.substring(mainKey) + alphabet.substring(0,mainKey);
       shiftedUpperAlphabet=shiftedAlphabet.toUpperCase();
   }
   
   public String encrypt(String input){
       StringBuilder sb = new StringBuilder(input);
       for(int k=0;k<sb.length();k++){
           char c = sb.charAt(k);
           int idx = alphabet.indexOf(c);
           if(idx !=-1)
           sb.setCharAt(k,shiftedAlphabet.charAt(idx));
           else{
               idx = upperAlphabet.indexOf(c);
               if(idx !=-1)
               sb.setCharAt(k,shiftedUpperAlphabet.charAt(idx));
           }
       }
       return sb.toString();
   }
   
   public String decrypt(String input){
       StringBuilder sb = new StringBuilder(input);
       
       for(int k = 0; k<sb.length();k++){
           char c = sb.charAt(k);
           int idx = shiftedAlphabet.indexOf(c);
           if(idx!=-1)
               sb.setCharAt(k,alphabet.charAt(idx));
           else{
               idx = shiftedUpperAlphabet.indexOf(c);
                if(idx !=-1)
               sb.setCharAt(k,upperAlphabet.charAt(idx));
           }
       }
       
       return sb.toString();
   }
   
   
}
