import java.util.*;

public class MarkovModel {
    private String myText;
    private Random myRandom;
    private int N;
    
    public MarkovModel(int n) {
        myRandom = new Random();
        N = n;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String s) {
        myText = s.trim();
    }

    public ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<>();
        int index = 0;
        while (index < myText.length() - key.length()) {
            int pos = myText.indexOf(key, index);
            if (pos == -1) break;
            if (pos + key.length() >= myText.length()) break;
            String next = myText.substring(pos + key.length(), pos + key.length() + 1);
            follows.add(next);
            index = pos + 1;
        }
        return follows;
    }

    public String getRandomText(int numChars) {
        if (myText == null) return "";
        
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - N);
        String key = myText.substring(index, index + N);
        sb.append(key);
        
        for (int k = 0; k < numChars - N; k++) {
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) break;
            String next = follows.get(myRandom.nextInt(follows.size()));
            sb.append(next);
            key = key.substring(1) + next;
        }
        
        return sb.toString();
    }
}
