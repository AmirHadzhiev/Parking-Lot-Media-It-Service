package de.bonprix.playground.components.addDialogs;


import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import de.bonprix.dto.CarDTO;
import de.bonprix.dto.ParkingPlaceDTO;

import java.util.List;

public class CarParkDialog extends Dialog {

    Binder<CarDTO> binderCars = new BeanValidationBinder<>(CarDTO.class);

    ComboBox<CarDTO> cars = new ComboBox<>("Not Parked Cars");

    Binder<ParkingPlaceDTO> binderParkingPlace = new BeanValidationBinder<>(ParkingPlaceDTO.class);
    ComboBox<ParkingPlaceDTO> parkingPlaces = new ComboBox<>("Free Parking PLaces");

    private Long carId ;
    private Long parkingPlaceId ;
    public CarParkDialog(List<ParkingPlaceDTO> parkingPlaceDTOS, List<CarDTO> carDTOS) {



        parkingPlaces.setItems(parkingPlaceDTOS);
        parkingPlaces.setItemLabelGenerator(ParkingPlaceDTO::getNumber);
        parkingPlaces.addValueChangeListener(parkingPlaceDTO -> parkingPlaceId = parkingPlaceDTO.getValue().getId());




        cars.setItems(carDTOS);
        cars.setItemLabelGenerator(CarDTO::getPlateNumber);
       cars.addValueChangeListener(carDTO -> carId= carDTO.getValue().getId());




        H2 headline = new H2("Create new Car");


        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");



        VerticalLayout fieldLayout = new VerticalLayout(cars,parkingPlaces);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);


        Button cancelButton = new Button("Cancel", e ->  {
            this.close();
            UI.getCurrent().getPage().reload();
        });

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(buttonClickEvent -> {

            fireEvent(new CarParkDialog.SaveEvent(this,parkingPlaceId,carId));
            this.close();
            UI.getCurrent().getPage().reload();
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        add(dialogLayout);

        binderCars.addStatusChangeListener(event -> saveButton.setEnabled(binderCars.isValid()));
        binderParkingPlace.addStatusChangeListener(event ->saveButton.setEnabled(binderParkingPlace.isValid()));
    }


    public static abstract class ParkingDialogEvent extends ComponentEvent<Dialog> {

        private Long carId ;
        private Long parkingPlaceId ;



        public ParkingDialogEvent(Dialog source,Long carId,Long parkingPlaceId) {
            super(source, false);
            this.carId = carId;
            this.parkingPlaceId = parkingPlaceId;

        }

        public Long getCarId() {
            return carId;
        }

        public Long getParkingPlaceId() {
            return parkingPlaceId;
        }


    }

    public static class SaveEvent extends CarParkDialog.ParkingDialogEvent {

        public SaveEvent(Dialog source, Long carId,Long parkingPlaceId) {
            super(source, carId,parkingPlaceId);
        }
    }



//     public static class CloseEvent extends CarParkDialog.ParkingDialogEvent {
//       CloseEvent(Dialog source) {
//         super(source, null);
//     }
//      }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
