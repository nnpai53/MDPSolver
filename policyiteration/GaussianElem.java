/*http://the-lost-beauty.blogspot.in/2009/04/moore-penrose-pseudoinverse-in-jama.html
 * http://math.stackexchange.com/questions/282321/how-to-solve-a-system-with-more-equations-than-unkowns
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package policyiteration;
import Jama.Matrix;
import Jama.SingularValueDecomposition;


public class GaussianElem {
     public double MACHEPS = 2E-16;
     
    public void updateMacheps() {
        MACHEPS = 1;
        do
            MACHEPS /= 2;
        while (1 + MACHEPS / 2 != 1);
    }

 /**
  * Computes the Moore–Penrose pseudoinverse using the SVD method.
  * 
  * Modified version of the original implementation by Kim van der Linde.
  */
    public Matrix pinv(Matrix x) {
        if (x.rank() < 1)
            return null;
        if (x.getColumnDimension() > x.getRowDimension())
            return pinv(x.transpose()).transpose();
        SingularValueDecomposition svdX = new SingularValueDecomposition(x);
        double[] singularValues = svdX.getSingularValues();
        double tol = Math.max(x.getColumnDimension(), x.getRowDimension()) * singularValues[0] * MACHEPS;
        double[] singularValueReciprocals = new double[singularValues.length];
        for (int i = 0; i < singularValues.length; i++)
            singularValueReciprocals[i] = Math.abs(singularValues[i]) < tol ? 0 : (1.0 / singularValues[i]);
        double[][] u = svdX.getU().getArray();
        double[][] v = svdX.getV().getArray();
        int min = Math.min(x.getColumnDimension(), u[0].length);
        double[][] inverse = new double[x.getColumnDimension()][x.getRowDimension()];
        for (int i = 0; i < x.getColumnDimension(); i++)
            for (int j = 0; j < u.length; j++)
                for (int k = 0; k < min; k++)
                    inverse[i][j] += v[i][k] * singularValueReciprocals[k] * u[j][k];
        return new Matrix(inverse);
    }
}