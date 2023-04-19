public class Memento{
	 private int[][] board = new int[8][5];
	 private int[][] checkingBoard = new int[8][5];
	    private int[][] previousBoard = new int[8][5];
	    int previousNum =0;
	    private int powerCounter = 1;
    	
    public Memento(int[][] boardToSave) {
    	this.board = boardToSave;
    	this.checkingBoard = checkingBoard;
    	this.previousBoard = previousBoard;
    	this.previousNum = previousNum;
    	this.powerCounter = powerCounter;
    }
    
    int[][] getBoard() {
    	return board;
    }

	public int[][] getCheckingBoard() {
		return checkingBoard;
	}

	public void setCheckingBoard(int[][] checkingBoard) {
		this.checkingBoard = checkingBoard;
	}

	public int[][] getPreviousBoard() {
		return previousBoard;
	}

	public void setPreviousBoard(int[][] previousBoard) {
		this.previousBoard = previousBoard;
	}

	public int getPreviousNum() {
		return previousNum;
	}

	public void setPreviousNum(int previousNum) {
		this.previousNum = previousNum;
	}

	public int getPowerCounter() {
		return powerCounter;
	}

	public void setPowerCounter(int powerCounter) {
		this.powerCounter = powerCounter;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
}    
