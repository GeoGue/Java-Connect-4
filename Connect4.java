// Program:	Connect4 (Week 15? Assignment 5)
// Written by:	Gina Guerra
// Program Description:	Runs a program to allow the user to play connect 4 against a simple AI
// File name:	Connect4.java
// File description: Runs a program to allow the user to play connect 4 against a simple AI and allows the user to continue playing upon winning/losing
// Other files in this project: Connect4Skynet.java
// Challenges: Writing AI for the first time is difficult and I now understand why Skynet went evil in the Terminator
// Time Spent:	actually coding? ~7 hours
// 			  	coding + thinking ~ 15 hours total
//
//               Revision History
// Date:                   By:                  Action:
// ---------------------------------------------------
// 12/20/20			    gg       	coded everything
// 12/22/20			    gg       	added comments
// 12/23/20				gg			fixed issue with playing a new game, removed commented out code to make it neater

import java.util.Scanner;

public class Connect4 {
	final static String TITLE = "Connect4"; // title of program
	static boolean repeat = true; // boolean to be used in main
	static Connect4Skynet skynet = new Connect4Skynet();

	public static String[][] createBoard() {		//create connect 4 grid/board

		String[][] board = new String[7][15];	//7 vertical spaces, 15 horizontal spaces (7 spaces and 8 "walls")

		for (int i = 0; i < board.length; i++) {

			for (int j = 0; j < board[i].length; j++) {
				if (j % 2 == 0) {
					board[i][j] = "|";
				} else {
					board[i][j] = " ";
				}

				if (i == 6)
					board[i][j] = "-";
			}

		}
		return board;
	}

	public static void printBoard(String[][] board) {			//display grid/board in console
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	public static void dropRedDisk(String[][] board) {			//allows red disk player to make a move
		System.out.print("Drop a red disk at column (0-6): ");
		Scanner sc = new Scanner(System.in);
		int c = 2 * sc.nextInt() + 1;
		if(c < 0 || c > 15){									//if they go out of bounds, give them a chance to make a real move
			System.out.print("That is not a valid column; please try again");
			dropRedDisk(board);
		}
		else{
		for (int i = 5; i >= 0; i--) {
			if (board[i][c] == " ") {
				board[i][c] = "R";
				break;
			}
			if(i == 0 && board[i][c] != " "){					//if they go out of bounds, give them a chance to make a real move
				System.out.println("That is not a valid column; please try again");
				dropRedDisk(board);
			}
		}
		}
	}

	public static void dropYellowDisk(String[][] board) {	
		skynet.makeMove(board);
	}

	public static String checkWinner(String[][] board) {		//checks if win conditions have been met to end the game
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j += 2) {
				if ((board[i][j + 1] != " ") && (board[i][j + 3] != " ") && (board[i][j + 5] != " ")
						&& (board[i][j + 7] != " ") && ((board[i][j + 1] == board[i][j + 3])
								&& (board[i][j + 3] == board[i][j + 5]) && (board[i][j + 5] == board[i][j + 7])))

					return board[i][j + 1];
			}
		}

		for (int i = 1; i < 15; i += 2) {
			for (int j = 0; j < 3; j++) {
				if ((board[j][i] != " ") && (board[j + 1][i] != " ") && (board[j + 2][i] != " ")
						&& (board[j + 3][i] != " ") && ((board[j][i] == board[j + 1][i])
								&& (board[j + 1][i] == board[j + 2][i]) && (board[j + 2][i] == board[j + 3][i])))
					return board[j][i];
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 1; j < 9; j += 2) {
				if ((board[i][j] != " ") && (board[i + 1][j + 2] != " ") && (board[i + 2][j + 4] != " ")
						&& (board[i + 3][j + 6] != " ")
						&& ((board[i][j] == board[i + 1][j + 2]) && (board[i + 1][j + 2] == board[i + 2][j + 4])
								&& (board[i + 2][j + 4] == board[i + 3][j + 6])))
					return board[i][j];
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 7; j < 15; j += 2) {
				if ((board[i][j] != " ") && (board[i + 1][j - 2] != " ") && (board[i + 2][j - 4] != " ")
						&& (board[i + 3][j - 6] != " ")
						&& ((board[i][j] == board[i + 1][j - 2]) && (board[i + 1][j - 2] == board[i + 2][j - 4])
								&& (board[i + 2][j - 4] == board[i + 3][j - 6])))
					return board[i][j];
			}
		}
		
		return null;
	}

	public static void main(String[] args) {
		System.out.println("Welcome to " + TITLE); // prints out title of program
		String[][] board = createBoard();			//creates board
		printBoard(board);							//prints board
		int count = 0;								//keeps track of turns
		Scanner sc = new Scanner(System.in);
		while (repeat) {							//make sure user wants to keep playing
			if (count % 2 == 0)
				dropRedDisk(board);	//player goes first
			else
				dropYellowDisk(board);	//AI goes next
			count++;
			printBoard(board);
			if (checkWinner(board) != null) {		//check to see if there's a winner (it's connect 4, not connect 7)
				if (checkWinner(board) == "R") {
					System.out.println("Red won.");
					System.out.println("Do you want to try again? 1.yes 2.no"); // give user the option to play another round.
					int lastPrompt = sc.nextInt();
					if (lastPrompt == 2)
						repeat = false; // no means no
					else{
						for (int i = 0; i < board.length; i++) {

							for (int j = 0; j < board[i].length; j++) {
								if (j % 2 == 0) {
									board[i][j] = "|";
								} else {
									board[i][j] = " ";
								}
								if (i == 6)
									board[i][j] = "-";
							}
						}
						repeat = true; // tried making it repeat, couldn't get the board to wipe. This is my solution.
					}
				} else if (checkWinner(board) == "Y") {
					System.out.println("Yellow won.");
					System.out.println("Do you want to try again? 1.yes 2.no"); // give user the option to play another round.
					int lastPrompt = sc.nextInt();
					if (lastPrompt == 2)
						repeat = false; // no means no
					else{
						for (int i = 0; i < board.length; i++) {

							for (int j = 0; j < board[i].length; j++) {
								if (j % 2 == 0) {
									board[i][j] = "|";
								} else {
									board[i][j] = " ";
								}
								if (i == 6)
									board[i][j] = "-";
							}
						}
						repeat = true; // tried making it repeat, couldn't get the board to wipe. This is my solution.
					}
				}
			}

		}

		sc.close();
		System.out.println("Thank you for using " + TITLE); // thanks user for using the program
	}

}