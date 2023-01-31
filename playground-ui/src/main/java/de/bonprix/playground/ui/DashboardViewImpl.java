package de.bonprix.playground.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;

import de.bonprix.dto.CarDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.playground.DashboardView;
import de.bonprix.playground.ParkingZoneView;
import de.bonprix.vaadin.components.menu.ModuleView;
import de.bonprix.vaadin.mvp.BaseView;

import java.util.List;

@ModuleView(
        name = "Dashboard", position = 6, route = "Dashboard"
)
public class DashboardViewImpl extends BaseView<DashboardView.Presenter>  implements DashboardView{

    @Override
    protected void init() {
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats(), getCompaniesChart());
    }

    private Component getContactStats() {
        Span stats = new Span(countOfParkingZones() + " Parking Zone");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getCompaniesChart() {

        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        allZones().forEach(zone ->
                dataSeries.add(new DataSeriesItem(zone.getName(),6L)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }



    @Override
    public void updateList() {

    }

    public Long countOfParkingZones() {
      return  getPresenter().getAllParkingZones().stream().count();
    }

    public List<ParkingZoneDTO> allZones (){
       return getPresenter().getAllParkingZones();
    }

    public CarDTO getCarForzone (Long id){
       return null;
    }
}
