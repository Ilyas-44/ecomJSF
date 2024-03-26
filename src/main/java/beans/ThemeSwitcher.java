package beans;

import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "themeSwitcher")
@SessionScoped
public class ThemeSwitcher {
	private Boolean checkedDBForTheme;
    private Map<String, String> themes;
    private String theme;

    public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public void setThemes(Map<String, String> themes) {
		this.themes = themes;
	}

	public ThemeSwitcher () {
        
        checkedDBForTheme = false;
        theme = "aristo";
        
        themes = new TreeMap<String, String>();
        themes.put("Afterdark", "afterdark");
        themes.put("Afternoon", "afternoon");
        themes.put("Afterwork", "afterwork");
        themes.put("Aristo", "aristo");
        themes.put("Black-Tie", "black-tie");
        themes.put("Blitzer", "blitzer");
        themes.put("Bluesky", "bluesky");
        themes.put("Bootstrap", "bootstrap");
        themes.put("Casablanca", "casablanca");
        themes.put("Cupertino", "cupertino");
        themes.put("Cruze", "cruze");
        themes.put("Dark-Hive", "dark-hive");
        themes.put("Dot-Luv", "dot-luv");
        themes.put("Eggplant", "eggplant");
        themes.put("Excite-Bike", "excite-bike");
        themes.put("Flick", "flick");
        themes.put("Glass-X", "glass-x");
        themes.put("Hot-Sneaks", "hot-sneaks");
        themes.put("Home", "home");
        themes.put("Humanity", "humanity");
        themes.put("Le-Frog", "le-frog");
        themes.put("Midnight", "midnight");
        themes.put("Mint-Choc", "mint-choc");
        themes.put("Overcast", "overcast");
        themes.put("Pepper-Grinder", "pepper-grinder");
        themes.put("Redmond", "redmond");
        themes.put("Rocket", "rocket");
        themes.put("Sam", "sam");
        themes.put("Smoothness", "smoothness");
        themes.put("South-Street", "south-street");
        themes.put("Start", "start");
        themes.put("Sunny", "sunny");
        themes.put("Swanky-Purse", "swanky-purse");
        themes.put("Trontastic", "trontastic");
        themes.put("UI-Darkness", "ui-darkness");
        themes.put("UI-Lightness", "ui-lightness");
        themes.put("Vader", "vader");
        
    }
    
    /*
     * Since the following is in web.xml:
     * 
    <context-param>
        <param-name>primefaces.THEME</param-name>
        <param-value>#{themeSwitcher.theme}</param-value>
    </context-param>
     * 
     * AND since I had the following:
     * 
     * 1. pf_UsersController usersController (managedproperty)
     * 2. PostConstruct init() to get theme from database
     * 
     * the following error was the result:
     * 
    SEVERE: Error Rendering View[/login.xhtml]
    com.sun.faces.mgbean.ManagedBeanCreationException: An error occurred performing
                                                       resource injection on managed
                                                       bean themeSwitcher

    Caused by: java.lang.NullPointerException
            at pf.ThemeSwitcher.init(ThemeSwitcher.java:83)
     * 
     * 
     * References (injecting managed bean property)
     * 
     * http://stackoverflow.com/questions/5303331/cant-get-value-of-object-in-session-scoped-bean
     * http://stackoverflow.com/questions/2337772/how-can-i-get-a-sessionscope-object-in-my-bean
     */
	/*
	 * private pf_UsersController getUsersController() {
	 * 
	 * pf_UsersController controller = null; if
	 * (FacesContext.getCurrentInstance().getExternalContext().getSessionMap() !=
	 * null) { controller = (pf_UsersController)
	 * FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
	 * get("pf_usersController"); } return controller; }
	 * 
	 * private void getThemeFromDatabase() { if (getUsersController() != null &&
	 * getUsersController().getUser() != null) { String userTheme =
	 * getUsersController().getUser().getTheme(); if (userTheme != null &&
	 * userTheme.length() > 0) theme = userTheme; checkedDBForTheme = true; } else
	 * checkedDBForTheme = false; }
	 * 
	 * public String getTheme() { if (!checkedDBForTheme) getThemeFromDatabase();
	 * return theme; }
	 * 
	 * public void setTheme(String theme) { this.theme = theme; }
	 */

    /*
     * Referenced by following XML:
     * 
    <p:themeSwitcher value="#{themeSwitcher.theme}">
        <f:selectItems value="#{themeSwitcher.themes}"/>
        <p:ajax listener="#{themeSwitcher.saveTheme}" />
    </p:themeSwitcher>
     * 
     * Need to inject the bean into memory via following:
     * 
    <h:inputHidden value="#{pf_usersController.forInjection}"/>
     */
	/*
	 * public void saveTheme() { if (getUsersController() != null) {
	 * getUsersController().updateTheme(theme); } else System.out.
	 * println("ThemeSwitcher.saveTheme(): theme was not saved, since pf_UsersController is null"
	 * ); }
	 */

    public Map<String, String> getThemes() {
        return themes;
    }
}
