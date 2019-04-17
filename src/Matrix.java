public class Matrix {

//State Variables: Private state varibles were created so that they could not be accidently accessed.
// Each iteration of the Matrix Class with have it's own m,getRows and numberOfColumns.

    private double[][] m;

    public double getElement(int rows, int columns){return m[rows][columns];}

    public Matrix(int rows , int columns) {
        m = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                m[i][j] = 0;
            }
        }
    }
    public Matrix(double[][] m) {
        this.m = m;
    }
    public double[][] getMatrix(){
        return this.m;
    }
    public void assignElement(double value, int i, int j){m[i][j] = value;}

    public Matrix multiply(Matrix x, Matrix y)
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

    public static void main( String [] args){

    }
}
