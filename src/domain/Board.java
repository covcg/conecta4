package domain;

import java.io.Serializable;
import java.util.TreeMap;

public class Board implements Serializable {
	private boolean turn;
	private int nTurn;
	private boolean win;
	private final TreeMap<Integer,Token> shots;
	private final int[] cells;
	private final int fils;
	private final int cols;

	public Board(int fils, int cols) {
		this.shots = new TreeMap();
		this.cells = new int[fils*cols];
		int len = cells.length;
		if (len > 0){
			cells[0] = 0;
		}
		for (int i = 1; i < len; i += i) {
			System.arraycopy(cells, 0, cells, i, ((len - i) < i) ? (len - i) : i);
		}
		this.fils = fils;
		this.cols = cols;
		this.turn = false;
		this.win = false;
	}

	public void addToken(int col) {
		if (!win && nTurn <= 42) {
			int fil = 0;
			/*
			while (shots.containsKey(col*fils + fil)) fil++;
			int left = shots.containsKey((col - 1) * fils + fil) && shots.get((col - 1) * fils + fil).type == turn ? shots.get((col - 1) * fils + fil).left + 1 : 0;
			int right = shots.containsKey((col + 1) * fils + fil) && shots.get((col + 1) * fils + fil).type == turn ? shots.get((col + 1) * fils + fil).right + 1 : 0;
			int bottom = shots.containsKey(col * fils + fil - 1) && shots.get(col * fils + fil - 1).type == turn ? shots.get(col * fils + fil - 1).bottom + 1: 0;
			int slashUp = shots.containsKey((col - 1) * fils + fil + 1) && shots.get((col - 1) * fils + fil + 1).type == turn ? shots.get((col - 1) * fils + fil + 1).slashUp + 1 : 0;
			int slashDown = shots.containsKey((col + 1) * fils + fil - 1) && shots.get((col + 1) * fils + fil - 1).type == turn ? shots.get((col + 1) * fils + fil - 1).slashDown + 1 : 0;
			int dSlashUp = shots.containsKey((col + 1) * fils + fil + 1) && shots.get((col + 1) * fils + fil + 1).type == turn ? shots.get((col + 1) * fils + fil + 1).dSlashUp + 1 : 0;
			int dSlashDown = shots.containsKey((col - 1) * fils + fil - 1) && shots.get((col - 1) * fils + fil - 1).type == turn ? shots.get((col - 1) * fils + fil - 1).dSlashDown + 1 : 0;
			if (shots.containsKey((col - 1) * fils + fil)) shots.get((col - 1) * fils + fil).right += right + 1;
			if (shots.containsKey((col + 1) * fils + fil)) shots.get((col + 1) * fils + fil).left += left + 1;
			if (shots.containsKey((col - 1) * fils + fil + 1)) shots.get((col - 1) * fils + fil + 1).slashDown += slashDown + 1;
			if (shots.containsKey((col + 1) * fils + fil - 1)) shots.get((col + 1) * fils + fil - 1).slashUp += slashUp + 1;
			if (shots.containsKey((col + 1) * fils + fil + 1)) shots.get((col + 1) * fils + fil + 1).dSlashDown += dSlashDown + 1;
			if (shots.containsKey((col - 1) * fils + fil - 1)) shots.get((col - 1) * fils + fil - 1).dSlashUp += dSlashUp + 1;
			if (fil < fils) shots.put(col*fils + fil, new Token(turn,left, right, bottom, slashUp, slashDown, dSlashUp, dSlashDown));
			/*/
			while (cells[col*fils + fil] != 0) fil++;
			if (turn && fil < fils) cells[col*fils + fil] = 1;
			else if (fil < fils) cells[col*fils + fil] = 2;
			//*/
			turn = !turn;
			this.checkAdd(fil, col);
		}
	}

