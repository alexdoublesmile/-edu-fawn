package edu.plohoy.fawn.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import edu.plohoy.fawn.dao.EmployeeDao;
import edu.plohoy.fawn.domain.Employee;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class EployeeEditor extends VerticalLayout implements KeyNotifier {
    private final EmployeeDao dao;
    private Employee employee;

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField patronymic = new TextField("Patronymic");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Employee> binder = new Binder<>(Employee.class);

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public EployeeEditor(EmployeeDao dao) {
        this.dao = dao;

        add(firstName, lastName, patronymic, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editEmployee(employee));

        setVisible(false);
    }

    private void editEmployee(Employee newEmp) {
        if (newEmp == null) {
            setVisible(false);
            return;
        }

        if (newEmp.getId() != null) {
            employee = dao.findById(newEmp.getId())
                    .orElse(newEmp);
        } else {
            employee = newEmp;
        }

        binder.setBean(employee);

        setVisible(true);

        lastName.focus();
    }

    private void delete() {
        dao.delete(employee);
        changeHandler.onChange();
    }

    private void save() {
        dao.save(employee);
        changeHandler.onChange();
    }
}
