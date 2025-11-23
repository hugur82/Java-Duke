
import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
    }
    
    public SecondRatings(String moviefile , String ratingsfile){
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getMovieSize(){
        return myMovies.size();
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
    
    public String getTitle (String id){
        for(Movie el : myMovies){
            if (el.getID().equals(id)){
                return el.getTitle();
            }
        }
        return "ID Was Not Find";
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters){
          ArrayList<Rating> ratingsList = new ArrayList<>();

    for (Movie m : myMovies) {
        String movieID = m.getID();
        double avg = getAverageByID(movieID, minimalRaters);
        if (avg > 0.0) {
            ratingsList.add(new Rating(movieID, avg));
        }
    }

    return ratingsList;
    }
    
    public String getID (String title){
        for(Movie movie :myMovies){
            if(movie.getTitle().equals(title)){
                return movie.getID();
            }
        }
        return "NO SUCH TITLE.";
    }
}