package de.bonprix.playground.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.playground.ParkingPlaceView;
import de.bonprix.playground.components.addDialogs.ParkingAddDialog;
import de.bonprix.playground.components.addDialogs.ParkingPlaceAddDialog;
import de.bonprix.playground.components.deleteDialogs.ParkingDeleteDialog;
import de.bonprix.playground.components.deleteDialogs.ParkingPlaceDeleteDialog;
import de.bonprix.playground.forms.EditForms.ParkingEditForm;
import de.bonprix.playground.forms.EditForms.ParkingPlaceEditForm;
import de.bonprix.vaadin.components.menu.ModuleView;
import de.bonprix.vaadin.mvp.BaseView;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import java.util.List;

@ModuleView(
        name = "ParkingPlace", position = 4, route = "ParkingPlace"
)
public class ParkingPlaceViewImpl extends BaseView<ParkingPlaceView.Presenter> implements ParkingPlaceView {
    private static final long serialVersionUID = 1L;
    Grid<ParkingPlaceDTO> grid = new Grid<>(ParkingPlaceDTO.class,false);

    ParkingPlaceAddDialog parkingPlaceAddDialog;
   // ParkingPlaceDeleteDialog parkingPlaceDeleteDialog;

    ParkingPlaceEditForm  parkingPlaceEditForm;

    Button buttonAddParkingPlace = new Button("Add Parking Place", e -> parkingPlaceAddDialog.open());


    @Override
    protected void init() {
        VerticalLayout verticalLayout = new VVerticalLayout();
        addClassName("list-view");
        setSizeFull();
        buttonAddParkingPlace.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        parkingPlaceAddDialog = new ParkingPlaceAddDialog(getAllParkingZones());
        parkingPlaceAddDialog.addListener(ParkingPlaceAddDialog.SaveEvent.class, this::saveParkingPlace);

        parkingPlaceEditForm = new ParkingPlaceEditForm();
        parkingPlaceEditForm.setWidth("25em");
        parkingPlaceEditForm.addListener(ParkingPlaceEditForm.SaveEvent.class, this::updateParkingPlace);
        parkingPlaceEditForm.addListener(ParkingPlaceEditForm.DeleteEvent.class, this::deleteParkingPlace);
        parkingPlaceEditForm.addListener(ParkingPlaceEditForm.CloseEvent.class, event -> closeEditor());

        configureGrid();

        verticalLayout.add(buttonAddParkingPlace);
        FlexLayout flexLayout = new FlexLayout(grid, parkingPlaceEditForm);

        flexLayout.setFlexGrow(2, grid);
        flexLayout.setFlexGrow(1, parkingPlaceEditForm);
        flexLayout.setFlexShrink(0,parkingPlaceEditForm);
        flexLayout.setSizeFull();

        add(verticalLayout,flexLayout);

        updateList(getPresenter().getAllParkingPlaces());
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event -> editParking(event.getValue()));



    }
    private void updateParkingPlace(ParkingPlaceEditForm.SaveEvent event) {
        getPresenter().updateParking(event.getParking());
        updateList(getPresenter().getAllParkingPlaces());
    }
    private void editParking(ParkingPlaceDTO parkingPlaceDTO) {
        if (parkingPlaceDTO == null) {
            closeEditor();
        } else {
            parkingPlaceEditForm.setParking(parkingPlaceDTO)
            ;
            parkingPlaceEditForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        parkingPlaceEditForm.setParking(null);
        parkingPlaceEditForm.setVisible(false);
        removeClassName("editing");
    }

    private void deleteParkingPlace(ParkingPlaceEditForm.DeleteEvent event) {
        getPresenter().deleteParking(event.getParking());
        updateList(getPresenter().getAllParkingPlaces());
    }


    private List<ParkingZoneDTO> getAllParkingZones() {
        return getPresenter().getAllParkingZones();
    }

    private void configureGrid() {
        grid.addClassNames("parking-place-grid");
        grid.setSizeFull();
        grid.addColumn(ParkingPlaceDTO::getId).setHeader("ID").setSortable(true);
        grid.addColumn(ParkingPlaceDTO::getNumber).setHeader("Number").setSortable(true);
        grid.addColumn(parkingPlaceDTO -> parkingPlaceDTO.getParkingZone().getId()).setHeader("Parking Zone ID").setSortable(true);
        grid.addColumn(parkingPlaceDTO -> {

            if (parkingPlaceDTO.getCar()==null) {
                return "Don't have Car";
            } else {
                return parkingPlaceDTO.getCar().getId();
            }


        } ).setHeader("Car in Place").setSortable(true);
        grid.getColumns().forEach(parkingColumn -> parkingColumn.setAutoWidth(true));
    }
    @Override
    public void updateList(List<ParkingPlaceDTO> parkingPlaces) {
        grid.setItems(getPresenter().getAllParkingPlaces());
    }

    private void saveParkingPlace(ParkingPlaceAddDialog.SaveEvent event){
        getPresenter().createParkingPlace(event.getParking());
        updateList(getPresenter().getAllParkingPlaces());
    }

    private void deleteById(ParkingPlaceDeleteDialog.DeleteEvent event){
        getPresenter().deleteById(event.getIdForDelete());
        updateList(getPresenter().getAllParkingPlaces());
    }

}
