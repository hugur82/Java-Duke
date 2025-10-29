import edu.duke.*;
import org.apache.commons.csv.*;
/**
 * Décrivez votre classe WhichCountriesExport ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class WhichCountriesExport
{
    public void listExporters(CSVParser parser, String exportOfInterest){
        System.out.println("---------------------------------");
        System.out.println("All countries with " + exportOfInterest);
        for(CSVRecord record:parser){
            if(record.get("Exports").contains(exportOfInterest))
                System.out.print(record.get("Country") + " - ");
        }
        System.out.println("---------------------------------");
    }
    
    public String countryInfo(CSVParser parser, String country){
        for(CSVRecord record:parser){
            if(record.get("Country").contains(country))
            return (country + ": "+record.get("Exports") + ": "+ record.get("Value (dollars)"));
        }
        
        return "NOT FOUND";
    }
    
    public  void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        System.out.println("---- Country with "+ exportItem1 + " and " + exportItem2);
        for(CSVRecord record:parser){
            boolean res = record.get("Exports").contains(exportItem1) && record.get("Exports").contains(exportItem2);
            if (res)
            System.out.println(record.get("Country"));
        }
        
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        int count = 0;
        for(CSVRecord record:parser){
            if (record.get("Exports").contains(exportItem)) count++;
        }
        
        System.out.print(exportItem+ ": ");
        return count;
    }

    public void bigExporters(CSVParser parser, String amount){
        
        System.out.println("----------Big Exporters-------------");
        for(CSVRecord record:parser){
            if(record.get("Value (dollars)").length() > amount.length())
            System.out.println(record.get("Country")+" "+ record.get("Value (dollars)"));
        }
    }
    
    public void test(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser,"cotton");
        parser = fr.getCSVParser();
        listExporters(parser,"coffee");
        parser = fr.getCSVParser();
        listExporters(parser,"gold");
        parser = fr.getCSVParser();
        System.out.println("----------test for Nauru-------------");
        System.out.println(countryInfo(parser,"Nauru"));
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser,"cotton","flowers");
        parser = fr.getCSVParser();
        System.out.println("----------Number of country with Item-------------");
        System.out.println("Number of country with Item " + numberOfExporters(parser,"cocoa"));
        parser = fr.getCSVParser();
        bigExporters(parser,"$999,999,999,999");
    }
}