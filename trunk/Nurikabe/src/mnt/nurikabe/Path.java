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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Manuel Nogales Tejado
 * 
 */
public class Path implements Iterable<Water> {
	private List<Path> childs = null;
	private Water water;

	List<Water> list;

	public Path(int i, int j) {
		water = new Water(i, j);
	}

	public Path(Water water) {
		this.water = water;
	}

	public Water getWater() {
		return water;
	}

	public void add(Path path) throws MalformedPathException {
		if (path == null)
			throw new NullPointerException("Path can't be null.");

		if (childs == null)
			childs = new ArrayList<Path>();

		if (childs.size() > 4)
			throw new MalformedPathException(
					"It can not be more than four childs.");

		childs.add(path);
	}

	public Iterator<Water> iterator() {
		return new DepthIterator();
	}

	private void fillIterator() {
		fillIterator(this);
	}

	private void fillIterator(Path path) {
		list.add(path.water);

		if (childs != null) {
			for (Path p : childs) {
				fillIterator(p);
			}
		}
	}

	public String toString(int level) {
		String s = "";
		for (int i = 0; i < level; i++) {
			s += level == i + 1 ? " |_" : " | ";
		}
		s += water + "\n";
		if (childs != null) {
			level++;
			for (Path child : childs) {
				s += child.toString(level);
			}
		}
		return s;
	}

	public String toString() {
		return toString(0);
	}

	private class DepthIterator implements Iterator<Water> {
		Iterator<Water> it;

		private DepthIterator() {
			list = new ArrayList<Water>();
			fillIterator();
			it = list.iterator();
		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public Water next() {
			return it.next();
		}

		public void remove() {}

	}
}
