import java.util.Random;
/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Mr. Levin
 * @version September 2017
 */
public class ChatBotTim {
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
		int emotion = 0;
		/**
		 * Get a default greeting 	
		 * @return a greeting
		 */	
		public String getGreeting()
		{
			return "Hello! This is Tim's Chatbot! What do you want to know about computer mouses?";
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
				response = "Need help with anything?";
			}

			else if (findKeyword(statement, "no") >= 0)
			{
				response = "Then goodbye.";
			}
			
			else if (findKeyword(statement, "Tim") >= 0)
			{
				response = "Yes?";
			}
			
			else if (findKeyword(statement, "buy") >= 0)
			{
				response = "Do you want a budget mouse or a good mouse?";
			}
			
			else if (findKeyword (statement, "budget") >= 0)
			{
				response = getRandomCheapMice();
			}
			
			else if (findKeyword(statement, "good") >= 0)
			{
				response = getRandomGoodMice();
			}
			
			else if (findKeyword(statement, "help") >= 0)
			{
				response = getHelp();
			}
			else if (findKeyword(statement, "I want",0) >= 0)
			{
				response = transformIWantStatement(statement);
			}	
			else
			{
				response = getRandomCheapMice();
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
		private String getRandomCheapMice()
		{
			Random r = new Random ();
			return randomCheapMice [r.nextInt(randomCheapMice.length)];
		}
		private String getRandomGoodMice()
		{
			Random r = new Random();
			return randomMice [r.nextInt(randomMice.length)];
		}
		private String getHelp()
		{
			Random r = new Random();
			return randomHelp [r.nextInt(randomHelp.length)];
		}
		private String[] randomCheapMice = { 
			"https://www.amazon.com/VicTsing-Wireless-Portable-Receiver-Adjustable/dp/B013WC0P2A/ref=sr_1_2_sspa?ie=UTF8&qid=1507221556&sr=8-2-spons&keywords=mice&psc=1",
			"https://www.amazon.com/Logitech-800dpi-Optical-3-button-Ambidextrous/dp/B003L62T7W/ref=sr_1_5?s=pc&ie=UTF8&qid=1507221688&sr=1-5&keywords=mice",
			"https://www.amazon.com/NPET-Backlighting-High-Precision-Programmable-Professional/dp/B01HPDJ4MO/ref=sr_1_11?s=videogames&ie=UTF8&qid=1507221719&sr=1-11&keywords=mice"
		};
		private String[] randomMice = {
			"https://www.amazon.com/Logitech-Master-Wireless-Mouse-High-precision/dp/B00TZR3WRM/ref=sr_1_8?s=videogames&ie=UTF8&qid=1507221719&sr=1-8&keywords=mice",
			"https://www.amazon.com/Razer-DeathAdder-Elite-Ergonomic-Comfortable/dp/B01LXC1QL0/ref=sr_1_5?s=videogames&ie=UTF8&qid=1507221719&sr=1-5&keywords=mice",
			"https://www.amazon.com/dp/B073WGFLQY?psc=1"
		};
		private String[] randomHelp = {
			"Try smashing the mouse on the floor",
			"Try unplugging then replugging the mouse",
			"Try restarting the computer",
			"Try getting a new mouse",
			"Try replacing the batteries",
			"Try getting a new mousepad",
			"Try getting a new computer"
		};
		private String[] helpKeywords = {
			"help",
			"assistance",
			"not work",
			"doesn't work",
			"Help"
		};
		
		
}
