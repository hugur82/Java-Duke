import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

/**
 * Décrivez votre classe CSVMax ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class CSVMax
{
    public CSVRecord hottestHourInFile(CSVParser parser){
        CSVRecord largestSoFar= null;
        
        for(CSVRecord currentRow:parser){
            largestSoFar = getLargestOfTwo(currentRow,largestSoFar);
        }
        
        return largestSoFar;
    }
    
    public void testHottestInDay(){
        FileResource fr=new FileResource("nc_weather/2012/weather-2012-01-01.csv");
        CSVRecord largest  =  hottestHourInFile(fr.getCSVParser());
        System.out.println("Hottest temperature was "+ largest.get("TemperatureF")+ " at " + largest.get("TimeEST"));
    }
    
    public CSVRecord coldestHourInFile (CSVParser parser){
        CSVRecord coldestSoFar= null;
        
        for(CSVRecord currentRow:parser){
            coldestSoFar = getcoldestOfTwo(currentRow,coldestSoFar);
        }
        
        return coldestSoFar;
    }
    
    public void testColdestInDay(){
        FileResource fr=new FileResource();
        CSVRecord coldest  =  coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature was "+ coldest.get("TemperatureF")+ " at " + coldest.get("TimeEDT"));
    }
    
     public CSVRecord getcoldestOfTwo(CSVRecord currentRow, CSVRecord coldestSoFar){
        
        if (coldestSoFar == null)
                coldestSoFar = currentRow;
            else {
                try {
                    Double.parseDouble(currentRow.get("Humidity"));
                    double currTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                    double maxTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                    if (currTemp < maxTemp)
                        coldestSoFar = currentRow;
                }
                catch (Exception e){
                    
                }
                
            }
        return coldestSoFar;
    }
    
    public String fileWithColdestTemperature (){
        String coldestFile="";
        CSVRecord coldest = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            for(CSVRecord currentRow:parser){
                if (coldest == null){
                    coldest=currentRow;
                    coldestFile = f.getName();
                }
                else {
                    double currTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                    double maxTemp = Double.parseDouble(coldest.get("TemperatureF"));
                    if (currTemp < maxTemp){
                    coldest = currentRow;
                    coldestFile = f.getName();
                    }
                }
            }
        }
        
        return coldestFile;
    }
    
    public CSVRecord coldestInManyDays(){
        CSVRecord coldestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            FileResource fr =new FileResource(f);
            CSVRecord currentRow  =  coldestHourInFile(fr.getCSVParser());
            coldestSoFar = getcoldestOfTwo(currentRow,coldestSoFar);
        }
        return coldestSoFar;
    }
    
    public void testColdestInManyDays(){
        CSVRecord coldest =coldestInManyDays();
        System.out.print("Coldest temperature is "+ coldest.get("TemperatureF") + " at " + coldest.get("DateUTC"));
    }
    
    public void testFileWithColdestTemperature(){
        String fileName = fileWithColdestTemperature();
        
        System.out.println("The coldest temp is on file: " + fileName);
        
    }
    
    public CSVRecord getLargestOfTwo(CSVRecord currentRow, CSVRecord largestSoFar){
        
        if (largestSoFar == null)
                largestSoFar = currentRow;
            else {
                double currTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double maxTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
                if (currTemp > maxTemp)
                    largestSoFar = currentRow;
            }
        return largestSoFar;
    }
    
    public CSVRecord hottestInManyDays(){
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            FileResource fr =new FileResource(f);
            CSVRecord currentRow  =  hottestHourInFile(fr.getCSVParser());
            largestSoFar = getLargestOfTwo(currentRow,largestSoFar);
        }
        return largestSoFar;
    }
    
    public void testHottestInManyDays(){
        CSVRecord largest =hottestInManyDays();
        System.out.print("Hottest temperature is "+ largest.get("TemperatureF") + " at " + largest.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestHumidity = null;
        for (CSVRecord currentRow : parser){
            if ( lowestHumidity == null){
                lowestHumidity = currentRow;
            }
            else {
                try{
                    double currentHumid = Double.parseDouble(currentRow.get("Humidity"));
                    double lowestHumid = Double.parseDouble(lowestHumidity.get("Humidity"));
                    if(currentHumid < lowestHumid)
                    lowestHumidity = currentRow;
                }
               catch (Exception e){
                   
               }
            }
        }
        
        return lowestHumidity;
    }
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord minHumidity = lowestHumidityInFile(parser);
        System.out.println("--------lowest humidity-------");
        System.out.println("The lowest humidity was " + minHumidity.get("Humidity")+ " at " +minHumidity.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles (){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord currentHumidity=null;
        CSVRecord minHumidity=null;
        
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            currentHumidity=lowestHumidityInFile(parser);
            if(minHumidity == null)
                minHumidity=currentHumidity;
            else{
                double valueCurrentHumidity = Double.parseDouble(currentHumidity.get("Humidity"));
                double valueMinHumidity = Double.parseDouble(minHumidity.get("Humidity"));
                if (valueCurrentHumidity < valueMinHumidity)
                    minHumidity=currentHumidity;
            }
        }
        
        return minHumidity;
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord minHumid = lowestHumidityInManyFiles();
        System.out.println("-------------Lowest humidity in many file----------");
        System.out.println("lowest humidity was " + minHumid.get("Humidity") + " at " + minHumid.get("DateUTC"));
        
    }
    
    public double averageTemperatureInFile (CSVParser parser){
        
        double total= 0;
        double count= 0;
        for(CSVRecord currentRow : parser){
            total += Double.parseDouble(currentRow.get("TemperatureF"));
            count++;
        }
        
        return total/count;
    }
    
    public void testAverageTemperatureInFile() {
        System.out.println("-----------AVERAGE TEMP------------");
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avg = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + avg);
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double total=0;
        int count = 0;
        for(CSVRecord currentRow: parser){
            if (value <= Double.parseDouble(currentRow.get("Humidity")))
            {
                total+= Double.parseDouble(currentRow.get("TemperatureF"));
                count++;
            }
        }
        if (count == 0) return 0;
        else
            return total/count;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile(){
        System.out.println("----------Average temp for humidity bigger than...----------");
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avg = averageTemperatureWithHighHumidityInFile(parser,80);
        if (avg <= 0) System.out.println("No temperature with that humidity");
        else {
            System.out.println("Average temperature when high humidity is " + avg);
        }
        
    }
}