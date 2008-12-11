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
 * Created on 04/12/2008
 */
package mnt.nurikabe;

public class Water {
	public final int i, j;

	public Water(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int distance(Water water) {
		return Math.abs(i - water.i) + Math.abs(j - water.j);
	}

	public boolean isAdjacent(Water water) {
		if (i == water.i && (j == water.j - 1 || j == water.j + 1))
			return true;
		if (j == water.j && (i == water.i - 1 || i == water.i + 1))
			return true;
		return false;
	}

	public String toString() {
		return "(" + i + ", " + j + ")";
	}
}