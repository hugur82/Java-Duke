import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry entry : quakeData){
            if (entry.getMagnitude() > magMin){
                answer.add(entry);
                
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,
        double minDepth, double maxDepth){
        
            ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
            for (QuakeEntry entry : quakeData){
                double depth = entry.getDepth();
                // condition : depth > minDepth ET depth < maxDepth (exclusif)
                if (depth > minDepth && depth < maxDepth) {
                    answer.add(entry);
                }
            }
            
            
            return answer;            
    }
    
    public void quakesOfDepth(){
        double min= -4000;
        double max = -2000;
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        
        System.out.println("read data for " + list.size() + " quakes");
        System.out.println("Find quakes with depth between " + min + " and " + max);

         ArrayList<QuakeEntry> answer = filterByDepth(list,min,max);
    for (QuakeEntry entry : answer){
        System.out.printf("(%.2f, %.2f), mag = %.2f, depth = %.2f, title = %s%n",
            entry.getLocation().getLatitude(),
            entry.getLocation().getLongitude(),
            entry.getMagnitude(),
            entry.getDepth(),
            entry.getInfo());
    }
    System.out.println("Found "+ answer.size() + " quakes that match that criteria");
    }
    
    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for( QuakeEntry entry : quakeData){
            if(distMax > from.distanceTo(entry.getLocation())){
                answer.add(entry);
            }
            
        }
       return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> filteredList = filterByMagnitude(list,5);
        
        System.out.println("read data for "+list.size()+" quakes");
        for( QuakeEntry entry : filteredList){
            System.out.println(entry);
        }
        System.out.println("Found "+filteredList.size()+" quakes that match that criteria");
        
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        Location city = new Location(38.17, -118.82); // Bridgeport, CA
        int distMax = 1000; // en km
        ArrayList<QuakeEntry> answer = filterByDistanceFrom(list, distMax*1000, city);
        
        for(QuakeEntry entry : answer){
            double distKm = city.distanceTo(entry.getLocation()) / 1000.0;
            System.out.println(distKm + " " + entry.getInfo());
        }
        
        System.out.println("found " + answer.size() + " quakes that match criteria");
    }

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where,
        String phrase){
         ArrayList<QuakeEntry> answer = new ArrayList<>();
         
        for (QuakeEntry entry : quakeData) {
            String title = entry.getInfo();
            
            if (where.equals("start") && title.startsWith(phrase)) {
                answer.add(entry);
            }
            else if (where.equals("end") && title.endsWith(phrase)) {
                answer.add(entry);
            }
            else if (where.equals("any") && title.contains(phrase)) {
                answer.add(entry);
            }
        }
            
        return answer;
    }
    
    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        
        String where = "any";
        String phrase = "Can";
    
        System.out.println("read data for " + list.size() + " quakes");
        System.out.println("Find quakes where title " + where + "s with \"" + phrase + "\"");
    
        ArrayList<QuakeEntry> answer = filterByPhrase(list, where, phrase);
    
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
    
        System.out.println("Found " + answer.size() + " quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
