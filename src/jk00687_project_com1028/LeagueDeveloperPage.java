package jk00687_project_com1028;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LeagueDeveloperPage {

	private JFrame frame;
	private JTextField teamName = null;
	private JTextField goalsScored = null;
	private JTextField goalsConceded = null;
	private JTextField matchResult = null;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LeagueDeveloperPage window = new LeagueDeveloperPage();
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
	public LeagueDeveloperPage() {
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
		lblLeagueDeveloperView.setBounds(10, 11, 134, 14);
		frame.getContentPane().add(lblLeagueDeveloperView);

		JTextField teamName = new JTextField();
		teamName.setBounds(134, 102, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Team name:");
		lblNewLabel.setBounds(10, 105, 114, 14);
		frame.getContentPane().add(lblNewLabel);

		JTextField goalsScored = new JTextField();
		goalsScored.setBounds(134, 130, 86, 20);
		frame.getContentPane().add(goalsScored);
		goalsScored.setColumns(10);

		JLabel lblGoalsScored = new JLabel("Goals Scored:");
		lblGoalsScored.setBounds(10, 133, 114, 14);
		frame.getContentPane().add(lblGoalsScored);

		JTextField goalsConceded = new JTextField();
		goalsConceded.setBounds(134, 155, 86, 20);
		frame.getContentPane().add(goalsConceded);
		goalsConceded.setColumns(10);

		JLabel lblGoalsConceded = new JLabel("Goals Conceded:");
		lblGoalsConceded.setBounds(10, 158, 114, 14);
		frame.getContentPane().add(lblGoalsConceded);

		JButton updateTableBtn = new JButton("Update tables");
		updateTableBtn.setBounds(10, 183, 114, 23);
		frame.getContentPane().add(updateTableBtn);

		updateTableBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String teamNameString = teamName.getText();
				String goalsScoredString = goalsScored.getText();
				String goalsConcededString = goalsConceded.getText();
				try {
					updateTable(teamNameString, goalsScoredString, goalsConcededString);

					JOptionPane.showMessageDialog(null, "Tables successfully updated");
				} catch (IllegalArgumentException | IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Tables could not be updated");
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Tables could not be updated");
					e.printStackTrace();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Tables could not be updated");
					e.printStackTrace();
				}
			}
		});

		JButton editStatsBtn = new JButton("Edit statistics");
		editStatsBtn.setBounds(290, 227, 134, 23);
		frame.getContentPane().add(editStatsBtn);

		JButton btnUpdateKnockouts = new JButton("Update knockouts");
		btnUpdateKnockouts.setBounds(134, 227, 146, 23);
		frame.getContentPane().add(btnUpdateKnockouts);

		btnUpdateKnockouts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				KnockoutsPage.main();
				frame.dispose();
			}

		});

		JLabel lblEnterResultsOf = new JLabel("Enter results of league fixture below");
		lblEnterResultsOf.setBounds(10, 73, 210, 14);
		frame.getContentPane().add(lblEnterResultsOf);
		editStatsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditStats.main();
				frame.dispose();
			}

		});

	}

	public void updateTable(String teamNameFinal, String goalsScoredFinal, String goalsConcededFinal)
			throws IllegalArgumentException, IOException, ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root",
				"password")) {
			Statement stmt = conn.createStatement();

			if (checkTeamExists(teamNameFinal)) {

				int goalSc = Integer.valueOf(goalsScoredFinal);
				int goalCon = Integer.valueOf(goalsConcededFinal);
				int goalDifference = goalSc - goalCon;

				String gamesPlayed = "SELECT GamesPlayed FROM leaguestandings WHERE TeamName = '" + teamNameFinal
						+ "';";
				String gp = null;
				ResultSet gamesPlayedResult = stmt.executeQuery(gamesPlayed);
				if (gamesPlayedResult.next()) {
					gp = gamesPlayedResult.getString("GamesPlayed");
				}

				int gpNew = Integer.valueOf(gp);
				int newGamesPlayed = gpNew + 1;

				String goalsScored = "SELECT GoalsScored FROM leaguestandings WHERE TeamName = '" + teamNameFinal
						+ "';";
				ResultSet goalsScoredResult = stmt.executeQuery(goalsScored);
				String gsr = null;
				if (goalsScoredResult.next()) {
					gsr = goalsScoredResult.getString("goalsScored");
				}
				int currentGoalsScored = Integer.valueOf(gsr);
				int newGoalsScored = goalSc + currentGoalsScored;

				if (Integer.valueOf(goalsScoredFinal) > Integer.valueOf(goalsConcededFinal)) {

					String currentWins = "SELECT Wins FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
					ResultSet currentWinsResult = stmt.executeQuery(currentWins);
					String cwr = null;
					if (currentWinsResult.next()) {
						cwr = currentWinsResult.getString("Wins");
					}
					int wins = Integer.valueOf(cwr);
					int newWins = wins + 1;

					String goalsConceded = "SELECT GoalsConceded FROM leaguestandings WHERE TeamName = '"
							+ teamNameFinal + "';";

					ResultSet goalsConcededResult = stmt.executeQuery(goalsConceded);
					String gcr = null;
					if (goalsConcededResult.next()) {
						gcr = goalsConcededResult.getString("GoalsConceded");
					}

					int currentGoalsConceded = Integer.valueOf(gcr);

					int newGoalsConceded = goalCon + currentGoalsConceded;

					int newGoalDifference = newGoalsScored - newGoalsConceded;

					String points = "SELECT Points FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
					ResultSet pointsResult = stmt.executeQuery(points);
					String pr = null;
					if (pointsResult.next()) {
						pr = pointsResult.getString("Points");
					}
					int newPoints = Integer.valueOf(pr) + 3;

					String insertdata = "UPDATE leaguestandings SET GamesPlayed = '" + newGamesPlayed + "', Wins = '"
							+ newWins + "', GoalsScored = '" + newGoalsScored + "', GoalsConceded = '"
							+ newGoalsConceded + "', GoalDifference = '" + newGoalDifference + "', Points = '"
							+ newPoints + "' WHERE TeamName = '" + teamNameFinal + "';";

					stmt.executeUpdate(insertdata);

				} else if (Integer.valueOf(goalsScoredFinal) == Integer.valueOf(goalsConcededFinal)) {

					String currentDraws = "SELECT Draws FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
					ResultSet currentDrawsResult = stmt.executeQuery(currentDraws);
					String cdr = null;
					if (currentDrawsResult.next()) {
						cdr = currentDrawsResult.getString("Draws");
					}
					int draws = Integer.valueOf(cdr);
					int newDraws = draws + 1;

					String goalsConceded = "SELECT GoalsConceded FROM leaguestandings WHERE TeamName = '"
							+ teamNameFinal + "';";

					ResultSet goalsConcededResult = stmt.executeQuery(goalsConceded);
					String gcr = null;
					if (goalsConcededResult.next()) {
						gcr = goalsConcededResult.getString("GoalsConceded");
					}

					int currentGoalsConceded = Integer.valueOf(gcr);

					int newGoalsConceded = goalCon + currentGoalsConceded;

					int newGoalDifference = newGoalsScored - newGoalsConceded;

					String points = "SELECT Points FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
					ResultSet pointsResult = stmt.executeQuery(points);
					String pr = null;
					if (pointsResult.next()) {
						pr = pointsResult.getString("Points");
					}
					int newPoints = Integer.valueOf(pr) + 1;

					String insertdata = "UPDATE leaguestandings SET GamesPlayed = '" + newGamesPlayed + "', Draws = '"
							+ newDraws + "', GoalsScored = '" + newGoalsScored + "', GoalsConceded = '"
							+ newGoalsConceded + "', GoalDifference = '" + newGoalDifference + "', Points = '"
							+ newPoints + "' WHERE TeamName = '" + teamNameFinal + "';";

					stmt.executeUpdate(insertdata);

				} else if (Integer.valueOf(goalsScoredFinal) < Integer.valueOf(goalsConcededFinal)) {

					String currentLosses = "SELECT Losses FROM leaguestandings WHERE TeamName = '" + teamNameFinal
							+ "';";
					ResultSet currentLossesResult = stmt.executeQuery(currentLosses);
					String clr = null;
					if (currentLossesResult.next()) {
						clr = currentLossesResult.getString("Losses");
					}
					int losses = Integer.valueOf(clr);
					int newLosses = losses + 1;

					String goalsConceded = "SELECT GoalsConceded FROM leaguestandings WHERE TeamName = '"
							+ teamNameFinal + "';";

					ResultSet goalsConcededResult = stmt.executeQuery(goalsConceded);
					String gcr = null;
					if (goalsConcededResult.next()) {
						gcr = goalsConcededResult.getString("GoalsConceded");
					}

					int currentGoalsConceded = Integer.valueOf(gcr);

					int newGoalsConceded = goalCon + currentGoalsConceded;

					int newGoalDifference = newGoalsScored - newGoalsConceded;

					String points = "SELECT Points FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
					ResultSet pointsResult = stmt.executeQuery(points);
					String pr = null;
					if (pointsResult.next()) {
						pr = pointsResult.getString("Points");
					}
					int newPoints = Integer.valueOf(pr) + 0;

					String insertdata = "UPDATE leaguestandings SET GamesPlayed = '" + newGamesPlayed + "', Losses = '"
							+ newLosses + "', GoalsScored = '" + newGoalsScored + "', GoalsConceded = '"
							+ newGoalsConceded + "', GoalDifference = '" + newGoalDifference + "', Points = '"
							+ newPoints + "' WHERE TeamName = '" + teamNameFinal + "';";

					stmt.executeUpdate(insertdata);

				} else {
					throw new IllegalArgumentException("Result must be a 'Win', 'Draw' or 'Loss'");
				}

			} else {

				try {

					String createTable = "CREATE TABLE IF NOT EXISTS leaguestandings(TeamName VARCHAR(50),GamesPlayed int, Wins int, Draws int, Losses int, GoalsScored int, GoalsConceded int, GoalDifference int, Points int);";

					stmt.executeUpdate(createTable);

					newTeamInput(teamNameFinal, goalsScoredFinal, goalsConcededFinal);

				} finally {

				}
			}
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

	public void newTeamInput(String teamNameFinal, String goalsScoredFinal, String goalsConcededFinal)
			throws SQLException {

		int goalSc = Integer.valueOf(goalsScoredFinal);
		int goalCon = Integer.valueOf(goalsConcededFinal);
		int goalDifference = goalSc - goalCon;

		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root",
				"password");
		Statement stmt = conn.createStatement();

		if (Integer.valueOf(goalsScoredFinal) > Integer.valueOf(goalsConcededFinal)) {
			String insertdata = "INSERT INTO leaguestandings VALUES ('" + teamNameFinal + "','1', '1', '0', '0', '"
					+ goalsScoredFinal + "', '" + goalsConcededFinal + "', '" + goalDifference + "', '3');";
			stmt.executeUpdate(insertdata);
		} else if (Integer.valueOf(goalsScoredFinal) == Integer.valueOf(goalsConcededFinal)) {
			String insertdata = "INSERT INTO leaguestandings VALUES ('" + teamNameFinal + "','1', '0', '1', '0', '"
					+ goalsScoredFinal + "', '" + goalsConcededFinal + "', '" + goalDifference + "', '1');";
			stmt.executeUpdate(insertdata);
		} else if (Integer.valueOf(goalsScoredFinal) < Integer.valueOf(goalsConcededFinal)) {
			String insertdata = "INSERT INTO leaguestandings VALUES ('" + teamNameFinal + "','1', '0', '0', '1', '"
					+ goalsScoredFinal + "', '" + goalsConcededFinal + "', '" + goalDifference + "', '0');";
			stmt.executeUpdate(insertdata);
		} else {
			throw new IllegalArgumentException("Result must be a 'Win', 'Draw' or 'Loss'");
		}

	}
}
