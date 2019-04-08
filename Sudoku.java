import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Sudoku {

	private int mtx[][];
	private int solutions;
	
	public Sudoku(){
		mtx = new int[10][10];
		solutions = 0;
	}
	
	public Sudoku(int x[][]){
		mtx = x;
		solutions = 0;
	}

	public Vector<Integer> buildVector(int i,int j,int[][] x){
		
		Vector<Integer> v = new Vector<Integer>();
		int a;
		
		//System.out.print("i="+i+" j="+j+": ");
		//feltoltom a vektort az osszes lehetseges szammal (1..9)
		for(a = 1; a<=9; a++)
			v.add(a);
		//torlom azokat amelyikeket nem irhatom be, sor/oszlop szerint
		for(a = 1; a<=9; a++) {
			v.remove((Integer)x[a][j]);
			v.remove((Integer)x[i][a]);
		}
		//torlom azokat amelyikeket nem irhatom be, kis negyzet szerint
		for(a = 3*((i-1)/3) + 1; a <= 3*((i-1)/3) + 3; a++) {
			for(int b = 3*((j-1)/3) + 1; b <= 3*((j-1)/3) + 3; b++) {
				v.remove((Integer)x[a][b]);
			}
		}
		
		return v;
	}

	//Vector<Vector<Integer>> sort by inner vector length
	public void sortVector(Vector<Vector<Integer>> vec) {
		
		Collections.sort(vec, new Comparator<Vector<Integer>>(){
		    public int compare(Vector<Integer> v1, Vector<Integer> v2) {
		    	if(v1.size() == v2.size())
		    		return 0;
		    	if (v1.size() < v2.size())
		    		return -1;
		    	else
		    		return 1;
		    }
		});
	}

	public int minVectorIndex(Vector<Vector<Integer>> vec) {
		
		int min = 11;
		int mini = 0;
		for (int i = 0; i < vec.size(); i++) {
			if (vec.elementAt(i).size() < min) {
				min = vec.elementAt(i).size();
				mini = i;
			}
		}
		return mini;
	}
	
	public void solve() {
		solve(mtx);
	}
	
	@SuppressWarnings("unchecked")
	public int solve(int[][] x) {

		Vector<Vector<Integer>> vv = new Vector<Vector<Integer>>();
		Vector<Integer> v = new Vector<Integer>();

		int last = 99;

		while(last > counter(x)) {

			if (vv.size() > 0) vv.removeAllElements();
			last = counter(x);
			
			for (int i = 1; i <= 9; i++) {
				for (int j = 1; j <= 9; j++) {
					if (x[i][j] == 0) {

						v = buildVector(i,j,x);

						//ha az adott poziciora nincs egy lehetseges elem se
						if(v.size() == 0) {
							return 0;
						}
						//ha az adott poziciora egy lehetseges elem van
						if (v.size() == 1) {
							x[i][j] = v.get(0);
						}else{ //ha tobb eltaroljuk mind
							v.add(i);
							v.add(j);
							vv.addElement((Vector<Integer>) v.clone());
						}
						v.removeAllElements();
					}
				}
			}
		}
		
		if (counter(x) == 0) {
			mtx = x;
			return 1;
		}

		sortVector(vv);
		int badItems = 0;

		Vector<Integer> tmp = new Vector<Integer>();
		tmp = (Vector<Integer>) vv.elementAt(0).clone();
		
		int i =  tmp.elementAt(tmp.size()-2);
		int j =  tmp.elementAt(tmp.size()-1);
		
		while (badItems < tmp.size() - 2) {

			x[i][j] = tmp.elementAt(badItems);

			int tmpMtx[][] = new int[10][10];
			
			for (int i1 = 1; i1 <= 9; i1++) {
				for (int j1 = 1; j1 <= 9; j1++) {
					tmpMtx[i1][j1] = x[i1][j1];
				}
			}
			
			if (solve(tmpMtx) == 1) {
				solutions++;
				badItems++;
				x[i][j] = 0;
				//return 1;
			}else {
				badItems++;
				x[i][j] = 0;
			}
		}
		
		return 0;
		
	}

	public int counter(int[][] mat) {
		
		int nullC = 0;
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				if (mat[i][j] == 0) {
					nullC++;
				}
			}
		}
		return nullC;
	}

	public void write(int x[][]){

		//matrix kiiras
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				System.out.print(x[i][j] + " ");
				if (j % 3 == 0) 
					System.out.print(" ");
			}
			if (i % 3 == 0) 
				System.out.println();
			System.out.println();
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

	public void read(String line){
		
		//stringbol matrix
		System.out.println(line)
		;
		for(int i = 0; i < line.length(); i++) {

			Character digit = line.charAt(i);
			
			if (Character.isDigit(digit) == true)
				mtx[i/9+1][i%9+1] = Character.getNumericValue(digit);
			else
				mtx[i/9+1][i%9+1] = 0;
		}
	}
	
	public int getSolutions(){
		return solutions;
	}
	
	public int[][] getMtx(){
		return mtx;
	}
}