package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Joseph Kutler
 *
 */
public class PlayerPage {

	private JFrame frame;
	private JTextField teamName = null;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
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

		JTextArea teamStatistics = new JTextArea();
		teamStatistics.setFont(new Font("Monospaced", Font.PLAIN, 10));
		teamStatistics.setBounds(10, 180, 173, 106);
		frame.getContentPane().add(teamStatistics);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(287, 133, 89, 23);
		frame.getContentPane().add(btnSearch);

		btnSearch.addActionListener(new ActionListener() {

			/*
			 * Upon the 'Search' button being clicked, the value of the teamName JTextField
			 * is converted into a String with an appropriate variable name so that it can
			 * be passed as a parameter into the 'viewTeamStatistics' method. Additionally,
			 * the JTextArea of 'teamStatistics' is passed into the method
			 * 'viewTeamStatistics' in order for the information to know where it is going
			 * to be written to. In addition to this I have caught a few exceptions being;
			 * IOException, HeadlessException and SQLException.
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					String teamNameString = teamName.getText();
					viewTeamStatistics(teamStatistics, teamNameString);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (HeadlessException e) {
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				}

			}

		});

	}

	/**
	 * This method is used to view the specific statistics of the team which the
	 * user has searched for. This is done by first executing the method
	 * 'tableSearch' and checking to see whether the team actually exists in the
	 * 'leaguestandings' table. If this returns a value of true then it has been
	 * validated that the team does exist and the program can proceed. The JTextArea
	 * is then set to blank in order for the new statistics to write to a blank
	 * canvas. The program then executes three key SQL SELECT statements which
	 * extracts the information held in the 'leaguestandings' table. These SELECT
	 * statements extract the values in the columns: GoalsScored, GoalsConceded and
	 * GoalDifference. The contents of these columns are then formatted in an
	 * aesthetically pleasing way and is then written to the teamStatistics
	 * JTextArea
	 * 
	 * @param teamStatistics
	 * @param teamNameString
	 * @throws IOException
	 * @throws HeadlessException
	 * @throws SQLException
	 */
	public void viewTeamStatistics(JTextArea teamStatistics, String teamNameString)
			throws IOException, HeadlessException, SQLException {

		if (findTeam(teamNameString)) {

			teamStatistics.setText(null);

			Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
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

	public boolean findTeam(String teamName) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
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
