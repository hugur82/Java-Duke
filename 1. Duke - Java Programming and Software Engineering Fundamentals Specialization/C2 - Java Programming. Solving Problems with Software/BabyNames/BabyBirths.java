import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Décrivez votre classe BabyBirths ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class BabyBirths
{
    public void printNames(){
        FileResource fr = new FileResource();
        for (CSVRecord rec: fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if ( 10000 <= numBorn){           
                System.out.println("Name: " + rec.get(0) +
                            " Gender " + rec.get(1) +
                            " Num Born " + rec.get(2));
            }
        }
    }
    
    

        public void totalBirths(FileResource fr){
            int totalBirths = 0;
            int totalBoys = 0;
            int totalGirls = 0;
            int countBoysNames = 0;
            int countGirlsNames = 0;
    
            for (CSVRecord rec : fr.getCSVParser(false)){
                int numBorn = Integer.parseInt(rec.get(2));
                totalBirths += numBorn;
    
                if (rec.get(1).equals("M")){
                    totalBoys += numBorn;
                    countBoysNames++;
                } else {
                    totalGirls += numBorn;
                    countGirlsNames++;
                }
            }
    
            System.out.println("Total births: " + totalBirths);
            System.out.println("Total boys: " + totalBoys + " | Number of boys' names: " + countBoysNames);
            System.out.println("Total girls: " + totalGirls + " | Number of girls' names: " + countGirlsNames);
        }
    
        public void testTotalBirths(){
            FileResource fr = new FileResource(); // permet de choisir le fichier
            totalBirths(fr);
        }
    
    public int getRank(int year, String name, String gender){
        int rank = 0;
        FileResource fr = new FileResource();
        for(CSVRecord rec: fr.getCSVParser(false)){
            if (rec.get(1).contains(gender)){
                rank++;
                if (rec.get(0).contains(name))
                    return rank;
                }
            
        }
        return -1;
    }
    
    public void testGetRank(){
        int rank = getRank(1961, "Franck", "M");
        System.out.println("The rank of Franck is " + rank);
    
        
    }
    
    public String getName (int year, int rank, String gender){
        
        int count = 0;
        FileResource fr = new FileResource();
        for(CSVRecord rec: fr.getCSVParser(false)){
           if(rec.get(1).contains(gender)){
               count++;
               if (count == rank)
                   return rec.get(0);
           }
        }
        
        return "NO NAME";
    }
    
    public void testGetName(){
        System.out.println("----- get name ----");
        String name = getName(1980,350,"F");
        System.out.println("Le 350 eme Femmale is:" + name);
        name = getName(1982,450,"M");
        System.out.println("Le 450 eme male is:" + name);
        
    }
    
    public void whatIsNameInYear (String name, int year, int newYear, String gender){
        int rank = getRank(year, name, gender);
        String newName = "";
        if(rank != -1){
            newName = getName(newYear, rank, gender);
        }
        System.out.println(name + " born in " + year + " would be " + newName + 
                            " if she was born in " + newYear );
    }
    
    public void testWhatIsNameInYear(){
        whatIsNameInYear("Susan",1972,2014,"F");
        whatIsNameInYear("Owen",1974,2014,"M");
    }
    
    public int getRankFromFile(FileResource fr, String name, String gender) {
        int rank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if (rec.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }

    public int yearOfHighestRank (String name, String gender){
        
         int bestYear = -1;
    int bestRank = Integer.MAX_VALUE;

    DirectoryResource dr = new DirectoryResource();
    for (File f : dr.selectedFiles()) {
        String fileName = f.getName(); // ex: yob1971.csv
        int year = Integer.parseInt(fileName.substring(3, 7));
 
        FileResource fr = new FileResource(f);
        int rank = getRankFromFile(fr, name, gender); // petite variante pour lire un fichier spécifique

        if (rank != -1 && rank < bestRank) { // plus petit rang = meilleur classement
            bestRank = rank;
            bestYear = year;
        }
    }
    return bestYear;
    }
    
    public void testYearOfHighestRank(){
         System.out.println("----- YearOfHighestRank ----");
         int year = yearOfHighestRank("Genevieve", "F");
    System.out.println("Best year for Genevieve (F) is " + year);
    year = yearOfHighestRank("Mich", "M");
    System.out.println("Best year for Mich (M) is " + year);
    }
    
    public int getRankFromFile(File f, String name, String gender) {
        int rank = 0;
        FileResource fr = new FileResource(f);
    
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if (rec.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1; // pas trouvé
    }
    
    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        double total = 0.0;
        int count = 0;
    
        for (File f : dr.selectedFiles()) {
            String fileName = f.getName();   // ex: yob1971.csv
            // extraire l'année
            int year = Integer.parseInt(fileName.replaceAll("[^0-9]", ""));
            
            int rank = getRankFromFile(f, name, gender);
            if (rank != -1) {
                total += rank;
                count++;
            }
        }
    
        if (count == 0) return -1.0;
        return total / count;
    }
    
    public void testGetAverageRank(){
       System.out.println("----- getAverageRank ----");
       double avg = getAverageRank("Susan","F");
       System.out.println("the avg rank for Susan is " + avg);
       avg = getAverageRank("Robert","M");
       System.out.println("the avg rank for Robert is " + avg);
    }
    
    public int getTotalBirthsRankedHigher (int year, String name, String gender){
        int count = 0;
        FileResource fr = new FileResource();
        for (CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).contains(gender)){
                if(rec.get(0).contains(name)) return count;
                count += Integer.parseInt(rec.get(2));
            }
        }
        
        return 0;
    }
    public void testGetTotalBirthsRankedHigher(){
         System.out.println("----- getTotalBirthsRankedHigher ----");
         int count = getTotalBirthsRankedHigher(1990,"Emily","F");
         System.out.println("ther are " + count + " babies before Emily");
         count = getTotalBirthsRankedHigher(1990,"Drew","M");
         System.out.println("ther are " + count + " babies before Drew");
    }
}