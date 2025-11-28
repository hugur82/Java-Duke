import java.util.*;


public class MarkovModel extends AbstractMarkovModel {
   
    private int order;  // <--- taille de la clé (N)

    public MarkovModel(int N) {
       order = N;
    }

    @Override
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

    @Override
    public String toString() {
        return "MarkovModel of order " + order;
    }
}
