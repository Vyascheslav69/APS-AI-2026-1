package estruturas_dados.grafos;

public class Coord {
	
	private double x;
	private double y;
	
	public Coord(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	static public double dist(Coord p1, Coord p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	} 
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
