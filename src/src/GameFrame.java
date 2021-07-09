package src;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private enum ColorMap {
		white(0, Color.white), red(1, Color.red), blue(2, Color.blue), green(3, Color.green), cyan(4, Color.cyan), lightGray(5, Color.lightGray), 
		darkGray(6, Color.darkGray), magenta(7, Color.magenta), orange(8, Color.orange), pink(9, Color.pink), black(10, Color.black), yellow(11, Color.yellow), 
		gray(12, Color.gray), BLUE(13, Color.BLUE), GREEN(14, Color.GREEN);
		Color color;
		int index;
		ColorMap(int index, Color color){
			this.index = index;
			this.color = color;
		}
		static Color getColor(int index) {
			for (ColorMap c : ColorMap.values()) {
				if (c.index == index)
					return c.color;
			}
			return Color.black;
		}
		static int size() {
			int i = 0;
			for (ColorMap c : ColorMap.values()) {
				if (c.index > i)
					i = c.index;
			}
			return i;
		}
	}
	public static int colorMapSize = ColorMap.size();
	
	private List<WaterContainer> containers = new ArrayList<>();
	
	private class Pair {public int from, to;Pair(int f, int t) {from = f; to = t;}}
	private List<Pair> moves = new ArrayList<>();
	private MainFrame parent;
	private GamePanel panel;

	public GameFrame(MainFrame parent, int containersCount) {
		super("Water Sort");
		setSize(530, (((containersCount + 2) / 10) + 1) * 100 + 100);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		this.parent = parent;
		panel = new GamePanel(this);
		setContentPane(panel);
		addMouseListener(panel);
		addMouseMotionListener(panel);

		generate(containersCount);
		
		setVisible(true);
	}
	
	private void generate(int containersCount) {
		clear();
		Color [] values = { null, null, null, null };
	    for (int i = 0; i < containersCount + 2; i++) {
	        for (int j = 0; j < 4; j++)
	            values[j] = i < containersCount ? ColorMap.getColor((i + 1) % ColorMap.size()) : WaterContainer.emptyColor;
	        WaterContainer newCointainer = new WaterContainer(values, i + 1, 10 + (i % 10) * 50, 50 + (i / 10) * 100);
	        containers.add(newCointainer);
	    }
	    //mieszanie
	    int factor = containersCount * 100, to = 0, from = 0;
	    Random random = new Random();
	    for (int i = 0; i < factor; i++) {
	        do {
	            do {
	                from = random.nextInt(containersCount + 2);
	            } while (containers.get(from).isEmpty());
	            to = random.nextInt(containersCount + 2);
	            
	        } while (!containers.get(to).hasSpace());
	        containers.get(to).pushColor(containers.get(from).popColor());
	    }
	    while (!containers.get(containers.size() - 2).isEmpty()) {
	    	for (WaterContainer c : containers)
	    		if (c.hasSpace()) {
	    			c.pushColor(containers.get(containers.size() - 2).popColor());
	    			break;
	    		}
	    }
	    while (!containers.get(containers.size() - 1).isEmpty()) {
	    	for (WaterContainer c : containers)
	    		if (c.hasSpace()) {
	    			c.pushColor(containers.get(containers.size() - 1).popColor());
	    			break;
	    		}
	    }
	}
	
	private int firstIndex = -1;
	
	protected void firstSelected(int x, int y) {
		for (int i = 0; i < containers.size(); i++) 
			if (containers.get(i).isUnderMouse(x, y))
				firstIndex = i;
	}
	
	protected void secondSelected(int x, int y) {
		if (firstIndex >= 0) {
			int secondIndex = -1;
			for (int i = 0; i < containers.size(); i++) 
				if (containers.get(i).isUnderMouse(x, y)) 
					secondIndex = i;
			while (secondIndex != -1 && firstIndex != secondIndex && containers.get(secondIndex).hasSpace() && (containers.get(secondIndex).isEmpty() || containers.get(secondIndex).getTopColor() == containers.get(firstIndex).getTopColor())) {
				containers.get(secondIndex).pushColor(containers.get(firstIndex).popColor());
				moves.add(new Pair(firstIndex, secondIndex));
			}
			containers.get(firstIndex).stopMoving();
		}
		firstIndex = -1;
		
		boolean win = true;
		for (WaterContainer c : containers)
			if (!c.isFullOneColor())
				win = false;
		if (win) {
			panel.repaint();
			JOptionPane.showMessageDialog(this, "Wygrałeś!", "Wygrana", JOptionPane.PLAIN_MESSAGE | JOptionPane.YES_OPTION);
			parent.gameEnded();
		}
	}
	
	protected void mouseDragged(int x, int y) {
		if (firstIndex >= 0) 
			containers.get(firstIndex).moveTo(x, y);
	}
	
	private void clear() {
		if (!containers.isEmpty())
			containers.clear();
	}

	public void paintContainers(Graphics g) {
		for (WaterContainer c : containers)
			c.paint(g);
	}

	public void moveBack() {
		if (moves.size() > 0) {
			Pair p = moves.get(moves.size() - 1);
			moves.remove(moves.size() - 1);
			containers.get(p.from).pushColor(containers.get(p.to).popColor());
			panel.repaint();
		}
	}

	public void backToMenu() {
		parent.gameEnded();
	}
}