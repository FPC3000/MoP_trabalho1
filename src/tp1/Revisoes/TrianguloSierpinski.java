package tp1.Revisoes;

import java.util.Scanner;

/**
 * Class referent to A3
 * @author Filipe Craveiro | 53564
 */

public class TrianguloSierpinski {
	static Scanner input = new Scanner(System.in);
	static AuxiliaryMethods auxMethods = new AuxiliaryMethods();
	
	/**
	 * Displays a Sierpinski Triangle.
	 * 
	 * For size = 1, it simply display a single asterisk ('*').
	 * For size > 1, it starts by displaying the first triangle, with its center in the first row as 2^(size-1)-1.
	 * 
	 * After identifying an asterisk, it then checks for two different cases:
	 * 
	 * 	1)	Check the right diagonal in the row above (canvas[i - 1][j + 1]) for an asterisk;
	 * 		Check the next available position to the right diagonal (canvas[i - 1][j + 3]) for a space (' ');
	 * 		Check the previous available position in the original row (canvas[i][j - 2]) for an asterisk;
	 * 		Add a triangle in the row below to the left (i + 1, j - 1).
	 * 
	 * 	2)	Check the left diagonal in the row above (canvas[i - 1][j - 1]) for an asterisk;
	 * 		Check the last available position to the left diagonal (canvas[i - 1][j - 3]) for a space;
	 * 		Check the next available position in the original row (canvas[i][j + 2]) for an asterisk;
	 * 		Add a triangle in the row below to the right (i + 1, j + 1).
	 * 
	 * I came up with this algorithm by myself and do not know if there is a better one.
	 * 
	 * It then prints everything out.
	 * 
	 * @param 	canvas	2D array of characters to fill and display
	 * @param 	row		current row in the array
	 * @param 	col		current collumn in the current row in the array
	 * @param 	size	size of the sierpinskitriangle.
	 */
	static void drawSierpinski(char[][] canvas, int row, int col, int size) {
		if (size == 1) {
			System.out.println("*");
			return;
		}
		
		canvas[row][col] = '*';
		canvas[row + 1][col - 1] = canvas[row + 1][col + 1] = '*';
		
		if (row != 0) {
			return;
		}
		
		for (int i = 1; i < canvas.length - 2; i += 2) {
			for (int j = 2; j < canvas[i].length - 2; j += 1) {
				if (canvas[i][j] == '*' && (canvas[i - 1][j + 1] == '*' && canvas[i - 1][j + 3] == ' ') && canvas[i][j - 2] == ' ') {
					drawSierpinski(canvas, i + 1, j - 1, size);
				}
				if (canvas[i][j] == '*' && (canvas[i - 1][j - 1] == '*' && canvas[i - 1][j - 3] == ' ') && canvas[i][j + 2] == ' ') {
					drawSierpinski(canvas, i + 1, j + 1, size);
				}
			}
		}
		
		System.out.println("");
		
		for (int i = 0; i < canvas.length; i += 1) {
			for (int j = 0; j < canvas[i].length; j += 1) {
				System.out.print(canvas[i][j]);
			}
			System.out.println("");
		}
		return;
	}
	
	/**
	 * Main method.
	 * Contains the text interface for the program.
	 * 
	 * @param 	args
	 */
	public static void main(String[] args) {
		auxMethods.clearScreen();
		
		int size;
		
		/**
		 * Gets user input for the size of the fractal.
		 */
		System.out.print("|===  Size of the Triangle?  ===|\n"
				   + "  -> ");
		size = auxMethods.getInt(input);
		
		/**
		 * Creates the characters array and fills it with spaces (' ').
		 */
		char[][] canvas = new char[(int) Math.pow(2, size - 1)][(int) (Math.pow(2, size) - 1)];
		for (int i = 0; i < canvas.length; i += 1) {
			for (int j = 0; j < canvas[i].length; j += 1) {
				canvas[i][j] = ' ';
			}
		}
		
		drawSierpinski(canvas, 0, (int) (Math.pow(2, size - 1) - 1), size);
	}
}