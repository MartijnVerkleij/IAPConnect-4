package nl.utwente.iapc.IAPConnect4.menu;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuView extends JFrame {

	private static final long serialVersionUID = 1640405174352813833L;
	private MenuController controller;
	private JLabel titleLabel;
	private JButton showJoinButton;
	private JButton showServerButton;
	private JButton quitButton;
	
	private JPanel joinPanel;
	private JPanel serverPanel;
	
	private JLabel joinNickLabel;
	private JTextField joinNick;
	private JLabel joinPortLabel;
	private JTextField joinPort;
	private JLabel hostnameLabel;
	private JTextField hostname;
	private JLabel joinLabel;
	private JButton joinButton;
	
	private JLabel serverPortLabel;
	private JTextField serverPort;
	private JButton serverButton;
	
	public MenuView(MenuController mc) {
		super("IAPConnect-4");
		controller = mc;
		init();
	}
	private void init() {
		Container c = getContentPane();
		
		Font fnt = new Font("Arial", java.awt.Font.BOLD , 28);
		// Element declaration
		titleLabel = new JLabel("IAPConnect-4");
		showJoinButton = new JButton("Join online game");
		showServerButton = new JButton("Start Server");
		quitButton = new JButton("Quit game");
		joinPanel = new JPanel(new GridLayout(0, 2));
		serverPanel = new JPanel();
		
		joinNickLabel = new JLabel("Nickname:");
		joinNick = new JTextField(20);
		joinPortLabel = new JLabel("Port:");
		joinPort = new JTextField("" + 2000);
		hostnameLabel = new JLabel("Hostname:");
		hostname = new JTextField("localhost");
		joinLabel = new JLabel("Ready?");
		joinButton = new JButton("Join");
		
		serverPortLabel = new JLabel("Port:");
		serverPort = new JTextField("" + 2000);
		serverButton = new JButton("Start");
		
		// Applying changes
		titleLabel.setFont(fnt);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		showJoinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		joinPanel.setVisible(false);
		joinLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));;
		showServerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		serverPanel.setVisible(false);
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		// Add ActionListeners
		showJoinButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				serverPanel.setVisible(false);
				joinPanel.setVisible(!joinPanel.isVisible());
				pack();
			} });
		showServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				joinPanel.setVisible(false);
				serverPanel.setVisible(!serverPanel.isVisible());
				pack();
			} });
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				hideView();
				controller.clientMode(joinNick.getText(), hostname.getText(), Integer.parseInt(joinPort.getText()));
			} });
		serverButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				hideView();
				controller.serverMode(Integer.parseInt(serverPort.getText()));
			} });
		
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				controller.quitGame();
			} });
		
		// Add elements
		c.add(titleLabel);
		c.add(showJoinButton);
		c.add(joinPanel);
		joinPanel.add(joinNickLabel);
		joinPanel.add(joinNick);
		joinPanel.add(hostnameLabel);
		joinPanel.add(hostname);
		joinPanel.add(joinPortLabel);
		joinPanel.add(joinPort);
		joinPanel.add(joinLabel);
		joinPanel.add(joinButton);
		c.add(showServerButton);
		c.add(serverPanel);
		serverPanel.add(serverPortLabel);
		serverPanel.add(serverPort);
		serverPanel.add(serverButton);
		c.add(quitButton);
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		pack();
		setVisible(true);
	}
	public void showView() {
		this.setVisible(true);
	}
	public void hideView() {
		this.setVisible(false);
	}
}
