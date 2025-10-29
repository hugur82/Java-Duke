import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        // Put code here
        if (s == null)
        return 0;
        else {
           int count=0;
           for (Point currPoint: s.getPoints())
           {
               count++;
           }
            return count;
        }
    }

    public double getAverageLength(Shape s) {
        // Put code here
        double avg = 0;
        if(s != null)
        avg = getPerimeter(s)/getNumPoints(s);
        
        return avg;
    }

    public double getLargestSide(Shape s) {
        // Put code here
        double larger=0;
        Point prevPt = s.getLastPoint();
        for(Point currPt : s.getPoints())
        {
            double currDist = prevPt.distance(currPt);
            larger = Math.max(larger,currDist);
            prevPt=currPt;
        }
        return larger;
    }

    public double getLargestX(Shape s) {
        // Put code here
        double x = Double.NEGATIVE_INFINITY;
        for(Point currPt : s.getPoints())
        {
            x=currPt.getX() > x ? currPt.getX():x;
        }
        
        return x;
    }

    public double getLargestPerimeterMultipleFiles() {
        // Put code here
        DirectoryResource dr = new DirectoryResource();
        double largestPerim=0;
        double tmp=0;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            tmp = getPerimeter(s);
            largestPerim = tmp > largestPerim ? tmp : largestPerim;
        }
        return largestPerim;
    }

    public String getFileWithLargestPerimeter() {
        // Put code here
        File temp = null; // replace this code
        DirectoryResource dr = new DirectoryResource();
        double tmp = 0;
        double largest = 0;
        for ( File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            tmp = getPerimeter(s);
            if(tmp > largest){
                largest = tmp;
                temp = f;
            }
            
        }
        return temp != null ? temp.getName() : "aucun file selectionné";
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr); 
        System.out.println("------------ Début ------------");
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
        int numberPoint = getNumPoints(s);
        System.out.println("nombre de points: " + numberPoint);
        double avg = getAverageLength(s);
        System.out.println("moyenne = "+ avg);
        double larger =getLargestSide(s);
        System.out.println("Le coté le plus long: " + larger);
        double largestX=getLargestX(s);
        System.out.println("Le coté le plus long des x: " + largestX);
        
        System.out.println("------------  fin  ------------");
        
    }
    
    public void testPerimeterMultipleFiles() {
        // Put code here
        System.out.println("------------ Début ------------");
        double largest = getLargestPerimeterMultipleFiles();
        System.out.println("The largest perimeter is equal to: " + largest);
           
        System.out.println("------------  fin  ------------");
    }

    public void testFileWithLargestPerimeter() {
        // Put code here
        
        System.out.println("------------ Début ------------");
        String fileName = getFileWithLargestPerimeter();
        System.out.println("Le fichier ou se trouve le plus long perimetre est: "+ fileName);
           
        System.out.println("------------  fin  ------------");
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeter();
    }
}
