package edu.plohoy.fawn.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import edu.plohoy.fawn.component.EmployeeEditor;
import edu.plohoy.fawn.dao.EmployeeDao;
import edu.plohoy.fawn.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

    private final EmployeeDao dao;
    private Grid<Employee> grid;

    private final TextField filter = new TextField("Type to filter");
    private final Button button = new Button("Add new");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, button);

    private final EmployeeEditor editor;

    @Autowired
    public MainView(EmployeeDao dao, EmployeeEditor editor) {
        this.dao = dao;
        this.editor = editor;

        grid = new Grid<>(Employee.class);

        add(toolbar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showEmployee(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editEmployee(e.getValue());
        });

        button.addClickListener(e -> editor.editEmployee(new Employee()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showEmployee(filter.getValue());
        });

        showEmployee("");
    }

    private void showEmployee(String name) {
        if (StringUtils.isEmpty(name)) {
            grid.setItems(dao.findAll());
        } else {
            grid.setItems(dao.findByName(name));
        }
    }
}
