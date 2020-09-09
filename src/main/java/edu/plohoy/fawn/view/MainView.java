package edu.plohoy.fawn.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import edu.plohoy.fawn.dao.EmployeeDao;
import edu.plohoy.fawn.domain.Employee;

@Route
public class MainView extends VerticalLayout {

    private EmployeeDao dao;
    private Grid<Employee> grid;

    public MainView(EmployeeDao dao) {
        this.dao = dao;

        grid = new Grid<>();
        add(grid);

        grid.setItems(dao.findAll());
    }
}
