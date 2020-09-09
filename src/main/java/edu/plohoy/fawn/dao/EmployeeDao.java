package edu.plohoy.fawn.dao;

import edu.plohoy.fawn.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

    @Query("from Employee e " +
            "where concat(e.lastName, ' ', e.firstName, ' ', e.patronymic) " +
            "like concat('%', :name, '%')")
    List<Employee> findByName(@Param("name") String name);
}
