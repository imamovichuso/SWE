package at.ac.univie.swe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.ac.univie.swe.model.Game;
import at.ac.univie.swe.model.Game.Status;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	Game findOneByStatus(Status status);

}
