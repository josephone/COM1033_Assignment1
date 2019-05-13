package jk00687_project_com1028;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * @author Joseph Kutler
 *
 */
public class KnockoutsPage {

	private JFrame frame;
	private JTextField teamName = null;
	private JTextField goalsScored = null;
	private JTextField goalsConceded = null;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					KnockoutsPage window = new KnockoutsPage();
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
	public KnockoutsPage() {
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
		lblLeagueDeveloperView.setBounds(10, 11, 131, 14);
		frame.getContentPane().add(lblLeagueDeveloperView);

		JLabel lblNewLabel = new JLabel("Enter results of knockouts fixture below");
		lblNewLabel.setBounds(10, 96, 281, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Team name:");
		lblNewLabel_1.setBounds(10, 140, 105, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Goals Scored:");
		lblNewLabel_2.setBounds(10, 165, 90, 14);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblGoalsConceded = new JLabel("Goals Conceded:");
		lblGoalsConceded.setBounds(10, 190, 105, 14);
		frame.getContentPane().add(lblGoalsConceded);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(66, 227, 89, 23);
		frame.getContentPane().add(btnSubmit);

		teamName = new JTextField();
		teamName.setBounds(126, 137, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		goalsScored = new JTextField();
		goalsScored.setBounds(126, 162, 86, 20);
		frame.getContentPane().add(goalsScored);
		goalsScored.setColumns(10);

		goalsConceded = new JTextField();
		goalsConceded.setBounds(126, 187, 86, 20);
		frame.getContentPane().add(goalsConceded);
		goalsConceded.setColumns(10);

		String teamNameString = teamName.getText();
		String goalsScoredString = goalsScored.getText();
		String goalsConcededString = goalsConceded.getText();

		btnSubmit.addActionListener(new ActionListener() {

			/*
			 * Upon the 'Search' button being clicked, the 'updateKnockouts' method is
			 * called. This code is wrapped in a try-catch statement in order to handle
			 * exceptions being thrown and errors being generated. I have caught a
			 * 'FileNotFoundException' since the 'updateKnockouts' method will require the
			 * program to write to a file which may throw up this specific error
			 * 
			 */

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					updateKnockouts(teamNameString, goalsScoredString, goalsConcededString);
					JOptionPane.showMessageDialog(null, "Knockouts updated");
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "File not found");
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * This is my precursor code that in the future could be extended to fully work.
	 * This method starts by creating an object of type FileOutputStream which will
	 * be used to locate the file in which the knockouts competition will update. I
	 * then will write to this .txt file basing the updates upon the information
	 * that is provided by the user in the JTextFields. This method however is
	 * incomplete, but if given more time could be completed and this additional
	 * functionality could be implemented
	 * 
	 * @param teamName      This is the String equivalent value of the JTextField
	 *                      'teamName'
	 * @param goalsScored   This is the String equivalent value of the JTextField
	 *                      'goalsScored'
	 * @param goalsConceded This is the String equivalent value of the JTextField
	 *                      'goalsConceded'
	 * @throws FileNotFoundException A 'FileNotFoundException' is being thrown here
	 *                               in order to protect the code in the case that 
	 *                               a file has not been found
	 */

	public void updateKnockouts(String teamName, String goalsScored, String goalsConceded)
			throws FileNotFoundException {
		FileOutputStream fstream = new FileOutputStream("C:\\Users\\Public\\knockout_tree.txt");
		try {
			fstream.write(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
