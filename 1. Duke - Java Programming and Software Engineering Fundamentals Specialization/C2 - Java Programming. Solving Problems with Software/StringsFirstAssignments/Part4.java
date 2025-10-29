import edu.duke.*;
/**
 * Décrivez votre classe Part4 ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Part4 {
    
    public void line(){
        URLResource ur = new URLResource("https://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for(String line: ur.lines()){
            line=line.toUpperCase();
            if(line.indexOf("YOUTUBE")!=-1){
                          
            System.out.println(line);
            }
        }
    }
    
    public void main(){
        URLResource ur = new URLResource("https://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String item : ur.words()) {
            System.out.println(item);
              String itemLower = item.toLowerCase();
              int pos = itemLower.indexOf("youtube.com");
              if (pos != -1) {
               int beg = itemLower.lastIndexOf("\"",pos);
               int end = itemLower.indexOf("\"",pos+1);
               System.out.println(item.substring(beg +1,end));
               
            }
        }
    }
}
