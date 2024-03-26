package service;

import java.util.List;

import dao.CategorieDAO;
import dao.ProduitDAO;
import model.Categorie;
import model.Produit;

public class Test{

	public static void main(String[] args) {
		CategorieDAO catDAO = new CategorieDAOImpl();
		ProduitDAO prodDAO = new ProduitDAOImpl();
		
		List<Categorie> lesCategs;
		Categorie uneCategorie;
		List<Produit> lesProds;
		Produit unProduit;
		
		lesCategs= catDAO.listCategories();
		lesProds= prodDAO.listProduits();
		
		
		System.out.println("List of all Categories (_"+lesCategs.size()+"_)");
		lesCategs.stream().forEach(e -> System.out.println(e));
		
		/*
		 * uneCategorie = new Categorie(); uneCategorie.setNom("Nouvelle Categorie");
		 * uneCategorie.setDescription("Nouvelle Categorie");
		 * catDAO.addCategorie(uneCategorie);
		 * 
		 * lesCateg= catDAO.listCategories();
		 * System.out.println("List of all Categories (_"+lesCateg.size()+"_)");
		 * //lesCateg.stream().forEach(e -> System.out.println(e));
		 * 
		 * uneCategorie = catDAO.getCategorieById(2);
		 * uneCategorie.setDescription("Boitier Tower, Big Tower, Destop or Slim");
		 * catDAO.updateCategorie(uneCategorie);
		 */
		
		lesProds= prodDAO.listProduits();
		System.out.println("List of all Products (_"+lesProds.size()+"_)");
		lesProds.stream().forEach(e -> System.out.println(e));
	}

}
