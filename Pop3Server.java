
import java.net.*;
import java.io.*;

public class Pop3Server {
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println("Usage: java Pop3Server <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		int timeOut = Integer.parseInt(args[1]);
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				new Pop3ServerThread(serverSocket.accept(), timeOut).start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
}