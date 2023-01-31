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
import de.bonprix.playground.ParkingView;
import de.bonprix.playground.components.addDialogs.ParkingAddDialog;
import de.bonprix.playground.components.deleteDialogs.ParkingDeleteDialog;
import de.bonprix.playground.forms.EditForms.ParkingEditForm;
import de.bonprix.vaadin.components.menu.ModuleView;
import de.bonprix.vaadin.mvp.BaseView;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import java.util.List;

@ModuleView(name = "Parking",
        route = "Parking",
        position = 2)
public class ParkingViewImpl extends BaseView<ParkingView.Presenter> implements ParkingView {

    private static final long serialVersionUID = 1L;

    Grid<ParkingDTO> grid = new Grid<>(ParkingDTO.class,false);


    ParkingAddDialog parkingAddDialog;
    ParkingDeleteDialog parkingDeleteDialog;

    ParkingEditForm parkingEditForm;

    Button buttonAddParking = new Button("Add parking", e -> parkingAddDialog.open());

   // Button buttonDelete = new Button("Delete parking", e -> parkingDeleteDialog.open());


    @Override
    protected void init() {
        VerticalLayout verticalLayout = new VVerticalLayout();
        addClassName("list-view");
        setSizeFull();

        buttonAddParking.addThemeVariants(ButtonVariant.LUMO_PRIMARY);



        parkingAddDialog = new ParkingAddDialog();
        parkingAddDialog.addListener(ParkingAddDialog.SaveEvent.class, this::saveParking);


        parkingEditForm = new ParkingEditForm();
        parkingEditForm.setWidth("25em");
        parkingEditForm.addListener(ParkingEditForm.SaveEvent.class, this::updateParking);
        parkingEditForm.addListener(ParkingEditForm.DeleteEvent.class, this::deleteParking);
        parkingEditForm.addListener(ParkingEditForm.CloseEvent.class, event -> closeEditor());

        configureGrid();

        verticalLayout.add(buttonAddParking);


        FlexLayout flexLayout = new FlexLayout(grid, parkingEditForm);

        flexLayout.setFlexGrow(2, grid);
        flexLayout.setFlexGrow(1, parkingEditForm);
        flexLayout.setFlexShrink(0,parkingEditForm);
        flexLayout.setSizeFull();

        add(verticalLayout,flexLayout);
        updateList(getPresenter().getAllParkings());
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event -> editParking(event.getValue()));


    }


    private void editParking(ParkingDTO parking) {
        if (parking == null) {
            closeEditor();
        } else {
            parkingEditForm.setParking(parking)
            ;
            parkingEditForm.setVisible(true);
            addClassName("editing");
        }
    }


    private void configureGrid() {
        grid.addClassNames("parking-grid");
        grid.setSizeFull();
        grid.addColumn(ParkingDTO::getId).setHeader("ID").setSortable(true);
        grid.addColumn(ParkingDTO::getName).setHeader("Name").setSortable(true);
        grid.addColumn(ParkingDTO::getCity).setHeader("City").setSortable(true);
        grid.addColumn(ParkingDTO::getStreet).setHeader("Street").setSortable(true);
        grid.addColumn(ParkingDTO::getZipCode).setHeader("Zip Code").setSortable(true);
        grid.getColumns().forEach(parkingColumn -> parkingColumn.setAutoWidth(true));
    }


    @Override
    public void updateList(List<ParkingDTO> parkings) {
        grid.setItems(getPresenter().getAllParkings());
    }

    private void saveParking(ParkingAddDialog.SaveEvent event) {
        if (getPresenter().createParking(event.getParking())){
            updateList(getPresenter().getAllParkings());
        } else {
            getNotification();
        }

    }

    private void deleteParking(ParkingEditForm.DeleteEvent event) {
        getPresenter().deleteParking(event.getParking());
        updateList(getPresenter().getAllParkings());
    }

    private void updateParking(ParkingEditForm.SaveEvent event) {
        getPresenter().updateParking(event.getParking());
        updateList(getPresenter().getAllParkings());
    }


//    private void deleteById(ParkingDeleteDialog.DeleteEvent event){
//        getPresenter().deleteById(event.getIdForDelete());
//        updateList(getPresenter().getAllParkings());
//    }
    private void closeEditor() {
        parkingEditForm.setParking(null);
        parkingEditForm.setVisible(false);
        removeClassName("editing");
    }

    private Notification getNotification (){
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.MIDDLE);
        Div text = new Div(new Text("Failed to create Parking, invalid input for Zip Code must be digit with 4 characters"));

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
