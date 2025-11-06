
/**
 * Décrivez votre classe MagnitudeFilter ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class MagnitudeFilter implements Filter
{
    private double minMag;
    private double maxMag;
    private String name;
    
    public MagnitudeFilter(double min, double max,String name){
        minMag = min;
        maxMag = max;
        this.name = name;
    }
    
    @Override
    public boolean satisfies(QuakeEntry qe) {
        return qe.getMagnitude() >= minMag && qe.getMagnitude() <= maxMag;
    }

    
   @Override
   public String getName(){
       return name;
   }
    
    
}