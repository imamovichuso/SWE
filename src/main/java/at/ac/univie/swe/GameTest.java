package at.ac.univie.swe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.ac.univie.swe.graph.Edge;
import at.ac.univie.swe.graph.Graph;
import at.ac.univie.swe.graph.GraphUtils;
import at.ac.univie.swe.graph.Distance;
import at.ac.univie.swe.graph.DistanceSelector;
import at.ac.univie.swe.graph.Vertex;
import at.ac.univie.swe.model.Board;
import at.ac.univie.swe.model.Field;
import at.ac.univie.swe.model.Game;
import at.ac.univie.swe.model.Player;
import at.ac.univie.swe.model.Row;

public class GameTest {

	public static void main(String[] args) {
		// create game
		Game game = new Game();
		// create board
		// Board board = new Board(4, 3, 1, 1);
		Board board = new Board(8, 5, 3, 9);
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
			game.placePlayersAndGold();
			printBoard(board);
		} while (!board.isOk(player1.getCastlePosition()));

		// finding ALL shortest paths
		System.out.println("P1 " + player1);
		Graph graph = GraphUtils.newGraph(board);
		Map<DistanceSelector, Distance> distances = GraphUtils.shortestDistances(graph);
		System.out.println(distances);
		// finding path PLAYER->CASTLE
		Vertex p1Vert = new Vertex(player1.getPosition());
		Vertex p1CastleVert = new Vertex(player1.getCastlePosition());
		List<Vertex> playerToCastle = GraphUtils.path(p1Vert, p1CastleVert, distances);
		System.out.println("PLAYER TO CASTLE:" + playerToCastle);
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