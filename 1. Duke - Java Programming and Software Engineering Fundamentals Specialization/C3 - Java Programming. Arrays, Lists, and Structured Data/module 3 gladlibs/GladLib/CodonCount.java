import java.util.*;
import edu.duke.*;
import java.io.*;
/**
 * Décrivez votre classe CodonCount ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class CodonCount
{
   private HashMap<String,Integer> codonMap ;
   
   public CodonCount(){
       codonMap  = new HashMap<>();
   }
   
   public void buildCodonMap (int start, String dna){
       codonMap.clear();
       dna = dna.trim().toUpperCase();
       
       for(int i = start; i + 3 < dna.length(); i+= 3){
           String codon = dna.substring(i,i+3);
           codonMap.put(codon, codonMap.getOrDefault(codon, 0) + 1);
       }
   }
   
   public String getMostCommonCodon(){
       String mostCommon = "";
       int max = 0;
       
       for(Map.Entry<String,Integer> entry : codonMap.entrySet()){
           if(entry.getValue() > max){
               max = entry.getValue();
               mostCommon = entry.getKey();
           }
       }
       return mostCommon;
   }
   
   public void printCodonCounts (int start, int end){
       for (Map.Entry<String, Integer> entry : codonMap.entrySet()) {
            int count = entry.getValue();
            if (count >= start && count <= end) {
                System.out.println(entry.getKey() + "\t" + count);
            }
        }       
   }
   
   /**
     * Tester method — reads DNA from file and prints analysis for all 3 reading frames
     */
    public void test() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter DNA file name (e.g. smalldna.txt): ");
        String filename = sc.nextLine();

        // Read file content
        StringBuilder dnaBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                dnaBuilder.append(line.trim());
            }
        }

        String dna = dnaBuilder.toString();
        System.out.println("\nDNA Strand: " + dna);

        for (int frame = 0; frame < 3; frame++) {
            buildCodonMap(frame, dna);
            System.out.println("\nReading frame starting with " + frame + " results in " + codonMap.size() + " unique codons");

            String mostCommon = getMostCommonCodon();
            System.out.println("  and most common codon is " + mostCommon + " with count " + codonMap.get(mostCommon));

            System.out.println("Counts of codons between 1 and 5 inclusive are:");
            printCodonCounts(1, 10);
        }

        sc.close();
    }

    // Main entry point
    public static void main(String[] args) throws IOException {
        CodonCount cc = new CodonCount();
        cc.test();
    }
}