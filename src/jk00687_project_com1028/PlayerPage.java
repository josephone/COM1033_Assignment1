package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
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
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

public class PlayerPage {

	private JFrame frame;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 320, 302);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblPlayerView = new JLabel("Player view");
		lblPlayerView.setBounds(10, 11, 96, 14);
		frame.getContentPane().add(lblPlayerView);

		try {
			viewTeamStatistics();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		JLabel lblTeamStatistics = new JLabel("Team statistics");
		lblTeamStatistics.setBounds(10, 83, 96, 14);
		frame.getContentPane().add(lblTeamStatistics);
	}
	
	public boolean findTeam(String teamName) throws FileNotFoundException {
		
		String content = new Scanner(new File("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/team_statistics.txt")).useDelimiter("\\Z").next();
		String pattern = teamName;

		Pattern patternString = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = patternString.matcher(content);

		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}


	public void viewTeamStatistics() throws FileNotFoundException {
		JTextArea teamStatistics = new JTextArea();
		teamStatistics.setFont(new Font("Monospaced", Font.PLAIN, 10));
		teamStatistics.setBounds(10, 108, 196, 142);
		frame.getContentPane().add(teamStatistics);
		
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
			}if (count == 14 && tracker == 1) {
				tracker++;
				String firstString = teamStatistics.getText();
				try {
					
					teamStatistics.setText(firstString + "Total goals conceded: " + word + "\n" + "Overall goal difference: ");
					
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

	}

}
