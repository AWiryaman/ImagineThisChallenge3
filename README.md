# ImagineThisChallenge3

For FTC#4029 2 Bits and a Byte Imagine This Challenge 3

This program uses scores from FTC Super Regionals competitions creates matrices and calculates OPR, CCWM and DPR. These statistics are then fed through a neural network to train it to be able to predict future matches. Essentially it weighs each of these stats to be able to determine how important they are to winning. 

This program runs data from last years FTC Super Regionals competitions. This could easily be adjusted to use more recent data as well, as using more data than what we currently use. In addition, the more times you train the network the more accurate it should be. The prediction capabilities of this program is currently very limited however, with more data and more statistics it could become more capable of predicting matches. 

Uses stdlib-package.jar available here: http://introcs.cs.princeton.edu/java/stdlib/stdlib-package.jar

Uses commons-math3-3.5.jar available here: http://mvnrepository.com/artifact/org.apache.commons/commons-math3/3.5

Run Master:
The program will first prompt you for the name of the file you would like to use to train along with the number of teams in that file.

There are 4 files that we have written the program to be able to use: EastHopper.txt, EastTesla.txt, WestGold.txt, and WestSilicon.txt. 

All of these files have 36 teams.

After entering this information about the first while, the program will say "Running..." and soon prompt for information about the text file to predict matches for.

You can use any of the 4 previous text files or Edison.txt or Franklin.txt to test the prediction ability.
Edison.txt and Frankin.txt have 64 teams in them.
