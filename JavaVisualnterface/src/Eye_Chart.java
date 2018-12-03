
/**
 * 
 * Eye Chart Test
 * 
 * @author Sara Ashcraft, Thao Tran, Bonaventure Raj, Josh Williams
 * @version 1.0
 * 
 * Description: This program will imitate an optometrist's Snellen eye chart test.
 *
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Eye_Chart extends JFrame {

	public static void main(String[] args) throws SQLException, InterruptedException {

		// Create window
		Eye_Chart frame = new Eye_Chart();

		// Create text area
		JTextField print = new JTextField();

		// Control font
		Font font;
		Font font2;
		int[] fontSizes = { 152, 130, 108, 87, 65, 43, 33, 21, 15, 9 };
		int fontSizeIndex = 0;

		// Tracking % correct
		int correctCount = 0;
		int totalCount = 0;
		int incorrectCount = 0;
		String vision = null;

		String userInput = null;

		// Keep looping until user gets 3 incorrect
		do {

			// Generate random char A-Z
			char rand = (char) (((int) (Math.random() * 26)) + 'A');
			font = new Font("Courier", Font.BOLD, fontSizes[fontSizeIndex]);

			// Print char in varying fonts
			frame.getContentPane().add(print);
			print.setHorizontalAlignment(JTextField.CENTER);
			print.setText(Character.toString(rand));
			print.setFont(font);
			frame.setVisible(true);
			print.setEditable(false);

			userInput = getUserInput();

			// Check if the user entered the correct letter

			if (rand == Character.toUpperCase(userInput.charAt(0))) {
				fontSizeIndex++;
				correctCount++;
			} else {
				if (fontSizeIndex > 0)
					fontSizeIndex--;
				incorrectCount++;
			}

			// Increment total answers
			totalCount++;

		} while (incorrectCount < 3);

		// Set vision
		switch (fontSizeIndex) {
		case 0:
			vision = "20/200";
			break;
		case 1:
			vision = "20/100";
			break;
		case 2:
			vision = "20/80";
			break;
		case 3:
			vision = "20/63";
			break;
		case 4:
			vision = "20/50";
			break;
		case 5:
			vision = "20/40";
			break;
		case 6:
			vision = "20/32";
			break;
		case 7:
			vision = "20/25";
			break;
		case 8:
			vision = "20/20";
			break;
		}

		// Print results
		frame.getContentPane().add(print);
		print.setHorizontalAlignment(JTextField.CENTER);
		font2 = new Font("Courier", Font.BOLD, 43);
		print.setFont(font2);
		print.setText(correctCount + "/" + totalCount + " correct - Your vision is " + vision);
	}

	public static String getUserInput() {
		String userInput = null;
		// Connect to database
		String url = "jdbc:sqlserver://visionuncc.database.windows.net:1433;database=vision_answers;user=uncclocalhack@visionuncc;password=vision#$UNCC@22;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		Connection con = null;

		try {
			con = DriverManager.getConnection(url);
			String schema = con.getSchema();

			Statement stmt = null;
			String query = null;
			// String query = "SELECT TOP 1 Character FROM Answers WHERE TimeAdded >
			// '2018-12-01 23:02:32.931'";
			try {
				stmt = con.createStatement();
				Timestamp timestamp = new Timestamp((long) (System.currentTimeMillis() + 1.8 * Math.pow(10, 7)));
				while (userInput == null) {
					

					query = "SELECT TOP 1 * FROM Answers ORDER BY TimeAdded DESC";
					// query = "SELECT TOP 1 Character FROM Answers WHERE TimeAdded > '2018-12-01
					// 23:00:52.829'";
					System.out.println(query);
					ResultSet rs = stmt.executeQuery(query);
					while (rs.next()) {
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Timestamp dateTimeStamp = new Timestamp(dateFormat.parse(rs.getString(2)).getTime());
						System.out.println("time after " + timestamp.toString());
						System.out.println("sql time " + dateTimeStamp.toString());
						System.out.println(dateTimeStamp.compareTo(timestamp) > 0);
						
						if(dateTimeStamp.compareTo(timestamp) > 0) {
							System.out.println("here");
							userInput = rs.getString(1);
						}
					}
					System.out.println("userInput " + userInput);
					Thread.sleep(2000);
				}

			} catch (Exception e) {
				System.err.println("Got an exception! ");
				System.err.println(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInput;
	}

	public Eye_Chart() {
		setBounds(100, 100, 500, 500);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}