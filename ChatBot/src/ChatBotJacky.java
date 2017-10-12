import java.util.Random;
import java.util.ArrayList;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @version September 2017
 * Starter code from MrLevinCSA
 */
public class ChatBotJacky
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;
	int count = 0;
	private String [] randomNeutralResponses = {"Keyboards are essential things", "Hello", "What are you looking for?"};
	private String [] randomAngryResponses = {"You're testing my patience!", "I hope you apologize to me."};
	private String [] randomHappyResponses = {"How are you doing?", ""};
	private String [] switchColors = {"red", "brown", "black", "blue", "green", "clear", "topre"};
	private String [] recoCheap = {"Red Dragon K552-M", "Qisan Magicforce", "DREVO 84", "EagleTec KG011", "Azio MGK1-K"};
	private String [] recoSmall = {"Pok3r", "KBparadise	V60", "Leopold FC660M", "Varmillo VA68M", "Anne Pro"};
	private String [] recoKb = {"WASD Code", "Corsair K70", "CM QuickFire Rapid", "Ducky 5", "Filco Majestouch"};
	boolean askColor;
	private ArrayList<String> userInput = new ArrayList<String>();
	private ArrayList<String> userWant = new ArrayList<String>();
	private ArrayList<String> userWantto = new ArrayList<String>();
	// userInput.add("");
	// userInput.get(The number);  Essentially this will grab the item in the array at that position index.
	public String theSwitch = "";
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */
	public String getGreeting()
	{
		return "Hi, I'm Jacky's ChatBot. I can tell you about mechanical keyboards. You should first find out what type of switches you prefer.";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		
		if (statement.length() == 0)
		{
			response = "Hey, you should try asking me about keyboard switches";
		}
		else if (findKeyword(statement, "cheap") >= 0)
		{
			response = "There are plenty of cheap keyboards out there. Here is a recommendation. \n" + getRandomCheap();
		}
		else if (findKeyword(statement, "switches") >= 0)
		{
			response = "There are many different switch types. Here are a few examples that I can tell you about : \n Red \n Brown \n Black \n Blue \n Green \n Clear \n Topre";
		}
		else if(findSwitch(statement)) 
		{
			if(userInput.contains(statement) == false)
			{
				response = switchExplain(statement);
				userInput.add(statement);
			}
			else
			{
				emotion--;
				return "Come on! You asked about that earlier already!";
			}
		}
		else if(findKeyword(statement, "small") >= 0)
		{
			response = "If you want a small keyboard, you should look into purchasing a 60%. Here is a recommendation \n" + getRandomSmall();
		}
		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			userWantto.add(statement);
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			userWant.add(statement);
			response = transformIWantStatement(statement);
		}	
		else if (findKeyword(statement, "keyboard",0) >= 0)
		{
			response = "Here's a keyboard I recommend : " + getRandomKb();
		}
		else if((emotion < 0) && (findKeyword(statement, "sorry") >= 0 ))
		{
			emotion++;
			response = "Alright, I'll forgive you.";
		}
		else if((emotion >= 0) && (findKeyword(statement, "thank you") >= 0 ))
		{
			emotion++;
			response = "Your welcome. Anything else you want to know?";
		}
		/*else if((count % 3 == 0) && (userWant.length > 0) )
		{
			response = "";
		}
		*/
		else 
		{
			response = getRandomResponse();
		}
		//System.out.println(userInput);
		return response;
	}
	
	public boolean findSwitch(String statement)
	{
		for(int i = 0; i < switchColors.length; i++)
		{
			if(findKeyword(statement, switchColors[i]) >= 0)
			{
				theSwitch = switchColors[i];
				return true;
			}
		}
		return false;
	}
	
	
	/*public String askQuestion(String statement)
	 * 
	{
		for(int i = 0; i < userWant.length; i++)
		{
			if(findKeyword(statement, switchColors[i]) >= 0)
			{
				theSwitch = switchColors[i];
				return true;
			}
		}
		return false;
	}
	*/
	// System.out.println("Do you want to know about other color switches? \n black \n brown \n blue \n green \n clear");
	public String switchExplain(String statement)
	{
		String explain = "";
		if(findKeyword(theSwitch, "red") >= 0)
		{
			explain = "Red switches are light, linear, non-clicky. They have an actuation force of 45 cN.";
		}
		else if(findKeyword(theSwitch, "blue") >= 0)
		{
			explain = "Black switches are medium stiff, linear, non-clicky. They have an actuation force of 60 cN.";
		}
		else if(findKeyword(theSwitch, "brown") >= 0)
		{
			explain = "Brown switches are light, tactile, non-clicky. They have an actuation force of 45 cN.";
		}
		else if(findKeyword(theSwitch, "blue") >= 0)
		{
			explain = "Blue switches are light, tactile, clicky. They have an actuation force of 50 cN.";
		}
		else if(findKeyword(theSwitch, "green") >= 0)
		{
			explain = "Green switches are heavy stiff, tactile, clicky. They have an actuation force of 80 cN.";
		}
		else if(findKeyword(theSwitch, "clear") >= 0)
		{
			explain = "Clear switches are medium stiff, tactile, non-clicky. They have an actuation force of 55 cN";
		}
		else if(findKeyword(theSwitch, "topre") >= 0)
		{
			explain = "Topre switches are electrostatic capacitive non-contact keyboard switch.";
		}
		return explain;
	}
	
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "Why do you want to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want to " + restOfStatement + "?";
	}

	
	/**
	 * Take a statement with "I want <something>." and transform it into 
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWantStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would you really be happy if you had " + restOfStatement + "?";
	}
	
	
	/**
	 * Take a statement with "I <something> you" and transform it into 
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIYouStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou = findKeyword (statement, "you", psnOfI);
		
		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
		return "Why do you " + restOfStatement + " me?";
	}
	

	
	
	/**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal,
			int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in
		// the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a
		// word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after
			// the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've
			// found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
											// letter
					&& ((after.compareTo("a") < 0) || (after
							.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find
			// the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

		}

		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse ()
	{
		Random r = new Random ();
		if (emotion == 0)
		{	
			return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 0)
		{	
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}	
		return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
	}
	
	private String getRandomCheap()
	{
		Random cheap = new Random ();
		return recoCheap [cheap.nextInt(recoCheap.length)];
	}	
	
	private String getRandomSmall()
	{
		Random small = new Random ();
		return recoSmall [small.nextInt(recoSmall.length)];
	}	
	
	private String getRandomKb()
	{
		Random kb = new Random ();
		return recoKb [kb.nextInt(recoKb.length)];
	}	
}
