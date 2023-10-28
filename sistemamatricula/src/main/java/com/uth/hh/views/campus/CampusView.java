package com.uth.hh.views.campus;

import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.uth.hh.data.Campus;
import com.uth.hh.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Campus")
@Route(value = "campus/:campusID?/:action?(edit)", layout = MainLayout.class)
public class CampusView extends Div implements BeforeEnterObserver {

    private final String CAMPUS_ID = "campusID";
    private final String CAMPUS_EDIT_ROUTE_TEMPLATE = "campus/%s/edit";

    private final Grid<Campus> grid = new Grid<>(Campus.class, false);

    private TextField nombre;
    private TextField departamento;
    private TextField ciudad;
    private TextField direccion;
    private TextField telefono;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    private final BeanValidationBinder<Campus> binder;

    private Campus campus;

    public CampusView() {
        addClassNames("campus-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);//AGREGA CONTROLES DIRECTAMENTE A LA PANTALLA

        // Configure Grid
        grid.addColumn("nombre").setAutoWidth(true).setHeader("Nombre del Campus");
        grid.addColumn("departamento").setAutoWidth(true);
        grid.addColumn("ciudad").setAutoWidth(true);
        grid.addColumn("direccion").setAutoWidth(true).setHeader("Dirección");
        grid.addColumn("telefono").setAutoWidth(true).setHeader("Teléfono");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CAMPUS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(CampusView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Campus.class);

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.campus == null) {
                    this.campus = new Campus();
                }
                binder.writeBean(this.campus);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(CampusView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> campusId = event.getRouteParameters().get(CAMPUS_ID).map(Long::parseLong);
        if (campusId.isPresent()) {
            /*Optional<Campus> campusFromBackend = campusService.get(campusId.get());
            if (campusFromBackend.isPresent()) {
                populateForm(campusFromBackend.get());
            } else {
                Notification.show(String.format("The requested alumno was not found, ID = %s", campusId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(CampusView.class);
            }*/
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nombre = new TextField("Nombre");
        departamento = new TextField("Departamento");
        ciudad = new TextField("Ciudad");
        direccion = new TextField("Dirección");
        telefono = new TextField("Teléfono");
        formLayout.add(nombre, departamento, ciudad, direccion, telefono);
        //AGREGA CONTROLES DENTRO DE OTRO CONTROL

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Campus value) {
        this.campus = value;
        binder.readBean(this.campus);

    }
}
