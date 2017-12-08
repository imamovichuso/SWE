package at.ac.univie.swe;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
	PlayerRepository playerRepository;

    @Autowired
	GameRepository gameRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		Game demogame = new Game("Demo game",5, 3, 5, 100);
		Game secondgame = new Game("second game", 4,3,5,80);

		gameRepository.save(demogame);
		gameRepository.save(secondgame);


		List<Player> players = new LinkedList<Player>();
		players.add(new Player("John", "Smith", "john.smith@example.com"));
		players.add(new Player("Mark", "Johnson", "mjohnson@example.com"));
		playerRepository.save(players);
	}

}
