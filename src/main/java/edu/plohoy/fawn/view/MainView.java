package edu.plohoy.fawn.view;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import edu.plohoy.fawn.dao.EmployeeDao;
import edu.plohoy.fawn.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

    private final EmployeeDao dao;
    private Grid<Employee> grid;
    
    @Autowired
    public MainView(EmployeeDao dao) {
        this.dao = dao;
        grid = new Grid<>(Employee.class);

        add(grid);

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
