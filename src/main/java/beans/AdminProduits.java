package beans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.file.UploadedFile;

import model.Categorie;
import model.Produit;
import service.CategorieDAOImpl;
import service.ProduitDAOImpl;



@ManagedBean(name = "adminProduits", eager = true)
@SessionScoped
public class AdminProduits implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Produit> allProduits;
	private List<Produit> filteredProduits;
	private List<Produit> categoryProducts;
	private List<Categorie> allCategories;

    private Produit selectedProduit;
    private Produit produitToAdd = new Produit();
    private int produit;


	private Categorie selectedCategorie;

	private ProduitDAOImpl prodDao = new ProduitDAOImpl();
    private boolean editMode = false;
	private boolean addMode = false;
	private CategorieDAOImpl categDao = new CategorieDAOImpl();
	private ProduitDAOImpl produitService;
    private UploadedFile photo;

	
	private int categorie;

	{
		produitService = new ProduitDAOImpl();
	}

	


	@PostConstruct
    public void init() {
       
        allProduits = getAllProduits();
    }
    
	public void edit(){
		System.out.println("edit clicked");
		editMode=true;
		addMode=false;
	}
	public void cancelUpdate(){
		editMode=false;
	}

	public void prepareAdd(){
		addMode=true;
		editMode=false;
	}
	public void cancelAdd(){
		produitToAdd=new Produit();
		addMode=false;	
	}
	
	public void setPhoto(UploadedFile photo) {
			this.photo = photo;
			try {
	            String uploadPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") +
	                    "images" + File.separator + "photos";
	            System.out.println("Upload Path:" + uploadPath);
	            File uploadDir = new File(uploadPath);
	            if (!uploadDir.exists()) {
	                uploadDir.mkdirs();
	            }
	            String newName = UUID.randomUUID().toString() + photo.getFileName();
	            if (photo.getSize() > 0) {
	                try (InputStream input = photo.getInputStream()) {
	                    Files.copy(input, new File(uploadPath, newName).toPath());
	                }
	                if(addMode) {
	                	produitToAdd.setPhoto(newName);
	                }else  if(editMode) {
	                	selectedProduit.setPhoto(newName);
	                }
	                
	            }
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	        }
		}
//
//public void upload() {
//    if (file != null) {
//        try {
//            // Generate a unique file name using UUID
//            String fileName = UUID.randomUUID().toString() + "_" + file.getFileName();
//            
//            // Get the temporary directory path
//            String tempDirPath = System.getProperty("java.io.tmpdir");
//            
//            // Create a File object representing the temporary directory
//            File tempDir = new File(tempDirPath);
//
//            // Create the temporary directory if it doesn't exist
//            if (!tempDir.exists()) {
//                tempDir.mkdirs(); 
//            }
//
//            // Construct the full file path
//            String filePath = tempDirPath + File.separator + fileName;
//            
//            // Create a File object representing the uploaded file
//            File uploadedFile = new File(fileName);
//            System.out.println(filePath);
//            // Write the uploaded file to the temporary directory
//            try (InputStream input = file.getInputStream();
//                 OutputStream output = new FileOutputStream(uploadedFile)) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//
//                while ((bytesRead = input.read(buffer)) != -1) {
//                    output.write(buffer, 0, bytesRead);
//                }
//            }
//
//            // Set the file path to the 'photo' attribute of the 'Produit' object
//            produitToAdd.setPhoto(fileName);
//            
//            // Print the path of the uploaded file
//            System.out.println("Uploaded file path: " + filePath);
//
//            // Display success message
//            addMessage(FacesMessage.SEVERITY_INFO, "Upload Successful", "Photo uploaded successfully");
//        } catch (IOException e) {
//            e.printStackTrace();
//            addMessage(FacesMessage.SEVERITY_ERROR, "Upload Error", "Failed to upload photo");
//        }
//    }
//}
	  public void addProduit() {
	        if (produitToAdd != null) {
	            if (photo != null) {
//	                upload(); 
	            	setPhoto(photo);
	            }
	            produitToAdd.setCategorie(selectedCategorie);
	            produitService.addProduit(produitToAdd);
	            addMessage(FacesMessage.SEVERITY_INFO, "Ajout Réussi", "Ajout de produit avec Succès");
	        } else {
	            addMessage(FacesMessage.SEVERITY_WARN, "Ajout échoué", "Erreur lors de l'ajout de le produit");
	        }
	        addMode = false;
	    }
	
	

