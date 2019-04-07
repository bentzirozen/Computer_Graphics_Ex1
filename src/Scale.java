public class Scale extends Matrix {
    public Scale(double a , double b , double c){
        //create a new matrix initialized with 0.
        super (4,4);

        super.assignElement(a,0,0);
        super.assignElement(b,1,1);
        super.assignElement(c,2,2);
        super.assignElement(1,3,3);
    }
}
