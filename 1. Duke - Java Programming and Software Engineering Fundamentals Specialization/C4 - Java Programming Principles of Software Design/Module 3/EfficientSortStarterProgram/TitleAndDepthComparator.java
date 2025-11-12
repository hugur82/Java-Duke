
import java.util.*;
import java.lang.*;
/**
 * Décrivez votre classe TitleAndDepthComparator ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class TitleAndDepthComparator implements Comparator<QuakeEntry>
{
   
   @Override 
   public int compare(QuakeEntry q1, QuakeEntry q2){
      
      int res = q1.getInfo().compareToIgnoreCase(q2.getInfo());
      if (res == 0)
          res = Double.compare(q1.getDepth(),q2.getDepth());
      
      return res;
   }
}