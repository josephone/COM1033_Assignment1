package jk00687_project_com1028;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ManagerPage {

	private JFrame frame;
	private JTextField teamName;
	private JTextField goalkeeperName;
	private JTextField defenderName;
	private JTextField midfielderName;
	private JTextField attackerName;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerPage window = new ManagerPage();
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
	public ManagerPage() {
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

		JLabel lblEnterTeamName = new JLabel("Enter team name:");
		lblEnterTeamName.setBounds(10, 56, 105, 14);
		frame.getContentPane().add(lblEnterTeamName);

		teamName = new JTextField();
		teamName.setBounds(164, 53, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		JLabel lblEnterGoalkeeperName = new JLabel("Enter goalkeeper name:");
		lblEnterGoalkeeperName.setBounds(10, 91, 127, 14);
		frame.getContentPane().add(lblEnterGoalkeeperName);

		goalkeeperName = new JTextField();
		goalkeeperName.setBounds(164, 88, 86, 20);
		frame.getContentPane().add(goalkeeperName);
		goalkeeperName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter names of defenders:");
		lblNewLabel.setBounds(10, 122, 157, 14);
		frame.getContentPane().add(lblNewLabel);

		defenderName = new JTextField();
		defenderName.setBounds(164, 119, 86, 20);
		frame.getContentPane().add(defenderName);
		defenderName.setColumns(10);

		JLabel lblEnterNamesOf = new JLabel("Enter names of midfielders:");
		lblEnterNamesOf.setBounds(10, 155, 157, 14);
		frame.getContentPane().add(lblEnterNamesOf);

		midfielderName = new JTextField();
		midfielderName.setBounds(164, 152, 86, 20);
		frame.getContentPane().add(midfielderName);
		midfielderName.setColumns(10);

		JLabel lblEnterNameOf = new JLabel("Enter name of attackers:");
		lblEnterNameOf.setBounds(10, 187, 148, 14);
		frame.getContentPane().add(lblEnterNameOf);

		attackerName = new JTextField();
		attackerName.setBounds(164, 184, 86, 20);
		frame.getContentPane().add(attackerName);
		attackerName.setColumns(10);

		JButton btnManager = new JButton("Update team sheets");
		btnManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String teamNameString = teamName.getText();
				String goalkeeperNameString = goalkeeperName.getText();
				String defenderNameString = defenderName.getText();
				String midfielderNameString = midfielderName.getText();
				String attackerNameString = attackerName.getText();

				try {
					updateTeamSheets(teamNameString, goalkeeperNameString, defenderNameString, midfielderNameString,
							attackerNameString);
					JOptionPane.showMessageDialog(null, "Team sheet updated");
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnManager.setBounds(276, 227, 148, 23);
		frame.getContentPane().add(btnManager);

		JLabel lblManagerView = new JLabel("Manager view");
		lblManagerView.setBounds(10, 11, 93, 14);
		frame.getContentPane().add(lblManagerView);

	}

	public void updateTeamSheets(String teamName, String goalkeeperName, String defenderName, String midfielderName,
			String attackerName) throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter(
				"C:/Users/hunya/Documents/GitHub/COM1033_Assignment1/" + teamName + "TeamSheet.txt", "UTF-8");
		writer.println("Gaolkeeper: " + goalkeeperName);
		writer.println("Defenders: " + defenderName);
		writer.println("Midfielders: " + midfielderName);
		writer.println("Attackers: " + attackerName);
		writer.close();

	}

}
