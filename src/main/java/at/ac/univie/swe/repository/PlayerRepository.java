package at.ac.univie.swe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.ac.univie.swe.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

}
