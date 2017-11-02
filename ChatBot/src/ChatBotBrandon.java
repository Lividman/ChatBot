import java.util.Random;

public class ChatBotBrandon 
{
	//The chatbot will either be in a rock paper scissors state or a computer state, where you either play rock paper scissors or talk about computers
	String state = "";
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
		/**
		 * Get a default greeting 	
		 * @return a greeting
		 */	
		public String getGreeting()
		{
			return "Yo wassup B, u wanna play a game or do you want to talk about computers?";
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
			String response = "Hmm, i dont quite understand what you said, could you rephrase it?";
			
			if (statement.length() == 0)
			{
				response = "Hey man I can only respond if you say something";
			}
			else if (findKeyword(statement, "computer") >=0)
			{
				response = "Alright, lets talk about computers then";
			}
			else if (findKeyword(statement, "Game") >= 0)
			{
				response = "Alright, lets play rock paper scissors, let me warn you though, I'm quite good. When you want to stop, type done";
	            state = "game";
	        }
			if(state == "game" && (findKeyword(statement, "rock") >=0 || findKeyword(statement, "paper") >=0 || findKeyword(statement, "scissors") >=0))
			{
				response = rps(statement);
			}
			if(state == "game" && findKeyword(statement, "done") >=0  )
			{
				state = "everythingelse";
				response = "Lets talk about computers now!";
			}	
			
			if (findKeyword(statement, "What is", 0) >= 0)
			{
				response = transformWhatis(statement);
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
		private String transformWhatis(String statement)
		{
			statement = statement.trim();
			String lastChar = statement.substring(statement
					.length() - 1);
			if (lastChar.equals("?"))
			{
				statement = statement.substring(0, statement
						.length() - 1);
			}
			int psn = findKeyword (statement, "What is");
			String rest = statement.substring(psn + 7);
			return rest + " is probably something you can google";
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
		
		private String rps(String statement)
		{
			Random rn = new Random();
			int r = (int) (Math.random() * (3 - 0)) + 0;
			String response = "";
			int[] num = {1,2,3};
			int ans = num[r];
			
			if (findKeyword(statement, "scissors") >= 0)
			{
				if(ans == 0)
				{
				 response = "Rock! Ha, I win!";
				}
				if(ans == 1)
				{
				 response = "Paper! Dang, I lost";
				}
				if(ans == 2)
				{
				response = "Scissors!We tied... are you cheating?";
				}
			}
			if (findKeyword(statement, "Paper") >= 0)
			{
				if(ans == 0)
				{
					response = "Rock! Ah, well, paper beats rock.";
				}
				if(ans == 1)
				{
					response = "Paper! A tie? alright fine.";
				}
				if(ans == 2)
				{
					response ="Scissors!A win's a win, sorry bud.";
				}
			}
			if (findKeyword(statement, "rock") >= 0)
			{
				if(ans == 0)
				{
					response = "Rock! Ah, a tie! ";
				}
				if(ans == 1)
				{
					response ="Paper! A win, rock paper scissors is my life";
				}
				if(ans == 2)
				{
					response = "Scissors!Ah! You crushed my scissors :(";
				}
			}
			return response;
			
		}

		/**
		 * Pick a default response to use if nothing else fits.
		 * @return a non-committal string
		 */
		private String getRandomResponse ()
		{
			Random r = new Random ();
			return randomComputerResponses [r.nextInt(randomComputerResponses.length)];
		}	
		
		private String [] randomNeutralResponses = {"Interesting, tell me more",
				"Hmmm.",
				"Do you really think so?",
				"You don't say.",
				"It's all boolean to me.",
				"So, would you like to go for a walk?",
				"Could you say that again?"
		};
		private String [] randomComputerResponses = {"Computers are nice!", "There are computers that use themselves out there!","Almost everything is a computer nowadays!"};
		
		
}
