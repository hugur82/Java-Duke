import java.util.*;


public class MarkovModel {
    private String myText;
    private Random myRandom;
    private int order;  // <--- taille de la clé (N)

    public MarkovModel(int N) {
        myRandom = new Random();
        order = N;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String s) {
        myText = s.trim();
    }

    public String getRandomText(int numChars) {
        if (myText == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        int index = myRandom.nextInt(myText.length() - order);
        String key = myText.substring(index, index + order);
        sb.append(key);

        for (int k = 0; k < numChars - order; k++) {

            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) break;

            int randIndex = myRandom.nextInt(follows.size());
            String next = follows.get(randIndex);

            sb.append(next);

            key = key.substring(1) + next;
        }

        return sb.toString();
    }

    private ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<>();
        int pos = 0;

        while (pos < myText.length()) {
            int start = myText.indexOf(key, pos);
            if (start == -1) break;

            if (start + key.length() >= myText.length() - 1) break;

            String next = myText.substring(start + key.length(), start + key.length() + 1);
            follows.add(next);

            pos = start + 1;
        }

        return follows;
    }
}
