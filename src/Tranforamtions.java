public class Tranforamtions {
    private View v;
    public Tranforamtions(View v){
        this.v= v ;
    }
    public Matrix Translation(double tx, double ty, double tz) {
        //create a new matrix initialized with 0.
        Matrix m = new Matrix(4, 4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            m.assignElement(1, i, i);
        }
        m.assignElement(tx, 0, 3);
        m.assignElement(ty, 1, 3);
        m.assignElement(tz, 2, 3);
        return m;
    }

    public Matrix Scale(double a, double b, double c) {
        //create a new matrix initialized with 0.
        Matrix m = new Matrix(4, 4);
        m.assignElement(a, 0, 0);
        m.assignElement(b, 1, 1);
        m.assignElement(c, 2, 2);
        m.assignElement(1, 3, 3);
        return m;
    }

    public Matrix Rotation(double angle, char axis) {
        //create a new matrix initialized with 0.
        Matrix m = new Matrix(4, 4);
        m.assignElement(1, 3, 3);
        //rotation in the x axis
        if (axis == 'x') {
            m.assignElement(1, 0, 0);
            m.assignElement(Math.cos(Math.toRadians(angle)), 1, 1);
            m.assignElement(Math.cos(Math.toRadians(angle)), 2, 2);
            m.assignElement(-(Math.sin(Math.toRadians(angle))), 1, 2);
            m.assignElement(Math.sin(Math.toRadians(angle)), 2, 1);
        }//rotation in the y axis
        else if (axis == 'y') {
            m.assignElement(1, 1, 1);
            m.assignElement(Math.cos(Math.toRadians(angle)), 0, 0);
            m.assignElement(Math.cos(Math.toRadians(angle)), 2, 2);
            m.assignElement(-(Math.sin(Math.toRadians(angle))), 0, 2);
            m.assignElement(Math.sin(Math.toRadians(angle)), 2, 0);

        }//rotation in the z axis
        //axis == z.
        else {
            m.assignElement(1, 2, 2);
            m.assignElement(Math.cos(Math.toRadians(angle)), 0, 0);
            m.assignElement(Math.cos(Math.toRadians(angle)), 1, 1);
            m.assignElement(-(Math.sin(Math.toRadians(angle))), 0, 1);
            m.assignElement(Math.sin(Math.toRadians(angle)), 1, 0);

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

    public static Vector vertexToVector(Point3D p) {
        return new Vector(p.getX(), p.getY(), p.getZ());
    }

    public static Point3D vectorToVertex(Vector v) {
        return new Point3D(v.getX(), v.getY(), v.getZ());
    }

    public static Point3D orthographicProjection(Point3D v) {
        double[][] m = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}};
        Matrix matrix = new Matrix(m);
        Matrix result = matrix.multiply(matrix, vectorToMatrix(vertexToVector(v)));
        return vectorToVertex(matrixToVector(result));
    }
    public Matrix projection(){
        double[][] m = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}};
        return new Matrix(m);
    }

    public Matrix t1() {
        //create a new matrix initialized with 0.
        Matrix m = new Matrix(4, 4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            m.assignElement(1, i, i);
        }
        double wcx = v.getLeftBound() + (v.getRightBound() - v.getLeftBound()) / 2;
        double wcy = v.getBottomBound() + (v.getTopBound() - v.getBottomBound()) / 2;
        m.assignElement(-wcx, 0, 3);
        m.assignElement(-wcy, 1, 3);
        return m;
    }

    public Matrix t2() {
        //create a new matrix initialized with 0.
        Matrix m = new Matrix(4, 4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            m.assignElement(1, i, i);
        }
        m.assignElement(20 + v.getScreenWidth() / 2, 0, 3);
        m.assignElement(20 + v.getScreenHeight() / 2, 1, 3);
        return m;
    }

    public Matrix s() {
        //create a new matrix initialized with 0.
        Matrix m = new Matrix(4, 4);
        m = Scale(this.v.getScreenWidth() / (v.getRightBound() - v.getLeftBound()), - v.getScreenHeight()
                / (v.getTopBound() - v.getBottomBound()), 1);
        return m;
    }

    public Matrix mv2(Matrix t2, Matrix s, Matrix t1) {
        return Matrix.multiply(t2,Matrix.multiply(s,t1));
    }

    public Matrix mv1(Point3D position, Point3D lookAt, Point3D up) {
        //create the t matrix
        Matrix t = new Matrix(4, 4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            t.assignElement(1, i, i);
        }
        t.assignElement(-position.getX(), 0, 3);
        t.assignElement(-position.getY(), 1, 3);
        t.assignElement(-position.getZ(), 2, 3);

        //create the r matrix.
        Matrix r = new Matrix(4,4);
        Vector v = new Vector(up.getX(), up.getY(), up.getZ());
        Vector l = new Vector(lookAt.getX(), lookAt.getY(), lookAt.getZ());
        Vector p = new Vector(position.getX(), position.getY(), position.getZ());
        Vector z = new Vector(p.sub(l).normalize().getX(),p.sub(l).normalize().getY(),p.sub(l).normalize().getZ());
        Vector x = new Vector(v.crossProduct(v,z).getX(),v.crossProduct(v,z).getY(),v.crossProduct(v,z).getZ());
        Vector y = new Vector(z.crossProduct(z,x).getX(),z.crossProduct(z,x).getY(),z.crossProduct(z,x).getZ());
        r.assignElement(x.getX(), 0,0);
        r.assignElement(x.getY(), 0,1);
        r.assignElement(x.getZ(), 0,2);
        r.assignElement(y.getX(), 1,0);
        r.assignElement(y.getY(), 1,1);
        r.assignElement(y.getZ(), 1,2);
        r.assignElement(z.getX(), 2,0);
        r.assignElement(z.getY(), 2,1);
        r.assignElement(z.getZ(), 2,2);

        Matrix mv1 = new Matrix(4,4);
        mv1 = r.multiply(r,t);
        return mv1;
    }

    public Matrix tl(Vector lookAt, Vector position){
        Matrix tl = new Matrix(4,4);
        //put 1 on the diagonal.
        for (int i = 0; i < 4; i++) {
            tl.assignElement(1, i, i);
        }
        //|l-p|
        tl.assignElement(lookAt.sub(lookAt,position).magnitude(),2,3);
        return tl;
    }

    public Matrix ct(Matrix t2, char axis, double angle, Matrix t1){
        Matrix ct = t2.multiply(t2,Rotation(angle, axis));
        ct = ct.multiply(ct,t1);
        return ct;
    }
    public Matrix tt(Matrix mv2, Matrix p, Matrix ct, Matrix at,Matrix mv1){
        Matrix tt = mv2.multiply(mv2,p);
        tt = tt.multiply(tt,ct);
        tt= tt.multiply(tt,at);
        tt= tt.multiply(tt,mv1);
        return tt;
    }
    public void setView(View v){
        this.v = v;
    }


}
