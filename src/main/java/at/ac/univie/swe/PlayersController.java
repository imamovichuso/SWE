package at.ac.univie.swe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class PlayersController {

	@Autowired
    PlayerRepository repository;

	@Autowired
    GameRepository gameRepository;

    //JSON
    @RequestMapping(value ="rest/player/{id}",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Player player2(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value="rest/players",method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Iterable playersList2(Model model) {
       return repository.findAll();
    }


    //html
	@RequestMapping("/player/{id}")
	public String player(@PathVariable Long id, Model model) {
        model.addAttribute("player", repository.findOne(id));
        return "player";
	}

    @RequestMapping(value="/players",method=RequestMethod.GET)
	public String playersList(Model model) {
        model.addAttribute("players", repository.findAll());
        return "players";
	}

    @RequestMapping(value="/players",method=RequestMethod.POST)
	public String playersAdd(@RequestParam String email,
                             @RequestParam String firstName, @RequestParam String lastName, Model model) {
        Player newPlayer = new Player();
        newPlayer.setEmail(email);
        newPlayer.setFirstName(firstName);
        newPlayer.setLastName(lastName);
        repository.save(newPlayer);

        model.addAttribute("player", newPlayer);
        return "redirect:/player/" + newPlayer.getId();
	}

}
