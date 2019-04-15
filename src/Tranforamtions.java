public class Tranforamtions {
    public Matrix Translation(double tx , double ty , double tz){
        //create a new matrix initialized with 0.
        Matrix m =new Matrix (4,4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            m.assignElement(1,i,i);
        }
        m.assignElement(tx,0,3);
        m.assignElement(ty,1,3);
        m.assignElement(tz,2,3);
        return m;
    }
    public Matrix Scale(double a , double b , double c) {
        //create a new matrix initialized with 0.
        Matrix m = new Matrix(4, 4);
        m.assignElement(a, 0, 0);
        m.assignElement(b, 1, 1);
        m.assignElement(c, 2, 2);
        m.assignElement(1, 3, 3);
        return m;
    }
    public Matrix Rotation(double angle , int axis){
        //create a new matrix initialized with 0.
        Matrix m=new Matrix (4,4);
        m.assignElement(1,3,3);
        //rotation in the x axis
        if(axis == 0){
            m.assignElement(1,0,0);
            m.assignElement(Math.cos(Math.toRadians(angle)),1,1);
            m.assignElement(Math.cos(Math.toRadians(angle)),2,2);
            m.assignElement(-(Math.sin(Math.toRadians(angle))),1,2);
            m.assignElement(Math.sin(Math.toRadians(angle)),2,1);
        }//rotation in the y axis
        else if(axis == 1){
            m.assignElement(1,1,1);
            m.assignElement(Math.cos(Math.toRadians(angle)),0,0);
            m.assignElement(Math.cos(Math.toRadians(angle)),2,2);
            m.assignElement(-(Math.sin(Math.toRadians(angle))),0,2);
            m.assignElement(Math.sin(Math.toRadians(angle)),2,0);

        }//rotation in the z axis
        //axis == 2.
        else {
            m.assignElement(1,2,2);
            m.assignElement(Math.cos(Math.toRadians(angle)),0,0);
            m.assignElement(Math.cos(Math.toRadians(angle)),1,1);
            m.assignElement(-(Math.sin(Math.toRadians(angle))),0,1);
            m.assignElement(Math.sin(Math.toRadians(angle)),1,0);

        }
        return m;
    }
    public static Matrix vectorToMatrix(Vector vector) {
        double[][] m = new double[4][1];
        m[0][0] = vector.getX();
        m[1][0] = vector.getY();
        m[2][0] = vector.getZ();
        m[3][0] = 1;
        return new Matrix(m);
    }
    public static Vector matrixToVector(Matrix m) {
        double vx = m.getMatrix()[0][0];
        double vy = m.getMatrix()[1][0];
        double vz = m.getMatrix()[2][0];
        return new Vector(vx, vy, vz);
    }
    public static Vector vertexToVector(Point3D p){
        return new Vector(p.getX(), p.getY(), p.getZ());
    }
    public static Point3D vectorToVertex(Vector v){
        return new Point3D(v.getX(), v.getY(), v.getZ());
    }
    public static Point3D orthographicProjection(Point3D v){
        double[][] m = {{1,0,0,0},{0,1,0,0},{0,0,0,0},{0,0,0,1}};
        Matrix matrix = new Matrix(m);
        Matrix result = matrix.multiply(matrix, vectorToMatrix(vertexToVector(v)));
        return vectorToVertex(matrixToVector(result));
    }


}
