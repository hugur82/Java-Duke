import edu.duke.*;

public class MarkovRunnerWithInterface {

    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for (int k = 0; k < 3; k++) {
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }

    private void printOut(String s) {
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for (String word : words) {
            System.out.print(word + " ");
            psize += word.length() + 1;
            if (psize > 60) {
                System.out.println();
                psize = 0;
            }
        }
        System.out.println("\n----------------------------------");
    }

    public void testHashMapConfucius() {
        FileResource fr = new FileResource("data/confucius.txt");
        String text = fr.asString();
        EfficientMarkovModel em = new EfficientMarkovModel(3); // order 3
        em.setRandom(371);       // seed 371
        em.setTraining(text);
          // buildMap et printHashMapInfo seront appelés ici
    }
    
    public void testLargestFollow() {
        FileResource fr = new FileResource("data/confucius.txt");
        String text = fr.asString();
        EfficientMarkovModel em = new EfficientMarkovModel(2); // order 2
        em.setRandom(65);        // seed 65
        em.setTraining(text); 
          /// printHashMapInfo() est appelé ici
    }
    
    public void testEfficientMarkovKeys() {
        // Charger le texte
        FileResource fr = new FileResource("data/confucius.txt");
        String text = fr.asString();
    
        // Créer l’EfficientMarkovModel avec order 6
        EfficientMarkovModel em = new EfficientMarkovModel(6);
    
        // Définir la seed pour le random (facultatif ici)
        em.setRandom(792);
    
        // Charger le texte dans le modèle
        em.setTraining(text);
    
        
    }
    public void runMarkovModel7() {
        FileResource fr = new FileResource("data/romeo.txt");
        String st = fr.asString();
        st = st.replace('\n', ' '); // optionnel, pour éviter les retours à la ligne

        int N = 7; // taille de l’ordre
        int seed = 953;

        MarkovModel markov = new MarkovModel(N); // constructeur avec ordre N
        markov.setRandom(seed);                  // seed fixe
        markov.setTraining(st);
        
        String generated = markov.getRandomText(200); // ou le nombre de caractères souhaité
        System.out.println(generated);
    }
    
    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovModel markov = new MarkovModel(3);
        markov.setRandom(792);
        runModel(markov, st, 50, 621);
    }

    public void testHashMap() {
        EfficientMarkovModel em = new EfficientMarkovModel(2);
        em.setRandom(42);
        em.setTraining("yes-this-is-a-thin-pretty-pink-thistle");
        em.getRandomText(50);
        em.printHashMapInfo();
    }
}
