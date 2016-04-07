import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.math3.linear.AbstractRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;


public class TeamFiller 
{
	private ArrayList<Team> teams = new ArrayList<Team>();
	private CreateTeams1 obj = new CreateTeams1();
	public TeamFiller()
	{
		RealMatrix opr, dpr, ccwm;
		Calculator calc = new Calculator();
		double[][] matrix = obj.getMatrix();
		teams = obj.getTeams();
		opr = calc.OPR(teams, matrix);
		dpr = calc.DPR(teams, matrix);
		ccwm = calc.CCWM(teams, matrix);
		for(int i=0; i<teams.size(); i++)
		{
			//System.out.print(teams.get(i).getTeamNumber() + " ");
			teams.get(i).setOPR(opr.getEntry(i, 0));
			teams.get(i).setDPR(dpr.getEntry(i, 0));
			teams.get(i).setCCWM(ccwm.getEntry(i, 0));
		}
		//System.out.println();
		//Collections.sort(teams);
		for(int i=0; i<teams.size(); i++)
		{
			System.out.println(teams.get(i).getTeamNumber() + ": " + teams.get(i).getOPR() + " " + teams.get(i).getDPR() + " " + teams.get(i).getCCWM());
		}
		/*RealMatrix totalScores = MatrixUtils.createRealMatrix(36, 1);
		RealMatrix realOPR = MatrixUtils.createRealMatrix(36, 1);
		RealMatrix matchesWith = MatrixUtils.createRealMatrix(matrix);
		for(int i=0; i<36; i++)
			totalScores.setEntry(i, 0, teams.get(i).totalScore());
		for(int i=0; i<36; i++)
			realOPR.setEntry(i, 0, teams.get(i).getOPR());*/
		//RealMatrix product = MatrixUtils.createRealMatrix(36, 1);
		//product = matchesWith.multiply(realOPR);
		/*for(int i=0; i<36; i++)
		{
			for(int j=0; j<36; j++)
			{
				System.out.print((int) matchesWith.getEntry(i, j) + " ");
			}
			System.out.print("      " + realOPR.getEntry(i, 0));
			System.out.print("      " + product.getEntry(i, 0));
			System.out.print("      " + totalScores.getEntry(i, 0));
			System.out.println();
		}*/
	}
	
	public ArrayList<Team> getTeams()
	{
		return teams;
	}
	
	public CreateTeams1 getObj()
	{
		return obj;
	}
}
