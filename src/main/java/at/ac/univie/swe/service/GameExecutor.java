package at.ac.univie.swe.service;

import java.util.HashSet;
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
		Map<DistanceSelector, Distance> allShortestDistances = GraphUtils.shortestDistances(graph);

		Player player1 = game.getPlayer1();
		Vertex p1Vert = new Vertex(player1.getPosition());
		Vertex p1CastleVert = new Vertex(player1.getCastlePosition());

		Set<Vertex> grassVertices = new HashSet<>();
		for (Vertex vertex : graph.getVertices()) {
			Field f = vertex.getField();
			if (f.getType().isGrass() && f.getRow() < board.getSize() / 2) {
				grassVertices.add(vertex);
			}
		}

		Path playerToCastle = GraphUtils.pathThroughAllGrass(p1Vert, p1CastleVert, grassVertices, allShortestDistances);
		for (Vertex v : playerToCastle.getVertices()) {
			// move player1
			player1.setPosition(v.getField());
			// gameRepository.save(game);
			playerRepository.save(player1);
			Thread.sleep(999); // wait one second
		}
	}

}
