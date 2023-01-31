package de.bonprix.playground.components.deleteDialogs;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;

public class ParkingZoneDeleteDialog extends Dialog {
    TextField idField = new TextField("Parking Zone Id");



    public ParkingZoneDeleteDialog() {


        H2 headline = new H2("Delete Parking Zone by Id");


        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");



        VerticalLayout fieldLayout = new VerticalLayout(idField);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);



        Button cancelButton = new Button("Cancel", e ->  this.close());

        Button deleteButton = new Button("Delete", e -> this.close());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addClickListener(buttonClickEvent -> {

            Long idForDelete = Long.valueOf(idField.getValue());

            fireEvent(new ParkingZoneDeleteDialog.DeleteEvent(this,idForDelete) );
            this.close();
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton,
                deleteButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

        add(dialogLayout);


    }
    public static abstract class ParkingDialogEvent extends ComponentEvent<Dialog> {


        // private ParkingDTO parking;
        Long idForDelete;
        public ParkingDialogEvent(Dialog source, Long id) {
            super(source, false);
            // this.parking = parking;
            this.idForDelete=id;

        }

        public Long getIdForDelete() {
            return idForDelete;
        }

    }



    public static class DeleteEvent extends ParkingZoneDeleteDialog.ParkingDialogEvent {
        DeleteEvent(Dialog source, Long id) {
            super(source, id);
        }

    }

    public static class CloseEvent extends ParkingZoneDeleteDialog.ParkingDialogEvent {
        CloseEvent(Dialog source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