public List<Categorie> getAllCategories() {
	allCategories = categDao.listCategories();
	return allCategories;
}




public void updateProduit() {
    if (selectedProduit != null) {
//        if (file != null) {
////            upload(); 
//        }
        selectedProduit.setCategorie(selectedCategorie);
        produitService.updateProduit(selectedProduit);
        addMessage(FacesMessage.SEVERITY_INFO, "Modification Réussie", "Modification de produit avec Succès");
    } else {
        addMessage(FacesMessage.SEVERITY_WARN, "Modification échouée", "Erreur lors de la modification de produit");
    }
    editMode = false;
}

    public void deleteProduit() {
        
            produitService.removeProduit(selectedProduit.getId());
            FacesContext context = FacesContext.getCurrentInstance();
  	      context.addMessage("msgDel", new FacesMessage(FacesMessage.SEVERITY_INFO, "Produit Supprimée", selectedProduit.toString()));
  	    allProduits=getAllProduits();
    }
    
//    !!
    public List<Produit> getCategoryProducts() {
		Categorie categ = categDao.getCategorieById(categorie);
		categoryProducts = new ArrayList<Produit>(categ.getProduits());
		return categoryProducts;
	}

	
	
//	public List<Produit> getCategoryProducts() {
//		return categoryProducts;
//	}

	public void setAllCategories(List<Categorie> allCategories) {
		this.allCategories = allCategories;
	}

	public void setCategoryProducts(List<Produit> categoryProducts) {
		this.categoryProducts = categoryProducts;
	}

	public List<Produit> getAllProduits() {
		allProduits = prodDao.listProduits();
		return allProduits;
	}


    public Produit getSelectedProduit() {
        return selectedProduit;
    }

    public void setSelectedProduit(Produit selectedProduit) {
        this.selectedProduit = selectedProduit;
    }

    public Produit getProduitToAdd() {
        return produitToAdd;
    }

    public void setProduitToAdd(Produit produitToAdd) {
        this.produitToAdd = produitToAdd;
    }
    public boolean isEditMode() {
		return editMode;
	}


	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}


	public boolean isAddMode() {
		return addMode;
	}


	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
	public List<Produit> getFilteredProduits() {
		return filteredProduits;
	}

	public void setFilteredProduits(List<Produit> filteredProducts) {
		this.filteredProduits = filteredProducts;
	}

	public int getProduit() {
		return produit;
	}

	public void setProduit(int produit) {
		this.produit = produit;
	}

	public ProduitDAOImpl getProdDao() {
		return prodDao;
	}

	public void setProdDao(ProduitDAOImpl prodDao) {
		this.prodDao = prodDao;
	}

	public ProduitDAOImpl getProduitService() {
		return produitService;
	}

	public void setProduitService(ProduitDAOImpl produitService) {
		this.produitService = produitService;
	}

	public void setAllProduits(List<Produit> allProduits) {
		this.allProduits = allProduits;
	}
	public Categorie getSelectedCategorie() {
		return selectedCategorie;
	}


	public void setSelectedCategorie(Categorie selectedCategorie) {
		this.selectedCategorie = selectedCategorie;
	}

	public UploadedFile getPhoto() {
		return photo;
	}
	
	

//    public UploadedFile getFile() {
//		return file;
//	}
//
//	public void setFile(UploadedFile file) {
//		this.file = file;
//	}

}
