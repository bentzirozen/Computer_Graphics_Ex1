public class Translation extends Matrix {
    public Translation(double tx , double ty , double tz){
        //create a new matrix initialized with 0.
        super (4,4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            super.assignElement(1,i,i);
        }
        super.assignElement(tx,0,3);
        super.assignElement(ty,1,3);
        super.assignElement(tz,2,3);
    }
}
