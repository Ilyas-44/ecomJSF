package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import model.User;

@ManagedBean(name = "adminUsers", eager = true)
@SessionScoped
public class AdminUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<User> allUsers;
    public List<User> filteredUsers;
    public User selectedUser;
    public User userToAdd = new User();
    private boolean editMode = false;
  	private boolean addMode = false;
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


	private String serviceUrl = "http://localhost:9999/RestWebService/api/user";

    @PostConstruct
    public void init() {
        getAllUsers();
    }
  

    public  List<User> getAllUsers() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(serviceUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONArray jsonArray = new JSONArray(responseBody);
                allUsers = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    User user = new User();
                    user.setId(jsonObject.getInt("id"));
                    user.setNom(jsonObject.getString("nom"));
                    user.setMdp(jsonObject.getString("mdp"));
                    allUsers.add(user);
                  
                }
               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("my list : " + allUsers);
		return allUsers;
    }

    public void addUser() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(serviceUrl);
            JSONObject requestBody = new JSONObject();
            requestBody.put("id", userToAdd.getId());
            requestBody.put("nom", userToAdd.getNom());
            requestBody.put("mdp", userToAdd.getMdp());
            StringEntity entity = new StringEntity(requestBody.toString());
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                getAllUsers();
                addMode = false; 
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "User added successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to add user");
        }
    }
    public void prepareAdd() {
        addMode = true;
        editMode = false;
        userToAdd = new User(); 
    }



    public void edit() {
        editMode = true;

        addMode = false;
    }

    public void updateUser() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String updateUserUrl = serviceUrl + "/" + selectedUser.getId(); 
            HttpPut httpPut = new HttpPut(updateUserUrl); 
            JSONObject requestBody = new JSONObject();
            requestBody.put("id", selectedUser.getId());
            requestBody.put("nom", selectedUser.getNom());
            requestBody.put("mdp", selectedUser.getMdp());
            StringEntity entity = new StringEntity(requestBody.toString());
            httpPut.setEntity(entity);
            httpPut.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                getAllUsers();
                editMode = false;
                addMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update user");
        }
    }

    public void cancelEdit() {
        editMode = false;
        selectedUser = null; 
    }
    public void saveChanges() {
        editMode = false;
        selectedUser = null; 
        addMessage(FacesMessage.SEVERITY_INFO, "Success", "Changes saved successfully");
    }

    public void deleteSelectedUser() {
        if (selectedUser != null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                String deleteUrl = serviceUrl + "/" + selectedUser.getId();
                HttpDelete httpDelete = new HttpDelete(deleteUrl);
                try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                    if (response.getCode() == HttpStatus.SC_OK) {
                        getAllUsers();
                        addMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully");
                    } else {
                        addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete user");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete user");
            }
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Warning", "No user selected for deletion");
        }
    }
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
	public List<User> getFilteredUsers() {
		return filteredUsers;
	}
	public void setFilteredUsers(List<User> filteredUsers) {
		this.filteredUsers = filteredUsers;
	}
	public User getSelectedUser() {
		return selectedUser;
	}
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	public User getUserToAdd() {
		return userToAdd;
	}
	public void setUserToAdd(User userToAdd) {
		this.userToAdd = userToAdd;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	
	  public List<User> setAllUsers(List<User> allUsers) {
	        this.allUsers = allUsers;
	        return allUsers;
	    }

   
}
