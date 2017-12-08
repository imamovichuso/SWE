package at.ac.univie.swe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
    private String name;
    private int wasserfeld =4;
    private int bergfeld = 3;
    private int wissenfeld = 5;
    private int max_movement =100;

    public Game() {
		super();
    }

    public Game(String name, int wasserfeld, int bergfeld, int wissenfeld, int max_movement) {
		super();
		this.name = name;
		this.wasserfeld = wasserfeld;
		this.bergfeld = bergfeld;
		this.wissenfeld = wissenfeld;
		this.max_movement = max_movement;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWasserfeld() {
		return wasserfeld;
	}

	public void setWasserfeld(int wasserfeld) {
		this.wasserfeld = wasserfeld;
	}

	public int getBergfeld() {
		return bergfeld;
	}

	public void setBergfeld(int bergfeld) {
		this.bergfeld = bergfeld;
	}

	public int getWissenfeld() {
		return wissenfeld;
	}

	public void setWissenfeld(int wissenfeld) {
		this.wissenfeld = wissenfeld;
	}

	public int getMax_movement() {
		return max_movement;
	}

	public void setMax_movement(int max_movement) {
		this.max_movement = max_movement;
	}


}
