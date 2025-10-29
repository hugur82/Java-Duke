import edu.duke.*; // pour utiliser FileResource
import java.io.*;

public class WordLengths {

    /**
     * Compte le nombre de mots de chaque longueur dans le fichier.
     * @param resource le fichier à analyser
     * @param counts le tableau des longueurs (ex: taille 31 pour 0–30+)
     */
    public void countWordLengths(FileResource resource, int[] counts) {
        for (String word : resource.words()) {
            int length = word.length();

            // Enlève le premier caractère s'il n'est pas une lettre
            if (length > 0 && !Character.isLetter(word.charAt(0))) {
                word = word.substring(1);
                length--;
            }

            // Enlève le dernier caractère s'il n'est pas une lettre
            if (word.length() > 0 && !Character.isLetter(word.charAt(word.length() - 1))) {
                word = word.substring(0, word.length() - 1);
                length--;
            }

            // Calcul de la longueur finale
            length = word.length();

            // Si le mot est trop long, on le regroupe dans la dernière case
            if (length >= counts.length) {
                counts[counts.length - 1]++;
            }
            // Si le mot a une longueur > 0, on le compte
            else if (length > 0) {
                counts[length]++;
            }
        }
    }

    /**
     * Trouve l'index de la longueur la plus fréquente.
     */
    public int indexOfMax(int[] values) {
        int maxIndex = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Méthode de test : lit un fichier, compte les longueurs et affiche les résultats.
     */
    public void testCountWordLengths() {
        FileResource resource = new FileResource(); // ouvre un fichier via le sélecteur
        int[] counts = new int[31]; // on regroupe les mots de 30+ dans la dernière case
        countWordLengths(resource, counts);

        // Affiche les résultats
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.println(counts[i] + " words of length " + i);
            }
        }

        int mostCommon = indexOfMax(counts);
        System.out.println("Most common word length is " + mostCommon);
    }
}
