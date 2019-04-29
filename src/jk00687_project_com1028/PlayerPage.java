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
	private JTextField searchBtn;

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
		
		searchBtn = new JTextField();
		searchBtn.setBounds(287, 102, 86, 20);
		frame.getContentPane().add(searchBtn);
		searchBtn.setColumns(10);
		
		String teamNameString = searchBtn.getText();

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
		
		if(checkTeamExists(teamNameString)) {
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
		}else {
			JOptionPane.showMessageDialog(null, "Team does not exist");
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
			words = s.split("\t"); // Split the word using space
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
