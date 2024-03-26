package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User  implements java.io.Serializable {

	private static final long serialVersionUID = 8364239647574512618L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    private String nom;
    
    private String mdp;

    public User() {
    }

    public User(String nom, String mdp) {
       this.nom = nom;
       this.mdp = mdp;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getMdp() {
        return this.mdp;
    }
    
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    
    @Override
	public String toString() {
		return "User : "+id + " => " + nom;
	}
}

