import java.net.*;
import java.io.*;

public class Pop3ServerThread extends Thread {
	private Socket socket = null;
	private int timeOut = 0;
	private CommandInterpreter kkp;

	public Pop3ServerThread(Socket socket, int timeOut) {
		super("Pop3ServerThread");
		this.socket = socket;
		this.timeOut = timeOut;
		kkp = new CommandInterpreter();
	}

	public void run() {

		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));) {
			socket.setSoTimeout(timeOut * 1000);
			String inputLine, outputLine;
			
			outputLine = "+OK POP3 Server Ready";
			
			out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				outputLine = kkp.handleInput(inputLine);
				out.println(outputLine);
				
			}
			socket.close();
		} catch (Exception e) {
			kkp.Release();
		}
	
	}
}