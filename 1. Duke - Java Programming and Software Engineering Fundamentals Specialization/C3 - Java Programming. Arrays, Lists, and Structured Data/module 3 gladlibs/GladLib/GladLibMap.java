import edu.duke.*;
import java.util.*;
import java.io.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedWords;       // mots déjà utilisés
    private ArrayList<String> usedCategories;  // catégories déjà utilisées
    private Random myRandom;
    private String dataSourceDirectory;

    // Constructeurs
    public GladLibMap() {
        this("data");
    }

    public GladLibMap(String source) {
        myMap = new HashMap<String, ArrayList<String>>();
        usedWords = new ArrayList<String>();
        usedCategories = new ArrayList<String>();
        myRandom = new Random();
        dataSourceDirectory = source;
        initializeFromSource(source);
    }

    // Charger les listes de mots à partir des fichiers
    private void initializeFromSource(String source) {
        String[] labels = {
            "adjective", "noun", "color", "country", "name",
            "animal", "timeframe", "verb", "fruit"
        };

        for (String s : labels) {
            ArrayList<String> list = readIt(source + "/" + s + ".txt");
            myMap.put(s, list);
        }
    }

    // Lire un fichier texte et renvoyer la liste des mots
    private ArrayList<String> readIt(String filename) {
        ArrayList<String> list = new ArrayList<String>();
        FileResource fr = new FileResource(filename);
        for (String word : fr.words()) {
            list.add(word);
        }
        return list;
    }

    // Choisir un mot aléatoire dans une liste
    private String randomFrom(ArrayList<String> source) {
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    // Remplacer les if par un accès direct à myMap
    private String getSubstitute(String label) {
        if (label.equals("number")) {
            return "" + myRandom.nextInt(50);
        }

        if (!myMap.containsKey(label)) {
            System.out.println("*** Warning: unknown category " + label + " ***");
            return "**UNKNOWN**";
        }

        // Suivi des catégories utilisées
        if (!usedCategories.contains(label)) {
            usedCategories.add(label);
        }

        // Tirage d’un mot non encore utilisé
        String word;
        do {
            word = randomFrom(myMap.get(label));
        } while (usedWords.contains(word));

        usedWords.add(word);
        return word;
    }

    // Créer un texte avec remplacements
    private String processWord(String w) {
        int first = w.indexOf("<");
        int last = w.indexOf(">", first);
        if (first == -1 || last == -1) {
            return w;
        }

        String prefix = w.substring(0, first);
        String suffix = w.substring(last + 1);
        String category = w.substring(first + 1, last);

        String sub = getSubstitute(category);
        return prefix + sub + suffix;
    }

    // Générer l’histoire complète
    private void printStory(String source) {
        FileResource storyFile = new FileResource(source);
        usedWords.clear();
        usedCategories.clear();

        for (String word : storyFile.words()) {
            System.out.print(processWord(word) + " ");
        }
        System.out.println("\n\nTotal words used: " + usedWords.size());
        System.out.println("Total words in all lists: " + totalWordsInMap());
        System.out.println("Total words considered in this story: " + totalWordsConsidered());
    }

    // 🔹 Total de tous les mots possibles (toutes catégories)
    public int totalWordsInMap() {
        int total = 0;
        for (String key : myMap.keySet()) {
            total += myMap.get(key).size();
        }
        return total;
    }

    // 🔹 Total des mots des catégories effectivement utilisées
    public int totalWordsConsidered() {
        int total = 0;
        for (String category : usedCategories) {
            ArrayList<String> list = myMap.get(category);
            total += list.size();
        }
        return total;
    }

    // Méthode de test
    public void makeStory() {
        printStory("data/madtemplate.txt");
    }
}
