
import java.util.*;
import java.lang.*;

public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry>
{
    @Override
    public int compare(QuakeEntry q1, QuakeEntry q2){
        
        String[] words1 = q1.getInfo().split(" ");
        String lastWord1 = words1[words1.length - 1];
        String[] words2 = q2.getInfo().split(" ");
        String lastWord2 = words2[words2.length - 1];
        int res = lastWord1.compareToIgnoreCase(lastWord2);
        if (res == 0)
            res = Double.compare(q1.getMagnitude(),q2.getMagnitude());
        return res;
    }
}