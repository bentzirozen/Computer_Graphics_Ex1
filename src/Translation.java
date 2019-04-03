public class Translation {
    public Translation(double tx , double ty , double tz){
        //create a new matrix initialized with 0.
        Matrix t = new Matrix(4,4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            t.assignElement(1,i,i);
        }
        t.assignElement(tx,0,3);
        t.assignElement(ty,1,3);
        t.assignElement(tz,2,3);
    }
}
