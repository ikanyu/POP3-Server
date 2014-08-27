//to use this java file, please change the testing.txt path

import java.io.*;
import java.net.*;

public class testPart2 {

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err
					.println("Usage: java Pop3Client <host name> <port number>");
			System.exit(1);
		}

		String hostName = "127.0.0.1";
		int portNumber = Integer.parseInt(args[0]);

		try (Socket kkSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(),
						true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						kkSocket.getInputStream()));) {
			BufferedReader stdIn = new BufferedReader(new FileReader(
					"testing.txt"));
			String fromServer;
			String fromUser;
			while (true) {
				while ((fromServer = in.readLine()) != null) {
					System.out.println("Server: " + fromServer);
					if (!in.ready()) {
						break;
					}
				}

				fromUser = stdIn.readLine();
				if (fromUser != null) {
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("\nCouldn't get I/O for the connection to "
					+ hostName);
			System.exit(1);
		}
	}
}
