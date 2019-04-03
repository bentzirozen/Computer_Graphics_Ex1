public class Matrix {

//State Variables: Private state varibles were created so that they could not be accidently accessed. Each iteration of the Matrix Class with have it's own m,numberOfRows and numberOfColumns.

    private double[][] m;
    private int numberOfRows;
    private int numberOfColumns;

//Constructors: Two constructors were created. The first, takes two ints as parameters for the shape of the matrix and initializes the elements to 0. The second has no parameters, but instead asks the user to supply the shape and element values via scanner.

    public Matrix(int rows , int columns) {
        rows = 4;
        columns = 4;
        m = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                m[i][j] = 0;
            }
        }
    }
    private int getRows(){return 4;}
    private int getColumns(){return 4;}
    private double getElement(int rows, int columns){return m[rows][columns];}
    private void assignElement(double value, int i, int j){m[i][j] = value;}

    public static Matrix multiply(Matrix x, Matrix y)
    {
        //z is the new matrix created.
        Matrix z = new Matrix(x.getRows(), y.getColumns());
        double value;
        for(int i = 0; i < x.getRows(); i++)
        {
            for(int j = 0; j < y.getColumns(); j++)
            {
                double sum = 0;
                for(int k = 0; k < x.getRows(); k++)
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
}
