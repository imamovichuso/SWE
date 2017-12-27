package at.ac.univie.swe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Field {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int row;
	private int column;
	private Type type;

	public Field() {
		this.row = 0;
		this.column = 0;
	}

	public Field(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "F[(" + row + "," + column + ")," + type.toString().substring(0, 1) + "]";
	}

	public static enum Type {
		GRASS, MOUNTAIN, WATER;

		public boolean isGrass() {
			return this == GRASS;
		}

		public boolean isMountain() {
			return this == MOUNTAIN;
		}

		public boolean isWater() {
			return this == WATER;
		}
	}

}
