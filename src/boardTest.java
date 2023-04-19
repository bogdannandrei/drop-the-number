import java.util.Arrays;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

public class boardTest {
	@Test
	public void emptyBoardTest() {
		board x = new board();
		x.zeroBoard();
		int[][] x1 = new int[8][5];
		for(int i = 0 ; i<8 ; i++){
			for (int j = 0 ; j<5 ; j++) {
				x1[i][j] = 0;
			}
	}
		Assert.assertEquals(Arrays.equals(x.getBoard(), x1),false); 
}
	@Test
	public void pushTest() {
		
		board x = new board();
		x.zeroBoard();
		x.push(2,1);
		int[][] xTest = new int[8][5];
		for(int i = 0 ; i < 8 ; i++) {
			for(int j = 0 ; j < 5 ; j++) {
				if(i==0 && j==0) {
					xTest[i][j]=2;
				}
				else
					xTest[i][j]=0;
			}
		}
		Assert.assertEquals(Arrays.equals(x.getBoard(), xTest),false); 
	}
	
	@Test
	public void mergeCheck() {
		board x = new board();
		x.push(2,1);
		x.push(2, 2);
		x.fullChecker(1); x.fullChecker(2);
		int[][] xTest = new int[8][5];
		for(int i = 0 ; i < 8 ; i++) {
			for(int j = 0 ; j < 5 ; j++) {
				if(i==0 && j==0) {
					xTest[i][j]=4;
				}
				else
					xTest[i][j]=0;
			}
		}
		Assert.assertEquals(Arrays.equals(x.getBoard(), xTest),false); 
	}
}
