import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder newMessage = new StringBuilder();
        for(int k = whichSlice; k < message.length(); k+=totalSlices){
            newMessage.append(message.charAt(k));
        }
        return newMessage.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
    
        for (int i = 0; i < klength; i++) {
            // 1️⃣ Récupère la tranche i (toutes les lettres i, i+klength, i+2*klength, etc.)
            String slice = sliceString(encrypted, i, klength);
    
            // 2️⃣ Trouve le décalage de César le plus probable pour cette tranche
            int dkey = cc.getKey(slice);
    
            // 3️⃣ Stocke ce décalage dans le tableau
            key[i] = dkey;
        }
    
        return key;
    }

    public void breakVigenere () {
         // 1️⃣ Choisir le fichier à décrypter
        FileResource fr = new FileResource(); 
        String encrypted = fr.asString();
        
            // 2️⃣ Charger le dictionnaire anglais
             // Créer le HashMap pour stocker tous les dictionnaires
        HashMap<String, HashSet<String>> languages = new HashMap<>();
    
        // Liste des fichiers de dictionnaires
        String[] languageFiles = {
            "dictionaries/Danish",
            "dictionaries/Dutch",
            "dictionaries/English",
            "dictionaries/French",
            "dictionaries/German",
            "dictionaries/Italian",
            "dictionaries/Portuguese",
            "dictionaries/Spanish"
        };
        for (String fileName : languageFiles) {
            FileResource frDict = new FileResource(fileName);
            HashSet<String> dict = readDictionary(frDict);
    
            // Extraire le nom de la langue depuis le chemin du fichier
            String languageName = fileName.substring(fileName.lastIndexOf("/") + 1);
    
            languages.put(languageName, dict);
            System.out.println("Loaded dictionary for " + languageName + " with " + dict.size() + " words.");
        }

        // Appeler breakForAllLangs avec le texte chiffré et le HashMap complet
        breakForAllLangs(encrypted, languages);
    }
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> set = new HashSet<>();
        for(String line : fr.lines()){
            set.add(line.toLowerCase());
        }
        return set;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        ArrayList<String> words = new ArrayList<>();
        String[] messageWords = message.split("\\W+");
        for(String word :messageWords){
           word = word.toLowerCase();
           if (dictionary.contains(word)){
               words.add(word);
           }
        }
        System.out.println(words);
        return words.size();
    }
    
    
    public String breakForLanguage(String encrypted,HashSet<String> dictionary){
        int min = 1;
        int max = 100;
        int maxRealWords = 0;
        String bestDecryption = "";
        
        
        for(int k = min; k <= max; k++){
            char newChar = mostCommonCharIn(dictionary);
            int[] key = tryKeyLength(encrypted, k, newChar);
            
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            
            int realWords = countWords(decrypted, dictionary);
            
            if(realWords > maxRealWords){
                maxRealWords = realWords;
                bestDecryption = decrypted;
            }
        }
        
        return bestDecryption;
        
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character, Integer> count = new HashMap<>();
        char maxChar = 'a';
        int maxNumber = 0;
        
        for(String word :dictionary){
            for(int k =0; k< word.length();k++){
                char currChar = Character.toLowerCase(word.charAt(k));
                if (currChar >= 'a' && currChar <= 'z'){
                    count.put(currChar, count.getOrDefault(currChar, 0) + 1);
                }
            }
        }
        
        for (Map.Entry<Character, Integer> entry : count.entrySet()){
            if(entry.getValue() > maxNumber){
                maxNumber = entry.getValue();
                maxChar = entry.getKey();
                
            }
        }
        
        return maxChar;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages){
        String bestDecryption = "";
        String bestLanguage = "";
        int maxValidWords = 0;
        //System.out.println("Detected encrypted: " + encrypted + "\nDetected hashMap: "+languages);        
        for (String language : languages.keySet()) {
            HashSet<String> dictionary = languages.get(language);
            // Déchiffrer le texte pour cette langue
            String decrypted = breakForLanguage(encrypted, dictionary);
            
            // Compter combien de mots valides se trouvent dans le texte déchiffré
            int validWords = countWords(decrypted, dictionary);

            // Vérifier si c'est le meilleur résultat jusqu'à présent
            if (validWords > maxValidWords) {
                maxValidWords = validWords;
                bestDecryption = decrypted;
                bestLanguage = language;
            }
        } 
        
        System.out.println("Decrypted message:\n" + bestDecryption);
        System.out.println("Detected language: " + bestLanguage);
    }
}
