import edu.duke.*;
/**
 * Décrivez votre classe PerimeterRunner ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class PerimeterRunner
{
    public double getPerimeter (Shape s){
        double totalPerim = 0;
        
        Point prevPt = s.getLastPoint();
        for(Point currPt: s.getPoints())
        {
            double currDist= prevPt.distance(currPt);
            totalPerim += currDist;
            prevPt= currPt;
        }
        return totalPerim;
    }
    
    public void testPerimeter() {
        FileResource fr =new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
    }
    
    public static void main(String[] args){
        PerimeterRunner pr = new PerimeterRunner();
        pr.testPerimeter();
    }
}