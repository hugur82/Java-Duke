
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         
         records = new ArrayList<LogEntry>();
         
     }
        
     public void readFile(String filename) {
         
         FileResource fr = new FileResource(filename);
         for(String line: fr.lines()){
             records.add(WebLogParser.parseEntry(line)); 
         }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public void printAllHigherThanNum(int num){
         for (LogEntry le : records){
             if(le.getStatusCode() > num){
                 System.out.println(le);
             }
         }
     }
     
     public int countUniqueIPs(){
         ArrayList<String> uniqueIp = new ArrayList<String>();
         for(LogEntry le : records){
             if(!uniqueIp.contains(le.getIpAddress())){
                 uniqueIp.add(le.getIpAddress());
             }
         }
         
         return uniqueIp.size();
     }
     
     public HashMap<String,Integer> countVisitsPerIP(){
         HashMap<String,Integer> counts = new HashMap<>();
         for ( LogEntry le : records){
             if(! counts.containsKey(le.getIpAddress())){ 
                 counts.put(le.getIpAddress(),1);
             }
             else{
                 counts.put(le.getIpAddress(),counts.get(le.getIpAddress()) +1);
             }
         }
         return counts;
     }
     
     public int mostNumberVisitsByIP(HashMap<String, Integer> count){
         
         int most=0;
         for(Integer v : count.values()){
             if (v>most) most = v;
         }
         return most;
     }
     
     public int uniqueIPVisitsOnDay (String someday){
         ArrayList<LogEntry> ipAddressOnDay = new ArrayList<LogEntry>();
         ArrayList<String> uniqueIp = new ArrayList<String>();
         for(LogEntry le : records){
             String d = le.getAccessTime().toString().substring(4,10);
             if (someday.equals(d)){
                 ipAddressOnDay.add(le);
             }
         }
         for (LogEntry le : ipAddressOnDay){
             if(!uniqueIp.contains(le.getIpAddress())){
                 uniqueIp.add(le.getIpAddress());
             }
         }
         return uniqueIp.size();
     }
     
     public int countUniqueIPsInRange (int low, int high){
         ArrayList<LogEntry> ipAdressInRange = new ArrayList<>();
         ArrayList<String> uniqueIp = new ArrayList<>();
         for(LogEntry le : records){
             int status = le.getStatusCode();
             if(status >= low && status <= high){
                 ipAdressInRange.add(le);
             }
         }
         
         for (LogEntry le : ipAdressInRange){
             if(!uniqueIp.contains(le.getIpAddress())){
                 uniqueIp.add(le.getIpAddress());
             }
         }
         return uniqueIp.size();
     }
     
     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> count){
         ArrayList<String> IPAddressList = new ArrayList<String>();
         int max = 0;
         for(String s : count.keySet()){
             if(count.get(s) > max){
                 max = count.get(s);
                 IPAddressList.clear();
                 IPAddressList.add(s);
             } else if(count.get(s) == max){
                 IPAddressList.add(s);
             }
         }
         
         return IPAddressList;
     }
     
     public HashMap<String, ArrayList<String>> iPsForDays(){
         HashMap<String, ArrayList<String>> mapList = new HashMap<>();
        
         for(LogEntry le : records){
             String d = le.getAccessTime().toString().substring(4,10);
             String ip = le.getIpAddress();
             //System.out.println("d = " + d);
             if(!mapList.containsKey(d)){
                ArrayList<String> ipList= new ArrayList<>();
                ipList.add(ip);
                mapList.put(d,ipList);  
             }
             else {
                 mapList.get(d).add(ip);
             }
         }
         
         return mapList;
     }
     
     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> hmap){
         int max = 0;
         String res="";
         for (String s : hmap.keySet()){
            int currSize = hmap.get(s).size();
            if (currSize > max){
            max = currSize;
            res= s;
            }
         }
         return res;
     }
     
     public ArrayList<String> iPsWithMostVisitsOnDay(
        HashMap<String, ArrayList<String>> hmap, String someday) {
        //System.out.println("Test des parametre" + hmap + someday);
        ArrayList<String> mostVisited = new ArrayList<>();
    
        // 1️⃣ récupérer la liste des IPs pour le jour donné
        ArrayList<String> ipsOnDay = hmap.get(someday);
        // System.out.println("Test des ips on day" + ipsOnDay );
        if (ipsOnDay == null) {
            return mostVisited; // aucun log pour ce jour
        }
    
        // 2️⃣ compter combien de fois chaque IP apparaît
        HashMap<String, Integer> counts = new HashMap<>();
         
        for (String ip : ipsOnDay) {
            counts.put(ip, counts.getOrDefault(ip, 0) + 1);
        }
         System.out.println("counts: " + counts );
        // 3️⃣ trouver le nombre maximum d’occurrences
        int max = 0;
        for (int count : counts.values()) {
            if (count > max) {
                max = count;
            }
        }
    
        // 4️⃣ ajouter toutes les IPs qui ont le maximum à la liste
        for (String ip : counts.keySet()) {
            if (counts.get(ip) == max) {
                mostVisited.add(ip);
            }
        }
    
        return mostVisited;
    }
}
