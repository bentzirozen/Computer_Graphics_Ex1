
public class Rotation extends Matrix {
    public Rotation(double angle , int axis){
        //create a new matrix initialized with 0.
        super (4,4);
        super.assignElement(1,3,3);
        //rotation in the x axis
        if(axis == 0){
            super.assignElement(1,0,0);
            super.assignElement(Math.cos(Math.toRadians(angle)),1,1);
            super.assignElement(Math.cos(Math.toRadians(angle)),2,2);
            super.assignElement(-(Math.sin(Math.toRadians(angle))),1,2);
            super.assignElement(Math.sin(Math.toRadians(angle)),2,1);
        }//rotation in the y axis
        else if(axis == 1){
            super.assignElement(1,1,1);
            super.assignElement(Math.cos(Math.toRadians(angle)),0,0);
            super.assignElement(Math.cos(Math.toRadians(angle)),2,2);
            super.assignElement(-(Math.sin(Math.toRadians(angle))),0,2);
            super.assignElement(Math.sin(Math.toRadians(angle)),2,0);

        }//rotation in the z axis
        //axis == 2.
        else {
            super.assignElement(1,2,2);
            super.assignElement(Math.cos(Math.toRadians(angle)),0,0);
            super.assignElement(Math.cos(Math.toRadians(angle)),1,1);
            super.assignElement(-(Math.sin(Math.toRadians(angle))),0,1);
            super.assignElement(Math.sin(Math.toRadians(angle)),1,0);

        }
    }
}
