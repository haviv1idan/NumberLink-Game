package pack;

public class Point {
	
	private int value;
	private int row;
	private int col;
	private int pathNumber;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	Point(int val,int i,int j)
	{
		this.value = val;
		this.row = i;
		this.col = j;
		this.up = true;
		this.down = true;
		this.right = true;
		this.left = true;
	}
	
	public int getPathNumber() {
		return pathNumber;
	}

	public void setPathNumber(int pathNumber) {
		this.pathNumber = pathNumber;
	}
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean IsPointsEquals(Point p)
	{
		return (this.row - 1 == p.row && this.col - 1 == p.col);
	}
}