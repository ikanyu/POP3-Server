public class CommandInterpreter {
	private DatabaseInterface db;
	private String state;
	private String c;
	private String command;
	private String inputSplit1;
	private String inputSplit2;
	private int checkOk;

	public CommandInterpreter() {
		state = "Authorization";
		db = new MockDatabase();
		db.sqldb();

	}

	public String handleInput(String input) {

		String[] splitted = input.split(" ");
		command = null;
		inputSplit1 = null;
		inputSplit2 = null;
		if (splitted.length == 1) {
			command = splitted[0];
		}

		if (splitted.length == 2) {
			command = splitted[0];
			inputSplit1 = splitted[1];
		}

		if (splitted.length == 3) {
			command = splitted[0];
			inputSplit1 = splitted[1];
			inputSplit2 = splitted[2];
		}

		if (command.equalsIgnoreCase("USER")
				&& state.equalsIgnoreCase("Authorization")) {
			c = db.username(inputSplit1);

			if (c.substring(0, 1).equals("+")) {
				checkOk = 1;

			}

		}

		else if (command.equalsIgnoreCase("PASS")
				&& state.equals("Authorization") && checkOk == 1) {
			c = db.password(inputSplit1);
			if ((c.substring(0, 1)).equals("+")) {
				state = "Transaction";
			}
		}

		else if (command.equalsIgnoreCase("LIST")
				&& state.equals("Transaction")) {//

			if (inputSplit1 == null) {
				inputSplit1 = "zero";
				c = db.numberEmail(inputSplit1, inputSplit2);
				
			}

			if (inputSplit2 == null) {
				inputSplit2 = "1";
				c = db.numberEmail(inputSplit1, inputSplit2);

			}

		}

		else if (command.equalsIgnoreCase("NOOP")
				&& state.equals("Transaction")) {
			c = db.noopResponse(command);
			// return c;
		}

		else if (command.equalsIgnoreCase("QUIT")
				&& state.equals("Transaction")) {
			c = db.quitServer(command);
			state = "Update";
		}

		else if (command.equalsIgnoreCase("RETR")
				&& state.equals("Transaction")) {
			c = db.retrieveMessage(inputSplit1);
		}

		else if (command.equalsIgnoreCase("DELE")
				&& state.equals("Transaction")) {
			c = db.deleteMessage(inputSplit1);
		}

		else if (command.equalsIgnoreCase("STAT")
				&& state.equals("Transaction")) {
			c = db.stat();
		}

		else if (command.equalsIgnoreCase("RSET")
				&& state.equals("Transaction")) {
			c = db.reset();
		}

		else if (command.equalsIgnoreCase("TOP") && state.equals("Transaction")) {

			c = db.top(inputSplit1, inputSplit2);

		}

		else if (command.equalsIgnoreCase("UIDL")
				&& state.equals("Transaction")) {

			if (inputSplit1 == null) {
				inputSplit1 = "none";
				c = db.uidl(inputSplit1, inputSplit2);
			}

			if (inputSplit2 == null) {
				inputSplit2 = "one";
				c = db.uidl(inputSplit1, inputSplit2);

			}

		}

		return c;
	}

	public void Release() {
		db.Release();		
	}
}
