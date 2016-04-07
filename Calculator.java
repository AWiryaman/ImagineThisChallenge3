import java.util.ArrayList;
import org.apache.commons.math3.linear.CholeskyDecomposition;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RRQRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;



public class Calculator {
	
	/* OPR (Offensive Power Ranking) is a calculation of a teams impact on matches
	 * It uses a matrix of the matches that each team played with each other and one with the total scores of each team
	 * The matchesWith matrix is A and the totalScores matrix is B
	 * The equation AX=B is setup and solved
	 * X is the OPR of the team
	 */
	public RealMatrix OPR(ArrayList<Team> Teams, double[][] A) 
	{
		double[][] array = new double[Teams.size()][1]; //setup of totalScore matrix
		for(int i=0; i<Teams.size(); i++)  
			array[i][0] = Teams.get(i).totalScore();
		RealMatrix totalScores = MatrixUtils.createRealMatrix(array); 
		RealMatrix matchesWith = MatrixUtils.createRealMatrix(A); //setup of matchesWith matrix
		RealMatrix solution = MatrixUtils.createRealMatrix(Teams.size(), 1);// creates empty solution matrix
		DecompositionSolver solver = new CholeskyDecomposition(matchesWith).getSolver(); //creates a Cholesky Decomposition solver for the equation
		solution = solver.solve(totalScores); //solves the matrix equation 
		return solution;
	}
	/* CCWM (Calculated Contribution to Winning Margin) is another calculation of a teams impact on matches but factors in defense as well
	 * It uses the same formula as OPR but uses win margin rather than total score
	 */
	public RealMatrix CCWM(ArrayList<Team> Teams, double[][] A) 
	{
		double[][] array = new double[Teams.size()][1];
		for(int i=0; i<Teams.size(); i++)
			array[i][0] = Teams.get(i).getTWM();
		RealMatrix winningMargin = MatrixUtils.createRealMatrix(array);
		RealMatrix matchesWith = MatrixUtils.createRealMatrix(A);
		RealMatrix solution = MatrixUtils.createRealMatrix(Teams.size(), 1);
		DecompositionSolver solver = new CholeskyDecomposition(matchesWith).getSolver();
		solution = solver.solve(winningMargin);
		return solution;
	}
	/* DPR is a ranking of a teams defensive contribution
	 * It is calculated by taking OPR and subtracting CCWM
	 */
	public RealMatrix DPR(ArrayList<Team> Teams, double[][] A) 
	{
		RealMatrix solution = MatrixUtils.createRealMatrix(Teams.size(), 1);
		solution = OPR(Teams, A).subtract(CCWM(Teams, A));
		return solution;
	}
}
