package at.ac.univie.swe.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.ac.univie.swe.model.Player;
import at.ac.univie.swe.repository.PlayerRepository;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

	@Autowired
	PlayerRepository playerRepository;

	@GetMapping("/")
	public List<Player> findAll() {
		return playerRepository.findAll();
	}

	@GetMapping("/{id}")
	public Player findOne(@PathVariable Long id) {
		return playerRepository.findOne(id);
	}

}
