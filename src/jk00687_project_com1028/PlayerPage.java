package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;
import javax.swing.JTextField;

public class PlayerPage {

	private JFrame frame;
	private JTextField teamName;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerPage window = new PlayerPage();
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
	public PlayerPage() {
		try {
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 483, 336);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblPlayerView = new JLabel("Player view");
		lblPlayerView.setBounds(10, 11, 96, 14);
		frame.getContentPane().add(lblPlayerView);

		teamName = new JTextField();
		teamName.setBounds(287, 102, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		JLabel lblTeamStatistics = new JLabel("Team statistics");
		lblTeamStatistics.setBounds(10, 155, 96, 14);
		frame.getContentPane().add(lblTeamStatistics);

		JLabel lblEnterTheName = new JLabel("Enter the name of the team you wish to view:");
		lblEnterTheName.setBounds(10, 91, 256, 42);
		frame.getContentPane().add(lblEnterTheName);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(287, 133, 89, 23);
		frame.getContentPane().add(btnSearch);

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					String teamNameString = teamName.getText();
					viewTeamStatistics(teamNameString);
				} catch (IOException e) {

					e.printStackTrace();
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

	}

	public void viewTeamStatistics(String teamNameString) throws IOException, HeadlessException, SQLException {

		JTextArea teamStatistics = new JTextArea();
		teamStatistics.setFont(new Font("Monospaced", Font.PLAIN, 10));
		teamStatistics.setBounds(10, 180, 340, 72);
		frame.getContentPane().add(teamStatistics);

		if (tableSearch(teamNameString, teamStatistics)) {

			teamStatistics.setText("");

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
					"root", "password");
			Statement stmt = conn.createStatement();
			String SQL = "select GoalsScored from leaguestandings where TeamName = '" + teamNameString + "';";

			ResultSet goalsScoredResult = stmt.executeQuery(SQL);
			String gsr = null;
			if (goalsScoredResult.next()) {
				gsr = goalsScoredResult.getString("GoalsScored");
			}

			int currentGoalsScored = Integer.valueOf(gsr);

			String goalsScoredString = String.valueOf(currentGoalsScored);

			String goalsConceded = "select GoalsConceded from leaguestandings where TeamName = '" + teamNameString
					+ "';";

			ResultSet goalsConcededResult = stmt.executeQuery(goalsConceded);
			String gcr = null;
			if (goalsConcededResult.next()) {
				gcr = goalsConcededResult.getString("GoalsConceded");
			}

			int currentGoalsConceded = Integer.valueOf(gcr);

			String goalsConcededString = String.valueOf(currentGoalsConceded);
			
			int overallGoalDifference = currentGoalsScored - currentGoalsConceded;

			teamStatistics.setText("Total goals scored: " + goalsScoredString + "\n" + "Total goals conceded: "
					+ goalsConcededString + "\n" + "Overall goal difference: " + overallGoalDifference);

		} else {
			teamStatistics.setText(null);
			JOptionPane.showMessageDialog(null, "Team does not exist");
		}

	}

	public boolean tableSearch(String teamName, JTextArea tableStandings) throws IOException, SQLException {
		tableStandings.setText(null);

		if (findTeam(teamName)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean findTeam(String teamName) throws SQLException {
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
