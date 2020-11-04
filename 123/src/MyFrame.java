import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.OverlayLayout;

public class MyFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int WIDETH = 600;
	public static int HIGHTH = 800;
	PanPanel panel;
	
	
	public MyFrame(){
		init();
	}
	
	void init(){
		panel = new PanPanel();
		this.setTitle("Lipstick Jungle");
		this.setSize(WIDETH, HIGHTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setBackground(Color.white);
		panel.setSize(WIDETH, HIGHTH);
		panel.setLayout(new BorderLayout());
		this.add(panel);
		panel.launchJPanel();
	}
	
	public static void main(String[] args) {
		new MyFrame();
	}
}
