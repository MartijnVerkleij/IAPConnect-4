package nl.utwente.iapc.IAPConnect4.server;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ServerView extends JFrame {

	private static final long serialVersionUID = 1640405174352813833L;
	private ServerController controller;
	private JLabel titleLabel;
	private JButton stopButton;
	
	public ServerView(ServerController sc) {
		super("IAPConnect-4 Server");
		controller = sc;
		init();
	}
	private void init() {
		Container c = getContentPane();
		
		Font fnt = new Font("Comic Sans MS", java.awt.Font.BOLD , 24);
		// Element declaration
		titleLabel = new JLabel("Server");
		stopButton = new JButton("Stop Server");
		
		// Applying changes
		titleLabel.setFont(fnt);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Add ActionListeners
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				stopButton.setEnabled(false);
				dispose();
				controller.stopServer();
			} });
		// Add elements
		c.add(titleLabel);
		c.add(stopButton);
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		pack();
		setVisible(true);
	}
	
}
