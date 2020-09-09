package edu.plohoy.fawn.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import edu.plohoy.fawn.dao.EmployeeDao;
import edu.plohoy.fawn.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class MainView extends VerticalLayout {

    private final EmployeeDao dao;
    private Grid<Employee> grid = new Grid<>();

    @Autowired
    public MainView(EmployeeDao dao) {
        this.dao = dao;

        add(grid);

        grid.setItems(dao.findAll());
    }
}
