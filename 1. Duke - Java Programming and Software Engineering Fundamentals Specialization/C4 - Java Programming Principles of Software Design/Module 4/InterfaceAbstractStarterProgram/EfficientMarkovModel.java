import java.util.*;

public class EfficientMarkovModel extends AbstractMarkovModel {
    private int myOrder;
    private HashMap<String, ArrayList<String>> myMap;

    public EfficientMarkovModel(int order) {
        myOrder = order;
    }

    @Override
    public String toString() {
        return "EfficientMarkovModel of order " + myOrder;
    }

    @Override
    public void setTraining(String s) {
        super.setTraining(s);  // si tu hérites d'AbstractMarkovModel
        buildMap();             // construit la HashMap automatiquement
    }

    

    public void buildMap() {
        myText = myText.replace('\n', ' '); 
        myMap = new HashMap<>();
        for (int i = 0; i <= myText.length() - myOrder; i++) {
            String key = myText.substring(i, i + myOrder);
            String next = (i + myOrder >= myText.length()) ? "" : myText.substring(i + myOrder, i + myOrder + 1);
            myMap.putIfAbsent(key, new ArrayList<>());
            if (!next.equals("")) myMap.get(key).add(next);
        }
        printHashMapInfo();
    }

    @Override
    protected ArrayList<String> getFollows(String key) {
        return myMap.getOrDefault(key, new ArrayList<>());
    }

    public void printHashMapInfo() {
        System.out.println("Number of keys: " + myMap.size());
        int max = 0;
        for (ArrayList<String> al : myMap.values()) {
            if (al.size() > max) max = al.size();
        }
        System.out.println("Largest value size: " + max);
        for (String key : myMap.keySet()) {
            if (myMap.get(key).size() == max) System.out.print(key + " ");
        }
        System.out.println();
    }

    @Override
    public String getRandomText(int numChars) {
        if (myText == null) return "";
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - myOrder);
        String key = myText.substring(index, index + myOrder);
        sb.append(key);
        for (int k = 0; k < numChars - myOrder; k++) {
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) break;
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            key = key.substring(1) + next;
        }
        return sb.toString();
    }
}
