package pack;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Maze extends JPanel {

	int row, col, pathNum,line,lastmove,helps,lives;
	String[] cheats = {"solve","help","refill"};
	private Stack<Integer> stackHelpPaths; 
	private Stack<Character> cheat;
	private int size; // size of rows and cols
	private Point[][] matrix;
	private int[][] board;
	private int[][] boardEzer;
	private Stack<Point> pointsStack;
	private Stack<PathNumbers> pathStack; // we don't know how much paths will
											// be so we need a stack
	private PathNumbers[] pathArr;

	public Maze(int n,int helps1,int lives1) {
		addMouseListener(new ML());
		addKeyListener(new KL());
		setFocusable(true);
		setBackground(Color.WHITE);
		Font font = new Font("Verdana", Font.BOLD, 20);
		JLabel l = new JLabel("<html><u><font color='red'>NumberLink Game</font></u></html>");
		l.setFont(font);
		add(l);
		helps = helps1;
		lives = lives1;
		this.board = new int[n][n];
		this.boardEzer = new int[n][n];
		this.cheat = new Stack<Character>();
		this.stackHelpPaths = new Stack<Integer>();
		this.size = n;
		try {
			File myObj = getFile(n);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {
			File myObj = getFile(n);
			Scanner myReader = new Scanner(myObj);
			Random rnd = new Random();
			int rows = rnd.nextInt(15); // 0-14
			//int rows = 1;
			line = rows;
			String data = "";
			while (myReader.hasNextLine() && rows > 0) {
				data = myReader.nextLine();
				rows--;
			}
			data = myReader.nextLine();
			int j = 0, row = 0, i;
			for (i = 0; i < data.length(); i += 2, j++) {
				// System.out.print(data.charAt(i));
				this.board[row][j] = data.charAt(i);
				this.boardEzer[row][j] = data.charAt(i);
				if (j == n - 1) {
					// System.out.println();
					j = -1;
					row++;
				}
			}
			myReader.close();
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++)
					System.out.print(this.board[i][j] - '0');
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image img1 = Toolkit.getDefaultToolkit().getImage("heart1.png");
		for(int i= 0;i<lives;i++)
			 g.drawImage(img1, getWidth() - 175 + i* 50, getHeight() - 100 , 50, 50, this);
		int cellSize = 50;
		int x, y = 50;
		g.setColor(Color.blue);
		for (int i = 1; i < size + 1; i++) {
			x = 50;
			for (int j = 1; j < size + 1; j++) {
				if (i == 1)
					g.drawLine(x, y, x + cellSize, y);
				if (j == 1)
					g.drawLine(x, y, x, y + cellSize);
				if (i == size)
					g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
				if (j == size)
					g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
				g.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
				g.drawLine(x, y + cellSize, x + cellSize, y + cellSize);
				int pathnum = this.board[i - 1][j - 1] - '0';
				if (pathnum > 0) {
					Font font = new Font("Verdana", Font.BOLD, 16);
					g.setFont(font);
					g.setColor(changeColor(pathnum));
					g.fillOval(x+5, y+5, 40, 40);
					g.setColor(Color.WHITE);
					g.drawString(Integer.toString(pathnum), x + 20, y + 30);
				}
				x += cellSize;
				g.setColor(Color.BLUE);
			}
			y += cellSize;
		}
	}

	class ML extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			lastmove=0;
			int x = e.getX(), y = e.getY();
			System.out.println("Mouse Clicked: x= " + x + " y= " + y);
			if (x - 50 < 50)
				col = 0;
			else if (x - 50 < 100)
				col = 1;
			else if (x - 50 < 150)
				col = 2;
			else if (x - 50 < 200)
				col = 3;
			else if (x - 50 < 250)
				col = 4;
			else if (x - 50 < 300)
				col = 5;
			else if (x - 50 < 350)
				col = 6;
			else if (x - 50 < 400)
				col = 7;
			else
				col = 8;

			if (y - 50 < 50)
				row = 0;
			else if (y - 50 < 100)
				row = 1;
			else if (y - 50 < 150)
				row = 2;
			else if (y - 50 < 200)
				row = 3;
			else if (y - 50 < 250)
				row = 4;
			else if (y - 50 < 300)
				row = 5;
			else if (y - 50 < 350)
				row = 6;
			else if (y - 50 < 400)
				row = 7;
			else
				row = 8;

			if (row >= size || col >= size)
				System.out.println("Its not in the board");
			else {
				System.out.println("row = " + row + " col = " + col);
				System.out.println(board[row][col] - '0');
				pathNum = board[row][col] - '0';
			}
		}
	}

	class KL extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
			if(e.getKeyCode() == 10)
			{
				String s = "";
				Stack<Character> ezer = new Stack<Character>();
				while(!cheat.isEmpty())
					ezer.push(cheat.pop());
				while(!ezer.isEmpty())
					s += ezer.pop();
				System.out.println(s);
				if(s.equals(cheats[0]))
					{
					solve();
					repaint();
					if(size <= 8)
						new FrameClass(size+1,helps,lives);
					else
					{
						JOptionPane.showMessageDialog(null, "Game Over...You Finish The Game :(");
						System.exit(0);
					}
					return;
					}
				else if(s.equals(cheats[1]))
					{
					if(helps == 0)
						JOptionPane.showMessageDialog(null, "You doesn't have more helps...");
					else {
						help();
						repaint();
						}
					if(checkIfEnd())
						if(size <= 8)
							new FrameClass(size+1,helps,lives);
						else
						{
							JOptionPane.showMessageDialog(null, "Game Over...You Finish The Game :(");
							System.exit(0);
						}

					return;
					}
				else if(s.equals(cheats[2]))
				{
					lives = 3;
					helps = 3;
				}
			}
			else if(e.getKeyCode() >= 65 && e.getKeyCode() <= 90)
			{
				cheat.push(e.getKeyChar());
				return;
			}
			else if(e.getKeyCode() == 27) //esc key == reset board... start again
			{
				if(lives > 1)
				{
					lives--;
					copyBoard1();
					repaint();
					return;
				}
				else if(lives == 1)
				{
					lives--;
					repaint();
					JOptionPane.showMessageDialog(null, "Game Over...YOU LOSE");
					System.exit(0);
				}
			}
			int col1 = col,row1=row,row2=row,col2=col;
			if (e.getKeyCode() == 38)
			{
				if(lastmove == 40 && board[--row2][col2] != '0')
					{
					board[row][col] = '0';
					row--;
					}
					else if (board[--row1][col] == '0')
					{
					row--;
					board[row][col] = pathNum + '0';
					}
				else
					System.out.println("cannot put on this square");
				
			}
			else if (e.getKeyCode() == 37)
			{
				if(lastmove == 39 && board[row2][--col2] != '0')
					{
					board[row][col] = '0';
					col--;
					}
				else if (board[row][--col1] == '0')
				{
					col--;
					board[row][col] = pathNum + '0';
				}
				else
					System.out.println("cannot put on this square");
			}
			else if (e.getKeyCode() == 39)
				{
				if(lastmove == 37 && board[row2][++col2] != '0')
					{
					board[row][col] = '0';
					col++;
					}
				else if (board[row][++col1] == '0')
					{
					col++;
					board[row][col] = pathNum + '0';
					}
				else
					System.out.println("cannot put on this square");
				}
			else
				{
				if(lastmove == 38 && board[++row2][col2] != '0')
					{
					board[row][col] = '0';
					row++;
					}
				else if (board[++row1][col] == '0')
					{
					row++;
					board[row][col] = pathNum + '0';
					}
				else
					System.out.println("cannot put on this square");
				}
			
			if(e.getKeyCode() <= 40 && e.getKeyCode() >= 37)
				lastmove = e.getKeyCode();
			

			if (checkIfEnd()) {
				repaint();
				JOptionPane.showMessageDialog(null, "Correct :)");	
				//int n = menu();
				if(size <= 8)
					new FrameClass(size+1,helps,lives);
				else
					{
					JOptionPane.showMessageDialog(null, "Game Over...You Finish The Game :)");
					System.exit(0);
					}
			} 
			else if(checkIfEnd() == false && checkBoardFull() == true)
			{
				JOptionPane.showMessageDialog(null, "Wrong...Try Again :(");
				lives--;
				copyBoard1();
				repaint();
			}
			else
				repaint();
		}
	}

	public static int  menu()
	{
		String[] options =  {"5X5", "6X6", "7X7","8X8","9X9","Exit"};
		int response = JOptionPane.showOptionDialog(null, "Choose Type Game \n", 
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
	
	public void solve()
	{
		try {
			File myObj = getFile(size);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {
			File myObj = getFile(size);
			Scanner myReader = new Scanner(myObj);
			int rows = line + 16; // 17 - 31
			String data = "";
			while (myReader.hasNextLine() && rows > 0) {
				data = myReader.nextLine();
				rows--;
			}
			data = myReader.nextLine();
			int j = 0, row = 0, i;
			for (i = 0; i < data.length(); i += 2, j++) {
					this.board[row][j] = data.charAt(i);
				if (j == size - 1) {
					j = -1;
					row++;
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public int findMaxPath()
	{
		int max = 0;
		for(int i = 0;i<size;i++)
			for(int j = 0;j<size;j++)
				if(board[i][j] - '0' > max)
					max = board[i][j] - '0';
		return max;
	}
	
	public boolean checkInStack(Stack<Integer> s,int num)
	{
		Stack<Integer> ezer = new Stack<Integer>();
		boolean flag = false;
		while(!s.isEmpty() && !flag)
		{
			if(num == s.peek())
				flag = true;
			ezer.push(s.pop());
		}
		while(!ezer.isEmpty())
			s.push(ezer.pop());
		return flag;
	}
	
	public void help()
	{
		try {
			File myObj = getFile(size);
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		try {
			File myObj = getFile(size);
			Scanner myReader = new Scanner(myObj);
			int rows = line + 16; // 17 - 31
			String data = "";
			while (myReader.hasNextLine() && rows > 0) {
				data = myReader.nextLine();
				rows--;
			}
			data = myReader.nextLine();
			int j = 0, row = 0, i;
			Random rnd = new Random();
			int path = rnd.nextInt(findMaxPath()) + 1;
			while(checkInStack(stackHelpPaths, path))
				path = rnd.nextInt(findMaxPath()) + 1;
			stackHelpPaths.push(path);
			for (i = 0; i < data.length(); i += 2, j++) {
				 //System.out.print(data.charAt(i));
				if (data.charAt(i) == path + '0')
				{
					this.board[row][j] = path + '0';
					this.boardEzer[row][j] = path + '0';
				}
				if (j == size - 1) {
					//System.out.println();
					j = -1;
					row++;
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		helps--;
	}
	
	public void copyBoard1()
	{
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				this.board[i][j] = this.boardEzer[i][j];
	}
	
	public boolean checkBoardFull()
	{
		//boolean flag = true;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board[i][j] == 0 || board[i][j] == '0')
					return  false;
		return true;
	}
	
	public File getFile(int n) {
		switch (n) {
		case 5:
			return new File("Puzzles5.txt");
		case 6:
			return new File("Puzzles6.txt");
		case 7:
			return new File("Puzzles7.txt");
		case 8:
			return new File("Puzzles8.txt");
		case 9:
			return new File("Puzzles9.txt");
		default:
			return new File("Puzzles5.txt");
		}
	}

	public boolean checkIfEnd() {
		boolean flag = true;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board[i][j] == 0 || board[i][j] == '0')
					flag = false;
		if (flag)
		{
			try {
				File myObj = getFile(size);
				if (myObj.createNewFile()) {
					System.out.println("File created: " + myObj.getName());
				} else {
					System.out.println("File already exists.");
				}
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			try {
				File myObj = getFile(size);
				Scanner myReader = new Scanner(myObj);
				int rows = line + 16; // 17 - 31
				String data = "";
				while (myReader.hasNextLine() && rows > 0) {
					data = myReader.nextLine();
					rows--;
				}
				data = myReader.nextLine();
				//System.out.println("size = " +  size);
				//System.out.print("board  = " + data);
				//System.out.println();
				int j = 0, row = 0, i;
				for (i = 0; i < data.length(); i += 2, j++) {
					 //System.out.print(data.charAt(i));
					if (this.board[row][j] != data.charAt(i))
						return false;
					if (j == size - 1) {
						//System.out.println();
						j = -1;
						row++;
					}
				}
				myReader.close();
				return true;
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
		return flag;
	}

	public Color changeColor(int path) {
		switch (path) {
		case 1:
			return Color.BLUE;
		case 2:
			return Color.BLACK;
		case 3:
			return Color.GRAY;
		case 4:
			return Color.RED;
		case 5:
			return Color.ORANGE;
		case 6:
			return Color.PINK;
		case 7:
			return Color.GREEN;
		}
		return Color.blue;
	}

	public PathNumbers[] stackToArray(Stack<PathNumbers> pStack) // convert the
																	// stack of
																	// paths to
																	// array
	{
		Stack<PathNumbers> ezer = pStack;
		int n = ezer.size(), i = 0;
		PathNumbers[] arr = new PathNumbers[n];
		while (!ezer.isEmpty()) {
			arr[i] = ezer.pop();
			i++;
		}
		return arr;
	}

	public void printPathStack(Stack<PathNumbers> pStack) {
		Stack<PathNumbers> ezer = new Stack<PathNumbers>();
		while (!pStack.isEmpty()) {
			System.out.println("Path - " + (pStack.peek().getPathNum()) + ": start= row:"
					+ pStack.peek().getStart().getRow() + " col: " + pStack.peek().getStart().getCol() + " End= row:"
					+ pStack.peek().getEnd().getRow() + " col: " + pStack.peek().getEnd().getCol());
			ezer.push(pStack.pop());
		}
		this.pathStack = flipStack(ezer);
	}

	public Stack<PathNumbers> flipStack(Stack<PathNumbers> pStack) {
		Stack<PathNumbers> ezer = new Stack<PathNumbers>();
		while (!pStack.isEmpty())
			ezer.push(pStack.pop());
		return ezer;
	}

	public static boolean isPointsEqual(Point p1, Point p2) {
		if (p1.getRow() == p2.getRow() && p1.getCol() == p2.getCol())
			return true;
		return false;
	}

	public static void printStack(Stack<Point> s) {
		while (!s.isEmpty()) {
			System.out.println(s.pop().getValue());
		}
	}

	public static boolean checkIfHaveMove(Point[][] mat, Point p) {
		int row = p.getRow() + 1;
		int col = p.getCol() + 1;
		if (mat[row - 1][col].getValue() == 0) // - up
			return true;
		else if (mat[row + 1][col].getValue() == 0) // - down
			return true;
		else if (mat[row][col - 1].getValue() == 0) // - left
			return true;
		else if (mat[row][col + 1].getValue() == 0) // - right
			return true;
		return false;
	}

	public static void printMatrix(Point[][] mat, int n) {
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < n + 1; j++)
				System.out.format("%02d ", mat[i][j].getValue());
			System.out.println();
		}
	}

	/* initBoard - create the matrix */
	public static void initBoard(Point[][] mat, int n) {
		for (int i = 0; i < n + 2; i++)
			for (int j = 0; j < n + 2; j++)
				if (i == 0 || i == n + 1 || j == 0 || j == n + 1)
					mat[i][j] = new Point(-1, i, j);
				else
					mat[i][j] = new Point(0, i, j);
	}

	public static boolean isDirectionGood(Point[][] mat, Point p, int n, int direction) {
		int row = p.getRow() + 1;
		int col = p.getCol() + 1;
		// 1- left, 2- right, 3- up, 4- down
		switch (direction) {
		case 1:
			if (col != 1 && mat[row][col - 1].getValue() == 0)
				return true;
			return false;
		case 2:
			if (col != n && mat[row][col + 1].getValue() == 0)
				return true;
			return false;
		case 3:
			if (row != 1 && mat[row - 1][col].getValue() == 0)
				return true;
			return false;
		case 4:
			if (row != n && mat[row + 1][col].getValue() == 0)
				return true;
			return false;
		}
		return false;
	}

	public static boolean checkNear(Point p1, Point p2) {
		if (p1.getRow() - p2.getRow() == 1 || p1.getRow() - p2.getRow() == -1 || p1.getCol() - p2.getCol() == 1
				|| p1.getCol() - p2.getCol() == -1)
			return true;
		return false;
	}

	/* fillMatrix - fill the matrix and create paths */
	public static void fillMatrix(Point[][] mat, int n, Stack<Point> s, Stack<PathNumbers> pStack) {
		boolean path = false; // check end of path
		int rank = 1, dir, pathNum = 1;
		Random rnd = new Random();
		int row = rnd.nextInt(n - 1);
		int col = rnd.nextInt(n - 1);
		// System.out.println("row: " + row + " col: " + col);
		s.push(new Point(rank, row, col));
		PathNumbers p = new PathNumbers(new Point(rank, row, col), new Point(0, 0, 0), pathNum);
		mat[row + 1][col + 1].setValue(rank);
		mat[row + 1][col + 1].setPathNumber(pathNum);
		while (rank < n * n) {
			if (checkIfHaveMove(mat, new Point(rank, row, col))) {
				dir = rnd.nextInt(4) + 1; // 1- left, 2- right, 3- up, 4- down
				if (isDirectionGood(mat, new Point(rank, row, col), n, dir)) {
					switch (dir) {
					case 1:
						mat[row + 1][col + 1].setLeft(false);
						mat[row + 1][col].setRight(false);
						col--;
						break;
					case 2:
						mat[row + 1][col + 2].setLeft(false);
						mat[row + 1][col + 1].setRight(false);
						col++;
						break;
					case 3:
						mat[row][col + 1].setDown(false);
						mat[row + 1][col + 1].setUp(false);
						row--;
						break;
					case 4:
						mat[row + 1][col + 1].setDown(false);
						mat[row + 2][col + 1].setUp(false);
						row++;
						break;
					}
					rank++;
					s.push(new Point(rank, row, col));
					mat[row + 1][col + 1].setValue(rank);
					mat[row + 1][col + 1].setPathNumber(pathNum);
					if (path == true) {
						path = false;
						p = new PathNumbers(new Point(rank, row, col), new Point(0, 0, 0), pathNum);
					}
				}
			} else {
				if (path == false) {
					path = true;
					p.setEnd(new Point(rank, row, col));
					if (checkNear(p.getStart(), p.getEnd()) || p.getStart().getRow() == p.getEnd().getRow()
							&& p.getStart().getCol() == p.getEnd().getCol())
						continue;
					else {
						pStack.push(p);
						pathNum++;
					}
				}
				s.pop();
				Point out = s.peek();
				if (row > out.getRow())
					row--;
				else if (row < out.getRow())
					row++;
				else if (col > out.getCol())
					col--;
				else
					col++;
			}
		}
		if (p.getStart().getValue() != 0)
			p.setEnd(new Point(rank, row, col));
		else
			p = new PathNumbers(new Point(rank, row, col), new Point(rank, row, col), pathNum + 1);
		if (checkNear(p.getStart(), p.getEnd())
				|| p.getStart().getRow() == p.getEnd().getRow() && p.getStart().getCol() == p.getEnd().getCol())
			return;
		else
			pStack.push(p);
	}
}