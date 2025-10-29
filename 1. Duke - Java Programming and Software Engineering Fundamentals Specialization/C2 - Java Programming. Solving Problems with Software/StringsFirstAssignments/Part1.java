
public class Part1 {
    public String findSimpleGene(String str){
        int startIndex = str.indexOf("ATG");
        if (startIndex == -1)
            return "Il n'y a pas d'ATG";
        int endIndex = str.indexOf("TAA");
        if (endIndex == -1)
            return "Il n'y a pas de TAA";
        String dna = str.substring(startIndex, endIndex + 3);
        if(dna.length() % 3 != 0)
            return "L'adn n'est pas un multiple de trois";
        return dna;
    }
    
    public void testSimpleGene(){
        String dna1 = "AUZGATGZERTAAGDFGJO";
        String dna2 = "AUZGATGJOEFCVOTOOTATAAGDFGJO";
        String dna3 = "AUZGaTGJOEFCVOTOOTAOTAAGDFGJO";
        String dna4 = "AUZGATGJOEFCVOTOOTAOTAaGDFGJO";
        String dna5 = "AUZGATGZEDRTAAGDFGJO";
        String dna6 = "ATGTAAGDFGJO";
        
        System.out.println("l'adn pour la sequence " + dna1 + " est: " + findSimpleGene(dna1));
        System.out.println("l'adn pour la sequence " + dna2 + " est: " + findSimpleGene(dna2));
        System.out.println("l'adn pour la sequence " + dna3 + " est: " + findSimpleGene(dna3));
        System.out.println("l'adn pour la sequence " + dna4 + " est: " + findSimpleGene(dna4));
        System.out.println("l'adn pour la sequence " + dna5 + " est: " + findSimpleGene(dna5));
        System.out.println("l'adn pour la sequence " + dna6 + " est: " + findSimpleGene(dna6));
        System.out.println("-----------------------------------------");
    }
}
