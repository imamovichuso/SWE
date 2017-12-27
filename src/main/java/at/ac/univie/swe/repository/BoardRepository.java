package at.ac.univie.swe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.ac.univie.swe.model.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
