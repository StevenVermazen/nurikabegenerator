/**
 * Copyright (C) 2008 by Manuel Nogales Tejado
 * nogales.manuel@gmail.com
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA  02111-1307, USA.
 */ 
/**
 * Created on 02/12/2008
 */
package mnt.nurikabe;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel Nogales Tejado
 * 
 */
public class Board {
	public static void main(String[] args) throws MalformedPathException {
		new Board(10, 10);
	}

	private static final int WATER_PERCENT = 60;
	private int[][] board;
	private int m;
	private int n;

	public Board(Board board) {
		this.board = board.board;
	}

	public Board(int m, int n) throws MalformedPathException {
		this.m = m;
		this.n = n;
		generate();
	}

	private void generate() throws MalformedPathException {
		board = new int[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = Math.random() > (WATER_PERCENT / 100D) ? 0 : 1;
			}
		}

		// print();
		//System.out.println();
		
		Point p;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				p = partOfBlock(i, j);
				if (board[i][j] == 1 && p != null) {
					breakBlock(i, j, p.x, p.y);
				}
			}
		}

		print();

		List<Path> pathList = getWaterPath();

		while (!pathList.isEmpty()) {
			Path path = pathList.remove(0);
			System.out.println(path);
			System.out.println("********************************************");
		}
	}

	private List<Path> getWaterPath() throws MalformedPathException {
		List<Water> freeNodes = new ArrayList<Water>();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 1)
					freeNodes.add(new Water(i, j));
			}
		}

		List<Path> paths = new ArrayList<Path>();

		while (!freeNodes.isEmpty()) {
			Water water = freeNodes.get(0);
			paths.add(constructPath(water, freeNodes));
		}

		return paths;
	}

	private Path constructPath(Water water, List<Water> freeNodes)
			throws MalformedPathException {
		Path p = new Path(water);
		freeNodes.remove(water);

		int i = 0;
		int count = 0;
		while (!freeNodes.isEmpty() && freeNodes.size() > i && count < 4) {
			Water w = freeNodes.get(i);
			if (water.isAdjacent(w)) {
				p.add(constructPath(w, freeNodes));
				i--;
				count++;
			}
			i++;
		}

		return p;
	}

	public void print() {
		System.out.println(this);
	}

	public String toString() {
		String s = "";

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				s += board[i][j] + ((j < (n - 1)) ? " " : "");
			}
			s += "\n";
		}

		return s;
	}

	private void breakBlock(int i1, int j1, int i2, int j2) {
		int rand = (int) Math.ceil((Math.random() * 4));

		switch (rand) {
		case 1:
			board[i1][j1] = 0;
			break;
		case 2:
			board[i2][j2] = 0;
			break;
		case 3:
			board[i1][j2] = 0;
			break;
		case 4:
			board[i2][j1] = 0;
			break;
		}
	}

	private Point partOfBlock(int i, int j) {
		Point no = new Point(i - 1, j - 1);
		Point n = new Point(i - 1, j);
		Point ne = new Point(i - 1, j + 1);
		Point e = new Point(i, j + 1);
		Point se = new Point(i + 1, j + 1);
		Point s = new Point(i + 1, j);
		Point so = new Point(i + 1, j - 1);
		Point o = new Point(i, j - 1);

		if (isIn(no.x, no.y) && board[no.x][no.y] == 1 && board[n.x][n.y] == 1
				&& board[o.x][o.y] == 1)
			return no;
		if (isIn(ne.x, ne.y) && board[ne.x][ne.y] == 1 && board[n.x][n.y] == 1
				&& board[e.x][e.y] == 1)
			return ne;
		if (isIn(se.x, se.y) && board[se.x][se.y] == 1 && board[s.x][s.y] == 1
				&& board[e.x][e.y] == 1)
			return se;
		if (isIn(so.x, so.y) && board[so.x][so.y] == 1 && board[s.x][s.y] == 1
				&& board[o.x][o.y] == 1)
			return so;
		return null;
	}

	private boolean isIn(int i, int j) {
		return i < m && i >= 0 && j < n && j >= 0;
	}
}
