import java.util.Random;
/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Mr. Levin
 * @version September 2017
 * Started
 */
public class ChatBotZilong
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time. hi
	int emotion = 0;
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hi! I'm Zilong's Chatbot! What do you need to know about headphones?";
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
			response = "Would you like to know about Headphones?";
		}
		
		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Then I can't help you";
                	emotion--;
		}
		// buying/purchase
		else if (findKeyword(statement, "buy") >= 0)
		{
			response = "What type of headphones would you like to purchase? There are a variety of headphones ranging from budget headphones to more pricey ones.";
					emotion++;
		}
		else if (findKeyword(statement, "purchase") >= 0)
		{
			response = "What type of headphones would you like to purchase? There are a variety of headphones ranging from budget/cheaper headphones to more pricey ones.";
					emotion++;
		}
		// headphones 
		else if (findKeyword(statement, "headphones") >= 0)
		{
			response = "Would you like help with your current headphones or assistance purchasing new ones?";
					emotion++;
		}
		// cheap
		else if (findKeyword(statement, "cheap") >= 0)
		{
			response = "It seems you're running on a budget but there are many great headphones that are cheap such as" + getRandomBudgetHeadphones();
					emotion++;
		}
		else if (findKeyword(statement, "budget") >= 0)
		{
			response = "Some budget headphones that I recommend are" + getRandomBudgetHeadphones();
					emotion++;
		}
		// Expensive/good/pricey
		else if (findKeyword(statement, "Expensive") >= 0)
		{
			response = "There are many expensive headphones such as" + getRandomExpensiveHeadphones();
					emotion++;
		}
		else if (findKeyword(statement, "good") >= 0)
		{
			response = "There are many good headphones such as" + getRandomExpensiveHeadphones();
					emotion++;
		}
		else if (findKeyword(statement, "pricey") >= 0)
		{
			response = "There are many pricey headphones such as" + getRandomExpensiveHeadphones();
					emotion++;
		}
		// Gaming
		else if (findKeyword(statement, "gaming") >= 0)
		{
			response = "Some gaming headphones I would recommend are ";
					emotion++;
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
		
		return response;
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
	
	private String [] randomNeutralResponses = {"Interesting, tell me more",
			"Do you need help with anything?",
			"What headphones are you planning to purchase?",
			"What headphones are you looking for?",
			"",
			"",
			""
	};
	private String [] randomAngryResponses = {"Do you need anything???", "Hurry up please.", "I don't have all day. What do you need help with?"};
	private String [] randomHappyResponses = {"It's nice talking to you", "Today is a good day", "You make me feel like a brand new pair of shoes."};
	
	private String getRandomBudgetHeadphones()
	{
		Random r = new Random();
		return RandomBudgetHeadphones [r.nextInt(RandomBudgetHeadphones.length)];
	}
	
	private String getRandomExpensiveHeadphones()
	{
		Random r = new Random();
		return RandomExpensiveHeadphones [r.nextInt(RandomExpensiveHeadphones.length)];
	}
	
	private String getRandomGamingHeadphones()
	{
		Random r = new Random();
		return RandomGamingHeadphones [r.nextInt(RandomGamingHeadphones.length)];
	}
	
	private String[] RandomBudgetHeadphones = { 
			"https://www.amazon.com/Skullcandy-Uproar-Headphones-Built-Remote/dp/B00TS1B8XK/ref=sr_1_14?ie=UTF8&qid=1507651425&sr=8-14&keywords=budget+headphones",
			"https://www.amazon.com/Skullcandy-Uproar-Bluetooth-Wireless-Headphones/dp/B01BPFDTDS/ref=sr_1_15?ie=UTF8&qid=1507651425&sr=8-15&keywords=budget+headphones",
			"https://www.amazon.com/Labvon-Bluetooth-Headphones-Cancelling-Wireless/dp/B074RF9WGJ/ref=sr_1_16?ie=UTF8&qid=1507651425&sr=8-16&keywords=budget+headphones"
		};
		private String[] RandomExpensiveHeadphones = {
			"https://www.amazon.com/Beats-Solo-Wired-Ear-Headphone/dp/B010KJDJC6/ref=sr_1_1_sspa?s=electronics&ie=UTF8&qid=1507651704&sr=1-1-spons&keywords=beats+headphones&psc=1",
			"https://www.amazon.com/Sennheiser-HD-598-Cs-Headphone/dp/B01JP436TS/ref=sr_1_1?s=electronics&ie=UTF8&qid=1507651722&sr=1-1&keywords=senhizer+headphones",
			"https://www.amazon.com/Skullcandy-Bluetooth-Wireless-Headphones-Black/dp/B00NCSIN4W/ref=sr_1_7?s=electronics&ie=UTF8&qid=1507651740&sr=1-7&keywords=skullcandy+wireless+headphones"
		};
		private String[] RandomGamingHeadphones = {
				"https://www.amazon.com/Razer-Wireless-Surround-Compatible-Playstation/dp/B01DPS4QQ2/ref=sr_1_1?s=electronics&ie=UTF8&qid=1507651762&sr=1-1&keywords=razer+wireless+headphones",
				"https://www.amazon.com/Razer-Kraken-7-1-Chroma-RZ04-02060100-R3U1/dp/B06XR3NCP4/ref=sr_1_1_sspa?s=electronics&ie=UTF8&qid=1507651778&sr=1-1-spons&keywords=razer+headset&psc=1&smid=AESX3141EPI7X",
				"https://www.amazon.com/Sennheiser-GAME-ONE-Gaming-Headset/dp/B00KK8ZLEC/ref=sr_1_1_sspa?s=electronics&ie=UTF8&qid=1507651807&sr=1-1-spons&keywords=gaming+headset&psc=1"
			};
}
