package jk00687_project_com1028;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
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
		
		JButton updateTableBtn = new JButton("Update tables");
		updateTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String teamNameString = teamName.getText();
				String matchResultString = matchResult.getText();
				String goalsScoredString = goalsScored.getText();
				String goalsConcededString = goalsConceded.getText();
				

				updateTable(teamNameString, matchResultString, goalsScoredString, goalsConcededString);
			}
		});
		
		updateTableBtn.setBounds(10, 215, 114, 23);
		frame.getContentPane().add(updateTableBtn);
		
		JLabel lblLeagueDeveloperView = new JLabel("League developer view");
		lblLeagueDeveloperView.setBounds(10, 11, 134, 14);
		frame.getContentPane().add(lblLeagueDeveloperView);
		
		teamName = new JTextField();
		teamName.setBounds(105, 58, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Team name:");
		lblNewLabel.setBounds(10, 61, 67, 14);
		frame.getContentPane().add(lblNewLabel);
		
		goalsScored = new JTextField();
		goalsScored.setBounds(105, 89, 86, 20);
		frame.getContentPane().add(goalsScored);
		goalsScored.setColumns(10);
		
		JLabel lblGoalsScored = new JLabel("Goals Scored:");
		lblGoalsScored.setBounds(10, 94, 67, 14);
		frame.getContentPane().add(lblGoalsScored);
		
		goalsConceded = new JTextField();
		goalsConceded.setBounds(105, 126, 86, 20);
		frame.getContentPane().add(goalsConceded);
		goalsConceded.setColumns(10);
		
		JLabel lblGoalsConceded = new JLabel("Goals Conceded:");
		lblGoalsConceded.setBounds(10, 129, 85, 14);
		frame.getContentPane().add(lblGoalsConceded);
		
		matchResult = new JTextField();
		matchResult.setBounds(105, 157, 86, 20);
		frame.getContentPane().add(matchResult);
		matchResult.setColumns(10);
		
		JLabel lblResult = new JLabel("Result:");
		lblResult.setBounds(10, 160, 46, 14);
		frame.getContentPane().add(lblResult);
		
	}
	
	public void updateTable(String teamName, String matchResult, String goalsScored, String goalsConceded) {
		
		String file_name = "C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/league_table_test.txt";
		try {
			FileWriter write = new FileWriter(file_name);
			BufferedWriter writeBuffer = new BufferedWriter(write);
			writeBuffer.write(teamName + "\t");
			writeBuffer.write("1" + "\t");
			writeBuffer.write("1" + "\t");
			writeBuffer.write("0" + "\t");
			writeBuffer.write("0" + "\t");
			writeBuffer.write(goalsScored + "\t");
			writeBuffer.write(goalsConceded + "\t");
			int goalSc = Integer.valueOf(goalsScored);
			int goalCon = Integer.valueOf(goalsConceded);
			int goalDifference = goalSc - goalCon;
			writeBuffer.write(goalDifference + "\t");
			writeBuffer.write("3");
			writeBuffer.close();
		}catch (IOException e){
			
		}
		
	}
	
}
