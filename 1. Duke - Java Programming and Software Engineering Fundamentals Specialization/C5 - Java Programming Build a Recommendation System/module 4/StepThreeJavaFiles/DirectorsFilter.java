import java.util.*;

public class DirectorsFilter implements Filter
{
    private ArrayList<String> directorsList= new ArrayList<>();
    
    public DirectorsFilter(String directors){
        String[] directorsTemp = directors.split(",");
        for(String el : directorsTemp){
            directorsList.add(el.trim());
        }
    }
    
    @Override
    public boolean satisfies(String id) {
        String movieDirectors = MovieDatabase.getDirector(id); // ex : "Charles Chaplin, Spike Jonze"
        String[] movieDirs = movieDirectors.split(",");
        for (String md : movieDirs) {
            md = md.trim(); // enlever espaces
            if (directorsList.contains(md)) {
                return true;
            }
        }
        return false;
    }
}