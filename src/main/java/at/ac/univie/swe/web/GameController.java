package at.ac.univie.swe.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ac.univie.swe.model.Game;
import at.ac.univie.swe.service.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {

	@Autowired
	GameService gameService;

	@GetMapping
	public List<Game> findAll() {
		return gameService.findAll();
	}

	@GetMapping("/{id}")
	public Game findOne(@PathVariable Long id) {
		return gameService.findOne(id);
	}

	@GetMapping("/create")
	public Game create() {
		return gameService.create();
	}

	@GetMapping("/current")
	public Game current() {
		return gameService.current();
	}

}
