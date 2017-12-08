package at.ac.univie.swe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GamesController {

	@Autowired
    PlayerRepository repository;

	@Autowired
    GameRepository gameRepository;

	//JSON
    @RequestMapping(value ="rest/game/{id}",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Game game2(@PathVariable Long id, Model model) {
        Game g = gameRepository.findOne(id);
        return g;
    }
    @RequestMapping(value="/rest/games",method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Iterable gamesList2(Model model) {
        return gameRepository.findAll();
    }

    //html

	@RequestMapping("/game/{id}")
	public String game(@PathVariable Long id, Model model) {
        model.addAttribute("game", gameRepository.findOne(id));
        return "game";
	}

    @RequestMapping(value="/games",method=RequestMethod.GET)
	public String gamesList(Model model) {
        model.addAttribute("games", gameRepository.findAll());
        return "games";
	}

    @RequestMapping(value="/games",method=RequestMethod.POST)
	public String gamesAdd(@RequestParam String name,
                             @RequestParam int wasserfeld, @RequestParam int bergfeld,int wissenfeld, int max_movement, Model model) {
        Game newGame = new Game(name,wasserfeld,bergfeld,wissenfeld,max_movement);
        gameRepository.save(newGame);
        model.addAttribute("game", newGame);
        return "redirect:/game/" + newGame.getId();
	}


}
