package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

public class KnockoutsPage {

	private JFrame frame;
	private JTextField teamName = null;
	private JTextField goalsScored = null;
	private JTextField goalsConceded = null;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					KnockoutsPage window = new KnockoutsPage();
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
	public KnockoutsPage() {
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
		
		JLabel lblLeagueDeveloperView = new JLabel("League developer view");
		lblLeagueDeveloperView.setBounds(10, 11, 131, 14);
		frame.getContentPane().add(lblLeagueDeveloperView);
		
		JLabel lblNewLabel = new JLabel("Enter results of knockouts fixture below");
		lblNewLabel.setBounds(10, 96, 281, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Team name:");
		lblNewLabel_1.setBounds(10, 140, 105, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Goals Scored:");
		lblNewLabel_2.setBounds(10, 165, 90, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblGoalsConceded = new JLabel("Goals Conceded:");
		lblGoalsConceded.setBounds(10, 190, 105, 14);
		frame.getContentPane().add(lblGoalsConceded);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(66, 227, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		teamName = new JTextField();
		teamName.setBounds(126, 137, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);
		
		goalsScored = new JTextField();
		goalsScored.setBounds(126, 162, 86, 20);
		frame.getContentPane().add(goalsScored);
		goalsScored.setColumns(10);
		
		goalsConceded = new JTextField();
		goalsConceded.setBounds(126, 187, 86, 20);
		frame.getContentPane().add(goalsConceded);
		goalsConceded.setColumns(10);
		
		String teamNameString = teamName.getText();
		String goalsScoredString = goalsScored.getText();
		String goalsConcededString = goalsConceded.getText();
		
		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateKnockouts(teamNameString, goalsScoredString, goalsConcededString);
				JOptionPane.showMessageDialog(null, "Knockouts updated");
			}

		});
	}
	
	public void updateKnockouts(String teamName, String goalsScored, String goalsConceded) {
		
	}
}
