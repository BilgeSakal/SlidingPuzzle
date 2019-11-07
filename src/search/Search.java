package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import game.Move;

public abstract class Search {

	protected Move start;
	protected Move goal;

	public Search(Move start, Move goal) {
		this.start = start;
		this.goal = goal;
	}

	/**
	 * Finds the solution for the puzzle and updates parentMap
	 * 
	 * @param parentMap that will keep the path.
	 * @return <tt>true</tt> if the puzzle is solved.
	 */
	public abstract boolean search(HashMap<Move, Move> parentMap);

	/**
	 * Find the path from start to goal.
	 * 
	 * @return The list of moves from start to goal.
	 */
	public List<Integer> solve() {
		if (start.equals(goal)) {
			return new ArrayList<Integer>();
		}
		HashMap<Move, Move> parentMap = new HashMap<>();
		boolean found = search(parentMap);
		if (!found)
			return new LinkedList<>();
		List<Integer> path = constructPath(parentMap);
		return path;
	}

	/**
	 * Constructs the path from start to goal.
	 * 
	 * @param parentMap The paths that traveled.
	 * @return The List of moves from start to goal.
	 */
	private List<Integer> constructPath(HashMap<Move, Move> parentMap) {
		LinkedList<Integer> path = new LinkedList<>();
		Move curr = goal;
		Move prev = null;
		while (!curr.equals(start)) {
			if (prev != null) {
				int dir = Move.findMovedDirection(curr, prev);
				path.addFirst(dir);
			}
			prev = curr;
			curr = parentMap.get(curr);
		}
		path.addFirst(Move.findMovedDirection(curr, prev));
		return path;
	}

}
