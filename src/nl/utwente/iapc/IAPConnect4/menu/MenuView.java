package nl.utwente.iapc.IAPConnect4.menu;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MenuView extends JFrame {

	private static final long serialVersionUID = 1640405174352813833L;
	private MenuController controller;
	private JLabel titleLabel;
	private JButton joinButton;
	private JButton serverButton;
	private JButton quitButton;
	
	private JPanel joinPanel;
	private JPanel serverPanel;
	
	private JLabel joinPortLabel;
	private JTextField joinPort;
	private JLabel hostnameLabel;
	private JTextField hostname;
	private JLabel serverPortLabel;
	private JTextField serverPort;
	
	public MenuView (MenuController mc) {
		super("IAPConnect-4");
		controller = mc;
		init();
	}
	private void init () {
		Container c = getContentPane();
		
		Font fnt = new Font("Comic Sans", java.awt.Font.BOLD , 24);
		// Element declaration
		titleLabel = new JLabel("IAPConnect-4");
		joinButton = new JButton("Join online game");
		serverButton = new JButton("Start Server");
		quitButton = new JButton("Quit game");
		joinPanel = new JPanel();
		serverPanel = new JPanel();
		joinPortLabel = new JLabel("Port:");
		joinPort = new JTextField(5);
		hostnameLabel = new JLabel("Hostname:");
		hostname = new JTextField(10);
		serverPortLabel = new JLabel("Port:");
		serverPort = new JTextField(5);
		
		// Applying changes
		titleLabel.setFont(fnt);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		joinPanel.setVisible(false);
		serverPanel.setVisible(false);
		
		// Add ActionListeners
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				serverPanel.setVisible(false);
				joinPanel.setVisible(!joinPanel.isVisible());
			}});
		serverButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				joinPanel.setVisible(false);
				serverPanel.setVisible(!serverPanel.isVisible());
			}});
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				controller.QuitGame();
			}});
		
		// Add elements
		c.add(titleLabel);
		c.add(joinButton);
		c.add(joinPanel);
		joinPanel.add(hostnameLabel);
		joinPanel.add(hostname);
		joinPanel.add(joinPortLabel);
		joinPanel.add(joinPort);
		c.add(serverButton);
		c.add(serverPanel);
		serverPanel.add(serverPortLabel);
		serverPanel.add(serverPort);
		c.add(quitButton);
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		pack();
		setVisible(true);
	}
	
}
