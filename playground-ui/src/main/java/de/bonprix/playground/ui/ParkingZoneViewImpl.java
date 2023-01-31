package de.bonprix.playground.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingZoneDTO;
import de.bonprix.playground.ParkingZoneView;
import de.bonprix.playground.components.addDialogs.ParkingAddDialog;
import de.bonprix.playground.components.addDialogs.ParkingZoneAddDialog;
import de.bonprix.playground.components.deleteDialogs.CarDeleteDialog;
import de.bonprix.playground.components.deleteDialogs.ParkingDeleteDialog;
import de.bonprix.playground.components.deleteDialogs.ParkingZoneDeleteDialog;
import de.bonprix.playground.forms.EditForms.ParkingEditForm;
import de.bonprix.playground.forms.EditForms.ParkingZoneEditForm;
import de.bonprix.vaadin.components.menu.ModuleView;
import de.bonprix.vaadin.mvp.BaseView;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ModuleView(name = "ParkingZone",
        route = "ParkingZone",
        position = 3)
public class ParkingZoneViewImpl extends BaseView<ParkingZoneView.Presenter> implements ParkingZoneView {

    private static final long serialVersionUID = 1L;

    Grid<ParkingZoneDTO> grid = new Grid<>(ParkingZoneDTO.class,false);
    ParkingZoneAddDialog parkingZoneAddDialog;

    ParkingZoneDeleteDialog parkingZoneDeleteDialog;

    ParkingZoneEditForm parkingZoneEditForm;
    Button buttonAddParkingZone = new Button("Add Parking Zone", e -> parkingZoneAddDialog.open());

    //Button buttonDeleteParkingZone = new Button("Delete parking", e -> parkingZoneDeleteDialog.open());


    @Override
    protected void init() {
        VerticalLayout verticalLayout = new VVerticalLayout();
        addClassName("list-view");
        setSizeFull();

        buttonAddParkingZone.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
       // buttonAddParkingZone.addClickListener(buttonClickEvent -> UI.getCurrent().getPage().reload());

        parkingZoneAddDialog = new ParkingZoneAddDialog(getAllParkings());
        parkingZoneAddDialog.addListener(ParkingZoneAddDialog.SaveEvent.class, this::saveParkingZone);


        parkingZoneEditForm = new ParkingZoneEditForm();
        parkingZoneEditForm.setWidth("25em");
        parkingZoneEditForm.addListener(ParkingZoneEditForm.SaveEvent.class, this::updateParkingZone);
        parkingZoneEditForm.addListener(ParkingZoneEditForm.DeleteEvent.class, this::deleteParkingZone);
        parkingZoneEditForm.addListener(ParkingZoneEditForm.CloseEvent.class, event -> closeEditor());

        configureGrid();

        verticalLayout.add(buttonAddParkingZone);

        FlexLayout flexLayout = new FlexLayout(grid, parkingZoneEditForm);

        flexLayout.setFlexGrow(2, grid);
        flexLayout.setFlexGrow(1, parkingZoneEditForm);
        flexLayout.setFlexShrink(0,parkingZoneEditForm);
        flexLayout.setSizeFull();

        add(verticalLayout,flexLayout);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event -> editParking(event.getValue()));


    }
    private void configureGrid() {
            grid.addClassNames("parkingZone-grid");
            grid.setSizeFull();
            grid.addColumn(ParkingZoneDTO::getId).setHeader("ID").setSortable(true);
            grid.addColumn(ParkingZoneDTO::getName).setHeader("Name").setSortable(true);
            grid.addColumn(parkingZoneDTO -> parkingZoneDTO .getParking().getId().toString()).setHeader("Parking Id").setSortable(true);
            grid.getColumns().forEach(parkingColumn -> parkingColumn.setAutoWidth(true));

    }

    @Override
    public void updateList() {
        grid.setItems(getPresenter().getAllParkingZones());
    }



    private void saveParkingZone(ParkingZoneAddDialog.SaveEvent event) {
        if (getPresenter().createParkingZone(event.getParkingZoneDTO())) {
            updateList();

        } else {
            getNotification();
        }

    }

    void deleteById(ParkingZoneDeleteDialog.DeleteEvent event){
        getPresenter().deleteById(event.getIdForDelete());
        updateList();
    }

    public List<ParkingDTO> getAllParkings(){
       return getPresenter().getAllParkings();
    }
    private void closeEditor() {
        parkingZoneEditForm.setParking(null);
        parkingZoneEditForm.setVisible(false);
        removeClassName("editing");
    }

    private void editParking(ParkingZoneDTO parkingZone) {
        if (parkingZone == null) {
            closeEditor();
        } else {
            parkingZoneEditForm.setParking(parkingZone)
            ;
            parkingZoneEditForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateParkingZone(ParkingZoneEditForm.SaveEvent event) {
        getPresenter().updateParking(event.getParking());
        updateList();
    }

    private void deleteParkingZone(ParkingZoneEditForm.DeleteEvent event) {
        getPresenter().deleteParking(event.getParking());
        updateList();
    }

    private Notification getNotification (){
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.MIDDLE);
        Div text = new Div(new Text("Failed to create Parking Zone, Name must be unique, already have zone with same name"));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
        return notification;
    }

}
