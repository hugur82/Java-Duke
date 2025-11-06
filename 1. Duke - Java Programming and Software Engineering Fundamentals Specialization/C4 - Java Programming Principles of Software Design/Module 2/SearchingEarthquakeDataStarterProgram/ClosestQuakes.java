
/**
 * Find N-closest quakes
 * 
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import java.util.*;

public class ClosestQuakes {
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany) {
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        // Si on demande plus que la taille totale, on limite
        if (howMany > quakeData.size()) {
            howMany = quakeData.size();
        }
    
        for (int i = 0; i < howMany; i++) {
            int minIndex = 0;
            for (int k = 1; k < copy.size(); k++) {
                Location loc = copy.get(k).getLocation();
                Location minLoc = copy.get(minIndex).getLocation();
                if (loc.distanceTo(current) < minLoc.distanceTo(current)) {
                    minIndex = k;
                }
            }
            // On ajoute le plus proche à la liste finale
            answer.add(copy.get(minIndex));
            // On le retire de la copie pour ne pas le reprendre
            copy.remove(minIndex);
        }
        return answer;
    }
    
    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());
    
        Location jakarta  = new Location(-6.211,106.845);
    
        ArrayList<QuakeEntry> close = getClosest(list,jakarta,3);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
