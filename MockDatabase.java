/*
 * My MockDatabase which is the backend acts as a place to analyze all the command that user enters.
 * Therefore, the work flow is as follows. Server -> CommandInterpreter -> MockDatabase to send data.
 * MockDatabase ->CommandInterpreter ->Server-> Client to output data.
 * After capturing the input, the code will query to the database accordingly to obtain the correct result or to update the database.
 * I added a catch when it is Timeout. This is to ensure the database will be reset back to the original state if user does not manage
 * to logout after Timeout occurs.I slightly modified my CommandInterpreter so that I can do this catch.
 */

import java.util.ArrayList;

import java.sql.*;

//String url;

public class MockDatabase implements DatabaseInterface {

	private String a;

	private ArrayList<Integer> iMailIDdb;
	private ArrayList<Integer> iMailIDdbtemp;
	private ArrayList<Integer> iMailIDposition;

	private ArrayList<String> txMailContentdb;

	private ArrayList<String> vchUIDLdb;

	private ArrayList<Integer> octetLengthdb;

	public MockDatabase() {
		a = "s";

		iMailIDdb = new ArrayList<Integer>();
		iMailIDdbtemp = new ArrayList<Integer>();
		iMailIDposition = new ArrayList<Integer>();

		txMailContentdb = new ArrayList<String>();

		vchUIDLdb = new ArrayList<String>();

		octetLengthdb = new ArrayList<Integer>();

	}

	public int intUserInput;

	private String line;

	boolean checkExistUser;
	boolean checkPassword;
	private int passIDdb;
	String url;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String userdb;
	String passdb;
	private int useriddb;
	private int numberEmail;
	private int sumOctet;
	private int tiLockeddb;

	public void sqldb() {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			url = "jdbc:mysql://mysql.cs.nott.ac.uk/wyw03u";
			conn = DriverManager.getConnection(url, "wyw03u", "tuesday");
			stmt = conn.createStatement();
	
			System.out.println("Connection made");
		}

		catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

	public String username(String userInput) {
		// user
		a = "";
		try {
			stmt = conn.createStatement();
			String query = "Select vchUsername,iMaildropID from m_Maildrop where vchUsername = '"
					+ userInput + "'";
			rs = stmt.executeQuery(query);

			checkExistUser = false;

			while (rs.next()) {
				checkExistUser = true;
				userdb = rs.getString("vchUsername");
				useriddb = rs.getInt("iMaildropID");
				a = "+OK Hello " + userdb;
			}

			if (checkExistUser == false) {

				a = "-ERR Sorry no mailbox for " + userdb + " here";

			}

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}

		return a;
	}

	public String password(String userInput) {
		// pass
		a = "";
		try {
			if (checkExistUser == true) {
				stmt = conn.createStatement();
				String query = "Select vchPassword, iMaildropID , tiLocked from m_Maildrop where vchUsername = '"
						+ userdb + "'";
				rs = stmt.executeQuery(query);

				checkPassword = false;

				while (rs.next()) {
					checkPassword = true;
					passdb = rs.getString("vchPassword");
					passIDdb = rs.getInt("iMaildropID");
					tiLockeddb = rs.getInt("tiLocked");

					if (userInput.equals(passdb) && passIDdb == useriddb) {
						if (tiLockeddb == 0) {
							stmt = conn.createStatement();
							String query1 = "Select iMailID, txMailContent, vchUIDL,(octet_length(txMailContent))AS octetLengthdb  from m_Mail where iMaildropID = "
									+ passIDdb + " ";
							rs = stmt.executeQuery(query1);

							while (rs.next()) {

								iMailIDdb.add(rs.getInt("iMailID"));
								txMailContentdb.add(rs
										.getString("txMailContent"));
								vchUIDLdb.add(rs.getString("vchUIDL"));
								octetLengthdb.add(rs.getInt("octetLengthdb"));

								stmt = conn.createStatement();
								String query2 = "Update m_Maildrop SET tiLocked = 1 Where iMaildropID = "
										+ passIDdb + "";

								stmt.executeUpdate(query2);

								a = "+OK " + userdb
										+ "'s maildrop is ready with "
										+ iMailIDdb.size() + " messages.";
							}
						}

						else {
							a = "-ERR user already logged in";
						}
					}

					if (!userInput.equals(passdb)) {
						a = "-ERR Invalid password";
					}

				}

				if (checkPassword == false) {
					a = "-ERR user still not logged in";
				}

			}

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}

		return a;
	}

	public String numberEmail(String userInput, String numberInput) {
		// list
		a = "";
		try {

			if (userInput.equals("zero")) {

				numberEmail = iMailIDdb.size() - iMailIDdbtemp.size();
				sumOctet = 0;

				for (int i = 0; i < octetLengthdb.size(); i++) {
					if (!iMailIDposition.contains(i)) {
						sumOctet += octetLengthdb.get(i);
					}
				}
				a = "+OK " + numberEmail + " messages (" + sumOctet
						+ " octets)";
				for (int listCounter = 0; listCounter < iMailIDdb.size(); listCounter++) {
					if (!iMailIDposition.contains(listCounter)) {
						a += "\n" + (listCounter + 1) + " "
								+ octetLengthdb.get(listCounter);

					}
					if (iMailIDposition.contains(listCounter)) {

					}

				}

			}

			else if (numberInput.equals("1")) {
				int intUserInput = Integer.parseInt(userInput);
				intUserInput = intUserInput - 1;

				if (intUserInput + 1 > numberEmail || (intUserInput + 1) == 0
						|| iMailIDposition.contains(intUserInput)) {
					numberEmail = iMailIDdb.size() - iMailIDdbtemp.size();
					a = "-ERR no such message, only " + numberEmail
							+ " messages in maildrop";

				}

				else {
					a = "+OK " + (intUserInput + 1) + " "
							+ octetLengthdb.get(intUserInput) + " octets";
				}

			}

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}
		return a;
	}

