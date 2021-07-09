package src;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	private GameFrame parent;
	private JButton backButton = new JButton("Cofnij"),
					menuButton = new JButton("Wróć do menu");
	
	private Font labelsFont = new Font("Source Sans Pro", Font.PLAIN, 20);
	
	GamePanel(GameFrame parent){
		setBounds(parent.getBounds());
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		this.parent = parent;
		
		backButton.setBounds(getWidth() / 4 - 80, 10, 160, 30);
		backButton.setFont(labelsFont);
		backButton.addActionListener((ActionEvent e) -> {
			parent.moveBack();
		});
		
		menuButton.setBounds(getWidth() / 4 * 3 - 80 , 10, 160, 30);
		menuButton.setFont(labelsFont);
		menuButton.addActionListener((ActionEvent e) -> {
			parent.backToMenu();
		});
		
		add(backButton);
		add(menuButton);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			parent.firstSelected(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			parent.secondSelected(e.getX(), e.getY());
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		parent.mouseDragged(e.getX(), e.getY());
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		parent.paintContainers(g);
	}
}