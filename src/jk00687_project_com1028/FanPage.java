package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class FanPage {

	Scanner scan;
	private List<TableInfo> tableInformation = new ArrayList<TableInfo>();
	private List<KnockoutTree> knockoutTree = new ArrayList<KnockoutTree>();
	private JFrame frame;
	private JTextField teamName;

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
	 * 
	 * @throws FileNotFoundException
	 */
	public FanPage() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws FileNotFoundException
	 */
	private void initialize() throws FileNotFoundException {

		frame = new JFrame();
		frame.setBounds(100, 100, 769, 498);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblFanView = new JLabel("Fan View");
		lblFanView.setBounds(20, 7, 81, 14);
		frame.getContentPane().add(lblFanView);

		JTextArea tableStandings = new JTextArea();
		tableStandings.setFont(new Font("Monospaced", Font.PLAIN, 10));
		tableStandings.setBounds(20, 101, 723, 162);
		frame.getContentPane().add(tableStandings);

		showTable(tableStandings);

		JLabel lblCurrentLeagueTable = new JLabel("Current League Table Standings");
		lblCurrentLeagueTable.setBounds(23, 82, 194, 14);
		frame.getContentPane().add(lblCurrentLeagueTable);

		JTextArea knockoutsStandings = new JTextArea();
		knockoutsStandings.setFont(new Font("Monospaced", Font.PLAIN, 10));
		knockoutsStandings.setBounds(20, 299, 723, 149);
		frame.getContentPane().add(knockoutsStandings);
		
		showKnockouts(knockoutsStandings);

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

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String teamNameChoice = teamName.getText();
				try {
					tableSearch(teamNameChoice, tableStandings);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	public void showKnockouts(JTextArea knockoutsStandings) {
		

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/knockout_tree.txt")));
			knockoutsStandings.read(input, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showTable(JTextArea tableStandings) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt")));
			tableStandings.read(input, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tableSearch(String teamName, JTextArea tableStandings) throws IOException {
		tableStandings.setText(null);
		FileReader file = new FileReader("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt");
		String topRow = Files.readAllLines(Paths.get("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt")).get(0);
		
		BufferedReader buffer = new BufferedReader(file);

		String line = buffer.readLine();

		List<Map<String, Integer>> list = new ArrayList<>();

		while (line != null) {
			Map<String, Integer> hash = new HashMap<String, Integer>();
			String[] words = line.split("  ");

			for (String s : words) {
				Integer i = hash.get(s);
				hash.put(s, (i == null) ? 1 : i + 1);
			}

			line = buffer.readLine();
			list.add(hash);
		}

		for (Map<String, Integer> mapAtRow : list) {
			if (findTeam(teamName, tableStandings, mapAtRow)) {
				tableStandings.setText(topRow + "\n"+ mapAtRow.toString());
			}
		}
	}

	public boolean findTeam(String teamName, JTextArea tableStandings, Map<String, Integer> MapAtRow) {

		String content = MapAtRow.toString();
		String pattern = teamName;

		Pattern patternString = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patternString.matcher(content);

		if (matcher.find()) {
			tableStandings.setText(null);
			return true;
		} else {
			return false;
		}
	}

}
