import java.util.*;
/**
 * Décrivez votre classe MovieRunnerSimilarRatings ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class MovieRunnerSimilarRatings
{
    public void printAverageRatings (){
        
        //ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize("data/ratings.csv");
        System.out.println("Number of rater:\t" + RaterDatabase.size());
        
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        
        System.out.println("Number of movies:\t"+ MovieDatabase.size());
        
        int arg = 35;
        ArrayList<Rating> avgRatings = fr.getAverageRatings(arg);
        Collections.sort(avgRatings);
        System.out.println("found "+ avgRatings.size() +" movies");
        
        for (Rating r : avgRatings) {
             String title = MovieDatabase.getTitle(r.getItem());
            System.out.println(r.getValue() + " " + title);
            }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize("data/ratings.csv");
        System.out.println("Read data for " + RaterDatabase.size() + " raters.");
    
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Read data for " + MovieDatabase.size() + " movies.");
    
        int minimalRaters = 8;
        int year = 1990;
        String genre = "Drama";
    
        // Crée un objet AllFilters et ajoute les filtres
        AllFilters filters = new AllFilters();
        filters.addFilter(new YearAfterFilter(year));
        filters.addFilter(new GenreFilter(genre));
    
        // Récupère les notes des films qui respectent les critères
        ArrayList<Rating> avgRatings = fr.getAverageRatingsByFilter(minimalRaters, filters);
        Collections.sort(avgRatings);
    
        System.out.println("found " + avgRatings.size() + " movies");
    
        // Affiche chaque film avec sa note, son année et son titre, puis ses genres
        for (Rating r : avgRatings) {
            String title = MovieDatabase.getTitle(r.getItem());
            int movieYear = MovieDatabase.getYear(r.getItem());
            String movieGenres = MovieDatabase.getGenres(r.getItem());
    
            System.out.println(r.getValue() + " " + movieYear + " " + title);
            System.out.println("\t" + movieGenres);
        }
    }
    
    public void printSimilarRatings() {
        FourthRatings fr = new FourthRatings();
    
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        RaterDatabase.initialize("data/ratings.csv");
    
        String raterID = "71";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
    
        ArrayList<Rating> list = fr.getSimilarRatings(raterID, numSimilarRaters, minimalRaters);
    
        for (Rating r : list) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }

    public void printSimilarRatingsByGenre() {
        FourthRatings fr = new FourthRatings();
    
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        RaterDatabase.initialize("data/ratings.csv");
    
        String raterID = "964";
        String genre = "Mystery";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
    
        Filter f = new GenreFilter(genre);
    
        ArrayList<Rating> list = fr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, f);
    
        for (Rating r : list) {
            String id = r.getItem();
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(id));
            System.out.println("   " + MovieDatabase.getGenres(id));
        }
    }

    public void printSimilarRatingsByDirector() {
        FourthRatings fr = new FourthRatings();
    
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        RaterDatabase.initialize("data/ratings.csv");
    
        String raterID = "120";
        int numSimilarRaters = 10;
        int minimalRaters = 2;
        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
    
        Filter f = new DirectorsFilter(directors);
    
        ArrayList<Rating> list = fr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, f);
    
        for (Rating r : list) {
            String id = r.getItem();
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(id));
            System.out.println("   " + MovieDatabase.getDirector(id));
        }
    }
    
    public void printSimilarRatingsByGenreAndMinutes() {
        FourthRatings fr = new FourthRatings();
    
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        RaterDatabase.initialize("data/ratings.csv");
    
        String raterID = "168";
        int numSimilarRaters = 10;
        int minimalRaters = 3;
    
        String genre = "Drama";
        int minMinutes = 80;
        int maxMinutes = 160;
    
        AllFilters af = new AllFilters();
        af.addFilter(new GenreFilter(genre));
        af.addFilter(new MinutesFilter(minMinutes, maxMinutes));
    
        ArrayList<Rating> list = fr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, af);
    
        for (Rating r : list) {
            String id = r.getItem();
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(id)
                    + " (" + MovieDatabase.getMinutes(id) + " min)");
            System.out.println("   " + MovieDatabase.getGenres(id));
        }
    }   
    
    public void printSimilarRatingsByYearAfterAndMinutes() {
        FourthRatings fr = new FourthRatings();
    
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        RaterDatabase.initialize("data/ratings.csv");
    
        String raterID = "314";
        int numSimilarRaters = 10;
        int minimalRaters = 5;
    
        int year = 1975;
        int minMinutes = 70;
        int maxMinutes = 200;
    
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(year));
        af.addFilter(new MinutesFilter(minMinutes, maxMinutes));
    
        ArrayList<Rating> list = fr.getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, af);
    
        for (Rating r : list) {
            String id = r.getItem();
            System.out.println(r.getValue() + " "
                    + MovieDatabase.getTitle(id)
                    + " (" + MovieDatabase.getYear(id)
                    + ", " + MovieDatabase.getMinutes(id) + " min)");
        }
    }    
}