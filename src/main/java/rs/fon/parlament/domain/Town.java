package rs.fon.parlament.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mesto")
public class Town {

	@Id
	@Column(name = "idmesto")
	private Integer id;

	@Column(name = "ime")
	private String name;

	public Town() {
	}

	public Town(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Town [id=" + id + ", name=" + name + "]";
	}

}
