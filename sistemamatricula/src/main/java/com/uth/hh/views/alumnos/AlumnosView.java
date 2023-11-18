package com.uth.hh.views.alumnos;

import com.uth.hh.controller.AlumnosInteractor;
import com.uth.hh.controller.AlumnosInteractorImpl;
import com.uth.hh.controller.CampusInteractor;
import com.uth.hh.controller.CampusInteractorImpl;
import com.uth.hh.data.Alumno;
import com.uth.hh.data.Campus;
import com.uth.hh.views.MainLayout;
import com.uth.hh.views.campus.CampusView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Alumnos")
@Route(value = "alumnos/:alumnoID?/:action?(edit)", layout = MainLayout.class)
public class AlumnosView extends Div implements BeforeEnterObserver, AlumnosViewModel {

    private final String ALUMNO_ID = "alumnoID";
    private final String ALUMNO_EDIT_ROUTE_TEMPLATE = "alumnos/%s/edit";

    private final Grid<Alumno> grid = new Grid<>(Alumno.class, false);

    private NumberField numerocuenta;
    private TextField nombre;
    private TextField apellido;
    private ComboBox<String> genero;
    private EmailField email;
    private TextField telefono;
    private ComboBox<Campus> campus;

    private final Button cancel = new Button("Cancelar", new Icon(VaadinIcon.CLOSE));
    private final Button save = new Button("Guardar", new Icon(VaadinIcon.CHECK));
    private final Button delete = new Button("Eliminar", new Icon(VaadinIcon.TRASH));
    private ConfirmDialog deleteDialog;
    
    private Alumno alumno;
    private AlumnosInteractor controladorAlumnos;
    private List<Alumno> elementos;

    public AlumnosView() {
        addClassNames("alumnos-view");

        controladorAlumnos = new AlumnosInteractorImpl(this);
        this.elementos = new ArrayList<>();
        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);//AGREGA CONTROLES DIRECTAMENTE A LA PANTALLA

