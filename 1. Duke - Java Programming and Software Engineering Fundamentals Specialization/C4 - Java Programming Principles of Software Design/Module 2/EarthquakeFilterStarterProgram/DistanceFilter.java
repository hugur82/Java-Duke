
/**
 * Décrivez votre classe DistanceFilter ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class DistanceFilter implements Filter
{
   private Location location;
   private double maxDist;
   private String name;
   
   public DistanceFilter(Location location, double maxDist,String name){
       this.maxDist = maxDist;
       this.location = location;
       this.name = name;
       
   }
   
   @Override
   public boolean satisfies(QuakeEntry qe){
       return location.distanceTo(qe.getLocation()) < maxDist; 
   }
   
   @Override
   public String getName(){
       return name;
   }
}