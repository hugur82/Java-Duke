import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*; 

public class FirstRatings
{
    public ArrayList<Movie> loadMovies (String filename){
        ArrayList<Movie> al=new ArrayList<>();
        FileResource fr = new FileResource("data/"+filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord rec: parser){
            try {
            al.add(new Movie(
            rec.get("id"),
            rec.get("title"),
            rec.get("year"),
            rec.get("genre"),
            rec.get("director"),
            rec.get("country"),
            rec.get("poster"),
            Integer.parseInt(rec.get("minutes"))
            ));
            } catch (Exception e) {
                System.out.println("Erreur sur la ligne : " + rec);
            }
        }
        
        return al;
    }
    
     
    public ArrayList<Rater> loadRaters (String filename){
       ArrayList<Rater> raters = new ArrayList<>();
       FileResource fr = new FileResource("data/" + filename);
       CSVParser parser = fr.getCSVParser();
       for ( CSVRecord rec : parser){
            String raterID = rec.get("rater_id");
            String movieID = rec.get("movie_id");
            double rating = Double.parseDouble(rec.get("rating"));
            
            Rater rater = null;
            for (Rater rt : raters) {
                if (rt.getID().equals(raterID)) {
                    rater = rt;
                    break;
                }
            }
             // Si le rater n’existe pas, on le crée
            if (rater == null) {
                rater = new Rater(raterID);
                raters.add(rater);
            }

             // Ajoute la note
            rater.addRating(movieID, rating);
       }
       return raters; 
    }
    
    
    public void filteredDuringMovies(){
        String fileName ="ratedmoviesfull.csv";
        ArrayList<Movie> moviesList =loadMovies(fileName);
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        int limit=150;
        for ( Movie el: moviesList){
            if(el.getMinutes() > limit){
                filteredMovies.add(el);
            }
        }
        
        for (Movie m : filteredMovies){
            System.out.println(m);
        }
        System.out.println("Le nombre de film Total: "+moviesList.size());
        System.out.println("\nLe nombre de film qui durent plus de "+ limit +" minutes est égal à "+ filteredMovies.size());
        
    }
    
    public void directorsWithMostMovies() {
        String fileName = "ratedmoviesfull.csv";
        ArrayList<Movie> moviesList = loadMovies(fileName);
    
        // Map pour stocker le nombre de films par réalisateur
        HashMap<String, Integer> directorCounts = new HashMap<>();
    
        for (Movie m : moviesList) {
            String directorsStr = m.getDirector(); // peut contenir plusieurs réalisateurs séparés par ","
            String[] directors = directorsStr.split(","); // sépare les réalisateurs
            for (String director : directors) {
                director = director.trim(); // enlève les espaces éventuels
                directorCounts.put(director, directorCounts.getOrDefault(director, 0) + 1);
            }
        }
    
        // Trouver le nombre maximum de films réalisés
        int maxMovies = 0;
        for (int count : directorCounts.values()) {
            if (count > maxMovies) {
                maxMovies = count;
            }
        }
    
        // Trouver les réalisateurs ayant ce nombre maximum
        ArrayList<String> maxDirectors = new ArrayList<>();
        for (String director : directorCounts.keySet()) {
            if (directorCounts.get(director) == maxMovies) {
                maxDirectors.add(director);
            }
        }
        System.out.println("Le nombre de film Total: "+moviesList.size());
        System.out.println("Maximum number of movies by a director: " + maxMovies);
        System.out.println("Directors with this many movies: " + maxDirectors);
    }   
    
    public void filteredGenderMovies (){
        String fileName ="ratedmoviesfull.csv";
        ArrayList<Movie> moviesList =loadMovies(fileName);
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        String gender="Comedy";
        for ( Movie el: moviesList){
            if(el.getGenres().contains(gender)){
                filteredMovies.add(el);
            }
        }
        
        for (Movie m : filteredMovies){
            System.out.println(m);
        }
        System.out.println("Le nombre de film Total: "+moviesList.size());
        System.out.println("\n nombre de film du genre "+ gender +" est egal à "+ filteredMovies.size());
        
    }
   
    public void testLoadRaters() {
        // 1. Charger les raters depuis le fichier
        ArrayList<Rater> raters = loadRaters("ratings.csv");
        
        // 2. Afficher le nombre total de raters
        System.out.println("Total number of raters: " + raters.size());
        
        // 3. Pour chaque rater, afficher son ID et le nombre de notes, puis chaque note
        for (Rater r : raters) {
            System.out.println("Rater ID: " + r.getID() + ", number of ratings: " + r.numRatings());
            for (String movieID : r.getItemsRated()) {
                System.out.println("   Movie ID: " + movieID + ", Rating: " + r.getRating(movieID));
            }
        }
        
        // 4. Trouver le nombre de ratings pour un rater particulier, par exemple rater_id = "2"
        String targetRaterID = "193";
        for (Rater r : raters) {
            if (r.getID().equals(targetRaterID)) {
                System.out.println("Rater " + targetRaterID + " has " + r.numRatings() + " ratings.");
                break;
            }
        }
        
        // 5. Trouver le maximum de ratings par n'importe quel rater et qui sont ces raters
        int maxRatings = 0;
        for (Rater r : raters) {
            if (r.numRatings() > maxRatings) {
                maxRatings = r.numRatings();
            }
        }
        System.out.println("Maximum number of ratings by any rater: " + maxRatings);
        
        System.out.print("Raters with maximum ratings: ");
        for (Rater r : raters) {
            if (r.numRatings() == maxRatings) {
                System.out.print(r.getID() + " ");
            }
        }
        System.out.println();
        
        // 6. Trouver combien de fois un film particulier a été noté, par exemple movieID = "1798709"
        String targetMovieID = "1798709";
        int countMovieRatings = 0;
        for (Rater r : raters) {
            if (r.hasRating(targetMovieID)) {
                countMovieRatings++;
            }
        }
        System.out.println("Movie " + targetMovieID + " was rated by " + countMovieRatings + " raters.");
        
        // 7. Déterminer combien de films différents ont été notés
        HashSet<String> allMovies = new HashSet<>();
        for (Rater r : raters) {
            allMovies.addAll(r.getItemsRated());
        }
        System.out.println("Number of different movies rated: " + allMovies.size());
    }

    
    public void testLoadMovies (){
           
        String filename ="ratedmoviesfull.csv";
        ArrayList<Movie> moviesList =loadMovies(filename);
        
        for(Movie el:moviesList){
             System.out.println(el);
        }
        System.out.println("Le nombre de film Total: "+moviesList.size());
    }
}