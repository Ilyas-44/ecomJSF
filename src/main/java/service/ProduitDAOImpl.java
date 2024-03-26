package service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dao.ProduitDAO;
import model.Categorie;
import model.Produit;
import util.HibernateUtil;

@ManagedBean(name = "produitService", eager = true)
@RequestScoped
public class ProduitDAOImpl implements Serializable, ProduitDAO{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CategorieDAOImpl.class.getName());

	public ProduitDAOImpl() {
    }
	
	
	
	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return HibernateUtil.getSessionFactory();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not create SessionFactory", e);
			throw new IllegalStateException("Could not create SessionFactory");
		}
	} 
	
	public void addProduit(Produit produit) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.persist(produit);
		session.getTransaction().commit();
		logger.info("Product successfully saved, Product Details="+produit);
	}
	
	public void updateProduit(Produit produit){
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.merge(produit);
		session.getTransaction().commit();
		logger.info("Product successfully updated, Product Details="+produit);
	}
	
	public List<Produit> listProduits(){
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Produit> productsList = session.createQuery("from Produit", Produit.class).list();
		session.getTransaction().commit();
		return productsList;
	}
	
	public List<Produit> listProduitsByCategorie(Categorie categ){
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Produit> productsList = session.createQuery("from Produit p where p.categorie.idCat="+categ.getId(),Produit.class).list();
		session.getTransaction().commit();
		return productsList;
	}
	
	public List<Produit> selectProdByKeyword(String keyWord){
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Produit> productsList = session.createQuery("from Produit p WHERE p.designation LIKE '%"+keyWord+"%'", Produit.class).list();
		session.getTransaction().commit();
		return productsList;
	}
	
	public Produit getProduitById(int id){
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Produit produit = session.getReference(Produit.class, Integer.valueOf(id));
		session.getTransaction().commit();
		return produit;
	}
	
	public void removeProduit(int id){
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Produit produit = session.getReference(Produit.class, Integer.valueOf(id));
		
		if(null != produit){
			session.delete(produit);
		}
		session.getTransaction().commit();
		logger.info("Product deleted successfully, Product details="+produit);
	}
	
	
}
