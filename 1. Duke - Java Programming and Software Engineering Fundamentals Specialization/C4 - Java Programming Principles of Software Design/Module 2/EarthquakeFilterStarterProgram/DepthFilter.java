
/**
 * Décrivez votre classe DepthFilter ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class DepthFilter implements Filter
{
    private double minDepth;
    private double maxDepth;
    private String name;
    
    public DepthFilter(double min, double max, String name){
        minDepth = min;
        maxDepth = max;
        this.name=name;
    }
    @Override
    public boolean satisfies(QuakeEntry qe){
        return qe.getDepth() >= minDepth && qe.getDepth() <= maxDepth;
    }
    
    
   @Override
   public String getName(){
       return name;
   }
}