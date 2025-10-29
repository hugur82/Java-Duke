
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        // complete method
        System.out.println("Répertoire de travail : " + System.getProperty("user.dir"));

        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        la.printAll();
    }
    
    public void testPrintAllHigherThanNum(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        la.printAllHigherThanNum(400);
    }
    
    public void testUniqueIp(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        System.out.println("there is " + la.countUniqueIPs() + " unique IP adress");
    }
    
    public void testUniqueIPVisitsOnDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        System.out.println("there is " + la.uniqueIPVisitsOnDay("Sep 24") + " unique ip on this date.");
    }
    
    public void testCountUniqueIPsInRange(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        //System.out.println("there is " + la.countUniqueIPsInRange(200,299) + " unique ip in that range.");
        System.out.println("there is " + la.countUniqueIPsInRange(400,499) + " unique ip in that range.");
    }
    
    public void testCountVisitsPerIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        System.out.println("count = " + la.countVisitsPerIP());
    }
    
    public void testMostNumberVisitsByIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        System.out.println("maximum number of visit = " + la.mostNumberVisitsByIP(la.countVisitsPerIP()));
        
    }
    
    public void testIPsMostVisits(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        System.out.println("maximum number of visit adresses = " + la.iPsMostVisits(la.countVisitsPerIP()));
        
    }
    
    public void testIPsForDays(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        System.out.println("All adresses " + la.iPsForDays());
        
    }
    
    public void testDayWithMostIPVisits(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log");
        System.out.println("most visited day is " + la.dayWithMostIPVisits(la.iPsForDays()));
    }
    
    public void testIPsWithMostVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("data/weblog2_log"); // assure-toi que le fichier est dans le bon dossier
    
        // Construire la map jours → IPs
        HashMap<String, ArrayList<String>> daysMap = la.iPsForDays();
        //System.out.println("Clés de la map : " + daysMap.keySet());

        // Tester pour "Sep 30"
        ArrayList<String> mostVisited = la.iPsWithMostVisitsOnDay(daysMap, "Sep 29");
    
        System.out.println("IP(s) with most visits on sep 29 :");
        for (String ip : mostVisited) {
            System.out.println(ip);
        }
    }
    
    
}
