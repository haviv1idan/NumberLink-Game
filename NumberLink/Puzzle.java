package pack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Puzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			File myObj = new File("Puzzles5x5.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
/*		try { 
		      FileWriter myWriter = new FileWriter("Puzzles5x5.txt");
		      myWriter.write("01 02 03 04\n");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    } */
		try {
		      File myObj = new File("Puzzles5x5.txt");
		      Scanner myReader = new Scanner(myObj); 
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        int j=1;
		        for(int i=0;i<data.length();i+=2,j++)
		        	{
		        	System.out.print(data.charAt(i));
		        	if(j%5==0)
		        		System.out.println();
		        	}
				System.out.println();
				if(j == 26)
					break;
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    } 
	}

}
