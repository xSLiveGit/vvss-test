package salariati.test;

import org.junit.Test;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;
import salariati.model.Employee;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.validator.EmployeeValidator;

import static org.junit.Assert.assertTrue;

public class Lab4test {
    private EmployeeRepositoryInterface employeeRepository;
    private EmployeeController controller;
    private EmployeeValidator employeeValidator;

    @Test
    public void add() {
        Employee employee = new Employee("ValidLastName", "1910509055057", DidacticFunction.ASISTENT, "3000");

        assertTrue(employeeValidator.isValid(employee));
    }
}
