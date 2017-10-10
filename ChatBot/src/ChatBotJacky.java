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
	private String [] randomNeutralResponses = {"Keyboards are essential things", "Hello", "What are you looking for"};
	private String [] randomAngryResponses = {"You're testing my patience!", ""};
	private String [] randomHappyResponses = {"How are you doing?", };
	private String [] switchColors = {"red", "brown", "black", "blue", "green", "clear"};
	boolean askColor;
	private ArrayList<String> userInput = new ArrayList<String>();
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
			response = "Hey, don't be so silent. Say something!";
		}
		else if (findKeyword(statement, "cheap") >= 0)
		{
			response = "There are plenty of cheap keyboards out there.";
		}
		else if (findKeyword(statement, "switches") >= 0)
		{
			response = "There are many different switch types. Here are a few examples that I can tell you about : \n Red \n Brown \n Black \n Blue \n Green \n Clear";
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
			response = "If you want a small keyboard, you should look into purchasing a 60%";
		}
		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}	
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
	// System.out.println("Do you want to know about other color switches? \n black \n brown \n blue \n green \n clear");
	public String switchExplain(String statement)
	{
		String explain = "";
		if(theSwitch.equals("red"))
		{
			explain = "Red switches are linear";
		}
		else if(theSwitch.equals("black"))
		{
			explain = "Black switches are linear";
		}
		else if(theSwitch.equals("brown"))
		{
			explain = "Brown switches are tactile";
		}
		else if(theSwitch.equals("blue"))
		{
			explain = "BLue switches are tactile";
		}
		else if(theSwitch.equals("green"))
		{
			explain = "Green switches are tactile";
		}
		else if(theSwitch.equals("clear"))
		{
			explain = "Clear switches are tactile";
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
	
}
