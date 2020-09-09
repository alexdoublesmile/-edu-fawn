package edu.plohoy.fawn.dao;

import edu.plohoy.fawn.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
}
