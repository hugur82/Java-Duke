import java.util.*;
/**
 * Décrivez votre classe MovieRunnerAverage ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class MovieRunnerAverage
{
   
    public MovieRunnerAverage()
    {
        
    }

    public void getAverageRatingOneMovie(){
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv","ratings.csv");
       
        String title = "Vacation";
        
        String id = sr.getID(title);
        
        
       
        if (id.equals("NO SUCH TITLE.")) {
        System.out.println("Movie not found");
        return;
        }
        
        ArrayList<Rating> avgRatings = sr.getAverageRatings(1);
    
        // Cherche le Rating correspondant à notre film
        for (Rating r : avgRatings) {
            if (r.getItem().equals(id)) {
                System.out.println("Average rating for \"" + title + "\" is " + r.getValue());
            }
        }
    }
   
    public void printAverageRatings ()
    {
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv","ratings.csv");
        System.out.println("\tNumber of movies:\t"+ sr.getMovieSize() +
        "\n\tNumber of rating:\t" + sr.getRaterSize());
        int arg = 12;
        ArrayList<Rating> avgRatings = sr.getAverageRatings(arg);
        Collections.sort(avgRatings);
        for (Rating r : avgRatings) {
        System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
        }
    }
}