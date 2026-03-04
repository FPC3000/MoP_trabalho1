package tp1.Revisoes;

import java.util.Scanner;
import java.util.Arrays;

/**
 * Class referent to A2
 * @author Filipe Craveiro | 53564
 */

public class GestaoHorarios {
	static Scanner input = new Scanner(System.in);
	static AuxiliaryMethods auxMethods = new AuxiliaryMethods();
	
	/**
	 * Takes user input and returns a positive integer between a set maximum and minimum.
	 * Makes sure the user input is valid by catching NumberFormatException.
	 * Displays an 'error' message for invalid inputs.
	 * 
	 * @param 	input	Scanner object
	 * @param 	max		set maximum
	 * @param	min		set minimum
	 * @return			positive integer inputed by user
	 */
	static int getInt(Scanner input, int max, int min) {
		int num;
		
		while (true) {
			try {
				num = Integer.parseInt(input.nextLine());
				while (num < min || num > max) {
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
	
	/**
	 * Allows the creation of a 'Subject' object with:
	 * a set name,
	 * a set day of the week (1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday),
	 * a set starting hour (24h format, example: 9 = 9h00, 14 = 14h00),
	 * and a set duration (1, 2 or 3 hours).
	 */
	static class Subject {
		public String subjectName;
		public int subjectDay, subjectStart, subjectDuration;
		
		public Subject(String name, int day, int start, int duration) {
			subjectName = name;
			subjectDay = day;
			subjectStart = start;
			subjectDuration = duration;
		}
		
		public String getName() {
			return subjectName;
		}
		
		public int getDay() {
			return subjectDay;
		}
		
		public int getStart() {
			return subjectStart;
		}
		
		public int getDuration() {
			return subjectDuration;
		}
	}
	
	/**
	 * Takes user input about each individual subject
	 * (name, day of the week, starting hour, duration)
	 * and returns every created object in an array.
	 * 
	 * @param 	subjects	array of subjects to fill in
	 * @return				filled array of subjects
	 */
	static Subject[] setSubjects(Subject[] subjects) {
		
		String name;
		int day, start, duration;
		
		for (int i = 0; i < subjects.length; i += 1) {
			System.out.print("\n\n-|==  SUBJECT #" + (i + 1) + "  ==|-\n"
						   + "  >  Subject Name: ");
			name = auxMethods.getString(input);
			
			System.out.print("  >  Day of the Week: ");
			day = getInt(input, 5, 1);
			
			System.out.print("  >  Starting Hour: ");
			start = getInt(input, 20, 8);
			
			System.out.print("  >  Class Duration: ");
			duration = getInt(input, 3, 1);
			
			subjects[i] = new Subject(name, day, start, duration);
		}
		
		return subjects;
	}
	
	/**
	 * Takes the subjects array and sorts it twice using bubble sort.
	 * @see <a href="https://www.w3schools.com/dsa/dsa_algo_bubblesort.php">Code taken from HERE</a>
	 * 
	 * First, the array is sorted by starting hour.
	 * Then, it is sorted by day of the week.
	 * From testing, this appears to be the best way to do it.
	 * 
	 * @param 	subjects	array of subjects to sort
	 * @return				sorted array of subjects
	 */
	static Subject[] bubbleSort(Subject[] subjects) {
		boolean swapped;
		Subject temp;
		
		for (int i = 0; i < subjects.length - 1; i += 1) {
		     swapped = false;
		    for (int j = 0; j < (subjects.length - i - 1); j += 1) {
		        if (subjects[j].getStart() > subjects[j + 1].getStart()) {
		        	temp = subjects[j];
		        	subjects[j] = subjects[j + 1];
		        	subjects[j + 1] = temp;
		            swapped = true;
		        }
		    }
		    if (!swapped) {
		        break;
		    }
		}
		
		for (int i = 0; i < (subjects.length - 1); i += 1) { // Sorting by day of the week
		     swapped = false;
		    for (int j = 0; j < (subjects.length - i - 1); j += 1) {
		        if (subjects[j].getDay() > subjects[j+1].getDay()) {
		        	temp = subjects[j];
		        	subjects[j] = subjects[j+1];
		        	subjects[j+1] = temp;
		            swapped = true;
		        }
		    }
		    if (!swapped) {
		        break;
		    }
		}
		
		return subjects;
	}
	
	/**
	 * Displays the sorted array of subjects as a schedule,
	 * visalized in a hierarchic structure.
	 * 
	 * @param 	subjects	sorted array of subjects to display
	 * @param 	weekDays	String array with the days of the week
	 */
	static void displaySchedule(Subject[] subjects, String[] weekDays) {
		int lastIndex = 0;
		
		System.out.println("===  SCHEDULE  ===");
		
		for (String d : weekDays) {
			System.out.println("\n   |- " + d + " -|:");
			for (int i = lastIndex; i < subjects.length; i += 1) {
				if (subjects[i].getDay() - 1 == Arrays.asList(weekDays).indexOf(d)) {
					System.out.println("  -> " + subjects[i].getName() + " (" + subjects[i].getStart() +"h00)");
					lastIndex += 1;
					continue;
				}
			}
		}
		
		input.nextLine();
	}
	
	/**
	 * Checks for overlapping subjects in the same day and displays them.
	 * When there is no overlapping, an appropriate message is displayed.
	 * 
	 * @param 	subjects 	sorted array of subjects to check
	 * @param 	weekDays	String array with the days of the week
	 */
	static void overlapping(Subject[] subjects, String[] weekDays) {
		boolean overlap = false;
		
		System.out.println("===  OVERLAPPING  ===");
		
		for (int i = 0; i < 5; i += 1) {
			for (int j = 0; j < subjects.length - 1; j += 1) {
				if (subjects[j].getDay() == i + 1 && subjects[j + 1].getDay() == i + 1) {
					if (subjects[j].getStart() + subjects[j].getDuration() > subjects[j + 1].getStart()) {
						overlap = true;
						System.out.println("(" + weekDays[i] + ") " + subjects[j].getName() + " overlaps with " + subjects[j + 1].getName());
					}
				}
			}
		}
		
		if (!overlap) {
			System.out.println("\n|-  There is no overlapping!  -|");
		}
		
		input.nextLine();
	}
	
	/**
	 * Calculates and displays the weekly stats regarding the schedule.
	 * The total 'hours per day' is stored in an array:
	 * 
	 * max >	largest value from the array
	 * min >	smallest (non zero) value from the array
	 * med >	average of the array's values
	 * total >	total sum of the array's values
	 * 
	 * It's then used 'maxIndex' and 'minIndex' to display the correct day of the week for max and min respectively.
	 * 
	 * @param 	subjects	sorted array of subjects to analyze
	 * @param 	weekDays	String array with the days of the week
	 */
	static void getStats(Subject[] subjects, String[] weekDays) {
		int[] dailyHours = new int[5];
		for (Subject s : subjects) {
			dailyHours[s.getDay() - 1] += s.getDuration();
		}
		
		int max, min, total;
		float med;
		max = total = 0;
		min = 3;
		for (int i : dailyHours) {
			if (i > max) {
				max = i;
			}
			if (i < min && i != 0) {
				min = i;
			}
			total += i;
		}
		med = total / 5.00f;
		
		int maxIndex, minIndex;
		maxIndex = minIndex = 0;
		for (int i = 0; i < 5; i += 1) {
			if (dailyHours[i] == max) {
				maxIndex = i;
			}
			if (dailyHours[i] == min) {
				minIndex = i;
			}
		}
		
		System.out.println("-|  ============= STATS =============  |-\n"
				 		 + "\n  -> Day with MOST Hours: " + weekDays[maxIndex] + "\n"
				 		 + "\n  -> Day with LEAST Hours: " + weekDays[minIndex] + "\n"
				 		 + "\n  -> AVERAGE Hours per Day: " + med + "\n"
				 		 + "\n  -> TOTAL Number of Hours: " + total + "\n"
				 		 + "\n-|  =================================  |-");
		
		input.nextLine();
	}
	
	/**
	 * Allows the user to search the name of a created subject and retrieve its data.
	 * Displays all subject which contain the searched term in their name.
	 * Returns an appropriate message for when no entries are found.
	 * 
	 * @param 	subjects	sorted array of subjects to search
	 * @param 	weekDays	String array with the days of the week
	 */
	static void searching(Subject[] subjects, String[] weekDays) {
		String search;
		
		System.out.print("===  SEARCH SUBJECT  ===\n\n"
					   + "  > ");
		search = auxMethods.getString(input);
		
		auxMethods.clearScreen();
		System.out.println("--=|  ENTRIES FOUND  |=--\n");
		
		boolean found = false;
		for (Subject s : subjects) {
			if (s.getName().toLowerCase().contains(search.toLowerCase())) {
				found = true;
				System.out.println("\n  » Name: " + s.getName()
								 + "\n  » Day: " + weekDays[s.getDay() - 1]
								 + "\n  » Start: " + s.getStart() + "h00"
								 + "\n  » Duration: " + s.getDuration() + "h");
			}
		}
		
		if (!found) {
			System.out.println("\n|-  No entries found!  -|");
		}
		
		input.nextLine();
	}
	
	/**
	 * Main method.
	 * Contains the text interface for accessing the various functions of the program.
	 * 
	 * @param 	args
	 */
	public static void main(String[] args) {
		auxMethods.clearScreen();
		
		int numSubjects;
		String[] weekDays = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
		
		/**
		 * Gets user input for the number of subjects to create and manage.
		 */
		System.out.print("|===  How many SUBJECTS to manage?  ===|\n"
				   + "  -> ");
		numSubjects = getInt(input, 10, 1);
		
		/**
		 * Creates the subjects array, gets the filled array and then sorts it.
		 */
		Subject[] subjects = new Subject[numSubjects];
		setSubjects(subjects);
		bubbleSort(subjects);
		
		/**
		 * Main program loop.
		 */
		boolean running = true;
		String choice;
		while (running) {
			auxMethods.clearScreen();
			
			System.out.println("\n           ___  \n"
							 + "     /\\   |__ \\ \n"
							 + "    /  \\     ) |\n"
							 + "   / /\\ \\   / / \n"
							 + "  / ____ \\ / /_ \n"
							 + " /_/    \\_\\____|\n"
							 + "                \n"
							 + "                "
							 + "\n[1] - View Full Schedule"
			 		 		 + "\n[2] - Detect Overlapping"
			 		 		 + "\n[3] - Weekly Stats"
			 		 		 + "\n[4] - Search\n"
			 		 		 + "\n[0] - Quit");
			
			choice = input.nextLine();
			switch(choice) {
			case "1":
				auxMethods.clearScreen();
				displaySchedule(subjects, weekDays);
				break;
			case "2":
				auxMethods.clearScreen();
				overlapping(subjects, weekDays);
				break;
			case "3":
				auxMethods.clearScreen();
				getStats(subjects, weekDays);
				break;
			case "4":
				auxMethods.clearScreen();
				searching(subjects, weekDays);
				break;
			case "0":
				running = false;
			default:
				auxMethods.clearScreen();
			}
		}
	}

}