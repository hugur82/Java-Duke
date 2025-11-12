
/**
 * Write a description of class QuakeSortInPlace here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class QuakeSortInPlace {
    public QuakeSortInPlace() {
        // TODO Auto-generated constructor stub
    }
    
    public int getLargestDepth(ArrayList<QuakeEntry> quakeData, int from){
        int idx = from;
        double maxDepth=quakeData.get(from).getDepth() ;
        for (int k = from+1; k <quakeData.size();k++){
            if (quakeData.get(k).getDepth() > maxDepth)
            {
                maxDepth=quakeData.get(k).getDepth();
                idx=k;
            }
        }
        
        return idx;
    }
    
    public void sortByLargestDepth(ArrayList<QuakeEntry> in){
        for (int i=0;i< 70;i++){
        //for (int i=0;i< in.size();i++){
            int maxIdx = getLargestDepth(in, i);
            
            QuakeEntry qi = in.get(i);
            QuakeEntry qmax = in.get(maxIdx);
            in.set(i,qmax);
            in.set(maxIdx,qi);
        }
    }
    
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = from;
        for (int i=from+1; i< quakes.size(); i++) {
            if (quakes.get(i).getMagnitude() < quakes.get(minIdx).getMagnitude()) {
                minIdx = i;
            }
        }
        return minIdx;
    }
    
    public void sortByMagnitude(ArrayList<QuakeEntry> in) {
       
       for (int i=0; i< in.size(); i++) {
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
        }
        
    }
    
    public void onePassBubbleSort(ArrayList<QuakeEntry> quakeData,int numSorted){
        for(int k = 0; k < quakeData.size() - numSorted - 1; k++){
            QuakeEntry currEntry = quakeData.get(k);
            QuakeEntry nextEntry = quakeData.get(k+1);
            
            if(currEntry.getMagnitude() > nextEntry.getMagnitude()){
                quakeData.set(k,nextEntry);
                quakeData.set(k+1,currEntry);
            }
        }
    }
    
    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> in){
        int n = in.size();
        
        //System.out.println("Initial Quakes:");
        printQuakes(in);
    
        for(int i=0;i< n-1;i++){
            onePassBubbleSort(in,i);
            //System.out.println("Printing Quakes after pass " + i);
        printQuakes(in);
        }
        //System.out.println("EarthQuakes in sorted order:");
        printQuakes(in);
    
    }
    
    public void printQuakes(ArrayList<QuakeEntry> quakes) {
    for (QuakeEntry q : quakes) {
        System.out.println(q);
    }
}
    
    public boolean checkInSortedOrder(ArrayList<QuakeEntry> quakes){
        for(int i=0;i< quakes.size()-1;i++)
            if(quakes.get(i).getMagnitude()>quakes.get(i+1).getMagnitude()) return false;
        
        return true;
    }

    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in){
        int N = in.size();
        int passes = 0;

        for (int pass = 0; pass < N - 1; pass++) {
            onePassBubbleSort(in, pass);
            passes++;
    
            // Vérifier si la liste est triée après cette passe
            if (checkInSortedOrder(in)) {
                break; // arrêter tôt si déjà triée
            }
        }
    
        // Afficher le résultat
        printQuakes(in);
        System.out.println("Number of passes needed: " + passes);

    }
    
    public void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in) {
    int passes = 0;

        for (int i = 0; i < in.size(); i++) {
            // Vérifier si la liste est déjà triée avant cette passe
            if (checkInSortedOrder(in)) {
                break;
            }
    
            // Trouver l'élément avec la plus petite magnitude
            int minIdx = getSmallestMagnitude(in, i);
    
            // Faire le swap (une passe = un swap)
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i, qmin);
            in.set(minIdx, qi);
    
            passes++; // une passe effectuée
        }

    // Affichage final
    printQuakes(in);
    System.out.println("Number of passes needed: " + passes);
    }
    
    public void testSort() {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/earthQuakeDataWeekDec6sample1.atom";
        //String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  
       
        System.out.println("read data for "+list.size()+" quakes");    
        //sortByMagnitude(list);
        //sortByLargestDepth(list);
        //sortByMagnitudeWithBubbleSort(list);
        sortByMagnitudeWithBubbleSortWithCheck(list);
        //sortByMagnitudeWithCheck(list);
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
            
        } 
        
    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
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
}
