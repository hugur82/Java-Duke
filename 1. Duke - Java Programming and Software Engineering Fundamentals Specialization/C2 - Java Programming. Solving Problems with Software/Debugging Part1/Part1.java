
/**
 * Décrivez votre classe Part1 ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Part1
{
  public void findAbc(String input) {
    int index = input.indexOf("abc");
    while (true) {
        if (index == -1 || index >= input.length() -3) {
            break;
        }
        //System.out.println("index"+index);
        String found = input.substring(index+1, index+4);
        System.out.println(found);
        index = input.indexOf("abc", index+3);
        //System.out.println("index after updating" +index);
    }
}
   public void test() {
    //no code yet
    //findAbc("abcd");
    //findAbc("abcdabc");
    //findAbc("woiehabchi");
    //findAbc("yabcyabc");
    //findAbc("eusabce");
    findAbc("abcabcabcabca");
}
}