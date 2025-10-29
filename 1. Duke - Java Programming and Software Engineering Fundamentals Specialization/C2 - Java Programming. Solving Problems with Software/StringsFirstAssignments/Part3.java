
/**
 * Décrivez votre classe Part3 ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Part3 {
    public String lastPart(String stringa, String stringb){
        String tmp = stringb;
        int idx = stringb.indexOf(stringa);
        if (idx != -1){
            tmp = stringb.substring(idx + stringa.length());
        }

        return tmp;
    }

    public boolean twoOccurrences(String stringa, String stringb){
        int count = 0;

        for(int idx = 0;idx + 1 <= stringb.length();idx++){

            if (stringb.indexOf(stringa,idx) != -1)
                count++;
        }
        return (count >= 2)? true: false;
    }

    public void testing (){
        System.out.println("On trouve plus de 2 fois le premier mot dans le second: " + twoOccurrences("a","salala"));
        System.out.println("On trouve plus de 2 fois le premier mot dans le second: " + twoOccurrences("la","salala"));
        System.out.println("On trouve plus de 2 fois le premier mot dans le second: " + twoOccurrences("as","salala"));
        System.out.println("On trouve plus de 2 fois le premier mot dans le second: " + twoOccurrences("sla","salala"));
        System.out.println("On trouve plus de 2 fois le premier mot dans le second: " + twoOccurrences("sa","salala"));
        System.out.println("-------------------------------------");
    }

    public void testingLastPart(){
        String stra1 = "an";
        String strb1 = "banana";
        String stra2 = "zoo";
        String strb2 = "Forest";
        String stra3 = "la petite maison dans la prairie";
        String strb3 = "dans la maison de la petite maison qui est dans la petite maison dans la prairie, il ya a tout un tas de troll";
        System.out.println("-------------------------------------");
        System.out.println("the part of the string after '" + stra1 + "' in '" + strb1 + "' is '" + lastPart(stra1,strb1)+ "'");
        System.out.println("the part of the string after '" + stra2 + "' in '" + strb2 + "' is '" + lastPart(stra2,strb2)+ "'");
        System.out.println("the part of the string after '" + stra3 + "' in '" + strb3 + "' is '" + lastPart(stra3,strb3)+ "'");
        System.out.println("-------------------------------------");

    }
}
