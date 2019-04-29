package jk00687_project_com1028;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class LeagueDeveloperPage {

	private JFrame frame;
	private JTextField teamName;
	private JTextField goalsScored;
	private JTextField goalsConceded;
	private JTextField matchResult;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
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
		teamName.setBounds(105, 58, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Team name:");
		lblNewLabel.setBounds(10, 61, 67, 14);
		frame.getContentPane().add(lblNewLabel);

		JTextField goalsScored = new JTextField();
		goalsScored.setBounds(105, 89, 86, 20);
		frame.getContentPane().add(goalsScored);
		goalsScored.setColumns(10);

		JLabel lblGoalsScored = new JLabel("Goals Scored:");
		lblGoalsScored.setBounds(10, 94, 67, 14);
		frame.getContentPane().add(lblGoalsScored);

		JTextField goalsConceded = new JTextField();
		goalsConceded.setBounds(105, 126, 86, 20);
		frame.getContentPane().add(goalsConceded);
		goalsConceded.setColumns(10);

		JLabel lblGoalsConceded = new JLabel("Goals Conceded:");
		lblGoalsConceded.setBounds(10, 129, 85, 14);
		frame.getContentPane().add(lblGoalsConceded);

		JTextField matchResult = new JTextField();
		matchResult.setBounds(105, 157, 86, 20);
		frame.getContentPane().add(matchResult);
		matchResult.setColumns(10);

		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(10, 160, 46, 14);
		frame.getContentPane().add(lblResult);

		JButton updateTableBtn = new JButton("Update tables");
		updateTableBtn.setBounds(10, 215, 114, 23);
		frame.getContentPane().add(updateTableBtn);

		updateTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String teamNameString = teamName.getText();
				String matchResultString = matchResult.getText();
				String goalsScoredString = goalsScored.getText();
				String goalsConcededString = goalsConceded.getText();
				try {
					updateTable(teamNameString, matchResultString, goalsScoredString, goalsConcededString);

					JOptionPane.showMessageDialog(null, "Tables successfully updated");
				} catch (IllegalArgumentException | IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Tables could not be updated");
				}
			}
		});

		JButton editStatsBtn = new JButton("Edit statistics");
		editStatsBtn.setBounds(262, 215, 134, 23);
		frame.getContentPane().add(editStatsBtn);
		editStatsBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditStats.main();
				frame.dispose();
			}

		});

	}

	public void updateTable(String teamNameFinal, String matchResultFinal, String goalsScoredFinal,
			String goalsConcededFinal) throws IllegalArgumentException, IOException {

		// TODO: INCREMENT GAMES PLAYED, MOVE TEAMS HIGHER OR LOWER DEPENDING ON THEIR
		// POINTS

		String file_name = "C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt";
		String RegExPattern = "[a-zA-Z]+[\t]{2}[0-9]+";
		
		FileWriter write = new FileWriter(file_name, true);
		BufferedWriter writeBuffer = new BufferedWriter(write);
		BufferedReader br = new BufferedReader(new FileReader(file_name));
		String newLine = System.getProperty("line.separator");
		
		if (checkTeamExists(teamNameFinal)) {
			
			//TODO: UPDATE TABLE IF TEAM EXISTS ALREADY
			
		} else {

			try {

				writeBuffer.write(newLine);
				if (teamNameFinal.length() < 8) {
					writeBuffer.write(teamNameFinal + "\t" + "\t");
				}else {
					writeBuffer.write(teamNameFinal + "\t");
				}
				writeBuffer.write("1" + "\t" + "\t");
				if (matchResultFinal.equals("Win")) {
					writeBuffer.write("1" + "\t");
					writeBuffer.write("0" + "\t");
					writeBuffer.write("0" + "\t");
				} else if (matchResultFinal.equals("Draw")) {
					writeBuffer.write("0" + "\t");
					writeBuffer.write("1" + "\t");
					writeBuffer.write("0" + "\t");
				} else if (matchResultFinal.equals("Loss")) {
					writeBuffer.write("0" + "\t");
					writeBuffer.write("0" + "\t");
					writeBuffer.write("1" + "\t");
				} else {
					throw new IllegalArgumentException("Result must be a 'Win', 'Draw' or 'Loss'");
				}

				writeBuffer.write(goalsScoredFinal + "\t" + "\t");
				writeBuffer.write(goalsConcededFinal + "\t" + "\t");

				int goalSc = Integer.valueOf(goalsScoredFinal);
				int goalCon = Integer.valueOf(goalsConcededFinal);
				int goalDifference = goalSc - goalCon;

				writeBuffer.write(goalDifference + "\t" + "\t");

				if (matchResultFinal.equals("Win")) {
					writeBuffer.write("3");
				} else if (matchResultFinal.equals("Draw")) {
					writeBuffer.write("1");
				} else if (matchResultFinal.equals("Loss")) {
					writeBuffer.write("0");
				} else {
					throw new IllegalArgumentException();
				}
				writeBuffer.close();
			} catch (IOException e) {

			}
		}
	}


	public boolean checkTeamExists(String teamName) throws IOException {
		File f1 = new File("C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table.txt"); // Creation of
																											// File
																											// Descriptor
																											// for input
																											// file
		String[] words = null; // Intialize the word Array
		FileReader fr = new FileReader(f1); // Creation of File Reader object
		BufferedReader br = new BufferedReader(fr); // Creation of BufferedReader object
		String s;
		String input = teamName; // Input word to be searched
		int count = 0; // Intialize the word to zero
		while ((s = br.readLine()) != null) // Reading Content from the file
		{
			words = s.split("\t"); // Split the word using tabs
			for (String word : words) {
				if (word.equals(input)) // Search for the given word
				{
					count++; // If Present increase the count by one
				}
			}
		}
		if (count != 0) // Check for count not equal to zero
		{
			return true;
		} else {
			return false;
		}
	}
}
