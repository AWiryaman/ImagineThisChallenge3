import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import neural.Network;


public class Train 
{
	private Network net = new Network(16, 10, 2);
	public Train()
	{
		for(int x=0; x<4; x++)
		{	
			TeamFiller filledTeams = new TeamFiller();
			ArrayList<Team> teams = filledTeams.getTeams();
			String file = filledTeams.getObj().getFilename();
			String fileString = file.substring(0, filledTeams.getObj().getFilename().indexOf(".")) + "Winner.txt";
			double [][] correct = getCorrect(fileString, teams);
			double [][] inputs = getInputs(teams, file, 9);
			for(int j=0; j<10000; j++)
				for(int i=0; i<correct.length; i++)
					train(inputs[i], correct[i]);
			System.out.println("done");
		}	
		System.out.println("Running...");
		TeamFiller filledTeams = new TeamFiller();
		ArrayList<Team> teams = filledTeams.getTeams();
		String file = filledTeams.getObj().getFilename();
		double [][] inputs = getInputs(teams, file, 16);
		for(int i=0; i<inputs.length; i++)
		{
			double[] output = run(inputs[i]);
			System.out.println(i+1 + ": " + output[0] + " " + output[1]);
		}
	}	
	
	public void train(double[] inputs, double[] correct)
	{
		net.train(inputs, correct);
	}
	
	private double[][] getCorrect(String fileString, ArrayList<Team> teams)
	{
		File file = new File(fileString);
		try 
		{
			Scanner input = new Scanner(file);
			System.out.println(file);
			/*while(input.hasNextLine())
			{
				i++;
				System.out.println(i);
			}*/
			double[][] correct = new double[teams.get(0).numOfGames()*9][2];	
			for(int x=0; x<correct.length; x++)
			{
				correct[x][0] = input.nextDouble();
				correct[x][1] = input.nextDouble();
			}
			input.close();
			return correct;
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private double[][] getInputs(ArrayList<Team> teams, String file, int a)
	{
		double[][] inputs = new double[teams.get(0).numOfGames()*a][16];
		int[] teams1 = new int[4];
		File pathname = new File(file);
		Scanner input = null;
        try 
        {
			input = new Scanner(pathname);
	        input.useDelimiter("[B]|[R]|-|\\s+");
	        for(int z=0; z<teams.get(0).numOfGames()*a; z++)
	        {	
		        for(int x=0; x<=6; x++)
		        {
		            if(x==6)//isolating team numbers
		            {
		                teams1[0]=Integer.parseInt(input.next().replaceAll("\\s+",""));
		                //System.out.print(teams1[0] + " ");
		                teams1[1]=Integer.parseInt(input.next().replaceAll("\\s+",""));
		                //System.out.print(teams1[1] + " ");
		                teams1[2]=Integer.parseInt(input.next().replaceAll("\\s+",""));
		                //System.out.print(teams1[2] + " ");
		                teams1[3]=Integer.parseInt(input.next().replaceAll("\\s+",""));
		                //System.out.println(teams1[3]);
		                x=9;
		            }
		            else
		            	input.next();
		        }
				for(int i=0; i<teams.size(); i++)
				{
					if(teams.get(i).getTeamNumber()==teams1[0])
					{
						inputs[z][0] = teams.get(i).getTeamNumber();
		    			inputs[z][1] = teams.get(i).getOPR();
		    			inputs[z][2] = teams.get(i).getDPR();
		    			inputs[z][3] = teams.get(i).getCCWM();
		    			//System.out.print("1: " + inputs[z][0] + " ");
					}	
					else if(teams.get(i).getTeamNumber()==teams1[1])
					{
		    			inputs[z][4] = teams.get(i).getTeamNumber();
		    			inputs[z][5] = teams.get(i).getOPR();
		    			inputs[z][6] = teams.get(i).getDPR();
		    			inputs[z][7] = teams.get(i).getCCWM();
		    			//System.out.print("2: " + inputs[z][4] + " ");
					}	
					else if(teams.get(i).getTeamNumber()==teams1[2])
					{
		    			inputs[z][8] = teams.get(i).getTeamNumber();
		    			inputs[z][9] = teams.get(i).getOPR();
		    			inputs[z][10] = teams.get(i).getDPR();
		    			inputs[z][11] = teams.get(i).getCCWM();
		    			//System.out.print("3: " + inputs[z][8] + " ");
					}	
					else if(teams.get(i).getTeamNumber()==teams1[3])
					{
		    			inputs[z][12] = teams.get(i).getTeamNumber();
		    			inputs[z][13] = teams.get(i).getOPR();
		    			inputs[z][14] = teams.get(i).getDPR();
		    			inputs[z][15] = teams.get(i).getCCWM();
		    			//System.out.println("4: " + inputs[z][12]);
					}	
				}
                input.nextLine();
	        }	
			input.close();
		} 
        catch (FileNotFoundException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputs;
	}
	private double[] run(double[] inputs)
	{
		return net.run(inputs);
	}
}
