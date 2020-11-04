import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
//import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int  THRESHOLDVALUE = 150;
	private String heartFileAddress = "./src/heart.jpg";
	private String gameOverAddress = "./src/gameOver.jpg";
	private PaintThread myThread;
	private Image backGround = new ImageIcon("./src/backGround.jpg").getImage();
	
	static Integer WIDTH = 200;
	static Integer HIGHTH = 200;
	static int ARCANGLE = 0;
	static Integer playedNumber = 0;
	static int PON = 1;
	static Color colors[] = { Color.blue, Color.cyan, Color.DARK_GRAY, Color.gray, Color.green, Color.LIGHT_GRAY,
			Color.magenta, Color.ORANGE, Color.PINK, Color.red, Color.white, Color.yellow };
	public static Integer X_COORDINATE = (MyFrame.WIDETH - WIDTH) / 2;
	public static Integer Y_COORDINATE = 100;
	static Point CENTER = new Point(X_COORDINATE + 100, Y_COORDINATE + 100);
	public static Integer LIVEWIDTH = 30;
	public static Integer LIVEHEIGHT = 30;
	public static Integer SPEED = 10;
	
	public static List<Lip> lips;
	private double LIMIT = 2.00 / 360.00 * Math.PI;
	private int NUMBER = 8;
	private int lives = 5;
	private static int lipsNumber = 9;
	private int REDIUS = 100;
	private boolean isClicked = false;
	private boolean gameIsOver = false;
	
	int start;
	int arcangle;

	public PanPanel() {
		init();
		LipstickListener();
	}

	void init() {
		start = 0;
		arcangle = getRealARC();
		lips = new ArrayList<>();
		for (int i = 0; i < lipsNumber; i++) {
			Lip lip = new Lip(CENTER);
			lips.add(lip);
		}
	}

	public int getRealARC() {
		ARCANGLE = 360 / NUMBER;
		return ARCANGLE;
	}

	/*
	 * It is used to draw the basic graph, and the NUMBER size is used to 
	 * determine the sharding of the graph
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backGround, 0,0,this.getWidth(), this.getHeight(), null);
		if (!gameIsOver) {
			/**
			 * Paint lipstick to launch
			 */
			
			for (int i = 0; i <= playedNumber && i < lips.size(); i++) {
				Lip lip = lips.get(i);
				if (i < playedNumber || i == 0) {
					drawLips(g, lip);
				}
				if (i == playedNumber && i > 0) {
					if (!isCrashed(i - 1)) {
						if (playedNumber - 1 > -1 && lips.get(i - 1).isRotate()) {
							drawLips(g, lip);
						}
					} else {
						lives--;
						lipsNumber = 10;
						playedNumber = 0;
						init();
						if (lives < 0)
							gameIsOver = true;
					}

				}
			}
			/***
			 * This is the place to split the circle
			 */
			for (int i = 0; i < NUMBER; i++) {
				g.setColor(colors[i % colors.length]);
				g.fillArc(X_COORDINATE, Y_COORDINATE, WIDTH, HIGHTH, start, arcangle);
				g.setColor(Color.white);
				start += arcangle;
			}

			/**
			 * There are a few lives left to draw the character
			 */
			int live_x_start = 10;
			int live_y_start = 10;
			ImageIcon liveIcon = new ImageIcon(heartFileAddress);
			for (int i = 0; i < lives; i++) {
				g.drawImage(liveIcon.getImage(), live_x_start, live_y_start, LIVEWIDTH, LIVEHEIGHT, null);
				live_x_start += 40;
			}

			/**
			 * The painting and a few lipsticks
			 */
			live_x_start = 10;
			live_y_start += 700;
			for (int i = 0; i < lipsNumber; i++) {
				g.setColor(new Color(255, 215, 0));
				g.fillRect(live_x_start, live_y_start-20, 50, 20);
				g.setColor(Color.red);
				
				int[] xPoints ={live_x_start+50,live_x_start+100,live_x_start+50};
				int[] yPoints= {live_y_start,live_y_start-10,live_y_start-20};
				g.fillPolygon(xPoints, yPoints, 3);
				live_y_start -= 30;
			}

			move();
		}
	else{
			ImageIcon icon= new ImageIcon(gameOverAddress);
			g.drawImage(icon.getImage(), 0, 200, Color.black, null);
		}
	}

	/*
	 * The action of motion and rotation
	 */
	public void move() {
		positiveOrNegative(playedNumber,CENTER);
		start += PON;
		for (int i = 0; i <= playedNumber && i < lips.size(); i++) {
			Lip temp = lips.get(i);
			Point[] points = temp.getPoints();
			if (temp.isMove()) {
				for (int j = 0; j < points.length; j++) {
					points[j].y -= SPEED;
				}
				if (Math.abs(points[0].y - CENTER.y) <= REDIUS) {
					temp.setMove(false);
					temp.setRotate(true);
				}
			}
			if (temp.isRotate()) {
				temp.setDegress(temp.getDegress() -  PON * LIMIT);
				temp.countPoint(CENTER);
			}
		}

	}

	private void drawLips(Graphics g, Lip lip) {
		Point[] points = lip.getPoints();
		g.setColor(Color.black);
		g.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
		g.drawLine(points[0].x, points[0].y, points[2].x, points[2].y);
		g.drawLine(points[1].x, points[1].y, points[2].x, points[2].y);
		g.drawLine(points[1].x, points[1].y, points[3].x, points[3].y);
		g.drawLine(points[2].x, points[2].y, points[4].x, points[4].y);
		g.drawLine(points[3].x, points[3].y, points[4].x, points[4].y);
		g.setColor(Color.red);
		int[] xPoints01 = { points[0].x, points[1].x, points[2].x };
		int[] yPoints01 = { points[0].y, points[1].y, points[2].y };
		int[] xPoints02 = { points[1].x, points[2].x, points[4].x, points[3].x };
		int[] yPoints02 = { points[1].y, points[2].y, points[4].y, points[3].y };
		g.fillPolygon(xPoints01, yPoints01, 3);
		g.setColor(new Color(255, 215, 0));
		g.fillPolygon(xPoints02, yPoints02, 4);
	}
	
	
	public void positiveOrNegative(int playedNumber,Point center){
		int sum = 0;
		for(int i =0;i<=playedNumber && i<lips.size();i++){
			Point point = lips.get(i).getPoints()[0];
			sum += point.getX() - center.getX();
		}
		if(sum>= THRESHOLDVALUE){
			PON = -1;
		}else if(sum<= -THRESHOLDVALUE){
			PON = 1;
		}
	}
	
	/*
	 * Start thread control
	 */
	public void launchJPanel() {
		myThread = new PaintThread();
		if (myThread.flag)
			myThread.run();
	}
	
	/**
	 * Used to monitor whether lipstick is emitted
	 */
	public void LipstickListener() {
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				isClicked = true;
				if (playedNumber <= lips.size()) {
					Lip lip = lips.get(playedNumber);
					Point[] points = lip.getPoints();
					if (isClicked) {
						if (Math.abs(points[0].y - CENTER.y) > REDIUS) {
							lip.setMove(true);
							lip.setRotate(false);
						} else {
							lip.setRotate(true);
							lip.setMove(false);
						}
						isClicked = false;
					}
					if (playedNumber < lips.size()) {
						playedNumber += 1;
						lipsNumber--;
					}
				}
				setNextRoundInit(playNextRound());
			}
		});
	}
	
	void setNextRoundInit(boolean flag) {
		if (flag) {
			playedNumber = 0;
			lipsNumber = 10;
			init();
		}
	}

	boolean playNextRound() {
		if (lives > -1 && lipsNumber == 0)
			return true;
		else
			return false;
	}

	/**
	 * Check for collisions with other lipsticks
	 * 
	 * @param lip
	 * @return
	 */
	public boolean isCrashed(int index) {
		Lip lip = lips.get(index);
		if (index == 0)
			return false;
		else if (index > 0 && index < lips.size()) {
			for (int i = 0; i < index; i++) {
				Lip l = lips.get(i);
				if (l.isCrashed(lip))
					return true;
			}
		}

		return false;
	}

	public int getNUMBER() {
		return NUMBER;
	}

	public void setNUMBER(int nUMBER) {
		NUMBER = nUMBER;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public boolean isGameIsOver() {
		return gameIsOver;
	}

	/**
	 * A thread control class
	 * 
	 * @author
	 *
	 */
	class PaintThread implements Runnable {
		private boolean flag = true;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				repaint();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
