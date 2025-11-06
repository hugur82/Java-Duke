import java.util.*;

public class LargestQuakes {

    // Retourne l'indice du séisme avec la plus grande magnitude
    public int indexOfLargest(ArrayList<QuakeEntry> data) {
        int largestIndex = 0;
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).getMagnitude() > data.get(largestIndex).getMagnitude()) {
                largestIndex = i;
            }
        }
        return largestIndex;
    }

    // Retourne les "howMany" séismes les plus gros (par magnitude)
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> result = new ArrayList<QuakeEntry>();

        for (int i = 0; i < howMany && !copy.isEmpty(); i++) {
            int largestIndex = indexOfLargest(copy);
            result.add(copy.get(largestIndex));
            copy.remove(largestIndex);
        }

        return result;
    }

    // Méthode principale pour lire le fichier et afficher les plus gros séismes
    public void findLargestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";

        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");

        // Affiche les 5 plus gros séismes
        ArrayList<QuakeEntry> largest = getLargest(list, 50);
        for (QuakeEntry qe : largest) {
            System.out.println(qe);
        }
        System.out.println("Found "+ largest.size() + " quakes that match that criteria");
    }
}