import edu.duke.*;

public class HelloWorld {
    public void runHello () {
        FileResource res = new FileResource("hello_unicode.txt");
        for (String line : res.lines()) {
            System.out.println(line);
        }
        int a = myFunction(15);
        System.out.println("a =" + a);
        point test = new point(1,2);
        System.out.println("x="+test.getX());
        test.addX();
        System.out.println("x="+test.getX());
        test.minusX();
        System.out.println("x="+test.getX());
        
    }
    
    int myFunction(int n) {
        if (n == 0)
        return 0;
        else
        {
            n = n-1;
            System.out.println("n est egal a :" + n);
            myFunction(n) ;
        }
        return 0;
    }
    
    public class point {
        private int a;
        private int b;
        
        public point(int x, int y){
        a = x;
        b = y;
        }
        
        public int getX(){return a;}
        public int getY() { return b;}
        public void addX() {
        a++;
        }
        public void minusX() {
            a--;
        }
    }
}
