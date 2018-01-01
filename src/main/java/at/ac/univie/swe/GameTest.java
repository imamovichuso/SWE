package at.ac.univie.swe;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import at.ac.univie.swe.graph.Distance;
import at.ac.univie.swe.graph.DistanceSelector;
import at.ac.univie.swe.graph.Graph;
import at.ac.univie.swe.graph.GraphUtils;
import at.ac.univie.swe.graph.Path;
import at.ac.univie.swe.graph.Vertex;
import at.ac.univie.swe.model.Board;
import at.ac.univie.swe.model.Field;
import at.ac.univie.swe.model.Game;
import at.ac.univie.swe.model.Player;
import at.ac.univie.swe.model.Row;

/*
 * Playground for testing game behaviour
 */
public class GameTest {

	public static void main(String[] args) {
		// create game
		Game game = new Game();
		// create board
		// Board board = new Board(4, 3, 1, 1);
		Board board = new Board(8, 3, 3, 4);
		game.setBoard(board);
		// create players
		Player player1 = new Player(true, board);
		Player player2 = new Player(false, board);
		game.setPlayer1(player1);
		game.setPlayer2(player2);

		/* initialization */
		// populate board
		do {
			board.init();
			player1.populateBoardHalf();
			player2.populateBoardHalf();
			printBoard(board);
		} while (!board.isOk(player1.getCastlePosition()));

		game.placePlayersAndGold();

		// finding ALL shortest paths
		System.out.println("P1 " + player1);
		Graph graph = GraphUtils.newGraph(board);
		Map<DistanceSelector, Distance> distances = GraphUtils.shortestDistances(graph);

		// finding path PLAYER->CASTLE
		Vertex p1Vert = new Vertex(player1.getPosition());
		Vertex p1CastleVert = new Vertex(player1.getCastlePosition());

		Set<Vertex> grassVertices = new HashSet<>();
		for (Vertex vertex : graph.getVertices()) {
			Field f = vertex.getField();
			if (f.getType().isGrass() && f.getRow() < board.getSize() / 2) {
				grassVertices.add(vertex);
			}
		}

		Path playerToCastle = GraphUtils.pathThroughAllGrass(p1Vert, p1CastleVert, grassVertices, distances);
		System.out.println("Through grass:" + playerToCastle);

		System.out.println("Player to castle (straight) " + GraphUtils.path(p1Vert, p1CastleVert, distances));
	}

	private static void printBoard(Board board) {
		for (Row row : board.getRows()) {
			for (Field field : row.getFields()) {
				if (field.getType().isGrass()) {
					System.out.print("G");
				} else if (field.getType().isMountain()) {
					System.out.print("M");
				} else if (field.getType().isWater()) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
		System.out.println("******************************************");
	}
}
