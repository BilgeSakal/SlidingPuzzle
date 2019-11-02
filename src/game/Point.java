package game;

// this is Point class
public class Point {

	private int x;
	private int y;

	public Point(int x, int y) {
		setX(x);
		setY(y);
	}

	@Override
	public String toString() {
		return "x: " + x + "\t" + "y:" + y;
	}

	@Override
	public boolean equals(Object obj) {
		Point p = (Point) obj;
		if (p == null)
			return false;
		return this.x == p.x && this.y == p.y;
	}

	// getters and setters

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
