package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class EditStats {

	private JFrame frame;
	private JTextField teamName;
	private JTextField goalsScored;
	private JLabel lblAdjustedGoalsConceded;
	private JTextField goalsConceded;
	private JButton btnUpdateStatistics;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditStats window = new EditStats();
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
	public EditStats() {
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
		lblLeagueDeveloperView.setBounds(10, 11, 119, 14);
		frame.getContentPane().add(lblLeagueDeveloperView);

		JLabel lblTeamName = new JLabel("Team name:");
		lblTeamName.setBounds(10, 58, 87, 14);
		frame.getContentPane().add(lblTeamName);

		teamName = new JTextField();
		teamName.setBounds(176, 55, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		JLabel lblAdjustedGoalsScored = new JLabel("Adjusted goals scored:");
		lblAdjustedGoalsScored.setBounds(10, 94, 187, 14);
		frame.getContentPane().add(lblAdjustedGoalsScored);

		goalsScored = new JTextField();
		goalsScored.setBounds(176, 86, 86, 20);
		frame.getContentPane().add(goalsScored);
		goalsScored.setColumns(10);

		lblAdjustedGoalsConceded = new JLabel("Adjusted goals conceded:");
		lblAdjustedGoalsConceded.setBounds(10, 130, 164, 14);
		frame.getContentPane().add(lblAdjustedGoalsConceded);

		goalsConceded = new JTextField();
		goalsConceded.setBounds(176, 127, 86, 20);
		frame.getContentPane().add(goalsConceded);
		goalsConceded.setColumns(10);

		btnUpdateStatistics = new JButton("Update statistics");
		btnUpdateStatistics.setBounds(96, 184, 149, 23);
		frame.getContentPane().add(btnUpdateStatistics);
		btnUpdateStatistics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String teamNameString = teamName.getText();
				String goalsScoredString = goalsScored.getText();
				String goalsConcededString = goalsConceded.getText();

				try {
					updateStatistics(teamNameString, goalsScoredString, goalsConcededString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Statistics updated");
				LeagueDeveloperPage.main();
				frame.dispose();
			}

		});
	}

	public void updateStatistics(String teamName, String goalsScored, String goalsConceded) throws IOException {

		String file_name = "C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/team_statistics.txt";
		FileWriter write = new FileWriter(file_name, true);
		BufferedWriter writeBuffer = new BufferedWriter(write);

		int goalDifference = Integer.valueOf(goalsScored) - Integer.valueOf(goalsConceded);
		String goalDifferenceString = Integer.toString(goalDifference);

		String newLine = System.getProperty("line.separator");
		writeBuffer.write(newLine);
		writeBuffer.write(teamName + "\t" + "\t");
		writeBuffer.write(goalsScored + "\t" + "\t" + "\t");
		writeBuffer.write(goalsConceded + "\t" + "\t" + "\t");
		writeBuffer.write(goalDifferenceString);
		writeBuffer.close();

	}

}
