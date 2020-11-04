import java.awt.Point;

public class Lip {
	private Point[] points;
	private double degress;
	private boolean isMove;
	private boolean isRotate;
	public static Point[] POINTS;
	public static double REDIUS = 80.00;
	public static double THETA1 = Math.atan(10.00 / (REDIUS + 50.00));
	public static double THETA2 = Math.atan(10.00 / (REDIUS + 100.00));
	
	public Lip(Point circleCenter) {
		this.isMove = false;
		this.isRotate = false;
		this.degress = -Math.PI * 3 / 2;
		points = new Point[5];
		for (int i = 0; i < points.length; i++) {
			points[i] = new Point();
		}
		points[0].x = 300;
		points[0].y = 600;
		points[1].x = 290;
		points[1].y = 650;
		points[2].x = 310;
		points[2].y = 650;
		points[3].x = 290;
		points[3].y = 700;
		points[4].x = 310;
		points[4].y = 700;
	}

	public void countPoint(Point center) {
		points[0].x = (int) (center.getX() + REDIUS * Math.cos(degress));
		points[0].y = (int) (center.getY() + REDIUS * Math.sin(degress));
		points[1].x = center.x + (int) ((REDIUS + 50) * Math.cos(degress - THETA1));
		points[1].y = center.y + (int) ((REDIUS + 50) * Math.sin(degress - THETA1));
		points[2].x = center.x + (int) ((REDIUS + 50) * Math.cos(degress + THETA1));
		points[2].y = center.y + (int) ((REDIUS + 50) * Math.sin(degress + THETA1));
		points[3].x = center.x + (int) ((REDIUS + 100) * Math.cos(degress - THETA2));
		points[3].y = center.y + (int) ((REDIUS + 100) * Math.sin(degress - THETA2));
		points[4].x = center.x + (int) ((REDIUS + 100) * Math.cos(degress + THETA2));
		points[4].y = center.y + (int) ((REDIUS + 100) * Math.sin(degress + THETA2));
	}

	/**
	 * Check if two lipsticks collide
	 * 
	 * @param lip01
	 * @return
	 */
	public boolean isCrashed(Lip lip01) {
		if (Math.abs(this.points[0].x - lip01.points[0].x) < 20
		 && Math.abs(this.points[0].y - lip01.points[0].y) < 10) {
			return true;
		}
		return false;
	}
	
	public double getDegress() {
		return this.degress;
	}

	public void setDegress(double d) {
		this.degress = d;
	}

	public Point[] getPoints() {
		return points;
	}

	public void setPoints(Point[] points) {
		this.points = points;
	}
	
	public boolean isMove() {
		return isMove;
	}

	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}

	public boolean isRotate() {
		return isRotate;
	}

	public void setRotate(boolean isRotate) {
		this.isRotate = isRotate;
	}
}
