package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class EditStats {

	private JFrame frame;
	private JTextField teamName = null;
	private JTextField goalsScored = null;
	private JLabel lblAdjustedGoalsConceded;
	private JTextField goalsConceded = null;
	private JButton btnUpdateStatistics;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				}
				
				LeagueDeveloperPage.main();
				frame.dispose();
			}

		});
	}

	public void updateStatistics(String teamName, String goalsScored, String goalsConceded) throws IOException, SQLException, ClassNotFoundException {

		if (!checkTeamExists(teamName)) {
			JOptionPane.showMessageDialog(null, "Team not found");
		}else {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root",
					"password");
				Statement stmt = conn.createStatement();
				
				int goalDifference = Integer.valueOf(goalsScored) - Integer.valueOf(goalsConceded);
				
				String query = "UPDATE leaguestandings SET GoalsScored = '" + goalsScored + "', GoalsConceded = '" + goalsConceded + "', GoalDifference = '" + goalDifference + "' WHERE TeamName = '"+ teamName + "';";
				
				stmt.executeUpdate(query);

				JOptionPane.showMessageDialog(null, "Statistics updated");
		}
	}
	
	public boolean checkTeamExists(String teamName) throws SQLException {

		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
				"root", "password");
		Statement stmt = conn.createStatement();
		String SQL = "select * from leaguestandings where TeamName = '" + teamName + "';";

		ResultSet rset = stmt.executeQuery(SQL);
		
		if (rset.next()) {
			return true;
		} else {
			return false;
		}
	}

}
