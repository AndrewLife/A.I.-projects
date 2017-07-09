/**
 * 
 * @author Andrew Fung
 * HopField Neural Network, recognize patterns and their inverses that have been trained 
 *
 */
public class Hopfield {
	private int [][] weightMatrix;
	
	/**
	 * Set the size of the Matrix to represent the number of neurons in a layer
	 * @param size, number of neurons
	 */
	public Hopfield(final int size) {
		this.weightMatrix = new int[size][size];
	}
	
	/**
	 * Get the Weight Matrix
	 * @return
	 */
	public int[][] getMatrix() {
		return this.weightMatrix;
	}
	
	/**
	 * Get the number of neurons (number of columns) in weight matrix
	 * @return
	 */
	public int getSize() {
		return this.weightMatrix.length;
	}
	
	/**
	 * Test weight metrix to see if it recognizes the pattern
	 * @param pattern
	 */
	public void present(final int[] pattern) {
		final boolean output[] = new boolean[pattern.length];
		
		//Convert boolean to bipolar values
		for(int i = 0; i < pattern.length; i++) {
			if(pattern[i] == 0) {
				pattern[i] = -1;
			}
		}
		
		
		for(int col = 0; col < getSize(); col++) {
			int [] getCol = new int[getSize()];
			int[] transposeMatrix = new int[getCol.length];
			for(int i = 0; i < getCol.length; i++) {
				getCol[i] = this.weightMatrix[col][i];
				transposeMatrix[i] = getCol[i];
			}
			
			double dotProduct = 0;
			for( int i = 0; i < getSize(); i++) {
				dotProduct+= pattern[i]*transposeMatrix[i];
			}
			
			if(dotProduct>0) {
				output[col] = true;
			}
			else {
				output[col] = false;
			}
		}
		
		for(int i = 0; i < output.length; i++) {
			System.out.print(output[i] + " ");
		}
	}
	
	public void train(final int[] pattern) {
		if(pattern.length != getSize()) {
			System.out.println("I can't train with pattern size of " + pattern.length + "on a Hopfield size of " + getSize());
		}
		else {
			for(int i = 0; i < pattern.length; i++) {
				if(pattern[i] == 0) {
					pattern[i] = -1;
				}
			}
			
			int [] transposeMatrix = new int[pattern.length];
			for(int i = 0; i < transposeMatrix.length; i++) {
				transposeMatrix[i] = pattern[i];
				
			}
			
			int[][] multiplyMatrix = new int[pattern.length][transposeMatrix.length];
			for(int index1 = 0; index1 < pattern.length; index1++) {
				for(int index2 = 0; index2 < transposeMatrix.length; index2++) {
					multiplyMatrix[index1][index2] = pattern[index1] * transposeMatrix[index2];
				}
			}
			
			for(int i = 0; i < multiplyMatrix.length; i++) {
				multiplyMatrix[i][i] --;
			}
			
			for(int i = 0; i < getSize(); i++) {
				for(int j = 0; j < multiplyMatrix.length; j++) {
					this.weightMatrix[i][j] = this.weightMatrix[i][j] + multiplyMatrix[i][j];
				}
			}
			
			
		}
	}
	
	
public static void main(String[]args) {
	Hopfield field = new Hopfield(4);
	int [] array1 = {0,1,0,1};
	int [] array2 = {0,0,1,1};
	int [] array3 = {1,1,1,1};
	int [] array4 = {1,0,1,0};
	field.train(array1);
	field.present(array1);
	System.out.println();
	field.train(array2);
	field.present(array2);
	System.out.println();
	field.present(array3);
	System.out.println();
	field.present(array1);
	System.out.println();
	field.present(array4);
	
}
}