package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.Font;
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
				}

			}

		});

	}

	public void viewTeamStatistics(String teamNameString) throws IOException {

		JTextArea teamStatistics = new JTextArea();
		teamStatistics.setFont(new Font("Monospaced", Font.PLAIN, 10));
		teamStatistics.setBounds(10, 180, 340, 72);
		frame.getContentPane().add(teamStatistics);

		if (tableSearch(teamNameString, teamStatistics)) {
			String file_name = "C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/team_statistics.txt";
			Scanner input = new Scanner(new File(file_name));
			int count = 0;

			while (input.hasNext() && count <= 15) {
				int tracker = 0;
				count++;
				String word = input.next();

				if (count == 13 && tracker == 0) {
					tracker++;
					try {

						teamStatistics.setText("Total goals scored: " + word + "\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (count == 14 && tracker == 1) {
					tracker++;
					String firstString = teamStatistics.getText();
					try {

						teamStatistics.setText(
								firstString + "Total goals conceded: " + word + "\n" + "Overall goal difference: ");

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						String secondString = teamStatistics.getText();

						teamStatistics.setText(secondString + "Overall goal difference: " + word);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			teamStatistics.setText(null);
			JOptionPane.showMessageDialog(null, "Team does not exist");
		}

	}

	public boolean tableSearch(String teamName, JTextArea tableStandings) throws IOException {
		tableStandings.setText(null);
		FileReader file = new FileReader("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt");
		
		
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
				return true;
			}
		}
		return false;
	}

	public boolean findTeam(String teamName, JTextArea tableStandings, Map<String, Integer> MapAtRow) {

		String content = MapAtRow.toString();
		String pattern = teamName;

		Pattern patternString = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patternString.matcher(content);
		
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
}
