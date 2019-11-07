package search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import game.MatrixManipulation;
import game.Move;
import game.Point;

public class HeuristicSearch extends Search {

	public HeuristicSearch(Move start, Move goal) {
		super(start, goal);
	}

	@Override
	public boolean search(HashMap<Move, Move> parentMap) {
		PriorityQueue<Pair> toExplore = new PriorityQueue<>();
		HashSet<Pair> visited = new HashSet<>();

		Pair startPair = new Pair(start, goal);

		toExplore.add(startPair);
		visited.add(startPair);

		boolean found = false;
		while (!toExplore.isEmpty()) {
			Pair curr = toExplore.remove();
			toExplore.remove(curr);
			if (curr.move.equals(goal)) {
				found = true;
				break;
			}
			List<Integer> moves = curr.move.possibleDirections();
			Iterator<Integer> itMoves = moves.iterator();
			while (itMoves.hasNext()) {
				int dir = itMoves.next();
				Move nextMove = new Move(MatrixManipulation.copyMatrix(curr.move.getPuzzleTable()),
						curr.move.getEmptyTileLocation());
				nextMove.moveEmptyTile(dir);
				Pair next = new Pair(nextMove, goal);
				if (!visited.contains(next)) {
					visited.add(next);
					toExplore.add(next);
					parentMap.put(next.move, curr.move);
				}
			}
		}
		return found;
	}

	private class Pair implements Comparable<Pair> {
		Move move;
		Move goal;
		int heu;

		public Pair(Move move, Move goal) {
			this.move = move;
			this.goal = goal;
			this.heu = getHeuristic();
		}

		private int placedCorrectly() {
			int total = 0;
			for (int i = 0; i < move.getPuzzleTable().length; ++i) {
				for (int j = 0; j < move.getPuzzleTable()[i].length; ++j) {
					if (move.getPuzzleTable()[i][j].getIdentifier() != goal.getPuzzleTable()[i][j].getIdentifier()) {
						++total;
					}
				}
			}
			return total;
		}

		private int getHeuristic() {
			int heu = 0;
			for (int i = 0; i < move.getPuzzleTable().length; ++i) {
				for (int j = 0; j < move.getPuzzleTable()[i].length; ++j) {
					int num = move.getPuzzleTable()[i][j].getIdentifier();
					int row = num / move.getPuzzleTable()[i].length;
					int col = num % move.getPuzzleTable()[i].length;
					Point actual = new Point(row, col);
					heu += actual.dist(new Point(i, j));
				}
			}
			heu += placedCorrectly();
			return heu;
		}

		@Override
		public int hashCode() {
			return move.hashCode();
		}

		@Override
		public int compareTo(Pair o) {
			return heu - o.heu;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair p = (Pair) obj;
				return this.move.equals(p.move);
			}
			return super.equals(obj);
		}
	}

}