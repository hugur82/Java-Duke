import java.util.*;
/**
 * Décrivez votre classe MatchAllFilter ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class MatchAllFilter implements Filter
{
   private ArrayList<Filter> allFilter;
   
   public MatchAllFilter(){
       allFilter = new ArrayList<Filter>();
   }
   
   public void addFilter(Filter filter){
       allFilter.add(filter);
   }
   
   @Override
   public boolean satisfies(QuakeEntry qe){
       for(Filter f : allFilter){
           if (!f.satisfies(qe))
           return false;
       }
       return true;
   }
   
   @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        for(Filter f : allFilter) {
            sb.append(f.getName()).append(" ");
        }
        return sb.toString().trim();
    }
   
}