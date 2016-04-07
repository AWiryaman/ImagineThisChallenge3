import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Object;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;


public class CreateTeams1 
{
	private ArrayList<Team> teams = new ArrayList<Team>();
    private static int size=0; // user input
    private int[][] matrix;
    private static String fileName;//user input
    private Scanner jin = new Scanner(System.in);
	public CreateTeams1()
	{
        userInput();
        matrix = new int[size+1][size+1];
        clearFile();
        defineTeam(fileName);
	}
	
	public void defineTeam (String fileName) 
    {
        //isolates the information for each team
        //and creates Team type variables and puts
        //them into the ArrayList
        String pathname = fileName;
        File file = new File(pathname);
        Scanner input = null;
        
        int[] finalscores = new int[2];
        int[] teams1 = new int[4];
        int wm=0; //win margin - this number will always hold the positive value
        boolean winner=false; // if true, finalscores[0] teams won. if false, finalscores[1] teams won
        try
        {
            input = new Scanner(file);
            input.useDelimiter("[B]|[R]|-|\\s+");
            for(int x=0; input.hasNext(); x++)
            {
               //System.out.println("x: "+x +". "+ input.next());
             //  System.out.println(x);
               
                if(x==2) //isolating team scores
                {
                    finalscores[0]=Integer.parseInt(input.next().replaceAll("\\s+",""));
                    finalscores[1]=Integer.parseInt(input.next().replaceAll("\\s+",""));
                    if(finalscores[0]-finalscores[1]<0)
                    {
                    	wm=-1*(finalscores[0]-finalscores[1]);
                    	winner=false;
                    }
                    else
                    {
                    	wm=finalscores[0]-finalscores[1];
                    	winner=true;
                    }
                    x=3;
                }
                else if(x==6)//isolating team numbers
                {
                    teams1[0]=Integer.parseInt(input.next().replaceAll("\\s+",""));
                    teams1[1]=Integer.parseInt(input.next().replaceAll("\\s+",""));
                    teams1[2]=Integer.parseInt(input.next().replaceAll("\\s+",""));
                    teams1[3]=Integer.parseInt(input.next().replaceAll("\\s+",""));
                    x=9;
                }
                else if(x==21)//assigning team numbers to teams and creating variable type Team
                {
                    x=0; //resets x value for next line
                    
                    boolean alreadyATeam = false;
                    if(teams.size()==0) // for the teams from the first line in the file
                    {
                        for(int i=0; i<4; i++)
                        {
                            Team temp = new Team(teams1[i]);
                            if(i<2)
                            {
                                temp.addScore(finalscores[0]);
                            }
                            else
                                temp.addScore(finalscores[1]);
                            teams.add(temp);
                            
                            //matrix


                           matrix[0][i+1]=teams1[i];
                           matrix[i+1][0]=teams1[i];

                        }
                    }
                    else // to make sure the isolated teams are actually new teams
                    {
                        int size1=teams.size();
                        //boolean[] matched = new boolean[4];
                        for(int i=0; i<4; i++)
                        {
                            for(int j=0; j<size1&&!alreadyATeam; j++)
                                if(teams.get(j).getTeamNumber()==teams1[i])
                                {
                                    alreadyATeam=true;
                                    if(i<2)
                                        teams.get(j).addScore(finalscores[0]);
                                    else
                                        teams.get(j).addScore(finalscores[1]);
                                }
                            if(alreadyATeam==false)
                            {
                               Team temp = new Team(teams1[i]);
                               if(i<2)
                                temp.addScore(finalscores[0]);
                               else
                                temp.addScore(finalscores[1]);
                            teams.add(temp);
                            //matrix
                            boolean enterTeamNum=false;
                            for(int w=1; w<=size&&!enterTeamNum; w++)
                            {
                                if(matrix[w][0]==0)
                                {
                                    enterTeamNum=true;
                                    matrix[w][0]=teams1[i];
                                    matrix[0][w]=teams1[i];
                                }
                            }
                            }
                        alreadyATeam=false;
                        }   
                    }
                    matrixFill(teams1);//puts in the frequency that teams were alliances
                    
                    //changing TWM value
                    for(int i=0; i<teams.size(); i++)
                    {
                    	if(teams.get(i).getTeamNumber()==teams1[0]||teams.get(i).getTeamNumber()==teams1[1])
                    	{
                    		if(winner)
                    			teams.get(i).addTWM(wm);
                    		else
                    			teams.get(i).addTWM(-wm);
                    	}
                    	else if(teams.get(i).getTeamNumber()==teams1[2]||teams.get(i).getTeamNumber()==teams1[3])
                    	{
                    		if(winner)
                        		teams.get(i).addTWM(-wm);
                        	else
                        		teams.get(i).addTWM(wm);
                    	}
                    }
                    
                    if(finalscores[0]-finalscores[1]<0)
                    {
                    	//wm=-1*(finalscores[0]-finalscores[1]);
                    	//winner=false;
                    	writeWinner(0,1);
                    }
                    else if(finalscores[0]-finalscores[1]==0)
                    {
                    	//wm=finalscores[0]-finalscores[1];
                    	//winner=true;
                    	writeWinner(1,1);
                    }
                    else
                    {
                    	writeWinner(1,0);
                    }
                    
                    input.next();
                    if(input.hasNext())
                    input.next();
                }
                else
                    input.next();
               // System.out.println("Scores: "+finalscores[0]+", "+finalscores[1]);
               // System.out.println("Teams: "+ teams1[0]+", "+ teams1[1]+", "+ teams1[2]+", "+ teams1[3]+", ");
              //  System.out.println();
            }//end of for loop
            check();
            
            /*for(int x=0; x<size+1; x++)
            {
                for(int y=0; y<size+1; y++)
                    System.out.print(matrix[x][y]+" 	");
            System.out.println();
            }*/
        //check();
           // System.out.println(teams.size());
            
            //checkTWM();
        }//end of try
        catch (FileNotFoundException ex)
        {
            System.out.println("*** Cannot open " + pathname+ " ***");
            System.exit(1);  // quit the program
        } 

    }// end of defineTeam()
        
