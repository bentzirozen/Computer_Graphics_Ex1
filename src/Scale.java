public class Scale {
    public Scale(double a , double b , double c){
        //create a new matrix initialized with 0.
        Matrix t = new Matrix(4,4);

        t.assignElement(a,0,0);
        t.assignElement(b,1,1);
        t.assignElement(c,2,2);
        t.assignElement(1,3,3);
    }
}
