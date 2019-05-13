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

/**
 * @author Joseph Kutler
 *
 */
public class ManagerPage {

	private JFrame frame;
	private JTextField teamName = null;
	private JTextField goalkeeperName = null;
	private JTextField defenderName = null;
	private JTextField midfielderName = null;
	private JTextField attackerName = null;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		teamName.setBounds(174, 53, 86, 20);
		frame.getContentPane().add(teamName);
		teamName.setColumns(10);

		JLabel lblEnterGoalkeeperName = new JLabel("Enter goalkeeper name:");
		lblEnterGoalkeeperName.setBounds(10, 91, 144, 14);
		frame.getContentPane().add(lblEnterGoalkeeperName);

		goalkeeperName = new JTextField();
		goalkeeperName.setBounds(174, 84, 86, 20);
		frame.getContentPane().add(goalkeeperName);
		goalkeeperName.setColumns(10);

		JLabel lblNewLabel = new JLabel("Enter names of defenders:");
		lblNewLabel.setBounds(10, 122, 157, 14);
		frame.getContentPane().add(lblNewLabel);

		defenderName = new JTextField();
		defenderName.setBounds(174, 119, 86, 20);
		frame.getContentPane().add(defenderName);
		defenderName.setColumns(10);

		JLabel lblEnterNamesOf = new JLabel("Enter names of midfielders:");
		lblEnterNamesOf.setBounds(10, 155, 157, 14);
		frame.getContentPane().add(lblEnterNamesOf);

		midfielderName = new JTextField();
		midfielderName.setBounds(174, 150, 86, 20);
		frame.getContentPane().add(midfielderName);
		midfielderName.setColumns(10);

		JLabel lblEnterNameOf = new JLabel("Enter name of attackers:");
		lblEnterNameOf.setBounds(10, 187, 148, 14);
		frame.getContentPane().add(lblEnterNameOf);

		attackerName = new JTextField();
		attackerName.setBounds(174, 181, 86, 20);
		frame.getContentPane().add(attackerName);
		attackerName.setColumns(10);

		JButton btnManager = new JButton("Update team sheets");
		btnManager.addActionListener(new ActionListener() {

			/*
			 * Upon the 'Update team sheets' button being clicked, the values of the
			 * JTextFields is converted into Strings with the appropriate variable names so
			 * that they can be passed as parameters into the 'updateTeamSheets' method.
			 * This code is wrapped in a try-catch statement in order to handle exceptions
			 * being thrown and errors being generated. I have caught a
			 * 'FileNotFoundException' since the 'updateKnockouts' method will require the
			 * program to write to a file which may throw up this specific error.
			 * Additionally I have thrown an 'UnsupportedEncodingException' which is being
			 * created since I specify which type of encoding (UTF-8) I wish to include. If
			 * the method is able to run as normal the user receives a dialog box which
			 * specifies to them whereabouts the new text file containing the team sheet has
			 * been created.
			 * 
			 */

			@Override
			public void actionPerformed(ActionEvent e) {

				String teamNameString = teamName.getText();
				String goalkeeperNameString = goalkeeperName.getText();
				String defenderNameString = defenderName.getText();
				String midfielderNameString = midfielderName.getText();
				String attackerNameString = attackerName.getText();

				try {
					updateTeamSheets(teamNameString, goalkeeperNameString, defenderNameString, midfielderNameString,
							attackerNameString);
					JOptionPane.showMessageDialog(null, "Team sheet updated at C:\\Users\\Public\\");
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					JOptionPane.showMessageDialog(null, "Error");
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

	/**
	 * This method is used to update the team sheets for the team that is being
	 * entered the information about and it takes in information for the goalkeeper
	 * name, names of the defenders, midfielders and attackers and writes all of
	 * this to a text file that is in a universally accessible file location. I
	 * create an instance of the object type PrintWriter in order to be able to
	 * create the file which is prefixed with the name of the team to make it more
	 * uniquely identifiable.
	 * 
	 * @param teamName       This is the String equivalent value of the JTextField
	 *                       'teamName'
	 * @param goalkeeperName This is the String equivalent value of the JTextField
	 *                       'goalKeeperName'
	 * @param defenderName   This is the String equivalent value of the JTextField
	 *                       'defenderName'
	 * @param midfielderName This is the String equivalent value of the JTextField
	 *                       'midfielderName'
	 * @param attackerName   This is the String equivalent value of the JTextField
	 *                       'attackerName'
	 * @throws FileNotFoundException        A 'FileNotFoundException' is being
	 *                                      thrown here in order to protect the code
	 *                                      in the case that a file has not been
	 *                                      found
	 * @throws UnsupportedEncodingException This type of exception is being thrown
	 *                                      here in case the encoding type is not
	 *                                      allowed
	 */
	public void updateTeamSheets(String teamName, String goalkeeperName, String defenderName, String midfielderName,
			String attackerName) throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter("C:\\Users\\Public\\" + teamName + "TeamSheet.txt", "UTF-8");
		writer.println("Gaolkeeper: " + goalkeeperName);
		writer.println("Defenders: " + defenderName);
		writer.println("Midfielders: " + midfielderName);
		writer.println("Attackers: " + attackerName);
		writer.close();

	}

}
