
/**
 * Décrivez votre classe Part2 ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Part2 {
 public String findSimpleGene(String dna,int startCodon, int stopCodon){
        dna = dna.substring(startCodon,stopCodon);
        dna = dna.toUpperCase();
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1)
            return "Il n'y a pas d'ATG";
        int endIndex = dna.indexOf("TAA");
        if (endIndex == -1)
            return "Il n'y a pas de TAA";
        String dnaStr = dna.substring(startIndex, endIndex + 3);
        if(dnaStr.length() % 3 != 0)
            return "L'adn n'est pas un multiple de trois";
        return dnaStr;
    }
    
    public void testSimpleGene(){
        String dna1 = "AUZGATGZERTAAGDFGJO";
        String dna2 = "AUZGATGJOEFCVOTOOTATAAGDFGJO";
        String dna3 = "AUZGaTGJOEFCVOTOOTAOTAAGDFGJO";
        String dna4 = "AUZGATGJOEFCVOTOOTAOTAaGDFGJO";
        String dna5 = "AUZGATGZEDRTAAGDFGJO";
        String dna6 = "ATGTAAGDFGJO";
        
        System.out.println("l'adn pour la sequence " + dna1 + " est: " + findSimpleGene(dna1,5,14));
        System.out.println("l'adn pour la sequence " + dna2 + " est: " + findSimpleGene(dna2,2,18));
        System.out.println("l'adn pour la sequence " + dna3 + " est: " + findSimpleGene(dna3,0,dna3.length()));
        System.out.println("l'adn pour la sequence " + dna4 + " est: " + findSimpleGene(dna4,1,27));
        System.out.println("l'adn pour la sequence " + dna5 + " est: " + findSimpleGene(dna5,2,15));
        System.out.println("l'adn pour la sequence " + dna6 + " est: " + findSimpleGene(dna6,0,6));
        System.out.println("-----------------------------------------");
    }
}
