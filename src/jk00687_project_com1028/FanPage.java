package jk00687_project_com1028;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
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
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTable;

public class FanPage {

	private JFrame frame;
	private JTextField teamName = null;
	private final String[] headers = { "Team Name", "Games Played", "Wins", "Draws", "Losses", "Goals Scored",
			"Goals Conceded", "Goal Difference", "Points" };
	DefaultTableModel tableModel = new DefaultTableModel(headers, 0);
	private JTable leagueTable;
	private JTable tableKnockouts;

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
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public FanPage() throws FileNotFoundException, ClassNotFoundException, SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private void initialize() throws FileNotFoundException, ClassNotFoundException, SQLException {

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

		tableKnockouts = new JTable();
		tableKnockouts.setBounds(20, 310, 723, 138);
		frame.getContentPane().add(tableKnockouts);

		showKnockouts(tableKnockouts);
		
		JLabel lblTeamName = new JLabel("Team Name   Games Played   Wins                 Draws            Losses        Goals Scored    Goals Conceded Goal Difference   Points");
		lblTeamName.setBounds(20, 103, 723, 14);
		frame.getContentPane().add(lblTeamName);

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				String teamNameChoice = teamName.getText();
				try {
					tableSearch(teamNameChoice, leagueTable);
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

			}
		});

	}

	public void showKnockouts(JTable tableKnockouts) {
		
		
	}

	public void showTable(JTable leagueTableStandings) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root",
				"password");

		Statement stmt = conn.createStatement();

		String showtable = "SELECT * FROM leaguestandings ORDER BY points DESC;";
		ResultSet showTableOverall = stmt.executeQuery(showtable);
		while (showTableOverall.next()) {

			String[] topRow = { showTableOverall.getString("TeamName"), showTableOverall.getString("GamesPlayed"),
					showTableOverall.getString("Wins"), showTableOverall.getString("Draws"),
					showTableOverall.getString("Losses"), showTableOverall.getString("GoalsScored"),
					showTableOverall.getString("GoalsConceded"), showTableOverall.getString("GoalDifference"),
					showTableOverall.getString("Points") };

			tableModel.addRow(topRow);
		}
		
	}

	public void tableSearch(String teamName, JTable tableStandings) throws IOException, ClassNotFoundException, SQLException {
	
		if (findTeam(teamName) == true) {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/users?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root",
					"password");

			Statement stmt = conn.createStatement();
			
			String query = "SELECT * FROM leagueStandings WHERE TeamName = '" + teamName + "';";
			String q = null;
			
			ResultSet queryResult = stmt.executeQuery(query);
			
			for( int i = tableModel.getRowCount() - 1; i >= 0; i-- ) {
		        tableModel.removeRow(i);
		    }
			
			while (queryResult.next()) {
				String[] topRow = { queryResult.getString("TeamName"), queryResult.getString("GamesPlayed"),
						queryResult.getString("Wins"), queryResult.getString("Draws"),
						queryResult.getString("Losses"), queryResult.getString("GoalsScored"),
						queryResult.getString("GoalsConceded"), queryResult.getString("GoalDifference"),
						queryResult.getString("Points") };

				tableModel.addRow(topRow);
			}
				
			}else {
				JOptionPane.showMessageDialog(null, "Team not found");
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
