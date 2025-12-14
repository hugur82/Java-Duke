
public class MinutesFilter implements Filter
{
    int min;
    int max;
    
    public MinutesFilter(int min, int max){
        this.min = min;
        this.max = max;
    }
    
    public boolean satisfies(String id){
        int currMinutes = MovieDatabase.getMinutes(id);
        
        return (min <= currMinutes && currMinutes <= max);
    }
}