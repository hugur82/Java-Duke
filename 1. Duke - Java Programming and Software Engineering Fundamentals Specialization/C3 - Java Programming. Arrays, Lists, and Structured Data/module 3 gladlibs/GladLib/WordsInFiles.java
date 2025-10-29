import java.util.*;
import java.io.*;
import edu.duke.*;

public class WordsInFiles {
    // HashMap to store each word and the list of files it appears in
    private HashMap<String, ArrayList<String>> wordFileMap;

    // Constructor
    public WordsInFiles() {
        wordFileMap = new HashMap<String, ArrayList<String>>();
    }

    // Add all words from one file into the map
    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        String fileName = f.getName();

        for (String word : fr.words()) {
            word = word.toLowerCase();

            if (!wordFileMap.containsKey(word)) {
                // If the word is not in the map, create a new ArrayList
                ArrayList<String> fileList = new ArrayList<String>();
                fileList.add(fileName);
                wordFileMap.put(word, fileList);
            } else {
                // Word already exists, check if fileName is already in the list
                ArrayList<String> fileList = wordFileMap.get(word);
                if (!fileList.contains(fileName)) {
                    fileList.add(fileName);
                }
            }
        }
    }

    // Build the map from multiple files
    public void buildWordFileMap() {
        wordFileMap.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }

    // Return the maximum number of files any word appears in
    public int maxNumber() {
        int max = 0;
        for (String word : wordFileMap.keySet()) {
            int size = wordFileMap.get(word).size();
            if (size > max) {
                max = size;
            }
        }
        return max;
    }

    // Return list of words that appear in exactly 'number' files
    public ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> result = new ArrayList<String>();
        for (String word : wordFileMap.keySet()) {
            if (wordFileMap.get(word).size() == number) {
                result.add(word);
            }
        }
        return result;
    }

    // Print filenames where the given word appears
    public void printFilesIn(String word) {
        ArrayList<String> fileList = wordFileMap.get(word);
        if (fileList != null) {
            for (String fileName : fileList) {
                System.out.println(fileName);
            }
        } else {
            System.out.println("Word not found in any file.");
        }
    }

    // Test everything
    public void tester() {
        buildWordFileMap();

        // Optional: Print the entire map to verify
        /*
        for (String word : wordFileMap.keySet()) {
            System.out.println(word + " -> " + wordFileMap.get(word));
        }
        */

        int maxFiles = maxNumber();
        maxFiles = 4;
        System.out.println("The greatest number of files a word appears in is: " + maxFiles);

        ArrayList<String> words = wordsInNumFiles(maxFiles);
        System.out.println("Words that appear in " + maxFiles + " files:");
        for (String w : words) {
            System.out.println("\nWord: " + w);
            System.out.println("Appears in files:");
            printFilesIn(w);
        }
        System.out.println("Le nombre de mots: "+ words.size());
        printFilesIn("sea");
        
    }
}