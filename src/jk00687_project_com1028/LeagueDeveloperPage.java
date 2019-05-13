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

/**
 * @author Joseph Kutler
 *
 */
public class LeagueDeveloperPage {

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

			/*
			 * Upon the 'Update tables' button being pressed the values contained within the
			 * JTextBoxes will be all converted into Strings with the appropriate variable
			 * name so that they can be passed as a parameter into the 'updateTable' method.
			 * The 'updateTable' method is then called and is surrounded by a try-catch
			 * statement, with the catches preventing IllegalArgumentExceptions from
			 * occurring and SQLExceptions occurring
			 * 
			 */

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

			/*
			 * Upon the 'Update knockouts' button being pressed, the user is redirected to
			 * the frame associated with the 'KnockoutsPage' class and the current frame is
			 * disposed of for efficiency reasons.
			 * 
			 */

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

			/*
			 * Upon the 'Edit stats' button being pressed, the user is redirected to the
			 * frame associated with the 'EditStats' class and the current frame is disposed
			 * of for efficiency reasons.
			 * 
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditStats.main();
				frame.dispose();
			}

		});

	}

	/**
	 * This extensive method is used to update values contained within the
	 * 'leaguestandings' table according to the inputs provided by the user in the
	 * JTextArea. The code checks to see whether the name of the team is already in
	 * the database, and if it does then the code first works out the new amount of
	 * games played, the new amount of goals scored and conceded and the new overall
	 * goal difference. Following this, a conditional statement occurs which checks
	 * to see whether the amount of goals scored in the match is greater than, equal
	 * to or less than the amount of goals conceded. If the value of the goals
	 * scored is greater than amount of goals conceded, the program registers that
	 * the details being entered for the team results in a win. If the two values
	 * are equal then the match has resulted in a draw and if the team has conceded
	 * more goals than they have scored then they have resulted in a loss. Depending
	 * on the outcome of the match, a different SQL UPDATE statement will be
	 * executed. This statement is then executed and the table has now been
	 * successfully updated. If the team does not already exist in the database then
	 * the 'newTeamInput' method is called which adds a new team to the table.
	 * 
	 * @param teamNameFinal      This is the String equivalent value of the
	 *                           JTextField 'teamName'
	 * @param goalsScoredFinal   This is the String equivalent value of the
	 *                           JTextField 'goalsScored'
	 * @param goalsConcededFinal This is the String equivalent value of the
	 *                           JTextField 'goalsConceded'
	 * @throws IllegalArgumentException An IllegalArgumentException has been thrown
	 *                                  here as some String to Integer and Integer
	 *                                  to String conversions occur within this
	 *                                  method which could result in an
	 *                                  IllegalArgumentException occurring
	 * @throws IOException              An IOException is thrown as there could be
	 *                                  an invalid input or output that occurs as a
	 *                                  result of the execution of this method
	 * @throws SQLException             An SQLException is thrown as the query being
	 *                                  executed on the database may be
	 *                                  syntactically incorrect in SQL, and so Java
	 *                                  must defend against this
	 */
	public void updateTable(String teamNameFinal, String goalsScoredFinal, String goalsConcededFinal)
			throws IllegalArgumentException, IOException, SQLException {

		if (checkTeamExists(teamNameFinal)) {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
			Statement stmt = conn.createStatement();

			int goalSc = Integer.valueOf(goalsScoredFinal);
			int goalCon = Integer.valueOf(goalsConcededFinal);
			int goalDifference = goalSc - goalCon;

			String gamesPlayed = "SELECT GamesPlayed FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
			String gp = null;
			ResultSet gamesPlayedResult = stmt.executeQuery(gamesPlayed);
			if (gamesPlayedResult.next()) {
				gp = gamesPlayedResult.getString("GamesPlayed");
			}

			int gpNew = Integer.valueOf(gp);
			int newGamesPlayed = gpNew + 1;

			String goalsScored = "SELECT GoalsScored FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
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

				String goalsConceded = "SELECT GoalsConceded FROM leaguestandings WHERE TeamName = '" + teamNameFinal
						+ "';";

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
						+ newWins + "', GoalsScored = '" + newGoalsScored + "', GoalsConceded = '" + newGoalsConceded
						+ "', GoalDifference = '" + newGoalDifference + "', Points = '" + newPoints
						+ "' WHERE TeamName = '" + teamNameFinal + "';";

				stmt.executeUpdate(insertdata);

				conn.close();
			} else if (Integer.valueOf(goalsScoredFinal) == Integer.valueOf(goalsConcededFinal)) {

				String currentDraws = "SELECT Draws FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
				ResultSet currentDrawsResult = stmt.executeQuery(currentDraws);
				String cdr = null;
				if (currentDrawsResult.next()) {
					cdr = currentDrawsResult.getString("Draws");
				}
				int draws = Integer.valueOf(cdr);
				int newDraws = draws + 1;

				String goalsConceded = "SELECT GoalsConceded FROM leaguestandings WHERE TeamName = '" + teamNameFinal
						+ "';";

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
						+ newDraws + "', GoalsScored = '" + newGoalsScored + "', GoalsConceded = '" + newGoalsConceded
						+ "', GoalDifference = '" + newGoalDifference + "', Points = '" + newPoints
						+ "' WHERE TeamName = '" + teamNameFinal + "';";

				stmt.executeUpdate(insertdata);
				conn.close();

			} else if (Integer.valueOf(goalsScoredFinal) < Integer.valueOf(goalsConcededFinal)) {

				String currentLosses = "SELECT Losses FROM leaguestandings WHERE TeamName = '" + teamNameFinal + "';";
				ResultSet currentLossesResult = stmt.executeQuery(currentLosses);
				String clr = null;
				if (currentLossesResult.next()) {
					clr = currentLossesResult.getString("Losses");
				}
				int losses = Integer.valueOf(clr);
				int newLosses = losses + 1;

				String goalsConceded = "SELECT GoalsConceded FROM leaguestandings WHERE TeamName = '" + teamNameFinal
						+ "';";

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
						+ newLosses + "', GoalsScored = '" + newGoalsScored + "', GoalsConceded = '" + newGoalsConceded
						+ "', GoalDifference = '" + newGoalDifference + "', Points = '" + newPoints
						+ "' WHERE TeamName = '" + teamNameFinal + "';";

				stmt.executeUpdate(insertdata);
				conn.close();

			} else {
				throw new IllegalArgumentException("Incorrect details entered");
			}

		} else {
			newTeamInput(teamNameFinal, goalsScoredFinal, goalsConcededFinal);
		}

	}

	/**
	 * This method takes in a parameter of 'teamName' as it must be able to access
	 * the name of the team that is being searched for. A connection is then created
	 * between the SQLite database 'users' and the Java program and a statement is
	 * initialised. The method then checks the database to see whether there is an
	 * entry in the database that has the same value as the name of the team that is
	 * being searched for. If there is then the boolean value of true is returned,
	 * if there is not then the boolean value of false is returned.
	 * 
	 * @param teamName This is the String equivalent value of the JTextField
	 *                 'teamName'
	 * @return This method returns a boolean depending on whether the team exists in
	 *         the database or not. A value of true is returned if the team does
	 *         exist and a value of false is returned if the team does not exist
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */

	public boolean checkTeamExists(String teamName) throws SQLException {

		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
				Statement stmt = conn.createStatement()) {
			String SQL = "select * from leaguestandings where TeamName = '" + teamName + "';";

			ResultSet rset = stmt.executeQuery(SQL);
			conn.close();
			if (rset.next()) {
				rset.close();
				return true;
			} else {
				rset.close();
				return false;
			}
		}

	}

	/**
	 * This method gets called if the name of the team having their result inputted
	 * can not be found in the 'leaguestandings' table. This code creates a whole
	 * new SQL row by using an INSERT INTO statement rather than an UPDATE statement
	 * and, depending on the amount of goals scored in comparison to the amount of
	 * goals conceded, the row value is initialised in different ways.
	 * 
	 * @param teamNameFinal      This is the String equivalent value of the
	 *                           JTextField 'teamName'
	 * @param goalsScoredFinal   This is the String equivalent value of the
	 *                           JTextField 'goalsScored'
	 * @param goalsConcededFinal This is the String equivalent value of the
	 *                           JTextField 'goalsConceded'
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */

	public void newTeamInput(String teamNameFinal, String goalsScoredFinal, String goalsConcededFinal)
			throws SQLException {

		int goalSc = Integer.valueOf(goalsScoredFinal);
		int goalCon = Integer.valueOf(goalsConcededFinal);
		int goalDifference = goalSc - goalCon;

		Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
		Statement stmt = conn.createStatement();

		if (Integer.valueOf(goalsScoredFinal) > Integer.valueOf(goalsConcededFinal)) {
			String insertdata = "INSERT INTO leaguestandings VALUES ('" + teamNameFinal + "','1', '1', '0', '0', '"
					+ goalsScoredFinal + "', '" + goalsConcededFinal + "', '" + goalDifference + "', '3');";
			stmt.executeUpdate(insertdata);
			conn.close();
		} else if (Integer.valueOf(goalsScoredFinal) == Integer.valueOf(goalsConcededFinal)) {
			String insertdata = "INSERT INTO leaguestandings VALUES ('" + teamNameFinal + "','1', '0', '1', '0', '"
					+ goalsScoredFinal + "', '" + goalsConcededFinal + "', '" + goalDifference + "', '1');";
			stmt.executeUpdate(insertdata);
			conn.close();
		} else if (Integer.valueOf(goalsScoredFinal) < Integer.valueOf(goalsConcededFinal)) {
			String insertdata = "INSERT INTO leaguestandings VALUES ('" + teamNameFinal + "','1', '0', '0', '1', '"
					+ goalsScoredFinal + "', '" + goalsConcededFinal + "', '" + goalDifference + "', '0');";
			stmt.executeUpdate(insertdata);
			conn.close();
		} else {
			throw new IllegalArgumentException("Error");
		}
		conn.close();

	}
}
