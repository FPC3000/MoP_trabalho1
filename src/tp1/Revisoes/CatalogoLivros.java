package tp1.Revisoes;

import java.util.Scanner;
import java.time.Year;

/**
 * Class referent to A1
 * @author Filipe Craveiro | 53564
 */

public class CatalogoLivros {
	static Scanner input = new Scanner(System.in);
	static AuxiliaryMethods auxMethods = new AuxiliaryMethods();
	
	/**
	 * Takes user input and returns a positive integer no bigger then the current year.
	 * Makes sure the user input is valid by catching NumberFormatException
	 * and actually checking the current year with 'Year.now()'.
	 * Displays an 'error' message for invalid inputs.
	 * 
	 * @param 	input	Scanner object
	 * @return			valid year inputed by user
	 */
	static int getYear(Scanner input) {
		int year;
		int currentYear = Year.now().getValue();
		
		while (true) {
			try {
				year = Integer.parseInt(input.nextLine());
				while (year > currentYear || year <= 0) {
					System.out.print("--- Please type a VALID YEAR ---\n"
							   	   + "  > ");
					year = Integer.parseInt(input.nextLine());
				}
				break;
			} catch (NumberFormatException e) {
				System.out.print("--- Please type a VALID YEAR ---\n"
							   + "  > ");
			}
		}
		
		return year;
	}
	
	/**
	 * Allows the creation of a 'Book' object with:
	 * a set title,
	 * and a set publishing year.
	 */
	static class Book {
		public String bookTitle;
		public int bookYear;
		
		public Book(String title, int year) {
			bookTitle = title;
			bookYear = year;
		}
		
		public String getTitle() {
			return bookTitle;
		}
		
		public int getYear() {
			return bookYear;
		}
	}
	
	/**
	 * Takes user input about each individual book
	 * (title, publishing year)
	 * and returns every created object in an array.
	 * 
	 * @param 	books	array of books to fill in
	 * @return			filled array of books
	 */
	static Book[] setBooks(Book[] books) {
		String title;
		int year;
		
		for (int i = 0; i < books.length; i += 1) {
			System.out.print("\n\n-|==  BOOK #" + (i + 1) + "  ==|-\n"
						   + "  >  Title: ");
			title = auxMethods.getString(input);
			
			System.out.print("  >  Year: ");
			year = getYear(input);
			
			books[i] = new Book(title, year);
		}
		
		return books;
	}
	
	/**
	 * Takes the books array and sorts it using bubble sort.
	 * @see <a href="https://www.w3schools.com/dsa/dsa_algo_bubblesort.php">Code taken from HERE</a>
	 * 
	 * The books are sorted by publishing year, from oldest to most recent.
	 * 
	 * @param 	books	array of books to sort
	 * @return			sorted array of books
	 */
	static Book[] bubbleSort(Book[] books) {
		boolean swapped;
		Book temp;
		
		for (int i = 0; i < (books.length - 1); i += 1) {
		     swapped = false;
		    for (int j = 0; j < (books.length - i - 1); j += 1) {
		        if (books[j].getYear() > books[j+1].getYear()) {
		        	temp = books[j];
		        	books[j] = books[j+1];
		        	books[j+1] = temp;
		            swapped = true;
		        }
		    }
		    if (!swapped) {
		        break;
		    }
		}
		
		return books;
	}
	
	/**
	 * Calculates the stats regarding the catalogued books:
	 * 
	 * med > 		average of the publishing years
	 * bef2000 >	number of books published before the year 2000
	 * 
	 * @param 	books	sorted array of books to analyze
	 * @return			int array with calculated stats
	 */
	static int[] getStats(Book[] books) {
		int med, bef2000;
		med = bef2000 = 0;
		for (Book b : books) {
			med += b.getYear();
			if (b.getYear() < 2000) {
				bef2000 += 1;
			}
		}
		
		int[] stats = {books[0].getYear(), books[books.length - 1].getYear(), med/books.length, bef2000};
		return stats;
	}
	
	/**
	 * Checks and displays the last stat (books published in between 2010 and 2020)
	 * Displays each individual book published within the set timespan.
	 * 
	 * @param 	books	sorted array of books to analyze
	 */
	static void display2010Books(Book[] books) {
		System.out.println("\n  -> Books PUBLISHED in the 2010s:");
		for (Book b : books) {
			if (b.getYear() >= 2010 && b.getYear() < 2020) {
				System.out.println("     » Title: " + b.getTitle());
			}
		}
	}
	
	/**
	 * Main method.
	 * Contains the text interface for usage of the program.
	 * 
	 * @param 	args
	 */
	public static void main(String[] args) {
		auxMethods.clearScreen();
		
		int numBooks;
		
		/**
		 * Gets user input for the number of books to create and catalogue.
		 */
		System.out.print("|===  How many BOOKS to catalogue?  ===|\n"
					   + "  -> ");
		numBooks = auxMethods.getInt(input);
		
		/**
		 * Creates the books array, gets the filled array and then sorts it.
		 */
		Book[] books = new Book[numBooks];
		setBooks(books);
		bubbleSort(books);
		
		/**
		 * Display the final calculated stats.
		 */
		auxMethods.clearScreen();
		int[] stats = getStats(books);
		System.out.println("\n\n-|  ============= STATS =============  |-\n"
						 + "\n  -> OLDEST Publishing Year: " + stats[0] + "\n"
						 + "\n  -> MOST RECENT Publishing Year: " + stats[1] + "\n"
						 + "\n  -> AVERAGE Publishing Year: " + stats[2] + "\n"
						 + "\n  -> Books Published BEFORE 2000: " + stats[3]);
		display2010Books(books);
		System.out.println("\n-|  =================================  |-");
	}
}