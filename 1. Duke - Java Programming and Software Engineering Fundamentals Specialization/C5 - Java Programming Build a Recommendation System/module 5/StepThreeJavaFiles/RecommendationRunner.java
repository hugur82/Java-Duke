import java.util.*;
import edu.duke.*;

public class RecommendationRunner implements Recommender
{
    @Override
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> items = new ArrayList<>();
        
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        ArrayList<String> allMovies = MovieDatabase.filterBy(new GenreFilter("Comedy"));
        
        Collections.shuffle(allMovies);
        
        for (int i = 0; i < 15; i++) {
            items.add(allMovies.get(i));
        }
        return items;
    }
    
    
    @Override
    public void printRecommendationsFor(String webRaterID) {       
        RaterDatabase.initialize("ratings.csv");
        MovieDatabase.initialize("ratedmoviesfull.csv");
        
        int numSimilarRaters = 15;
        int minimalRaters = 5;
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> rated = fr.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters);
        
        if (rated.size() == 0) {
            System.out.println("<h1 style='color:red;'>No recommendations available!</h1>");
            return;
        }
        System.out.println("<h1>Recommended Movies</h1>");
        System.out.println("<style>");
        System.out.println("table { margin: 0 15%; }");
        System.out.println("th, td {  border: 1px solid #000; }");
        System.out.println("</style>");
        
        System.out.println("<table>");
        System.out.println("<tr><th>Title</th><th>Year</th><th>Rating</th></tr>");
        
       
        int limit = Math.min(15, rated.size());
        
        for (int i = 0; i < limit; i++) {
            Rating r = rated.get(i);
            String movieID = r.getItem();
            String bgcol=(i%2==0 ? "#CCC":"#999");
            
            System.out.println("<tr style='background-color="+bgcol+"'>");
            System.out.println("<td>" + MovieDatabase.getTitle(movieID) + "</td>");
            System.out.println("<td>" + MovieDatabase.getYear(movieID) + "</td>");
            System.out.println("<td>" + String.format("%.2f", r.getValue()) + "</td>");
            System.out.println("</tr>");
        }
        
        System.out.println("</table>");
    }
     
}