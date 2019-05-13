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

/**
 * @author Joseph Kutler
 *
 */
public class EditStats {

	/*
	 * This class is being used to allow the user to make edits to the statistics of
	 * the team that is chosen. These statistics are: Goals scored, Goals conceded
	 * and Goal difference.
	 */

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
		lblLeagueDeveloperView.setBounds(10, 11, 164, 14);
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

			/*
			 * Upon the the 'Update statistics button being clicked, the following code will
			 * be executed. The values contained within the JTextBoxes will be all converted
			 * into Strings with the appropriate variable name so that they can be passed as
			 * a parameter into the 'updateStatistics' method. This code is wrapped in a
			 * try-catch statement in order to handle exceptions being thrown and errors
			 * being generated. I have caught an 'SQLException' since in the
			 * 'updateStatistics' method I will be creating a connection between a database
			 * and the Java program. In addition to this I have caught an IOException since
			 * the user will be providing an input which must be protected against
			 */

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
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				}

				LeagueDeveloperPage.main();
				frame.dispose();
			}

		});
	}

	/**
	 * This method takes in three parameters, being the results of the users input
	 * into the three JTextFields. The first line of code for this method is a
	 * conditional statement that calls the 'checkTeamExists' method and passes the
	 * parameter 'teamName' into it. If this method returns a boolean value of false
	 * then the user receives a pop up message that the team they have searched for
	 * could not be found. If this method returns a boolean value of true then a
	 * connection must be created between the SQLite database 'users' and the Java
	 * program and a statement must be initialised. Following this, a new variable
	 * has been created which works out the goal difference of the new statistics. A
	 * new String variable is created which contains the SQL statement that is to be
	 * executed on the database. In this case an UPDATE statement will occur, since
	 * the aim of this method is to alter already existing values in the league
	 * table. The query is then executed and the connection is closed. The user then
	 * receives a pop up message that lets them know that the statistics have been
	 * successfully updated.
	 * 
	 * @param teamName      This is the String equivalent value of the JTextField
	 *                      'teamName'
	 * @param goalsScored   This is the String equivalent value of the JTextField
	 *                      'goalsScored'
	 * @param goalsConceded This is the String equivalent value of the JTextField
	 *                      'goalsConceded'
	 * @throws IOException  An IOException is thrown as there could be an invalid
	 *                      input or output that occurs as a result of the execution
	 *                      of this method
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */

	public void updateStatistics(String teamName, String goalsScored, String goalsConceded)
			throws IOException, SQLException {

		if (!checkTeamExists(teamName)) {
			JOptionPane.showMessageDialog(null, "Team not found");
		} else {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
			Statement stmt = conn.createStatement();

			int goalDifference = Integer.valueOf(goalsScored) - Integer.valueOf(goalsConceded);

			String query = "UPDATE leaguestandings SET GoalsScored = '" + goalsScored + "', GoalsConceded = '"
					+ goalsConceded + "', GoalDifference = '" + goalDifference + "' WHERE TeamName = '" + teamName
					+ "';";

			stmt.executeUpdate(query);

			conn.close();

			JOptionPane.showMessageDialog(null, "Statistics updated");
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

		Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
		Statement stmt = conn.createStatement();
		String SQL = "select * from leaguestandings where TeamName = '" + teamName + "';";

		ResultSet rset = stmt.executeQuery(SQL);

		if (rset.next()) {
			conn.close();
			return true;
		} else {
			conn.close();
			return false;
		}
	}

}
