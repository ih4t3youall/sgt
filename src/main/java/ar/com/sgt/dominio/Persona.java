package ar.com.sgt.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persona")
public class Persona {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="nombre")
	private String name;
	
	@Column(name="pais")
	private String conuntry;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConuntry() {
		return conuntry;
	}

	public void setConuntry(String conuntry) {
		this.conuntry = conuntry;
	}

}
