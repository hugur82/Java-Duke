import java.util.*;

public class MarkovZero extends AbstractMarkovModel {
    @Override
    public String toString() {
        return "MarkovModel of order 0";
    }

    @Override
    public String getRandomText(int numChars) {
        if (myText == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numChars; i++) {
            int index = myRandom.nextInt(myText.length());
            sb.append(myText.charAt(index));
        }
        return sb.toString();
    }
}
