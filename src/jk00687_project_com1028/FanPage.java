package jk00687_project_com1028;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;

public class FanPage {

	Scanner scan;
	private List<TableInfo> tableInformation = new ArrayList<TableInfo>();
	private List<KnockoutTree> knockoutTree = new ArrayList<KnockoutTree>();
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FanPage window = new FanPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public FanPage() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblFanView = new JLabel("Fan View");
		lblFanView.setBounds(10, 11, 81, 14);
		frame.getContentPane().add(lblFanView);

		JTextPane tableStandings = new JTextPane();
		tableStandings.setBounds(10, 101, 194, 149);
		frame.getContentPane().add(tableStandings);
		scan = new Scanner(new File("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt"));
		showTable(tableInformation);

		JLabel lblCurrentLeagueTable = new JLabel("Current League Table Standings");
		lblCurrentLeagueTable.setBounds(23, 82, 194, 14);
		frame.getContentPane().add(lblCurrentLeagueTable);
		
		JTextPane knockoutsStandings = new JTextPane();
		knockoutsStandings.setBounds(227, 101, 197, 149);
		frame.getContentPane().add(knockoutsStandings);
		showKnockouts(knockoutTree);
		
		JLabel lblCurrentKnockoutsStadings = new JLabel("Current Knockouts Standings");
		lblCurrentKnockoutsStadings.setBounds(247, 82, 177, 14);
		frame.getContentPane().add(lblCurrentKnockoutsStadings);
		
	}

	public String showKnockouts(List<KnockoutTree> knockoutTree) {

		return null;
	}

	public String knockoutsSearch(List<KnockoutTree> knockoutTree) {

		return null;
	}

	public String showTable(List<TableInfo> tableInformation) {

		return null;
	}

	public String tableSearch(List<TableInfo> tableInformation) {

		return null;
	}
}
