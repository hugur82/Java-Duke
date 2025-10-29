import edu.duke.*;
import java.util.*;
/**
 * Décrivez votre classe test ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class test
{
    
   private ArrayList<String> adjectiveList;
   private ArrayList<String> nounList;
   private ArrayList<String> colorList;
   private ArrayList<String> countryList;
   private ArrayList<String> nameList;
   private ArrayList<String> animalList;
   private ArrayList<String> timeList;
   private ArrayList<String> verbList;
   private ArrayList<String> fruitList;
   private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
   
   public test(){
       initializeFromSource(dataSourceDirectory);
       myRandom = new Random();
   }
   
   public void initializeFromSource(String source){
       adjectiveList= readIt(source+"/adjective.txt");
       nounList = readIt(source+"/noun.txt");
       colorList = readIt(source+"/color.txt");
       countryList = readIt(source+"/country.txt");
       nameList = readIt(source+"/name.txt");        
       animalList = readIt(source+"/animal.txt");
       timeList = readIt(source+"/timeframe.txt");
       verbList = readIt(source+"/verb.txt");
       fruitList = readIt(source+"/fruit.txt");
   }
   
   public ArrayList<String> readIt(String source){
       ArrayList<String> list = new ArrayList<>();
       if (source.startsWith("http")){
           URLResource resource = new URLResource(source);
           for(String w : resource.lines()){
               list.add(w);
           }
       } else {
           FileResource resource = new FileResource(source);
           for(String w : resource.lines()){
               list.add(w);
           }
       }
       return list;          
   }
   
   private String randomFrom(ArrayList<String> source){
       int idx = myRandom.nextInt(source.size());
       return source.get(idx);
   }
   
   private String getSubstitute(String label) {
        if (label.equals("country")) {
            return randomFrom(countryList);
        }
        if (label.equals("color")){
            return randomFrom(colorList);
        }
        if (label.equals("noun")){
            return randomFrom(nounList);
        }
        if (label.equals("name")){
            return randomFrom(nameList);
        }
        if (label.equals("adjective")){
            return randomFrom(adjectiveList);
        }
        if (label.equals("animal")){
            return randomFrom(animalList);
        }
        if (label.equals("timeframe")){
            return randomFrom(timeList);
        }
        if (label.equals("verb")){
            return randomFrom(verbList);
        }
        if (label.equals("fruit")){
            return randomFrom(fruitList);
        }
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        return "**UNKNOWN**";
   }
    
   private String processWord(String w){
       int startIdx = w.indexOf("<");
       int endIdx = w.indexOf(">");
       if ((startIdx != -1) && (endIdx != -1)){
           w = getSubstitute(w.substring(startIdx + 1, endIdx));
       }
       return w;
   }
   
   private String fromTemplate(String source){
       String story="";
       if (source.startsWith("http")){
           URLResource resource = new URLResource(source);
           for(String word: resource.words()){
               story = story + processWord(word) + " ";
           }
       }else{
           FileResource resource = new FileResource(source);
           for(String word : resource.words()){
               story = story + processWord(word) + " ";
           }
       }
       return story;
   }
   
   private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
   
   public void t(){
       /*System.out.println("adjective: \n" +adjectiveList);
       System.out.println("\n nounList: \n" +nounList);
       System.out.println("\n colorList: \n" +colorList);
       System.out.println("\n countryList: \n" +countryList);
       System.out.println("\n nameList: \n" +nameList);
       System.out.println("\n animalList: \n" +animalList);
       System.out.println("\n timeList: \n" +timeList);
       System.out.println("\n verbList: \n" +verbList);
       *//*
       System.out.println("random from adjective "+ randomFrom(adjectiveList));
       System.out.println("random from adjective "+ randomFrom(adjectiveList));
       System.out.println("random from adjective "+ randomFrom(adjectiveList));
       System.out.println("random from adjective "+ randomFrom(adjectiveList));
       System.out.println("random from adjective "+ randomFrom(adjectiveList));
       System.out.println("random from adjective "+ randomFrom(adjectiveList));
       System.out.println("random from adjective "+ randomFrom(adjectiveList));
       */
      
      String story = fromTemplate("data/madtemplate2.txt");
      printOut(story,60);
   }
}