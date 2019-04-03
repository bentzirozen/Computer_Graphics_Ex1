public class Matrix{

//State Variables: Private state varibles were created so that they could not be accidently accessed. Each iteration of the Matrix Class with have it's own m,numberOfRows and numberOfColumns.

    private double[][] m;
    private int numberOfRows; private int numberOfColumns;

//Constructors: Two constructors were created. The first, takes two ints as parameters for the shape of the matrix and initializes the elements to 0. The second has no parameters, but instead asks the user to supply the shape and element values via scanner.

    public Matrix(int rows, int columns)
    {
        m = new double[rows][columns];

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                m[i][j] = 0;
            }
        }
    }