    private void check()
    {
        //Makes sure what the user entered as the number of teams
        //matches the number of teams found on the document
        if(teams.size()!=(size))
        {
            System.err.println("**** Fatal Error ****");
            System.err.println("The number of teams that you entered");
            System.err.println("does not equal the number of teams found");
            System.exit(1);  // quit the program
        }
    }
        
    private void matrixFill(int[] teams)
    {
        int col=0;
        int row=0;
        int col1=0;
        int row1=0;
        for(int x=0; x<=size; x++)
            if(matrix[0][x]==teams[0])
                col=x;
        for(int x=0; x<=size; x++)
            if(matrix[0][x]==teams[1])
                row=x;
        for(int x=0; x<=size; x++)
            if(matrix[0][x]==teams[2])
                col1=x;
        for(int x=0; x<=size; x++)
            if(matrix[0][x]==teams[3])
                row1=x;
       //filling matrix
        matrix[col][col]++;
        matrix[row][row]++;
        matrix[row1][row1]++;
        matrix[col1][col1]++;
        matrix[col][row]++;
        matrix[row][col]++;
        matrix[row1][col1]++;
        matrix[col1][row1]++;
    }
    
    private void userInput()
    {
        System.out.print("Enter the Name of the file with the data: ");
        fileName = jin.next();
        System.out.print("Enter the number of teams in this file: ");
        size=jin.nextInt();
    }
    
    public double[][] getMatrix()
    {
    	double[][] doubleMatrix = new double[matrix.length-1][matrix.length-1];
    	for(int i=0; i<doubleMatrix.length; i++)
    	{
    		for(int j=0; j<doubleMatrix[i].length; j++)
    		{
    			doubleMatrix[i][j]=(double) matrix[i+1][j+1];
    			//System.out.print((int) doubleMatrix[i][j] + " ");
    		}
    		//System.out.println();
    	}	
    	return doubleMatrix;
    }
    
    public ArrayList<Team> getTeams()
    {
    	return teams;
    }
    
    /*private void checkTWM()
    {
    	for(int x=0; x<size; x++)
    	{
    		System.out.print(teams.get(x).getTeamNumber() + ": ");
    		System.out.println(teams.get(x).getTWM());
    	}
    }*/
    
   private void writeWinner(int team1, int team2) //writes who won each match to a textfile
   {
	   //String newFile = fileName.substring(fileName.indexOf('.'))+"Winner.txt";
	    String pathname = fileName.substring(0,fileName.indexOf('.'))+"Winner.txt";
	    File file = new File(pathname);
	    //System.out.println(pathname);
	    FileWriter output = null;
	    try
	    {
	    	//System.out.println("in writeWinner()");
	       output = new FileWriter(file,true);
	       output.write(team1 + " " + team2 + "\n");
	       
	    output.close();
	    }
	    catch (FileNotFoundException ex)
	    {
	       System.out.println("Cannot create " + pathname);
	       System.exit(1);  // quit the program
	    } catch (IOException e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);  // quit the program
			System.err.println("Fail");
		}
	    
	     

   }
   
   private void clearFile()
   {
	   String pathname = fileName.substring(0,fileName.indexOf('.'))+"Winner.txt";
	    File file = new File(pathname);
	    //System.out.println(pathname);
	    PrintWriter output = null;
	    try
	    {
	    	//System.out.println("in writeWinner()");
	       output = new PrintWriter(file);
	       output.print("");
	       
	    output.close();
	    }
	    catch (FileNotFoundException ex)
	    {
	       System.out.println("Cannot create " + pathname);
	       System.exit(1);  // quit the program
	    } 
   }
   
   public String getFilename()
   {
	   return fileName;
   }
}// end of class
