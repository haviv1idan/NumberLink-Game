import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DragRect extends JPanel {
	Rectangle area = new Rectangle(50, 50, 100, 100);
	int dx, dy;
	public DragRect() {
		addMouseListener(new ML());
		addMouseMotionListener(new MML());
	}
	class ML extends MouseAdapter {
		public void mousePressed(MouseEvent me) {
				dx = me.getX() - area.x;
				dy = me.getY() - area.y;
		}	
	}
	
	class MML extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent me) {
			if (area.contains(me.getPoint()))
				area.setLocation(me.getX() - dx, me.getY() - dy);
			repaint();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.red);
		g.fillRect(area.x, area.y, area.width, area.height);
	}
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new DragRect());
		f.setSize(400, 400);
		f.setVisible(true);
	}
}