	public String quitServer(String userInput) {
		a = "";
		// quit
		try {
			if (userInput.equalsIgnoreCase("QUIT")) {

				numberEmail = iMailIDdb.size() - iMailIDdbtemp.size();

				if (numberEmail > 0) {
					for (int iMailCounter = 1; iMailCounter <= iMailIDdbtemp
							.size(); iMailCounter++) {
						stmt = conn.createStatement();
						String query = "Delete from m_Mail where iMailID = '"
								+ iMailIDdbtemp.get(iMailCounter - 1) + "'";

						stmt.executeUpdate(query);
						stmt.close();

					}
					a = "+OK POP3 server signing off (" + numberEmail
							+ " messages left)";

					stmt = conn.createStatement();
					String query3 = "Update m_Maildrop SET tiLocked = 0 Where iMaildropID = "
							+ passIDdb + "";

					stmt.executeUpdate(query3);

				}

			}
		} catch (Exception exp) {
			a = "-ERR invalid command";
		}
		return a;

	}

	public String noopResponse(String command) {
		// noop
		a = "";
		try {
			a = "+OK";

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}
		return a;
	}

	public String retrieveMessage(String userInput) {
		// retrieve
		a = "";
		try {
			int intUserInput = Integer.parseInt(userInput);
			intUserInput = intUserInput - 1;

			if (intUserInput + 1 > iMailIDdb.size() || (intUserInput + 1) == 0
					|| iMailIDposition.contains(intUserInput)) {
				a = "-ERR no such message";
			}

			else {
				a = "+OK " + octetLengthdb.get(intUserInput) + " octet \n "
						+ txMailContentdb.get(intUserInput);
			}

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}
		return a;
	}

	public String deleteMessage(String userInput) {
		// dele
		a = "";
		try {
			int intUserInput = Integer.parseInt(userInput);

			iMailIDdbtemp.add(iMailIDdb.get(intUserInput - 1));

			iMailIDposition.add(intUserInput - 1);

			a = "+OK message " + intUserInput + " deleted";
			numberEmail = iMailIDdb.size();

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}

		return a;
	}

	public String stat() {
		// stat
		a = "";
		try {

			sumOctet = 0;
			numberEmail = iMailIDdb.size() - iMailIDdbtemp.size();

			for (int i = 0; i < octetLengthdb.size(); i++) {
				if (!iMailIDposition.contains(i)) {
					sumOctet += octetLengthdb.get(i);
				}
			}

			a = "+OK " + numberEmail + " " + sumOctet;
		} catch (Exception exp) {
			a = "-ERR invalid command";
		}
		return a;
	}

	public String reset() {
		a = "";
		try {

			sumOctet = 0;

			iMailIDdbtemp.clear();
			iMailIDposition.clear();

			for (int m = 0; m < iMailIDdb.size(); m++) {
				sumOctet += octetLengthdb.get(m);
			}

			a = "+OK maildrop has " + iMailIDdb.size() + " message ("
					+ sumOctet + " octets)";

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}

		return a;

	}

	public String top(String userInput, String emailLine) {
		a = "";
		try {
			int intUserInput = Integer.parseInt(userInput);
			int emailLine1 = Integer.parseInt(emailLine);

			String email = txMailContentdb.get(intUserInput - 1);
			String headerPlusContent[] = email.split("\n\n", 2);
			String contentSplit[] = headerPlusContent[1].split("\n");

			line = "";

			if (iMailIDposition.contains(intUserInput - 1)) {
				a = "-ERR this message has been deleted";
			}

			else if (intUserInput > txMailContentdb.size()) {
				emailLine1 = 0;
				a = "-ERR no such message";

			}

			else {
				line += headerPlusContent[0];
				for (int b = 0; b < emailLine1; b++) {
					line += "\n" + contentSplit[b];

				}
				a = "+OK" + line;

			}
		}

		catch (Exception exp) {
			a = "-ERR invalid command";
		}
		return a;
	}

	public String uidl(String userInput, String numberInput) {
		// uidl
		a = "";
		try {
			a = "+OK ";
			if (userInput.equals("none")) {

				for (int b = 0; b < vchUIDLdb.size(); b++) {

					if (!iMailIDposition.contains(b)) {
						a += "\n" + (b + 1) + " " + vchUIDLdb.get(b);
					}
				}
			}

			else if (numberInput.equals("one")) {
				int intUserInput = Integer.parseInt(userInput);
				intUserInput = intUserInput - 1;
				if (intUserInput + 1 > vchUIDLdb.size()
						|| (intUserInput + 1) == 0) {
					a = "-ERR wrong command";
				}

				else if (iMailIDposition.contains(intUserInput)) {
					a = "-ERR No UIDL for this deleted message";
				}

				else {
					a += "\n" + vchUIDLdb.get(intUserInput);
				}

			}

		} catch (Exception exp) {
			a = "-ERR invalid command";
		}
		return a;
	}

	public void Release() {
		try {
			stmt = conn.createStatement();
			String query6 = "Update m_Maildrop SET tiLocked = 0 Where iMaildropID = "
					+ passIDdb + "";

			stmt.executeUpdate(query6);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
