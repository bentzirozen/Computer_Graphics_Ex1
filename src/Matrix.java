public class Matrix {

//State Variables: Private state varibles were created so that they could not be accidently accessed.
// Each iteration of the Matrix Class with have it's own m,getRows and numberOfColumns.

    private double[][] m;

    public double getElement(int rows, int columns){return m[rows][columns];}

    public Matrix(int rows , int columns) {
        m = new double[rows][columns];
        if(rows!=columns){
            return;
        }
        for (int i = 0; i < rows; i++) {
            m[i][i]=1;
        }
    }
    public Matrix(double[][] m) {
        this.m = m;
    }
    public double[][] getMatrix(){
        return this.m;
    }
    public void assignElement(double value, int i, int j){m[i][j] = value;}

    public static Matrix multiply(Matrix x, Matrix y)
    {
        int rows = x.getRows();
        int cols = y.getCols();
        //z is the new matrix created.
        Matrix z = new Matrix(rows,cols);
        double value;
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols;j++)
            {
                double sum = 0;
                for(int k = 0; k < rows; k++)
                {
                    sum += x.getElement(i,k) * y.getElement(k,j);
                }
                value = sum;
                //put the value in the correct cell.
                z.assignElement(value,i,j);
            }
        }
        //return the new matrix.
        return z;
    }
    public Matrix transpose() {
        int rows = this.getRows();
        int cols = this.getCols();
        double[][] mat = new double[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                mat[j][i] = this.m[i][j];
        return new Matrix(mat);
    }


    public int getRows(){
        return this.m.length;
    }

    public int getCols(){
        return this.m[0].length;
    }

    //reset the matrix to the identity matrix.
    public Matrix reset() {
        Matrix i = new Matrix(4,4);
        //put 1 on the diagonal.
        for (int j = 0; j < 4; j++) {
            i.assignElement(1, j, j);
        }
        return i;
    }

    public static void main( String [] args){

    }

    public Matrix multiply(Matrix other) {
        return Matrix.multiply(this,other);
    }
}
