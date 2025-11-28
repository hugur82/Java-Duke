import java.util.*;

public class EfficientMarkovModel extends AbstractMarkovModel {
    private int myOrder;
    private HashMap<String, ArrayList<String>> myMap;

    public EfficientMarkovModel(int order) {
        myOrder = order;
        myRandom = new Random();
        myMap = new HashMap<>();
    }
    
    @Override
    public void setTraining(String s) {
        myText = s.trim();
        buildMap();             // 🚀 on construit la map
        printHashMapInfo();     // 👉 commenter après test
    }
    
    @Override
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    @Override
    public String toString() {
        return "EfficientMarkovModel of order " + myOrder;
    }

    // ---------------------------------------------------------
    // 1️⃣ buildMap : construit la HashMap une seule fois
    // ---------------------------------------------------------
    private void buildMap() {
        myMap.clear();

        for (int i = 0; i <= myText.length() - myOrder; i++) {

            String key = myText.substring(i, i + myOrder);

            String follow = "";
            if (i + myOrder < myText.length()) {
                follow = myText.substring(i + myOrder, i + myOrder + 1);
            }

            myMap.putIfAbsent(key, new ArrayList<>());

            if (!follow.isEmpty()) {
                myMap.get(key).add(follow);
            }
        }
    }

    // ---------------------------------------------------------
    // 2️⃣ getFollows : beaucoup plus court !
    // ---------------------------------------------------------
    @Override
    protected ArrayList<String> getFollows(String key) {
        return myMap.getOrDefault(key, new ArrayList<>());
    }

    // ---------------------------------------------------------
    // 3️⃣ getRandomText : identique à MarkovModel
    // ---------------------------------------------------------
    @Override
    public String getRandomText(int numChars) {
        if (myText == null || myText.length() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - myOrder);
        String key = myText.substring(index, index + myOrder);
        sb.append(key);

        for (int i = 0; i < numChars - myOrder; i++) {
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) break;

            int randIndex = myRandom.nextInt(follows.size());
            String next = follows.get(randIndex);
            sb.append(next);

            key = key.substring(1) + next;
        }

        return sb.toString();
    }

    // ---------------------------------------------------------
    // 4️⃣ printHashMapInfo (debug)
    // ---------------------------------------------------------
    public void printHashMapInfo() {

        // if (myMap.size() < 50) {
            // System.out.println(myMap);
        // }

        System.out.println("Number of keys: " + myMap.size());

        int maxSize = 0;
        for (ArrayList<String> al : myMap.values()) {
            if (al.size() > maxSize) {
                maxSize = al.size();
            }
        }

        System.out.println("Largest value size: " + maxSize);
        System.out.print("Keys with max value: ");

        for (String key : myMap.keySet()) {
            if (myMap.get(key).size() == maxSize) {
                System.out.print(key + " ");
            }
        }
        System.out.println();
    }
}
