import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
	public static void main(String[] agrv) throws IOException{

		BufferedReader br = new BufferedReader(new FileReader("sudoku.txt"));
		String line = br.readLine();
		int a = 0;
		long startTime = System.currentTimeMillis();
		Sudoku sudoku;
		
		while(line != null) {
			sudoku = new Sudoku();
			sudoku.read(line);
			sudoku.write();
			sudoku.solve();
			
			if(sudoku.getSolutions() == 0) {
				System.out.println("Nincs megoldas!");
			}
			else {
				System.out.println("Megoldva:");
				//System.out.println(sudoku.getSolutions());
				a++;
			}
			
			sudoku.write();
			line = br.readLine();
		}

		System.out.println("Megoldottak szama: "+a);

		long endTime = System.currentTimeMillis();
		System.out.println("Took "+((endTime - startTime)) + "ms");
		br.close();

		new SudokuGen();
	}
}