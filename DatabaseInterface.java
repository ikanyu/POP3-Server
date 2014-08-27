public interface DatabaseInterface {

	public String username(String userInput);

	public String password(String userInput);

	public String numberEmail(String userInput, String numberInput);

	public String quitServer(String userInput);

	public String noopResponse(String command);

	public String retrieveMessage(String userInput);

	public String deleteMessage(String userInput);

	public String top(String userInput, String emailLine);

	public String stat();

	public String reset();

	public String uidl(String userInput, String numberInput);

	public void sqldb();

	public void Release();

}
