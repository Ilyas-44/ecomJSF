package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import model.Categorie;
import service.CategorieDAOImpl;

@ManagedBean(name="stats")
@SessionScoped
public class Stats implements Serializable {

	private static final long serialVersionUID = 8107634688016550766L;
	
	private BarChartModel produitParCategorieBct;
	private org.primefaces.model.charts.bar.BarChartModel barModel;
	private PieChartModel produitParCategoriePCt;
	
	@PostConstruct
    public void init() {
        createModels();
    }
	
	private void createModels() {
		initBarChartModel();
		initPieChartModels();
		initBarBarChart_v2();
	}

	private void initPieChartModels() {
		produitParCategoriePCt = new PieChartModel();
		CategorieDAOImpl catService = new CategorieDAOImpl();
		List<Categorie> allCategs = catService.listCategories();
		for (Categorie categorie : allCategs) {
			produitParCategoriePCt.set(categorie.getNom(), 
					catService.productsCount(categorie));
		}
		produitParCategoriePCt.setTitle("% de Produits par Catégories");
		produitParCategoriePCt.setLegendPosition("w");
		produitParCategoriePCt.setFill(true);
		produitParCategoriePCt.setShowDataLabels(true);
	}

	private void initBarChartModel() {
		produitParCategorieBct = new BarChartModel();
		CategorieDAOImpl catService = new CategorieDAOImpl();
		List<Categorie> allCategs = catService.listCategories();
		for(Categorie categ : allCategs) {
			ChartSeries serie = new ChartSeries();
			serie.set(categ.getNom(), catService.productsCount(categ));
			serie.setLabel(categ.getNom());
			produitParCategorieBct.addSeries(serie);
		}
		produitParCategorieBct.setAnimate(true);
		produitParCategorieBct.setZoom(true);
		produitParCategorieBct.setLegendPosition("ne");
	    produitParCategorieBct.setTitle("Nombre de produits par catégorie");
	    Axis axeX = produitParCategorieBct.getAxis(AxisType.X);
	    axeX.setTickAngle(5);
	    //produitParCategorieBct.setExtender("chartExtender");
	    
	    
	}
	
	private void initBarBarChart_v2(){
		barModel = new org.primefaces.model.charts.bar.BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Nombre de produits par catégorie Version 2");
        
        List<Number> values = new ArrayList<Number>();
        List<String> labels = new ArrayList<String>();
        
        CategorieDAOImpl catService = new CategorieDAOImpl();
		List<Categorie> allCategs = catService.listCategories();
		for(Categorie categ : allCategs) {
			values.add(catService.productsCount(categ));
			labels.add(categ.getNom());
		}

        barDataSet.setData(values);

		
		  List<String> bgColor = new ArrayList<String>();
		  bgColor.add("rgba(255, 99, 132, 0.2)");
		  bgColor.add("rgba(255, 159, 64, 0.2)");
		  bgColor.add("rgba(255, 205, 86, 0.2)");
		  bgColor.add("rgba(75, 192, 192, 0.2)");
		  bgColor.add("rgba(54, 162, 235, 0.2)");
		  bgColor.add("rgba(153, 102, 255, 0.2)");
		  bgColor.add("rgba(201, 203, 207, 0.2)");
		  barDataSet.setBackgroundColor(bgColor);
		 

		
		  List<String> borderColor = new ArrayList<String>();
		  borderColor.add("rgb(255, 99, 132)"); borderColor.add("rgb(255, 159, 64)");
		  borderColor.add("rgb(255, 205, 86)"); borderColor.add("rgb(75, 192, 192)");
		  borderColor.add("rgb(54, 162, 235)"); borderColor.add("rgb(153, 102, 255)");
		  borderColor.add("rgb(201, 203, 207)");
		  barDataSet.setBorderColor(borderColor); barDataSet.setBorderWidth(1);
		 

        data.addChartDataSet(barDataSet);

        
       
        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        //linearAxes.setBeginAtZero(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("italic");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        // disable animation
		/*
		 * Animation animation = new Animation(); animation.setDuration(0);
		 * options.setAnimation(animation);
		 */

        barModel.setOptions(options);
	}
	

	public BarChartModel getProduitParCategorieBct() {
		return produitParCategorieBct;
	}

	public PieChartModel getProduitParCategoriePCt() {
		return produitParCategoriePCt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public org.primefaces.model.charts.bar.BarChartModel getBarModel() {
		return barModel;
	}
	
	
}
