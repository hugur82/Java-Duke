
/**
 * Décrivez votre classe PhraseFilter ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class PhraseFilter implements Filter
{
   private String where;
   private String phrase;
   private String name;
   
   public PhraseFilter (String where, String phrase,String name){
       this.where = where;
       this.phrase = phrase;
       this.name = name;
   }
   
   @Override
   public boolean satisfies(QuakeEntry qe){
       String title = qe.getInfo();
       
       if (where.equals("start")){
           return title.startsWith(phrase);
       } else if (where.equals("end")){
           return title.endsWith(phrase);
       }else if (where.equals("any")){
           return title.contains(phrase);
       }
       return false;
   }
   
   @Override
   public String getName(){
       return name;
   }
}