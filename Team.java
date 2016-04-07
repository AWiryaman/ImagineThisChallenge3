import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Team implements Comparable<Team>
{
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private int teamNumber;	
	private int TWM;
	private double OPR;
	private double CCWM;
	private double DPR;
	

	public Team(int number)
	{
		teamNumber=number;
		TWM=0;
		OPR=0;
		CCWM=0;
		DPR=0;
	}
	
	public void addTWM(int num)
	{
		TWM += num;
	}
	
	public int getTWM()
	{
		return TWM;
	}
	
	public void setTeamNumber(int number)
	{
		teamNumber = number;
	}
	
	public int getTeamNumber()
	{
		return teamNumber;
	}
	
	public void addScore(int score)
	{
		scores.add(score);
	}
	
	public void setOPR(double opr)
	{
		OPR = opr;
	}
	
	public double getOPR()
	{
		return OPR;
	}
	
	public void setCCWM(double ccwm)
	{
		CCWM = ccwm;
	}
	
	public double getCCWM()
	{
		return CCWM;
	}
	
	public void setDPR(double dpr)
	{
		DPR = dpr;
	}
	
	public double getDPR()
	{
		return DPR;
	}
	
	public void displayStats()
	{
		System.out.println("Team number: "+teamNumber);
		for(int x=0; x<scores.size(); x++)
			System.out.print(scores.get(x)+"   ");
	System.out.println(" ");
	}
	
    public int numOfGames()
    {
        return scores.size();
    }
    
    public int totalScore()
    {
    	int totalScore=0;
    	for(int i=0; i<scores.size(); i++)
    		totalScore+=scores.get(i);
    	return totalScore;
    }
    
	@Override
	public int compareTo(Team compareTeam) {
		// TODO Auto-generated method stub
    	int compareTeamNumber = (int) compareTeam.OPR;
    	return ((int)this.OPR)-compareTeamNumber;
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return String.valueOf(teamNumber);
	}

}//end of class