        // Configure Grid
        grid.addColumn("numerocuenta").setAutoWidth(true).setHeader("Número de Cuenta");
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("apellido").setAutoWidth(true);
        grid.addColumn("genero").setAutoWidth(true).setHeader("Género");
        grid.addColumn("email").setAutoWidth(true).setHeader("Correo Electrónico");
        grid.addColumn("telefono").setAutoWidth(true).setHeader("Teléfono");
        grid.addColumn("campus").setAutoWidth(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(ALUMNO_EDIT_ROUTE_TEMPLATE, event.getValue().getNumerocuenta()));
            } else {
                clearForm();
                UI.getCurrent().navigate(AlumnosView.class);
            }
        });

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
        
        save.setId("btnguardaralumno");
      
        save.addClickListener(e -> {
            try {
            	if (this.alumno == null) {
                    this.alumno = new Alumno();
                    //ESTOY CREANDO UN NUEVO CAMPUS
                    
                    this.alumno.setNombre(this.nombre.getValue());
                    this.alumno.setApellido(this.apellido.getValue());
                    this.alumno.setNumerocuenta(this.numerocuenta.getValue().toString());
                    this.alumno.setTelefono(this.telefono.getValue());
                    this.alumno.setEmail(this.email.getValue());
                    this.alumno.setGenero(this.genero.getValue());
                    
                    this.controladorAlumnos.crearAlumno(alumno);
                }else {
                	//ESTOY ACTUALIZANDO UNO QUE YA EXISTE
                    this.alumno.setNombre(this.nombre.getValue());
                    this.alumno.setApellido(this.apellido.getValue());
                    this.alumno.setTelefono(this.telefono.getValue());
                    this.alumno.setEmail(this.email.getValue());
                    this.alumno.setGenero(this.genero.getValue());
                    
                    this.controladorAlumnos.actualizarAlumno(alumno);
                }
                clearForm();
                refreshGrid();
                UI.getCurrent().navigate(AlumnosView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        
        delete.addClickListener( e -> {
        	this.deleteDialog.open();
        });
        
        this.controladorAlumnos.consultarAlumnos();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    	 Optional<String> alumnoId = event.getRouteParameters().get(ALUMNO_ID);
         if (alumnoId.isPresent()) {
             Alumno alumnoFromBackend = obtenerAlumno(alumnoId.get());
             if (alumnoFromBackend != null) {
                 populateForm(alumnoFromBackend);
             } else {
                 Notification.show(String.format("El alumno con ID = %s no existe", alumnoId.get()), 3000,
                         Notification.Position.BOTTOM_START);
                 refreshGrid();
                 event.forwardTo(AlumnosView.class);
             }
         }
    }

    private Alumno obtenerAlumno(String numerocuenta) {
    	Alumno alumnoEncontrado = null;
		for (Alumno alumno : elementos) {
			if(alumno.getNumerocuenta().equals(numerocuenta)) {
				alumnoEncontrado = alumno;
				break;
			}
		}
		return alumnoEncontrado;
	}

	private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        
        numerocuenta = new NumberField();
        numerocuenta.setId("txtnumerocuenta");
        numerocuenta.setLabel("Número de Cuenta");
        numerocuenta.setValue(0.0);
        
        nombre = new TextField("Nombre del Alumno");
        nombre.setId("txtnombre");
        nombre.setPrefixComponent(VaadinIcon.USER.create());
        
        apellido = new TextField("Apellido del Alumno");
        apellido.setPrefixComponent(VaadinIcon.USER_CARD.create());
        
        genero = new ComboBox<>("Genero");
        genero.setAllowCustomValue(true);
        genero.setItems("Masculino", "Femenino");
        genero.setHelperText("Seleccione el genero del alumno");
        
        email = new EmailField();
        email.setLabel("Correo Electrónico");
        email.getElement().setAttribute("name", "email");
        email.setValue("");
        email.setErrorMessage("Ingresa un correo electrónico valido");
        email.setClearButtonVisible(true);
        
        telefono = new TextField("Teléfono");
        
        telefono.setRequiredIndicatorVisible(true);
        telefono.setPattern("^[+]?[(]?[0-9]{3}[)]?[-s.]?[0-9]{4}[-s.]?[0-9]{4}$");
        telefono.setAllowedCharPattern("[0-9()+-]");
        telefono.setMinLength(8);
        telefono.setMaxLength(12);
        
        ComboBox<Campus> campus = new ComboBox<>("Campus");
        campus.setItemLabelGenerator(Campus::getNombre);
        
        
        deleteDialog = new ConfirmDialog();
        deleteDialog.setHeader("¿Desea eliminar este alumno?");
        deleteDialog.setText(
                "Confirma la eliminación del alumno seleccionado");

        deleteDialog.setCancelable(true);
        deleteDialog.setCancelText("Cancelar");
        deleteDialog.setConfirmText("Eliminar");
        deleteDialog.setConfirmButtonTheme("error primary");
        deleteDialog.addConfirmListener(event -> {
        	this.controladorAlumnos.eliminarAlumno(this.alumno.getNumerocuenta());
        	refreshGrid();
        });
        
        
        
        formLayout.add(numerocuenta, nombre, apellido, genero, email, telefono, campus);
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
        this.controladorAlumnos.consultarAlumnos();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Alumno value) {
        this.alumno = value;

        if(value == null) {
        	this.nombre.setValue("");
            this.apellido.setValue("");
            this.numerocuenta.setValue(0.0);
            this.genero.setValue("");
            this.telefono.setValue("");
            this.email.setValue("");
            this.campus.clear();
            delete.setEnabled(false);
        }else {
            this.nombre.setValue(value.getNombre());
            this.apellido.setValue(value.getApellido());
            this.numerocuenta.setValue(Double.parseDouble(value.getNumerocuenta()));
            //this.genero.setValue(value.getGenero());
            this.telefono.setValue(value.getTelefono());
            this.email.setValue(value.getEmail());
            //this.campus.setValue(value.getCampus());
            delete.setEnabled(true);
        }
    }

	@Override
	public void mostrarAlumnosEnGrid(List<Alumno> items) {
		Collection<Alumno> itemsCollection = items;
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
