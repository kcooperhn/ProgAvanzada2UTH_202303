package com.uth.hh.views.campus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.uth.hh.controller.CampusInteractor;
import com.uth.hh.controller.CampusInteractorImpl;
import com.uth.hh.data.Campus;
import com.uth.hh.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
public class CampusView extends Div implements BeforeEnterObserver, CampusViewModel {

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
    private final Button delete = new Button("Eliminar", new Icon(VaadinIcon.TRASH));
    
    private ConfirmDialog deleteDialog;

    private Campus campus;
    private CampusInteractor controlador;
    private List<Campus> elementos;

    public CampusView() {
        addClassNames("campus-view");
        
        controlador = new CampusInteractorImpl(this);
        this.elementos = new ArrayList<>();

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

        controlador.consultarCampus();
        
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.campus == null) {
                    this.campus = new Campus();
                    //ESTOY CREANDO UN NUEVO CAMPUS
                    
                    this.campus.setNombre(this.nombre.getValue());
                    this.campus.setDepartamento(this.departamento.getValue());
                    this.campus.setCiudad(this.ciudad.getValue());
                    this.campus.setDireccion(this.direccion.getValue());
                    this.campus.setTelefono(this.telefono.getValue());
                    
                    this.controlador.crearCampus(campus);
                }else {
                	//ESTOY ACTUALIZANDO UNO QUE YA EXISTE
                	this.campus.setNombre(this.nombre.getValue());
                    this.campus.setDepartamento(this.departamento.getValue());
                    this.campus.setCiudad(this.ciudad.getValue());
                    this.campus.setDireccion(this.direccion.getValue());
                    this.campus.setTelefono(this.telefono.getValue());
                    
                    this.controlador.actualizarCampus(campus);
                }
                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(CampusView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        
        delete.addClickListener( e -> {
        	deleteDialog.open();
        });
        delete.setEnabled(false);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> campusId = event.getRouteParameters().get(CAMPUS_ID).map(Integer::parseInt);
        if (campusId.isPresent()) {
            Campus campusFromBackend = obtenerCampus(campusId.get());
            if (campusFromBackend != null) {
                populateForm(campusFromBackend);
            } else {
                Notification.show(String.format("El campus con ID = %s no existe", campusId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(CampusView.class);
            }
        }
    }

    private Campus obtenerCampus(Integer campusId) {
    	Campus campusEncontrado = null;
		for (Campus campus : elementos) {
			if(campus.getId() == campusId) {
				campusEncontrado = campus;
				break;
			}
		}
		return campusEncontrado;
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
        
        deleteDialog = new ConfirmDialog();
        deleteDialog.setHeader("¿Desea eliminar este campus?");
        deleteDialog.setText(
                "Confirma la eliminación del campus seleccionado");

        deleteDialog.setCancelable(true);
        deleteDialog.setCancelText("Cancelar");
        deleteDialog.setConfirmText("Eliminar");
        deleteDialog.setConfirmButtonTheme("error primary");
        deleteDialog.addConfirmListener(event -> {
        	this.controlador.eliminarCampus(this.campus.getId());
        	refreshGrid();
        });

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        buttonLayout.add(save, cancel, delete);
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
        this.controlador.consultarCampus();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Campus value) {
        this.campus = value;
        if(value == null) {
        	this.nombre.setValue("");
            this.departamento.setValue("");
            this.ciudad.setValue("");
            this.direccion.setValue("");
            this.telefono.setValue("");
            delete.setEnabled(false);
        }else {
        	this.nombre.setValue(value.getNombre());
            this.departamento.setValue(value.getDepartamento());
            this.ciudad.setValue(value.getCiudad());
            this.direccion.setValue(value.getDireccion());
            this.telefono.setValue(value.getTelefono());
            delete.setEnabled(true);
        }
    }

	@Override
	public void mostrarCampusEnGrid(List<Campus> items) {
		Collection<Campus> itemsCollection = items;
		grid.setItems(itemsCollection);
		this.elementos = items;
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		Notification.show(mensaje);
	}

	@Override
	public void mostrarMensajeExito(String mensaje) {
		Notification.show(mensaje);
	}
}
