package pack;

public class PathNumbers {
	private Point start;
	private Point end;
	private int pathNum;
	
	public PathNumbers(Point s,Point e,int pNum)
	{
		this.start = s;
		this.end = e;
		this.pathNum = pNum;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public int getPathNum() {
		return pathNum;
	}

	public void setPathNum(int pathNum) {
		this.pathNum = pathNum;
	}
	
}
