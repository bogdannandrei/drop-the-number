
public class score {
	
	public int value;
	
	score(){
		value = 0;
	}
	public score(int value) {
		this.value = value;
	}
	
	public void setNull() {
		value = 0;
	}
	
	public void updatePlus(int x) {
		value +=x;
	}
}
