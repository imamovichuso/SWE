package at.ac.univie.swe.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import at.ac.univie.swe.graph.Edge;
import at.ac.univie.swe.graph.Graph;
import at.ac.univie.swe.graph.GraphUtils;
import at.ac.univie.swe.graph.Vertex;
import at.ac.univie.swe.model.Board;
import at.ac.univie.swe.model.Field;
import at.ac.univie.swe.model.Game;
import at.ac.univie.swe.model.Game.Status;
import at.ac.univie.swe.model.Player;
import at.ac.univie.swe.model.Row;
import at.ac.univie.swe.repository.GameRepository;

@Service
@Transactional
public class GameService {

	private static final Logger logger = LoggerFactory.getLogger(GameService.class);

	@Autowired
	GameRepository gameRepository;

	public List<Game> findAll() {
		return gameRepository.findAll();
	}

	public Game findOne(@PathVariable Long id) {
		return gameRepository.findOne(id);
	}

	public Game create() {

		Game currentGame = current();
		if (currentGame != null) {
			throw new IllegalStateException("There is already a game that is not finished: " + currentGame.getId());
		}

		// create game
		Game game = new Game();
		game = gameRepository.save(game);
		// create board
		Board board = new Board();
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
			logger.info("Trying board: ");
			printBoard(board);
		} while (!game.getBoard().isOk(player1.getCastlePosition()));

		// place players and their respective gold
		game.placePlayersAndGold();

		return game;
	}

	public Game current() {
		// There should be ONLY ONE game that has status STARTED !!!
		return gameRepository.findOneByStatus(Status.STARTED);
	}
	

	/* HELPERS */
	private static void printBoard(Board board) {
		for (Row row : board.getRows()) {
			String rowString = "";
			for (Field field : row.getFields()) {
				if (field.getType().isGrass())
					rowString += "G";
				else if (field.getType().isMountain())
					rowString += "M";
				else if (field.getType().isWater())
					rowString += " ";
			}
			logger.info(rowString);
		}
	}
}
