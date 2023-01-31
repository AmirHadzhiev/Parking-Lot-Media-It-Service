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
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;

import de.bonprix.dto.CarDTO;
import de.bonprix.dto.ParkingDTO;
import de.bonprix.dto.ParkingPlaceDTO;
import de.bonprix.dto.ParkingZoneDTO;

import java.util.List;

public class ParkingPlaceAddDialog extends Dialog {
    TextField numberField = new TextField("Number");

    Binder<ParkingPlaceDTO> binder = new BeanValidationBinder<>(ParkingPlaceDTO.class);

    ComboBox<ParkingZoneDTO> parkingZones = new ComboBox<>("Zones");

    public ParkingPlaceAddDialog(List<ParkingZoneDTO> zoneDTOList) {
        binder.forField(numberField).asRequired().bind(ParkingPlaceDTO::getNumber, ParkingPlaceDTO::setNumber);
        binder.forField(parkingZones).asRequired().bind(ParkingPlaceDTO::getParkingZone,ParkingPlaceDTO::setParkingZone);


        binder.bindInstanceFields(this);
        parkingZones.setItems(zoneDTOList);
        parkingZones.setItemLabelGenerator(ParkingZoneDTO::getName);




        H2 headline = new H2("Create new Parking Place");


        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");



        VerticalLayout fieldLayout = new VerticalLayout(numberField,parkingZones);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);




        Button cancelButton = new Button("Cancel", e -> {
            this.close();
            //numberField.clear();
            UI.getCurrent().getPage().reload();
        });

        Button saveButton = new Button("Save", e -> fireEvent(new ParkingPlaceAddDialog.CloseEvent(this)));
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(buttonClickEvent -> {
            ParkingPlaceDTO parkingPlaceDTO = new ParkingPlaceDTO();
            try {
                binder.writeBean(parkingPlaceDTO);
              //  parkingPlaceDTO.setCar(new CarDTO());
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            fireEvent(new ParkingPlaceAddDialog.SaveEvent(this,parkingPlaceDTO));
            this.close();
           // numberField.clear();
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


        private ParkingPlaceDTO parkingPlaceDTO;
        public ParkingDialogEvent(Dialog source, ParkingPlaceDTO parkingPlaceDTO) {
            super(source, false);
            this.parkingPlaceDTO = parkingPlaceDTO;
        }

        public ParkingPlaceDTO getParking() {
            return parkingPlaceDTO;
        }
    }

    public static class SaveEvent extends ParkingPlaceAddDialog.ParkingDialogEvent {

        public SaveEvent(Dialog source, ParkingPlaceDTO parkingPlaceDTO) {
            super(source, parkingPlaceDTO);
        }
    }

    public static class DeleteEvent extends ParkingPlaceAddDialog.ParkingDialogEvent {
        DeleteEvent(Dialog source, ParkingPlaceDTO parkingPlaceDTO) {
            super(source, parkingPlaceDTO);
        }

    }

    public static class CloseEvent extends ParkingPlaceAddDialog.ParkingDialogEvent {
        CloseEvent(Dialog source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
