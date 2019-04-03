
public class Rotation {
    public Rotation(double angle , int axis){
        //create a new matrix initialized with 0.
        Matrix t = new Matrix(4,4);
        t.assignElement(1,3,3);
        //rotation in the x axis
        if(axis == 0){
            t.assignElement(1,0,0);
            t.assignElement(Math.cos(angle),1,1);
            t.assignElement(Math.cos(angle),2,2);
            t.assignElement(-(Math.sin(angle)),1,2);
            t.assignElement(Math.sin(angle),2,1);
        }//rotation in the y axis
        else if(axis == 1){
            t.assignElement(1,1,1);
            t.assignElement(Math.cos(angle),0,0);
            t.assignElement(Math.cos(angle),2,2);
            t.assignElement(-(Math.sin(angle)),0,2);
            t.assignElement(Math.sin(angle),2,0);

        }//rotation in the z axis
        //axis == 2.
        else {
            t.assignElement(1,2,2);
            t.assignElement(Math.cos(angle),0,0);
            t.assignElement(Math.cos(angle),1,1);
            t.assignElement(-(Math.sin(angle)),0,1);
            t.assignElement(Math.sin(angle),1,0);

        }
    }
}
