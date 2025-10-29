import java.util.Random;

/**
 * Décrivez votre classe DiceRolling ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class DiceRolling
{
     public void Simulate(int rolls){
        Random rand = new Random();
        int[] counts = new int[13]; 
        
        
        for (int k =0; k<rolls;k++){
            int d1 = rand.nextInt(6) + 1;
            int d2 = rand.nextInt(6) + 1;
            counts[d1+d2] +=1 ;
        }
        for(int i=2;i<=12;i++){
            System.out.println(i + "'s\t"+ counts[i]+"\t"+100.0*counts[i]/rolls );
        }
        
    }
    
    public void simpleSimulate(int rolls){
        Random rand = new Random();
        int twos = 0; 
        int twelves = 0;
        
        for (int k =0; k<rolls;k++){
            int d1 = rand.nextInt(6) + 1;
            int d2 = rand.nextInt(6) + 1;
            if(d1 + d2 == 2){
                 twos+=1;   
            }
            else if(d1 +d2 ==12) {
               twelves+=1;
            }
        }
        System.out.println("2's=\t" + twos + "\t" +100.0 *twos/rolls);
        System.out.println("12's=\t" + twelves + "\t" +100.0 *twelves/rolls);
    }
}