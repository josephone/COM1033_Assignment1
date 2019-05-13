package jk00687_project_com1028;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * @author Joseph Kutler
 *
 */

public class FanPage {

	private JFrame frame;
	private JTextField teamName = null;

	/**
	 * This String array is used to hold the headers of the JTable so that I know
	 * what values I am going to extract from the database and insert into the
	 * JTable. I then create an object instance of type 'DefaultTableModel' which
	 * allows me to be able to properly interpret and access the JTable
	 */
	private final String[] headers = { "Team Name", "Games Played", "Wins", "Draws", "Losses", "Goals Scored",
			"Goals Conceded", "Goal Difference", "Points" };
	DefaultTableModel tableModel = new DefaultTableModel(headers, 0);

	private JTable leagueTable;

	/**
	 * Launch the application.
	 */

	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FanPage window = new FanPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error");
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 * @throws IOException  An IOException is thrown as there could be an invalid
	 *                      input or output that occurs as a result of the execution
	 *                      of this method
	 */
	public FanPage() throws SQLException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 * @throws IOException  An IOException is thrown as there could be an invalid
	 *                      input or output that occurs as a result of the execution
	 *                      of this method
	 */
	private void initialize() throws SQLException, IOException {

		frame = new JFrame();
		frame.setBounds(100, 100, 769, 498);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblFanView = new JLabel("Fan View");
		lblFanView.setBounds(20, 7, 81, 14);
		frame.getContentPane().add(lblFanView);

		JLabel lblCurrentLeagueTable = new JLabel("Current League Table Standings");
		lblCurrentLeagueTable.setBounds(23, 82, 194, 14);
		frame.getContentPane().add(lblCurrentLeagueTable);

		leagueTable = new JTable();
		leagueTable.setBounds(20, 118, 723, 149);
		frame.getContentPane().add(leagueTable);

		leagueTable.setModel(tableModel);
		showTable(leagueTable);

		JLabel lblCurrentKnockoutsStandings = new JLabel("Current Knockouts Standings");
		lblCurrentKnockoutsStandings.setBounds(23, 274, 177, 14);
		frame.getContentPane().add(lblCurrentKnockoutsStandings);

		JLabel lblEnterTeamName = new JLabel("Enter team name:");
		lblEnterTeamName.setBounds(384, 7, 109, 14);
		frame.getContentPane().add(lblEnterTeamName);

		teamName = new JTextField();
		teamName.setBounds(503, 4, 201, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		JButton btnSearch = new JButton("Search for team");
		btnSearch.setBounds(498, 40, 221, 32);
		frame.getContentPane().add(btnSearch);

		JLabel lblTeamName = new JLabel(
				"Team Name   Games Played   Wins                 Draws            Losses        Goals Scored    Goals Conceded Goal Difference   Points");
		lblTeamName.setBounds(20, 103, 723, 14);
		frame.getContentPane().add(lblTeamName);

		createKnockouts();

		JTextArea knockoutsStandings = new JTextArea();
		knockoutsStandings.setBounds(20, 299, 720, 149);
		frame.getContentPane().add(knockoutsStandings);
		showKnockouts(knockoutsStandings);

		JButton btnBackToHome = new JButton("Back to home");
		btnBackToHome.setBounds(96, 0, 124, 23);
		frame.getContentPane().add(btnBackToHome);

		btnBackToHome.addActionListener(new ActionListener() {

			/*
			 * Upon this button being clicked, the user is taken back to the home page and
			 * the current frame is disposed of for efficiency reasons
			 */

			@Override
			public void actionPerformed(ActionEvent e) {
				HomePage.main(null);
				frame.dispose();
			}

		});

		btnSearch.addActionListener(new ActionListener() {

			/*
			 * Upon the 'Search' button being clicked, the value of the teamName JTextField
			 * is converted into a String with an appropriate variable name so that they can
			 * be passed as a parameter into the 'tableSearch' method. This code is wrapped
			 * in a try-catch statement in order to handle exceptions being thrown and
			 * errors being generated. I have caught an 'SQLException' since in the
			 * 'tableSearch' method I will be creating a connection between a database and
			 * the Java program. In addition to this I have caught an IOException since the
			 * user will be providing an input which must be protected against
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String teamNameChoice = teamName.getText();
				try {
					tableSearch(teamNameChoice, leagueTable);
				} catch (IOException e) {
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
	 * This method begins by creating a connection between the SQLite database and
	 * the Java program and creates an initial statement. There is then an SQL
	 * statement defined within a String which selects the name of the teams from
	 * the league table that have played 20 games or more. This is then ordered in
	 * descending order of points in order to locate the top 8 teams with the most
	 * points. An object of type BufferedWriter is then created with the location of
	 * the text file in which the names of the top 8 teams will be written to. The
	 * program then iterates through the database and writes the names of the top 8
	 * teams that are able to qualify for the knockouts competition.
	 * 
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 * @throws IOException  An IOException is thrown as there could be an invalid
	 *                      input or output that occurs as a result of the execution
	 *                      of this method
	 */
	public void createKnockouts() throws SQLException, IOException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:users");
		int count = 0;
		Statement stmt = conn.createStatement();
		String SQL = "select TeamName from leaguestandings where GamesPlayed >= '20' ORDER BY Points DESC;";

		ResultSet queryResult = stmt.executeQuery(SQL);
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("C:\\Users\\Public\\knockout_tree.txt"), "utf-8"));

		writer.append("Qualified teams are as follows:");
		writer.newLine();
		while (queryResult.next() && count < 8) {
			count++;
			try {
				writer.append(queryResult.getString("TeamName"));
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writer.close();
	}

	/**
	 * This method is used to print out the contents of the text file that contains
	 * the names of the 8 teams that have qualified for the knockouts competition
	 * and display it to the user in a JTextArea.
	 * 
	 * @param knockoutsArea This is the JTextArea in which the contents of the
	 *                      chosen text file will be written to
	 */

	public void showKnockouts(JTextArea knockoutsArea) {

		File file = new File("C:\\Users\\Public\\knockout_tree.txt");

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "File not found");
			e1.printStackTrace();
		}

		String st = null;

		try {
			while ((st = br.readLine()) != null) {
				knockoutsArea.append(st + "\n" + "" + "");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error");
			e.printStackTrace();
		}

	}

	/**
	 * This method is used to display the entire contents of the 'leaguestandings'
	 * table to the user in a JTable. This is done by first connecting to the SQLite
	 * database and then intialising a statement. From here there is a SELECT
	 * statement being used to get the contents of the database in descending order
	 * of points in order to properly emulate an actual football league table. An
	 * iterative statement is then created which repeats until all rows in the table
	 * have been accessed. Inside this WHILE loop I create a String array in which
	 * the contents of each column is saved into a different entry of the array. The
	 * contents of the array is then written to the JTable.
	 * 
	 * @param leagueTableStandings This is the JTable in which the information from
	 *                             the table will be printed out to
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 */

	public void showTable(JTable leagueTableStandings) throws SQLException {

		Connection conn = DriverManager.getConnection("jdbc:sqlite:users");

		Statement stmt = conn.createStatement();

		String showtable = "SELECT * FROM leaguestandings ORDER BY Points DESC;";
		ResultSet showTableOverall = stmt.executeQuery(showtable);
		while (showTableOverall.next()) {

			String[] topRow = { showTableOverall.getString("TeamName"), showTableOverall.getString("GamesPlayed"),
					showTableOverall.getString("Wins"), showTableOverall.getString("Draws"),
					showTableOverall.getString("Losses"), showTableOverall.getString("GoalsScored"),
					showTableOverall.getString("GoalsConceded"), showTableOverall.getString("GoalDifference"),
					showTableOverall.getString("Points") };

			tableModel.addRow(topRow);
		}
		conn.close();
	}

	/**
	 * This method is used to take the name of the team that the user wishes to find
	 * and searches through the table to locate and display only the row that
	 * relates to the name of the team that is being searched for. This is done by
	 * executing an SQL statement that selects the data from the 'leaguestandings'
	 * table where the name of the team is contained as a field. The rows that are
	 * currently in the JTable will be removed and the sole required row will be
	 * displayed in the JTable in the same manner as the method above
	 * 
	 * @param teamName       This is the String equivalent value of the JTextField
	 *                       'teamName'
	 * @param tableStandings This is the JTable in which the information from the
	 *                       table will be printed out to
	 * 
	 * @throws SQLException An SQLException is thrown as the query being executed on
	 *                      the database may be syntactically incorrect in SQL, and
	 *                      so Java must defend against this
	 * @throws IOException  An IOException is thrown as there could be an invalid
	 *                      input or output that occurs as a result of the execution
	 *                      of this method
	 */

	public void tableSearch(String teamName, JTable tableStandings) throws IOException, SQLException {

		if (findTeam(teamName) == true) {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:users");

			Statement stmt = conn.createStatement();

			String query = "SELECT * FROM leagueStandings WHERE TeamName LIKE '%" + teamName + "%';";

			ResultSet queryResult = stmt.executeQuery(query);

			for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
				tableModel.removeRow(i);
			}

			while (queryResult.next()) {
				String[] topRow = { queryResult.getString("TeamName"), queryResult.getString("GamesPlayed"),
						queryResult.getString("Wins"), queryResult.getString("Draws"), queryResult.getString("Losses"),
						queryResult.getString("GoalsScored"), queryResult.getString("GoalsConceded"),
						queryResult.getString("GoalDifference"), queryResult.getString("Points") };

				tableModel.addRow(topRow);
			}
			conn.close();
		} else {
			JOptionPane.showMessageDialog(null, "Team not found");
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
			conn.close();
			return true;
		} else {
			return false;
		}
	}
}
