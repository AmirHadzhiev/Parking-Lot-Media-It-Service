package de.bonprix.playground.ui;

import com.vaadin.flow.component.Text;
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
import de.bonprix.dto.CarDTO;
import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.playground.CarView;
import de.bonprix.playground.components.addDialogs.CarAddDialog;
import de.bonprix.playground.components.addDialogs.CarParkDialog;
import de.bonprix.playground.components.deleteDialogs.CarDeleteDialog;
import de.bonprix.playground.forms.EditForms.CarEditForm;
import de.bonprix.playground.forms.EditForms.ParkingEditForm;
import de.bonprix.vaadin.components.menu.ModuleView;
import de.bonprix.vaadin.mvp.BaseView;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ModuleView(
        name = "Car", position = 5, route = "Cars"
)
public class CarViewImpl extends BaseView<CarView.Presenter> implements CarView {
    private static final long serialVersionUID = 1L;
    Button button = new Button("Add new car");


    Grid<CarDTO> grid = new Grid<>(CarDTO.class,false);
    CarAddDialog carAddDialog;
    CarParkDialog carParkDialog;


    Button buttonAddCar = new Button("Add car", e -> carAddDialog.open());
    Button buttonParkCar = new Button("Park car", e -> carParkDialog.open());

    CarEditForm carEditForm;





    @Override
    protected void init() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        addClassName("list-view");
        setSizeFull();
        buttonAddCar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        carAddDialog = new CarAddDialog(getAllValidParkingPlaces());
        carAddDialog.addListener(CarAddDialog.SaveEvent.class, this::saveCar);

        buttonParkCar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        carParkDialog = new CarParkDialog(getAllValidParkingPlaces(),getAllValidCars());
        carParkDialog.addListener(CarParkDialog.SaveEvent.class, this::parkCar);



        carEditForm = new CarEditForm();
        carEditForm.setWidth("25em");
        carEditForm.addListener(CarEditForm.SaveEvent.class, this::updateCar);
        carEditForm.addListener(CarEditForm.DeleteEvent.class, this::deleteCar);
        carEditForm.addListener(CarEditForm.CloseEvent.class, event -> closeEditor());


        configureGrid();
        horizontalLayout.add(buttonAddCar,buttonParkCar);


        FlexLayout flexLayout = new FlexLayout(grid, carEditForm);

        flexLayout.setFlexGrow(2, grid);
        flexLayout.setFlexGrow(1, carEditForm);
        flexLayout.setFlexShrink(0,carEditForm);
        flexLayout.setSizeFull();

        add(horizontalLayout,flexLayout);
        updateList(getPresenter().getAllCars());
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event -> editParking(event.getValue()));


    }
    private void updateCar(CarEditForm.SaveEvent event) {
        getPresenter().updateParking(event.getCar());
        updateList(getPresenter().getAllCars());
    }

    private void deleteCar(CarEditForm.DeleteEvent event) {
        getPresenter().deleteParking(event.getCar());
        updateList(getPresenter().getAllCars());
    }
    private void closeEditor() {
        carEditForm.setParking(null);
        carEditForm.setVisible(false);
        removeClassName("editing");
    }
    private void configureGrid() {
        grid.addClassNames("parking-grid");
        grid.setSizeFull();
        grid.addColumn(CarDTO::getId).setHeader("ID").setSortable(true);
        grid.addColumn(CarDTO::getPlateNumber).setHeader("Plate Number").setSortable(true);
        grid.addColumn(carDTO -> {
            for (ParkingPlaceDTO place : getPresenter().getAllParkingPlaces()) {
                if (place.getCar()!=null){
                   if (place.getCar().getId()==carDTO.getId()){
                        return carDTO.getId();
                    }
                }
            }
            return "Car is not Parked";
        }).setHeader("Parking Place").setSortable(true);

        grid.getColumns().forEach(parkingColumn -> parkingColumn.setAutoWidth(true));
    }

    private void editParking(CarDTO car) {
        if (car == null) {
            closeEditor();
        } else {
            carEditForm.setParking(car)
            ;
            carEditForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveCar(CarAddDialog.SaveEvent event){
       if (getPresenter().createCar(event.getCarDTO(),event.getPlaceId())){
           updateList(getPresenter().getAllCars());

       } else {
           getNotification();
       }
    }
    private void parkCar(CarParkDialog.SaveEvent event){
        getPresenter().parkCar(event.getParkingPlaceId(),event.getCarId());
        updateList(getPresenter().getAllCars());
    }

    @Override
    public void updateList(List<CarDTO> cars) {
        grid.setItems(getPresenter().getAllCars());
    }

//    @Override
//    public void updateListParkingPlace(List<ParkingPlaceDTO> places) {
//        validPlaces.addAll( getAllValidParkingPlaces());
//    }


    void deleteById(CarDeleteDialog.DeleteEvent event){
        getPresenter().deleteById(event.getIdForDelete());
        updateList(getPresenter().getAllCars());
    }

    private List<ParkingPlaceDTO> getAllParkingPlaces(){
       return getPresenter().getAllParkingPlaces();
    }

    private List<CarDTO> getAllCars(){
        return getPresenter().getAllCars();
    }

       private List<CarDTO> getAllValidCars(){
         List<CarDTO> placeCars = getAllParkingPlaces().stream()
                .filter(ParkingPlace -> ParkingPlace.getCar() != null)
                .map(ParkingPlaceDTO::getCar)
                .collect(Collectors.toList());
           List<CarDTO> validCars = getAllCars();
           validCars.removeAll(placeCars);
           return validCars;
    }

   private List<ParkingPlaceDTO> getAllValidParkingPlaces(){
        return getPresenter().getAllParkingPlaces().stream()
                .filter(parkingPlaceDTO -> parkingPlaceDTO.getCar()==null)
                .collect(Collectors.toList());
    }

    private Notification getNotification (){
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.MIDDLE);
        Div text = new Div(new Text("Failed to create Car, Plate number must be unique and between 8 and 10 characters"));

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
