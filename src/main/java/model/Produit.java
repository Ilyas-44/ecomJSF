package model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="produit")
public class Produit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String designation;

	private float prix;

	private int quantite;
	private String photo;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	private int sdr;

	//bi-directional many-to-one association to Categorie
	@ManyToOne
	@JoinColumn(name="idCateg")
	private Categorie categorie;

	public Produit() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public float getPrix() {
		return this.prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public int getSdr() {
		return this.sdr;
	}

	public void setSdr(int sdr) {
		this.sdr = sdr;
	}

	public Categorie getCategorie() {
		return this.categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	@Override
	public String toString() {
		return id + " => " + designation + ","+prix+ ","+quantite;
	}
}