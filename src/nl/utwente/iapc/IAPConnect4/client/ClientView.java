package nl.utwente.iapc.IAPConnect4.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientView extends JFrame {

	private static final long serialVersionUID = -6605519650382227198L;
	private ClientController controller;
	private JLabel titleLabel;
	private JPanel boardJPanel;
	
	private final Color empty = new Color(255, 255, 255);
	private final Color player1 = new Color(0, 255, 0);
	private final Color player2 = new Color(255, 0, 0);
	
	
	public ClientView(ClientController cc) {
		super("IAPConnect-4");
		controller = cc;
		init();
	}
	private void init() {
		System.out.println("Gui spawned!");
		Container c = getContentPane();
		
		Font fnt = new Font("Arial", java.awt.Font.BOLD , 28);
		// Element declaration
		titleLabel = new JLabel("Client");
		boardJPanel = new JPanel();
		
		// Applying changes
		titleLabel.setFont(fnt);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		// Add ActionListeners
		
		// Add elements
		drawBoard(boardJPanel);
		c.add(titleLabel);
		c.add(boardJPanel);
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		pack();
		setVisible(true);
	}
	
	private void drawBoard(JPanel target) {
		target.setLayout(new GridLayout(0, controller.getBoard().getBoardWidth()));
		for (int y = 0; y < controller.getBoard().getBoardHeight(); y++) {
			for (int x = 0; x < controller.getBoard().getBoardWidth(); x++) {
				JButton field = new JButton(x + "," + y);
				field.setEnabled(false);
				switch (controller.getBoard().getField(x, y)) {
					case 0: field.setBackground(empty);
						break;
					case 1: field.setBackground(player1);
						break;
					case 2: field.setBackground(player2);
						break;
				}
				field.addActionListener(new ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						doMove(e);
					} });
				target.add(field);
			}
		} 
	}
	
	private void doMove(java.awt.event.ActionEvent e) {
		// Executes when button pressed
		controller.doMove(boardJPanel.getComponentZOrder((JButton) e.getSource()) % controller.getBoard().getBoardWidth());
		
	}
	public void updateCell (int x, int y, int player, boolean enabled){
		JButton field = (JButton) boardJPanel.getComponent(x * controller.getBoard().getBoardWidth() + y);
		field.setEnabled(enabled);
		switch (controller.getBoard().getField(x, y)) {
		case 0: field.setForeground(empty);
			break;
		case 1: field.setForeground(player1);
			break;
		case 2: field.setForeground(player2);
			break;
		}
	}
	public void updateAllCells (boolean enabled){
		for (int x = 0; x < boardJPanel.getComponentCount(); x++) {
			boardJPanel.getComponent(x).setEnabled(enabled);
		}
	}
	public void refreshBoard() {
		JPanel newBoardJPanel = new JPanel();
		drawBoard(newBoardJPanel);
		boardJPanel = newBoardJPanel;
	}
	public void unlockBoard(){
		for (Component field : boardJPanel.getComponents()) {
			if (field.getBackground() == empty) {
				field.setEnabled(true);
			}
		}
	}
	public void gameEnd(String winnerName) {
		String result = "The game has ended, you ";
		if (winnerName == null) {
			result += "didn't lose, but didn't win either.\nIt's a draw!";
		} else if (winnerName.equals(controller.getClient().getPlayerName())) {
			result += "won!\nWell done!";
		} else {
			result += "lost!\nBetter luck next time!";
		}
		JOptionPane.showMessageDialog(this, result);
	}
}
