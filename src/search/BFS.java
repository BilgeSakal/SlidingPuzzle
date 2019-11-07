package search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import game.MatrixManipulation;
import game.Move;

public class BFS extends Search {

	public BFS(Move start, Move goal) {
		super(start, goal);
	}

	@Override
	public boolean search(HashMap<Move, Move> parentMap) {
		Queue<Move> toExplore = new LinkedList<>();
		HashSet<Move> visited = new HashSet<>();

		toExplore.add(start);
		visited.add(start);
		boolean found = false;
		while (!toExplore.isEmpty()) {
			Move curr = toExplore.remove();
			if (curr.equals(goal)) {
				found = true;
				break;
			}
			List<Integer> moves = curr.possibleDirections();
			Iterator<Integer> itMoves = moves.iterator();
			while (itMoves.hasNext()) {
				Integer dir = itMoves.next();
				Move next = new Move(MatrixManipulation.copyMatrix(curr.getPuzzleTable()), curr.getEmptyTileLocation());
				next.moveEmptyTile(dir);
				if (!visited.contains(next)) {
					visited.add(next);
					toExplore.add(next);
					parentMap.put(next, curr);
				}
			}
		}
		return found;
	}

}