// Program:	Connect4 (Week 15? Assignment 5)
// Written by:	Gina Guerra
// File name:	Connect4Skynet.java
// File description: Runs an AI with the talent and strategy of a 2 year old.
// Help:	took a look at https://codereview.stackexchange.com/questions/150541/connect-four-game-with-minimax-ai
//			for ideas: a lot of it didn't make sense to my brain, so I changed up the ideas, but I kept their naming
//			for certain methods.
//               Revision History
// Date:                   By:                  Action:
// ---------------------------------------------------
// 12/20/20			    gg       	coded most of the program
// 12/22/20			    gg       	fixed an issue that made the AI try to make moves out of bounds; added comments
// 12/23/20				gg			added verticalStart method
public class Connect4Skynet {

	public void makeMove(String[][] board) {	//makes the AI make a move
		int moveMade = 0;	//keep track of it making a move so it doesn't instantly fill out the board
		while (moveMade == 0) { 
			if (board[5][7] == " ") {	//good basic first moves so it has where to start
				board[5][7] = "Y";
				moveMade++;
				break;
			}
			if (board[5][5] == " ") {	//good basic first moves so it has where to start
				board[5][5] = "Y";
				moveMade++;
				break;
			}
			for (int i = 5; i >= 0; i--) {	//go through the board to find where it already placed disks
				for (int j = 1; j < 15; j += 2) {
					if (board[i][j] == "Y") {
						if(verticalStart(i,j,board)){
							for(int k = 4; k >= 0 ; k--){
								if(board[k][j] == " "){
									board[k][j] = "Y";
									moveMade++;
									break;
								}
								if(moveMade > 0)
									break;
								if(board[k][j] == "R")
									break;
							}
						}
						if(moveMade > 0)
							break;
						if (possibleConnections(i, j, board) != 0) {
							optimalMove(possibleConnections(i, j, board), i, j, board);	//tries to make a move to place a disk adjacent to one they already placed
							//System.out.println(i +", " + (j-1)/2 + ": " + possibleConnections(i, j, board)); //here for troubleshooting reasons
							moveMade++;
							break;
						}
					}
					if (moveMade != 0)
						break;
				}
				if (moveMade != 0)
					break;
			}
		}
		if(moveMade ==0){	//if it has nowhere to place a disk adjacent to an existing one, places disk where it can
			for (int i = 5; i >= 0; i--) {
				for (int j = 1; j < 15; j += 2) {
					if(board[i][j]== " "){
						board[i][j] = "Y";
						moveMade++;
						break;
					}
					if (moveMade != 0)
						break;
				}
				if (moveMade != 0)
					break;
			}
		}

	}

	public int possibleConnections(int i, int j, String[][] board) {	//checks for available spaces adjacent to already placed disk
		int value = 0;	//needed to make sure a move is valid
		int count = 0;	//needed for switch case later
		for (int y = -1; y < 2; y++) {
			for (int x = -1; x < 2; x++) { 	//go through adjacent areas
				count++;
				value = value + lineOfFour(i, j, y, x, board);	//check to see what moves are legal and picks the first legal move available
				if (value > 1)
					break;
			}
			if (value > 1)
				break;
		}
		if (count == 9 || value ==0)	//count = 9 broke the AI, so I just removed it as an option
			return 0;
		else{
			//System.out.println(count);	//here for troubleshooting reasons
			return count;				//return value for switch case
		}
	}

	public int lineOfFour(int i, int j, int y, int x, String[][] board) { //make sure you can legally make a line of 4
		if ((y + i) < 0 || (j + x*2) < 0 || (y + i) > 5 || (j + x*2) > 15)
			return 0; // cannot do an illegal move (off board)
		if (board[y + i][j + x*2] != " ")
			return 0; // cannot do an illegal move (occupied space)
		if (board[y + i][j + x*2] == " "){
			if(y + i == 5)
				return 1;	//bottom row +empty = legal
			if(y + i < 5 && board[y + i+1][j + x*2] == " ")
				return 0; // no floating in mid-air
			if(y + i < 5 && board[y + i+1][j + x*2] != " ")
				return 1;	//not bottom row, but space below not empty = legal
		}
		return 0;
	}
	
	public boolean verticalStart(int i, int j, String[][] board){	//makes the AI play smarter: tries making a vertical line
		if(board[i-1][j] == "Y")
			return true;
		if(board[i-1][j] == "R")
			return false;
		else
			return true;
	}

	public void optimalMove(int count, int i, int j, String[][] board) {	//switch case to make a good, legal move
		switch (count) {
		case 1:
			board[i - 1][j - 2] = "Y";
			break;
		case 2:
			board[i - 1][j] = "Y";
			break;
		case 3:
			board[i - 1][j + 2] = "Y";
			break;
		case 4:
			board[i][j - 2] = "Y";
			break;
		case 6:
			board[i][j + 2] = "Y";
			break;
		case 7:
			board[i + 1][j - 2] = "Y";
			break;
		case 8:
			board[i + 1][j] = "Y";
			break;
		case 9:
			board[i + 1][j + 2] = "Y";
			break;
		default:
			break;
		}
	}
}
