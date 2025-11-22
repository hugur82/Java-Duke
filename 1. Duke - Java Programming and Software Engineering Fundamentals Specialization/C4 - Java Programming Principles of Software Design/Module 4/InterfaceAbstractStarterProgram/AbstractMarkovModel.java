import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected String myText;
    protected Random myRandom;

    public AbstractMarkovModel() {
        myRandom = new Random();
    }

    @Override
    public void setTraining(String s) {
        myText = s.trim();
    }

    @Override
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    protected ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<>();
        int pos = 0;
        while (pos < myText.length()) {
            int start = myText.indexOf(key, pos);
            if (start == -1 || start + key.length() >= myText.length()) break;
            follows.add(myText.substring(start + key.length(), start + key.length() + 1));
            pos = start + key.length();
        }
        return follows;
    }

    @Override
    abstract public String getRandomText(int numChars);
}
