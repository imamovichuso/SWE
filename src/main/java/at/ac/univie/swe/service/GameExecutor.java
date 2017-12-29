package at.ac.univie.swe.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import at.ac.univie.swe.graph.Distance;
import at.ac.univie.swe.graph.DistanceSelector;
import at.ac.univie.swe.graph.Graph;
import at.ac.univie.swe.graph.GraphUtils;
import at.ac.univie.swe.graph.Path;
import at.ac.univie.swe.graph.Vertex;
import at.ac.univie.swe.model.Board;
import at.ac.univie.swe.model.Field;
import at.ac.univie.swe.model.Game;
import at.ac.univie.swe.model.Game.Status;
import at.ac.univie.swe.model.Player;
import at.ac.univie.swe.repository.GameRepository;
import at.ac.univie.swe.repository.PlayerRepository;

@Component
public class GameExecutor {

	private static final Logger logger = LoggerFactory.getLogger(GameExecutor.class);

	@Autowired
	GameRepository gameRepository;
	@Autowired
	PlayerRepository playerRepository;

	// http://www.baeldung.com/spring-async
	// https://spring.io/guides/gs/async-method/
	@Async
	public void startGame(Game game) throws InterruptedException {
		logger.info("Starting game...");
		// finding ALL shortest paths
		Board board = game.getBoard();
		Graph graph = GraphUtils.newGraph(board);
		Map<DistanceSelector, Distance> distances = GraphUtils.shortestDistances(graph);

		Player player1 = game.getPlayer1();
		Player player2 = game.getPlayer2();

		Vertex p1StartVert = new Vertex(player1.getPosition());
		Vertex p1CastleVert = new Vertex(player1.getCastlePosition());
		Vertex p2StartVert = new Vertex(player2.getPosition());
		Vertex p2CastleVert = new Vertex(player2.getCastlePosition());

		Set<Vertex> player1GrassVertices = new HashSet<>();
		Set<Vertex> player2GrassVertices = new HashSet<>();
		for (Vertex vertex : graph.getVertices()) {
			Field f = vertex.getField();
			if (f.getType().isGrass()) {
				if (f.getRow() < board.getSize() / 2) {
					player1GrassVertices.add(vertex);
				} else {
					player2GrassVertices.add(vertex);
				}
			}
		}
		System.out.println("grass1 " + player1GrassVertices);
		System.out.println("grass2 " + player2GrassVertices);

		int numMoves = 0;
		int maxMoves = 200;
		boolean player1Won = false;
		boolean player2Won = false;
		boolean player1Move = true; // whos turn is it
		List<Vertex> player1Path = GraphUtils
				.pathThroughAllGrass(p1StartVert, p1CastleVert, player1GrassVertices, distances).getVertices();
		List<Vertex> player2Path = GraphUtils
				.pathThroughAllGrass(p2StartVert, p2CastleVert, player2GrassVertices, distances).getVertices();

		System.out.println("player1Path " + player1Path);
		System.out.println("player2Path " + player2Path);

		while (numMoves < maxMoves && !player1Won && !player2Won) {

			if (player1Move) {
				player1.setPosition(player1Path.get(0).getField());
				if (!player1.isFoundGold() && player1.getPosition().equals(board.getPlayer1GoldPosition())) {
					player1.setFoundGold(true);
					player1Path = GraphUtils
							.path(new Vertex(player1.getPosition()), new Vertex(player2.getCastlePosition()), distances)
							.getVertices();
				} else {
					player1Path.remove(0);
				}
			} else {
				player2.setPosition(player2Path.get(0).getField());
				if (!player2.isFoundGold() && player2.getPosition().equals(board.getPlayer2GoldPosition())) {
					player2.setFoundGold(true);
					player2Path = GraphUtils
							.path(new Vertex(player2.getPosition()), new Vertex(player1.getCastlePosition()), distances)
							.getVertices();
				} else {
					player2Path.remove(0);
				}
			}
			System.out.println("MOVE " + numMoves);

			numMoves++;
			player1Move = !player1Move;

			// check if someone won
			if (player1.isFoundGold() && player1.getPosition().equals(player2.getCastlePosition()))
				player1Won = true;
			if (player2.isFoundGold() && player2.getPosition().equals(player1.getCastlePosition()))
				player2Won = true;
			// check if fell in water
			if (player1.getPosition().getType().isWater())
				player2Won = true;
			if (player2.getPosition().getType().isWater())
				player1Won = true;

			playerRepository.save(player1);
			playerRepository.save(player2);
			Thread.sleep(1000); // wait one second
		}

		if (player1Won) {
			game.setStatus(Status.FINISHED_PLAYER1_WON);
		} else if (player1Won) {
			game.setStatus(Status.FINISHED_PLAYER2_WON);
		} else {
			game.setStatus(Status.FINISHED_TIE);
		}

		System.out.println("Status " + game.getStatus());
		gameRepository.save(game);

	}

}
