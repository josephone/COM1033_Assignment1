package jk00687_project_com1028;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ManagerPage {

	private JFrame frame;
	private JTextField teamName;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerPage window = new ManagerPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnManager = new JButton("Update team sheets");
		btnManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateTeamSheets();
			}
		});
		btnManager.setBounds(53, 209, 148, 23);
		frame.getContentPane().add(btnManager);
		
		JLabel lblManagerView = new JLabel("Manager view");
		lblManagerView.setBounds(10, 11, 93, 14);
		frame.getContentPane().add(lblManagerView);
		
		JLabel lblEnterTeamName = new JLabel("Enter team name:");
		lblEnterTeamName.setBounds(10, 56, 105, 14);
		frame.getContentPane().add(lblEnterTeamName);
		
		teamName = new JTextField();
		teamName.setBounds(115, 53, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);
	}
	
	public void updateTeamSheets() {
		
	}

}
