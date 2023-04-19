public class board {

    private int[][] board = new int[8][5];
    private int[][] checkingBoard = new int[8][5];
    private int[][] previousBoard = new int[8][5];
    int previousNum =0;
    private int powerCounter = 1;
    board() //umplerea tabelei cu zero-uri
    {
        zeroBoard();
    }
    public void zeroBoard() //metoda de umplere a tabelei cu zerouri
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = 0;
            }
        }
    }
    public int[][] getBoard()
    {
        return board;
    }
    public int randomize()  // genereaza valoare la intamplare
    {
        int[] randomBlocks = new int[6];
        int val=powerCounter+5;
        if(getMaxValue()>=Math.pow(2,val+7))
        {
            powerCounter++;
            blockRemover();
            zeroRemover();
        }
        for (int i = 0; i < 6; i++) {
            randomBlocks[i] = (int) Math.pow(2,powerCounter);
            powerCounter++;
        }
        powerCounter-=6;
        int rand = (int) (Math.random() * (randomBlocks[5] - randomBlocks[0] + 1) + randomBlocks[0]);
        while(rand!=randomBlocks[0] && rand!=randomBlocks[1] && rand !=randomBlocks[2] && rand !=randomBlocks[3] && rand !=randomBlocks[4] && rand !=randomBlocks[5])
        {
            rand = (int) (Math.random() * (randomBlocks[5] - randomBlocks[0] + 1) + randomBlocks[0]);
        }
        return rand;
    }
    public void push(int rand, int x) { //ia input si il pune pe tabela

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                previousBoard[i][j]=board[i][j];
            }
        }
        previousNum = rand;
        for (int i = 0; i < 8; i++) {
            if (board[i][x - 1] == 0) {
                board[i][x - 1] = rand;
                break;
            }
        }
    }
    public boolean isBoardFull(int rand){
        for (int i = 0; i < 5; i++) {
            if (board[6][i] == 0 || board[6][i] == rand) {
                return false;
            }
        }
        return true;
    }
    public boolean isColumnEmpty(int x, int rand )  // Column full checker
    {
        if (board[6][x - 1] == 0 || board[6][x-1] == rand)
            return true;
        return false;
    }
    private void currentColumnSimilarityChecker(int x)
    {
    		//Similaritate in stanga, dreapta si sus
            for (int i = 7; i >= 0; i--) {
                if(i - 1 >= 0 && x > 1 && x < 5 && board[i][x - 1] == board[i][x - 2] && board[i][x - 1] == board[i][x] && board[i][x - 1] == board[i - 1][x - 1]) 
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                    board[i][x - 2] = 0;
                    board[i - 1][x - 1] = 0;

                }
                else if (i - 1 >= 0 && x > 1 && board[i][x - 1] == board[i][x - 2] && board[i][x - 1] == board[i - 1][x - 1]) //Similarity in left triangle of blocks checker and handler
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 2] = 0;
                    board[i - 1][x - 1] = 0;
                }
              //Similaritate in triunghi dreapta
                else if (x < 5 && i - 1 >= 0 && board[i][x - 1] == board[i][x] && board[i][x - 1] == board[i - 1][x - 1]) 
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                    board[i - 1][x - 1] = 0;
                }
              //Similaritate in doua linii
                else if (i - 1 >= 0 && board[i][x - 1] == board[i - 1][x - 1]) { 
                    board[i - 1][x - 1] += board[i - 1][x - 1];
                    board[i][x - 1] = 0;
                }
              //Similaritate in 3 coloane checker and handler
                else if (x < 5 && x > 1 && board[i][x - 1] == board[i][x] && board[i][x - 1] == board[i][x - 2]) 
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                    board[i][x - 2] = 0;
                }
              //Similaritate in coloana din dreapta
                else if (x < 5 && board[i][x - 1] == board[i][x]) {  
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x] = 0;
                } else if (x > 1 && board[i][x - 1] == board[i][x - 2]) //Similarity in left column checker and handler
                {
                    board[i][x - 1] += board[i][x - 1];
                    board[i][x - 2] = 0;
                }
            }
    }
    private void similarityChecker() {  //Toate cazurile de similaritate
        for (int j = 5; j >=1; j--) {
            for (int i = 7; i >= 0; i--) {
                if (i - 1 >= 0 && board[i][j - 1] == board[i - 1][j - 1]) { //doua randuri
                    board[i - 1][j - 1] += board[i - 1][j - 1];
                    board[i][j - 1] = 0;
                }
                else if (j < 5 && board[i][j - 1] == board[i][j]) {  //coloana din dreapta
                    board[i][j - 1] += board[i][j - 1];
                    board[i][j] = 0;
                } else if (j > 1 && board[i][j - 1] == board[i][j - 2]) //coloana din stanga
                {
                    board[i][j - 1] += board[i][j - 1];
                    board[i][j - 2] = 0;
                }
            }
        }
    }
    private void zeroRemover() // verifica zerourile si le sterge
    {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5 ; j++) {
                if (i - 1 >= 0 && board[i - 1][j] == 0 && board[i][j] != 0) {
                    board[i - 1][j] = board[i][j];
                    board[i][j] = 0;
                }
            }
        }
    }
    private void blockRemover()
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                if(board[i][j]<Math.pow(2,powerCounter))
                {
                    board[i][j]=0;
                }
            }
        }
    }
    public void fullChecker(int x) //reverificare
    {

        currentColumnSimilarityChecker(x);
        zeroRemover();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                checkingBoard[i][j]=board[i][j];
            }
        }
        similarityChecker();
        zeroRemover();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 5; j++) {
                    if(checkingBoard[i][j]!=board[i][j])
                    {
                        fullChecker(x);
                        break;
                    }
                    else {}
                }
            }
    }
    public int getMaxValue()  // valoarea maxima de pe tabela
    {
        int maxValue = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                if(board[i][j] >= maxValue )
                    maxValue = board[i][j];
            }
        }
        return maxValue;
    }
    public void display()  // afiseaza tabela
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(board[i][j] + "\t\t");
            }
            System.out.println();
        }
    }
    public Memento createMemento() {
    	Memento memento = new Memento(board);
    	return memento;
    }
    
    public void restore(Memento memento) {
    	this.board = memento.getBoard();
    }
    
    public void undo()
    {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j]= previousBoard[i][j];
            }
        }
    }
}