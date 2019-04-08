import java.util.Random;
import java.util.Vector;

public class SudokuGen {
	
	private int mtx[][];
	
	public SudokuGen() {
		
		mtx = new int[10][10];
		smallSquares();
		write();
		Sudoku s = new Sudoku();
		s.solve(mtx);
		mtx = s.getMtx();
		System.out.println("Megoldások: "+s.getSolutions());
		
//		write();
		
		delete();
		System.out.println("Generált: ");
		write();
		
		s = new Sudoku(mtx);
		s.solve();
		System.out.println("Megoldva: ");
		System.out.println("Megoldasok szama: " + s.getSolutions());
		mtx = s.getMtx();
		write();
	}
	
	public void delete(){
		
		Random r = new Random();
		int i,j,tmp;
		int a = 0;
		while(a < 40) {
			i = r.nextInt(9)+1;
			j = r.nextInt(9)+1;
			tmp = mtx[i][j];
			if (mtx[i][j] != 0 ) {
				
				mtx[i][j] = 0;
				
				int tmpMtx[][] = new int[10][10];
				
				for (int i1 = 1; i1 <= 9; i1++) {
					for (int j1 = 1; j1 <= 9; j1++) {
						tmpMtx[i1][j1] = mtx[i1][j1];
					}
				}
				
				Sudoku s = new Sudoku(tmpMtx);
				s.solve();
				if ( s.getSolutions() == 0) {
					a++;
				}else {
					System.out.println("Tobb megoldas: " + s.getSolutions());
					mtx[i][j] = tmp;
				}
		
			}
		}	
	}
	
	public void smallSquares(){
		
		Random r = new Random();
		
		for (int i = 1; i < 9; i=i+3) {
			
			Vector<Integer> item = new Vector<Integer>();
			for (int j = 1; j < 10; j++) {
				item.add(j);
			}
			for(int a = 3*((i-1)/3) + 1; a <= 3*((i-1)/3) + 3; a++) {
				for(int b = 3*((i-1)/3) + 1; b <= 3*((i-1)/3) + 3; b++) {
					mtx[a][b] = item.elementAt(r.nextInt(item.size()));
					item.remove((Integer)mtx[a][b]);
				}
			}
		}
	}
	
	public void write(){

		//matrix kiiras
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				System.out.print(mtx[i][j] + " ");
				if (j % 3 == 0) 
					System.out.print(" ");
			}
			if (i % 3 == 0) 
				System.out.println();
			System.out.println();
		}
	}

}
