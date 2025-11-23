
import java.util.*;

public class FourthRatings {
    
    public FourthRatings() {
       FirstRatings fr = new FirstRatings();
    }  
    
    private double getAverageByID (String id, int minimalRaters){
        int countRaters = 0;
        double total = 0.0;
        
        for (Rater r : RaterDatabase.getRaters()) {
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
    
    private double dotProduct(Rater me, Rater r){
        double sum = 0.0;
        for (String movieID : me.getItemsRated()){
            if(r.hasRating(movieID)){
                double ratingMe = me.getRating(movieID) - 5.0;
                double ratingR = r.getRating(movieID) - 5.0;
                sum += ratingMe * ratingR;
            }
        }       
        return sum;
    }
    
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarities = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        
        for (Rater r : RaterDatabase.getRaters()) {
            if (!r.getID().equals(id)) {
                double similarity = dotProduct(me, r);
                if (similarity > 0) {
                    similarities.add(new Rating(r.getID(), similarity));
                }
            }
        }
        
        // Tri décroissant par similarité
        Collections.sort(similarities, Collections.reverseOrder());
        return similarities;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> weightedRatings = new ArrayList<>();
        ArrayList<Rating> similarRaters = getSimilarities(id);
    
        // Limite aux top numSimilarRaters
        if (similarRaters.size() > numSimilarRaters) {
            similarRaters = new ArrayList<>(similarRaters.subList(0, numSimilarRaters));
        }
    
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String movieID : movies) {
            double weightedSum = 0.0;
            int count = 0;
    
            for (Rating r : similarRaters) {
                Rater rater = RaterDatabase.getRater(r.getItem());
                if (rater.hasRating(movieID)) {
                    weightedSum += r.getValue() * rater.getRating(movieID);
                    count++;
                }
            }
    
            if (count >= minimalRaters) {
                double weightedAvg = weightedSum / count;
                weightedRatings.add(new Rating(movieID, weightedAvg));
            }
        }
    
        Collections.sort(weightedRatings, Collections.reverseOrder());
        return weightedRatings;
    }    

    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> weightedRatings = new ArrayList<>();
        ArrayList<Rating> similarRaters = getSimilarities(id);
    
        if (similarRaters.size() > numSimilarRaters) {
            similarRaters = new ArrayList<>(similarRaters.subList(0, numSimilarRaters));
        }
    
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for (String movieID : movies) {
            double weightedSum = 0.0;
            int count = 0;
    
            for (Rating r : similarRaters) {
                Rater rater = RaterDatabase.getRater(r.getItem());
                if (rater.hasRating(movieID)) {
                    weightedSum += r.getValue() * rater.getRating(movieID);
                    count++;
                }
            }
    
            if (count >= minimalRaters) {
                double weightedAvg = weightedSum / count;
                weightedRatings.add(new Rating(movieID, weightedAvg));
            }
        }
    
        Collections.sort(weightedRatings, Collections.reverseOrder());
        return weightedRatings;
    }
}