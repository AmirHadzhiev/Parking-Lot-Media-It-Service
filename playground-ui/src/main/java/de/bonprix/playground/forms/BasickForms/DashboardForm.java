package de.bonprix.playground.forms.BasickForms;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DashboardForm extends VerticalLayout {


//    public DashboardForm() {
//
//        addClassName("dashboard-view");
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//        add(getContactStats(), getCarChart());
//    }
//
//    private Component getContactStats(Integer countOfParkingZones) {
//        Span stats = new Span(countOfParkingZones + " parking zones");
//        stats.addClassNames("text-xl", "mt-m");
//        return stats;
//    }
//
//    private Chart getCompaniesChart() {
//        Chart chart = new Chart(ChartType.PIE);
//
//
//        DataSeries dataSeries = new DataSeries();
//        service.findAllCompanies().forEach(company ->
//                dataSeries.add(new DataSeriesItem(company.getName(), company.getEmployeeCount())));
//        chart.getConfiguration().setSeries(dataSeries);
//        return chart;
//    }
}
