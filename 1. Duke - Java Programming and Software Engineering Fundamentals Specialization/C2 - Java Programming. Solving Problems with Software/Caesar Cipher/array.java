import java.util.Arrays;
/**
 * Décrivez votre classe array ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class array
{
    public void test(){
        String texte= "Hello les boys, les gars, los plos";
        String alpha ="abcdefghijklmnopqrstuvwxyz";
        char[] counters=new char[26];
        
        for(int i=0; i < alpha.length(); i++){
            counters[i]= alpha.charAt(i);
        }
        System.out.println(Arrays.toString(counters));
        String[] test = texte.split(",");
        
        System.out.println(Arrays.toString(test));
    }
}