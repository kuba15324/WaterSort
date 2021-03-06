package src;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JSpinner containersCountSpinner = new JSpinner(new SpinnerNumberModel(GameFrame.colorMapSize / 2, 2, GameFrame.colorMapSize - 1, 1));
	
	private JLabel titleLabel = new JLabel("Water Sort"),
				   containersCountLabel = new JLabel("Ilość kolorów");
	
	private JButton startGameButton = new JButton("Rozpocznij");
	
	private Font titleFont = new Font("Source Sans Pro", Font.PLAIN, 25),
				 labelsFont = new Font("Source Sans Pro", Font.PLAIN, 20),
				 othersFont = new Font("Source Sans Pro", Font.PLAIN, 12);
	
	private GameFrame gameFrame;

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 248);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		
		titleLabel.setFont(titleFont);
		containersCountSpinner.setFont(othersFont);
		containersCountLabel.setFont(labelsFont);
		startGameButton.setFont(labelsFont);
		
		titleLabel.setBounds(getWidth() / 2 - 60, 20, 120, 30);
		containersCountSpinner.setBounds(181, 80, 75, 30);
		containersCountLabel.setBounds(10, 80, 161, 30);
		startGameButton.setBounds(68, 150, 144, 40);
		
		getContentPane().add(titleLabel);
		getContentPane().add(containersCountSpinner);
		getContentPane().add(containersCountLabel);
		getContentPane().add(startGameButton);
		
		startGameButton.addActionListener((ActionEvent e) -> {
			setVisible(false);
			gameFrame = new GameFrame(this, (int)containersCountSpinner.getValue());
		});
	}
	
	public void gameEnded() {
		setVisible(true);
		gameFrame.setVisible(false);
		gameFrame = null;
	}
}
