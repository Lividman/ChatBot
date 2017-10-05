import java.util.Scanner;

/**
 * A simple class to run our chatbot teams.
 * @author Mr. Levin
 * @version September 2017
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args)
	{
		int num = 0;
		ChatBotBrandon chatbot1 = new ChatBotBrandon();
		ChatBotJacky chatbot2 =  new ChatBotJacky();
		ChatBotTim chatbot3 =  new ChatBotTim();
		ChatBotZilong chatbot4 =  new ChatBotZilong();
		System.out.println("Do you want to talk to Brandon or Jacky?");
		Scanner in = new Scanner (System.in);
		String statement = in.nextLine();
		if (statement.equals("Brandon"))
		{
			System.out.println(chatbot1.getGreeting());
			num = 1;
		}
		if(statement.equals("Jacky"))
		{
			System.out.println(chatbot2.getGreeting());
			num = 2;
		}
		if (statement.equals("Tim"))
		{
			System.out.println(chatbot3.getGreeting());
			num = 3;
		}
		if (statement.equals("Zilong"))
		{
			System.out.println(chatbot4.getGreeting());
			num = 4;
		}
		
		
		


		while (!statement.equals("Bye"))
		{
			if(num == 1)
			{
				statement = in.nextLine();
				System.out.println (chatbot1.getResponse(statement));
				
			}
			if(num == 2)
			{
				statement = in.nextLine();
				System.out.println (chatbot2.getResponse(statement));
			}
			if(num == 3)
			{
				statement = in.nextLine();
				System.out.println (chatbot3.getResponse(statement));
			}
			if(num == 4)
			{
				statement = in.nextLine();
				System.out.println (chatbot4.getResponse(statement));
			}
		}
	}

}
