import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }
   
    
    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void testMatchAllFilter2(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        // for( QuakeEntry qe : list){
            // System.out.println(qe);
        // }
        System.out.println("# quakes read: "+list.size());
        
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(0.0,5.0,"Magnitude"));
        maf.addFilter(new PhraseFilter("any","e","PhraseFilter"));
        Location city=new Location(55.7308, 9.1153);
        double dist = 3000;
        maf.addFilter(new DistanceFilter(city,dist*1000,"distance"));
        ArrayList<QuakeEntry> filtered=filter(list,maf);
        for (QuakeEntry qe: filtered){
            System.out.println(qe);
        }
        System.out.println("Found " + filtered.size() + " quakes that match all criteria");
    }
    
    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        double dist = 1000;
        Location city = new Location(39.7392, -104.9903);
        Filter f = new MagnitudeFilter(3.5,4.5,"magnitude"); 
        Filter f1 = new DepthFilter(-55000,-20000,"depth");
        Filter f2 = new DistanceFilter(city, dist*1000,"distance");
        Filter f3 = new PhraseFilter("end","a","phrase");
        
        ArrayList<QuakeEntry> filtered  = filter(list, f2); 
        
        filtered = filter(filtered,f3);
        for (QuakeEntry qe: filtered) { 
            System.out.println(qe);
        } 
       
        System.out.println("Found " + filtered.size() + " quakes that match criteria");

    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

    public void testMatchAllFilter(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        
        System.out.println("# quakes read: "+list.size());
        
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(1.0,4.0,"magnitude"));
        maf.addFilter(new DepthFilter(-180000,-30000,"depth"));
        maf.addFilter(new PhraseFilter("any","o","phrase"));
        
        ArrayList<QuakeEntry> filtered = filter(list, maf);

        for (QuakeEntry qe : filtered) {
            System.out.println(qe);
        }
    
        System.out.println("Found " + filtered.size() + " quakes that match all criteria");
        
         System.out.println("Filters used are: " + maf.getName());

    }
    
    
}
