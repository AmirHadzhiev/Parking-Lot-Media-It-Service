package de.bonprix.playground.components.addDialogs;

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
import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;


import java.util.List;

public class CarAddDialog  extends Dialog{


    TextField plateNumberField = new TextField("Plate Number");

    Binder<CarDTO> binder = new BeanValidationBinder<>(CarDTO.class);

    ComboBox<ParkingPlaceDTO> parkingPlaces = new ComboBox<>("Free Parking PLaces");
    private Long placeId ;

    public CarAddDialog(List<ParkingPlaceDTO> parkingPlaceDTOS) {

        binder.forField(plateNumberField).asRequired().bind(CarDTO::getPlateNumber, CarDTO::setPlateNumber);

        binder.bindInstanceFields(this);
        parkingPlaces.setItems(parkingPlaceDTOS);
        parkingPlaces.setItemLabelGenerator(ParkingPlaceDTO::getNumber);
        parkingPlaces.addValueChangeListener(c -> placeId=c.getValue().getId() );


        H2 headline = new H2("Create new Car");


        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");



        VerticalLayout fieldLayout = new VerticalLayout(plateNumberField,parkingPlaces);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);


        Button cancelButton = new Button("Cancel", e -> {
            this.close();
            //plateNumberField.clear();
           // parkingPlaces.clear();
            UI.getCurrent().getPage().reload();
        });

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(buttonClickEvent -> {

         CarDTO carDTO = new CarDTO();
            try {
                binder.writeBean(carDTO);
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            fireEvent(new CarAddDialog.SaveEvent(this,carDTO,placeId));
            this.close();
           // plateNumberField.clear();
           // parkingPlaces.clear();
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

        binder.addStatusChangeListener(event -> saveButton.setEnabled(binder.isValid()));
    }


    public static abstract class ParkingDialogEvent extends ComponentEvent<Dialog> {


        private CarDTO carDTO;

        private Long placeId;


        public ParkingDialogEvent(Dialog source, CarDTO carDTO,Long id) {
            super(source, false);
            this.carDTO = carDTO;
            this.placeId=id;

        }


        public CarDTO getCarDTO() {
            return carDTO;
        }

        public Long getPlaceId() {
            return placeId;
        }
    }

    public static class SaveEvent extends CarAddDialog.ParkingDialogEvent {

        public SaveEvent(Dialog source, CarDTO carDTO , Long placeId) {
            super(source, carDTO, placeId);
        }
    }



   // public static class CloseEvent extends CarAddDialog.ParkingDialogEvent {
     //   CloseEvent(Dialog source) {
       //     super(source, null);
       // }
  //  }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
