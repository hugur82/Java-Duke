import java.util.*;

public class MovieRunnerWithFilters
{
  

    public void printAverageRatings (){
        
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        
        System.out.println("Number of rater:\t" + tr.getRaterSize());
        
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        
        System.out.println("Number of movies:\t"+ MovieDatabase.size());
        
        int arg = 35;
        ArrayList<Rating> avgRatings = tr.getAverageRatings(arg);
        Collections.sort(avgRatings);
        System.out.println("found "+ avgRatings.size() +" movies");
        
        for (Rating r : avgRatings) {
             String title = MovieDatabase.getTitle(r.getItem());
            System.out.println(r.getValue() + " " + title);
            }
    }
    
    public void printAverageRatingsByYear(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Read data for " + tr.getRaterSize() + " raters.");
        
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Read data for " + MovieDatabase.size() + " movies.");
        int arg = 20;
        YearAfterFilter f = new YearAfterFilter(2000);
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(arg,f);
        Collections.sort(avgRatings);
        System.out.println("found "+ avgRatings.size() +" movies");
        
        for (Rating r : avgRatings) {
            String title = MovieDatabase.getTitle(r.getItem());
            System.out.println(r.getValue() + " " + title);
        }
    }
    
    public void printAverageRatingsByGenre(){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Read data for " + tr.getRaterSize() + " raters.");
        
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Read data for " + MovieDatabase.size() + " movies.");
        
        int arg = 20;
        GenreFilter f = new GenreFilter("Comedy");
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(arg,f);
        Collections.sort(avgRatings);
        System.out.println("found " + avgRatings.size() + " movies");
        for(Rating r: avgRatings){
            String title = MovieDatabase.getTitle(r.getItem());
            String gender = MovieDatabase.getGenres(r.getItem());
            System.out.println(r.getValue() + " " + title);
            System.out.println("\t"+ gender);
        }
    }
    
    public void printAverageRatingsByMinutes (){
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Read data for " + tr.getRaterSize() + " raters.");
        
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Read data for " + MovieDatabase.size() + " movies.");
        
        int arg = 5;
        MinutesFilter f = new MinutesFilter(105,135);
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(arg,f);
        Collections.sort(avgRatings);
        System.out.println("found " + avgRatings.size() + " movies");
        
        for(Rating r : avgRatings){
            int time = MovieDatabase.getMinutes(r.getItem());
            String title = MovieDatabase.getTitle(r.getItem());
            
            System.out.println(r.getValue() + " Time: " + time + " " + title);
        }
        
    }
    
    public void printAverageRatingsByDirectors(){
         ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Read data for " + tr.getRaterSize() + " raters.");
        
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Read data for " + MovieDatabase.size() + " movies.");
        
        int arg = 4;
        DirectorsFilter f = new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(arg,f);
        Collections.sort(avgRatings);
        System.out.println("found " + avgRatings.size() + " movies");
        
        for (Rating r : avgRatings) {
            String title = MovieDatabase.getTitle(r.getItem());
            String directors = MovieDatabase.getDirector(r.getItem());
            
            System.out.println(r.getValue() + " " + title);
            System.out.println("\t" + directors);
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Read data for " + tr.getRaterSize() + " raters.");
    
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
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(minimalRaters, filters);
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
    
    public void printAverageRatingsByDirectorsAndMinutes() {
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        System.out.println("Read data for " + tr.getRaterSize() + " raters.");
    
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        System.out.println("Read data for " + MovieDatabase.size() + " movies.");
    
        int minimalRaters = 3;
        int minMinutes = 90;
        int maxMinutes = 180;
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
    
        // Crée un objet AllFilters et ajoute les filtres pour les minutes et les réalisateurs
        AllFilters filters = new AllFilters();
        filters.addFilter(new MinutesFilter(minMinutes, maxMinutes));
        filters.addFilter(new DirectorsFilter(directors));
    
        // Récupère les notes des films qui passent tous les filtres
        ArrayList<Rating> avgRatings = tr.getAverageRatingsByFilter(minimalRaters, filters);
        Collections.sort(avgRatings);
    
        System.out.println("found " + avgRatings.size() + " movies");
    
        // Affiche chaque film avec sa note, sa durée, son titre et ses réalisateurs
        for (Rating r : avgRatings) {
            String title = MovieDatabase.getTitle(r.getItem());
            int minutes = MovieDatabase.getMinutes(r.getItem());
            String movieDirectors = MovieDatabase.getDirector(r.getItem());
    
            System.out.println(r.getValue() + " Time: " + minutes + " " + title);
            System.out.println("\t" + movieDirectors);
        }
    }

}