	public void checkAdd(int fil, int col) {
		//*
		int a = 0;
		int b = 0;
		if (fil >= 3) for (int i = 0; i <= fil && !win; i++) {
			switch (cells[col * fils + i]) {
				case 0:
					a = 0;
					b = 0;
					break;
				case 1:
					a++;
					b = 0;
					break;
				case 2:
					b++;
					a = 0;
					break;
			}
			if (a == 4) this.win = true;
			else if (b == 4) this.win = true;
		}
		a = 0;
		b = 0;
		if ((cols - col >= 4 && col + 1 < cols && cells[(col + 1) * fils + fil] != 0) ||
		(col + 1 >= 4 && col - 1 > 0 && cells[(col - 1) * fils + fil] != 0)) {
			for (int i = 0; i < cols && !win; i++) {
				switch (cells[i * fils + fil]) {
					case 0:
						a = 0;
						b = 0;
						break;
					case 1:
						a++;
						b = 0;
						break;
					case 2:
						b++;
						a = 0;
						break;
				}
				if (a == 4) this.win = true;
				else if (b == 4) this.win = true;
			}
		}

		a = 0;
		b = 0;
		int aux = fil < col ? fil : col;
		int aux2 = fils - fil < cols - col ? fils - fil : cols - col;
		for (int i = 0; i < aux + aux2 && !win; i++) {
			switch (cells[(col - aux + i) * fils + fil - aux + i]) {
				case 0:
					a = 0;
					b = 0;
					break;
				case 1:
					a++;
					b = 0;
					break;
				case 2:
					b++;
					a = 0;
					break;
			}
			if (a == 4) this.win = true;
			else if (b == 4) this.win = true;
		}

		a = 0;
		b = 0;
		aux = fil < cols - 1 - col ? fil : cols - 1 - col;
		aux2 = col < fils - fil ? col + 1 : fils - fil;
		for (int i = 0; i < aux + aux2 && !win; i++) {
			switch (cells[(col + aux - i) * fils + fil - aux + i]) {
				case 0:
					a = 0;
					b = 0;
					break;
				case 1:
					a++;
					b = 0;
					break;
				case 2:
					b++;
					a = 0;
					break;
			}
			if (a == 4) this.win = true;
			else if (b == 4) this.win = true;
		}
		/*/
		System.out.println("f: " + fil + ", c: " + col);
		if (shots.get(col * fils + fil).left + shots.get(col * fils + fil).right + 1 > 3) this.win = true;
		else if (shots.get(col * fils + fil).slashUp + shots.get(col * fils + fil).slashDown + 1 > 3) this.win = true;
		else if (shots.get(col * fils + fil).dSlashUp + shots.get(col * fils + fil).dSlashDown + 1 > 3) this.win = true;
		else if (shots.get(col * fils + fil).bottom + 1 > 3) this.win = true;
		//*/
	}

/*
	public void removeToken(int fil, int col) {
		int cell;
		int i = 1;
		do {
			cell = col * fils + fil + i;
			cells[cell - 1] = cells[cell];
			i++;
		} while (cell % fils != 0 && cells[cell] != 0);
		this.checkRemove(fil, col);
	}

	public void checkRemove(int fil, int col) {
		int lenght = 0;
		while (cells[col*fils + lenght] != 0) lenght++;
		int a = 0;
		int b = 0;
		for (int i = 0; i < lenght && !win; i++) {
			if (cells[col*fils + i] == 1) {
				a++;
				b = 0;
			} else if (cells[col*fils + i] == 1) {
				b++;
				a = 0;
			} else {
				a = 0;
				b = 0;
			}
			if (a == 4) {
				win = true;
				turn = false;
			}
			else if (b == 4) {
				win = true;
				turn = true;
			}
		}
		for (int i = fil; i < lenght && !win; i++) {
			int aux1 = col - i < 0 ? 0 : col - 1;
			for (int j = 0; j < lenght && !win; j++) {

			}
		}
	}
//*/

	public boolean [] getWinner() {
		boolean [] res =  {this.win, this.turn};
		return res;
	}

	public int getFils() {
		return this.fils;
	}

	public int getCols() {
		return this.cols;
	}

	public int [] getCells() {
		return this.cells;
	}

	public TreeMap<Integer, Token> getShots() {
		return this.shots;
	}
}
