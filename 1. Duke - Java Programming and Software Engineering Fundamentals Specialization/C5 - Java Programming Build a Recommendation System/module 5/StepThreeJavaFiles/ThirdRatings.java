
import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
       this("data/ratings.csv");
    }
    
    public ThirdRatings( String ratingsfile){
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    
    public int getRaterSize(){
        return myRaters.size();
    }
    
    private double getAverageByID (String id, int minimalRaters){
        int countRaters = 0;
        double total = 0.0;
    
        for (Rater r : myRaters) {
            if (r.hasRating(id)) {
                countRaters++;
                total += r.getRating(id);
            }
        }
    
        if (countRaters < minimalRaters) {
            return 0.0;
        }
    
        return total / countRaters;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter (int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> filteredRatings  = new ArrayList<>();
         // 1. Récupère les films filtrés par le critère
        ArrayList<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);
        
        for (String movieID : filteredMovies) {
            double avg = getAverageByID(movieID, minimalRaters);
            if (avg > 0.0) {
                filteredRatings.add(new Rating(movieID, avg));
            }
        }
        
        return filteredRatings ;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String id : movies) {
            double avg = getAverageByID(id, minimalRaters);
            if (avg > 0.0) {
                ratingsList.add(new Rating(id, avg));
            }
        }

        return ratingsList;
    }
    
}