
/**
 * Décrivez votre classe Part2 ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Part2
{
   public int howMany(String stringa, String stringb){
       int startIndex = 0;
       int count=0;
       while(true){
           if(stringb.indexOf(stringa,startIndex) == -1) break;
           count++;
           startIndex = stringb.indexOf(stringa,startIndex) + stringa.length();          
       }
       return count;
   }
   
   public void test(){
       String a="helo";
       String b="hellhelloheloollhelohehelo";
       System.out.println("nombre de fois \"" + a + "\" dans \"" + b + "\": "+ howMany(a,b));
       a="l";
       b="hellhelloheloollhelohehelo";
       System.out.println("nombre de fois \"" + a + "\" dans \"" + b + "\": "+ howMany(a,b));
       a="helo";
       b="helo";
       System.out.println("nombre de fois \"" + a + "\" dans \"" + b + "\": "+ howMany(a,b));
   }
}