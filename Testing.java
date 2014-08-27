import java.util.Scanner;

public class Testing {

	public static void main(String args[]) {
		String in;

		int a = 0;
		CommandInterpreter cmd = new CommandInterpreter();
		while (a != 1) {

			Scanner input = new Scanner(System.in);
			in = input.nextLine();

			String catches = cmd.handleInput(in);

			System.out.println(catches);
		}
	}

}
