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
import javax.swing.JPanel;

public class ClientView extends JFrame {

	private static final long serialVersionUID = -6605519650382227198L;
	private ClientController controller;
	private JLabel titleLabel;
	private JPanel board;
	
	
	public ClientView(ClientController cc) {
		super("IAPConnect-4");
		controller = cc;
		init();
	}
	private void init() {
		Container c = getContentPane();
		
		Font fnt = new Font("Arial", java.awt.Font.BOLD , 28);
		// Element declaration
		titleLabel = new JLabel("Client");
		
		// Applying changes
		titleLabel.setFont(fnt);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		// Add ActionListeners
		
		// Add elements
		drawBoard(board);
		c.add(titleLabel);
		c.add(board);
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		pack();
		setVisible(true);
	}
	private void drawBoard(JPanel target) {
		target.setLayout(new GridLayout(0, controller.getBoard().getBoardWidth()));
		Color empty = new Color(255, 255, 255);
		Color player1 = new Color(0, 255, 0);
		Color player2 = new Color(255, 0, 0);
		for (int y = 0; y < controller.getBoard().getBoardHeight(); y++) {
			for (int x = 0; x < controller.getBoard().getBoardWidth(); x++) {
				JButton field = new JButton(x+","+y);
				switch (controller.getBoard().getField(x, y)) {
					case 0: field.setForeground(empty);
						break;
					case 1: field.setForeground(player1);
						break;
					case 2: field.setForeground(player2);
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
		//((GridLayout) board.getLayout()).;
		
	}
}
