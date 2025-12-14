import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected String myText;
    protected Random myRandom;
    
    public AbstractMarkovModel() {
        myRandom = new Random();
    }
    
    public void setTraining(String s) {
        myText = s.trim();
    }
    
    public void setRandom(int seed) {
    myRandom = new Random(seed);
    }
    
    protected ArrayList<String> getFollows(String key) {
    ArrayList<String> follows = new ArrayList<>();
    int pos = 0;
    while (pos < myText.length()) {
        int start = myText.indexOf(key, pos);
        pos = start + key.length();
    }
    return follows;
}

}
