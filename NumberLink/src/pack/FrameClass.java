package pack;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FrameClass extends JFrame {
	public int helps,lives;
	public FrameClass(int size, int helps, int lives)
	{
		Maze m= new Maze(size,helps,lives);
		add(m);
		setFocusable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(size*50 + 100,size*50 + 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);

	}

	public static int  menu()
	{
		String[] options =  {"5X5", "6X6", "7X7","8X8","9X9","Exit"};
		int response = JOptionPane.showOptionDialog(null, "Choose Type Game \n"
				+ "easy - 5X5 , 6X6 \n"
				+ "medium -  7X7 , 8X8 \n"
				+ "hard - 9X9 ", 
				"Starting Game Options",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		
		switch(response)
		{
			case -1:
				System.out.println("Option Dialog Window Was Closed");
				System.exit(0);
			case 0:
				return 5;
			case 1:
				return 6;
			case  2:
				return 7;
			case 3:
				return 8;
			case 4:
				return 9;
			case 5: 
				System.out.println("Clicked on Exit ");
				System.exit(0);
			default:
				return 5;		
		}

	}
	
	public static void main(String[] args)
	{
		JOptionPane.showMessageDialog(null,
				"Hi And welcome My Game \n"
				+ "My Game called Numberlink and this is how to play: \n"
				+ "1. Click on the number that you want to connect \n"
				+ "2. start walk with the arrows on the keyboard \n"
				+ "3. You can back only on the last move \n"
				+ "4. Press on esc to try again \n"
				+ "Good luck :)");
		//int n = menu();
		int n = 5;
		int helps = 3;
		int lives = 3;
		new FrameClass(n, helps, lives);
	}
}
