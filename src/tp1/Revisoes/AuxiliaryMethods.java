package tp1.Revisoes;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class referent to the auxiliary methods used throughout teh project
 * @author Filipe Craveiro | 53564
 */

public class AuxiliaryMethods {
	/**
	 * Executes the command 'cls' on the current command line
	 * Essencially, cleans the screen.
	 */
	public void clearScreen() {
		try {
			new ProcessBuilder("cmd", "/c", "cls")
				.inheritIO()
				.start()
				.waitFor();
		} catch (IOException | InterruptedException e) {
			System.out.println("\n\n---| AN ERROR HAS OCCURRED |---\n");
		}
	}
	
	/**
	 * Takes user input and returns a non-empty string.
	 * Displays an 'error' message for invalid inputs.
	 * 
	 * @param 	input	Scanner object
	 * @return			String inputed by user
	 */
	public String getString(Scanner input) {
		String str;
		
		str = input.nextLine();
		while (str.matches("")) {
			System.out.print("--- Please type a VALID WORD ---\n"
					   	   + "  > ");
			str = input.nextLine();
		}
		
		return str;
	}
	
	/**
	 * Takes user input and returns a positive integer.
	 * Makes sure the user input is valid by catching NumberFormatException.
	 * Displays an 'error' message for invalid inputs.
	 * 
	 * @param 	input	Scanner object
	 * @return			positive integer inputed by user
	 */
	public int getInt(Scanner input) {
		int num;
		
		while (true) {
			try {
				num = Integer.parseInt(input.nextLine());
				while (num <= 0) {
					System.out.print("--- Please type a VALID NUMBER ---\n"
								   + "  > ");
					num = Integer.parseInt(input.nextLine());
				}
				break;
			} catch (NumberFormatException e) {
				System.out.print("--- Please type a VALID NUMBER ---\n"
							   + "  > ");
			}
		}
		
		return num;
	}
}