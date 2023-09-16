package controller;

import dao.GenericDao;
import model.Pessoa;
import org.primefaces.component.donutchart.DonutChart;
import org.primefaces.component.donutchart.DonutChartBase;
import org.primefaces.component.donutchart.DonutChartRenderer;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.ChartDataSet;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartOptions;
import repository.IDaoPessoa;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@RequestScoped
@Named
public class GraficoBean implements Serializable {

    @Inject
    private GenericDao genericDao;

    @Inject
    private IDaoPessoa iDaoPessoa;

    private BarChartModel barChartModel = new BarChartModel();
    private PieChartModel pieChartModel = new PieChartModel();
    private DonutChartModel donutChartModel = new DonutChartModel();
    private org.primefaces.model.charts.donut.DonutChartModel donutSpecial = new org.primefaces.model.charts.donut.DonutChartModel();
    private org.primefaces.model.charts.pie.PieChartModel pieSpecial = new org.primefaces.model.charts.pie.PieChartModel();

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

    public PieChartModel getPieChartModel() {
        return pieChartModel;
    }

    public void setPieChartModel(PieChartModel pieChartModel) {
        this.pieChartModel = pieChartModel;
    }

    public DonutChartModel getDonutChartModel() {
        return donutChartModel;
    }

    public void setDonutChartModel(DonutChartModel donutChartModel) {
        this.donutChartModel = donutChartModel;
    }

    public org.primefaces.model.charts.donut.DonutChartModel getDonutSpecial() {
        return donutSpecial;
    }

    public void setDonutSpecial(org.primefaces.model.charts.donut.DonutChartModel donutSpecial) {
        this.donutSpecial = donutSpecial;
    }

    public org.primefaces.model.charts.pie.PieChartModel getPieSpecial() {
        return pieSpecial;
    }

    public void setPieSpecial(org.primefaces.model.charts.pie.PieChartModel pieSpecial) {
        this.pieSpecial = pieSpecial;
    }

    @PostConstruct
    public void init() {
        construirGraficos();
    }

    public void construirGraficos() {
        try {
            List<String> nomes = new ArrayList<>();
            List<Number> salarios = new ArrayList<>();
            ChartData data = new ChartData();
            List<Pessoa> pessoas =  iDaoPessoa.listaPessoaSalarios();
            ChartSeries chartSeries = new ChartSeries(); // Grupo de Funcionarios HashMap

            Map<String, Number> map = new LinkedHashMap<>();

            for (Pessoa p : pessoas) {
                chartSeries.set(p.getNome(), p.getSalario()); // Adicionando Salarios para grafico de barras - Grafico Simples

                pieChartModel.set(p.getNome(),p.getSalario()); // Adicionando Salarios para grafico de pizza - Grafico Simples

                map.put(p.getNome(), p.getSalario()); // Adicionando Salarios para grafico de donut - Grafico Simples

                nomes.add(p.getNome()); salarios.add(p.getSalario()); // Adicionando nomes e salarios a cada uma das listas - Grafico Interativo
            }
            barChartModel.addSeries(chartSeries); // Adicionando grupo no Grafico
            barChartModel.setTitle("Salario em Barras");
            barChartModel.setAnimate(true);

            pieChartModel.setTitle("Salario em Pizza");
            pieChartModel.setLegendPosition("n");
            pieChartModel.setFill(true);
            pieChartModel.setShowDataLabels(true);
            pieChartModel.setDiameter(200);

            donutChartModel.addCircle(map);
            donutChartModel.setTitle("Salario em Donut");
            donutChartModel.setLegendPosition("n");
            donutChartModel.setShowDataLabels(true);
/*=================================Fim Graficos Simples================================================================== */

            List<String> bgColor = new ArrayList<>();
            bgColor.add("rgba(255, 99, 71, 1)");
            bgColor.add("rgba(0, 255, 0, 1)");
            bgColor.add("rgba(255, 89, 255, 1)");
            bgColor.add("rgba(0, 10, 255, 0.4)");
            bgColor.add("rgba(0, 255, 244, 1)");
            bgColor.add("rgba(153, 102, 255, 0.2)");
            bgColor.add("rgba(44, 0, 0, 0.6)");
            bgColor.add("rgba(111, 140, 84, 1)");
            bgColor.add("rgba(244, 67, 152, 1)");
            bgColor.add("rgba(67, 244, 158, 1)");
            bgColor.add("rgba(244, 241, 67, 1)");

            DonutChartOptions donutChartOptions = new DonutChartOptions(); // Funciona para os dois graficos, Generico
            PieChartOptions pieChartOptions = new PieChartOptions();

            donutChartOptions.setAnimateRotate(true);
            donutChartOptions.setAnimateScale(true);
            pieChartOptions.setAnimateRotate(true);
            pieChartOptions.setAnimateScale(true);

            Title title = new Title();
            title.setText("Salario em Donut");
            title.setDisplay(true);
            title.setFontSize(20);

            donutChartOptions.setTitle(title);
            donutSpecial.setOptions(donutChartOptions);

            Title title2 = new Title();
            title2.setText("Salario em Pizza");
            title2.setDisplay(true);
            title2.setFontSize(20);

            pieChartOptions.setTitle(title2);
            pieSpecial.setOptions(pieChartOptions);

            DonutChartDataSet donutDataSet = new DonutChartDataSet();
//            PieChartDataSet pieDataSet = new PieChartDataSet();

            donutDataSet.setData(salarios);
            donutDataSet.setBackgroundColor(bgColor);


//            pieDataSet.setData(salarios);
//            pieDataSet.setBackgroundColor(bgColor);

            data.addChartDataSet(donutDataSet);
            data.setLabels(nomes);

            donutSpecial.setData(data);

//            data.addChartDataSet(pieDataSet);
//            data.setLabels(nomes);

            pieSpecial.setData(data);
            pieSpecial.setExtender("chartExtender");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
