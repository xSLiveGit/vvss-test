package salariati.test;


import static org.junit.Assert.*;

import salariati.exception.EmployeeException;
import salariati.model.Employee;

import org.junit.Before;
import org.junit.Test;

import salariati.repository.implementations.EmployeeImpl;
import salariati.repository.interfaces.EmployeeRepositoryInterface;
import salariati.repository.mock.EmployeeMock;
import salariati.validator.EmployeeValidator;
import salariati.controller.EmployeeController;
import salariati.enumeration.DidacticFunction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class WhiteBoxAddEmployeeTest {
    private EmployeeRepositoryInterface employeeRepository;
    private EmployeeController controller;
    private EmployeeValidator employeeValidator;

    @Before
    public void setUp() throws FileNotFoundException, EmployeeException {
        PrintWriter writer = new PrintWriter(new File("employeeDB/test-employee.txt"));
        writer.print("");
        writer.close();

        employeeRepository = new EmployeeImpl("employeeDB/test-employee.txt");
        employeeValidator  = new EmployeeValidator();
        controller         = new EmployeeController(employeeRepository, employeeValidator);

    }

    @Test
    public void testAddInvalidEmployee() throws EmployeeException {
        Employee employee = new Employee("Sergiu","1953423423453",DidacticFunction.ASISTENT, "543");
        employeeRepository.addEmployee(employee);

        String[] attrs = new String[4];
        attrs[0] = "ValidLastName";
        attrs[1] = "19105045353453463456346345349055057";
        attrs[2] = "ASISTENT";
        attrs[3] = "3000";
        try {
            controller.addEmployee(attrs);
            assertFalse(true);
        } catch (EmployeeException e) {
            assertEquals(e.getMessage(), "Invalid employee");
        }
    }

    @Test
    public void testAddValidEmployeeUsingEmptylist(){
        String[] attrs = new String[4];
        attrs[0] = "Sergiu";
        attrs[1] = "1953423423453";
        attrs[2] = "ASISTENT";
        attrs[3] = "3000";
        try {
            controller.addEmployee(attrs);
            assertFalse(false);
        } catch (EmployeeException e) {
            assertFalse(true);
        }
    }

    @Test
    public void testAddUserWithExistingCnp() throws EmployeeException {
        Employee employee = new Employee("Sergiu","1953423423453",DidacticFunction.ASISTENT, "543");
        employeeRepository.addEmployee(employee);

        String[] attrs = new String[4];
        attrs[0] = "aa";
        attrs[1] = "1953423423453";
        attrs[2] = "ASISTENT";
        attrs[3] = "3560";
        try {
            controller.addEmployee(attrs);
            assertFalse(false);
        } catch (EmployeeException e) {
            assertEquals(e.getMessage(),"There's already an employee with this cnp");
        }
    }

    @Test
    public void testAddUserInNonemptyList() throws EmployeeException {
        Employee employee = new Employee("Sergiu","1953423423453",DidacticFunction.ASISTENT, "543");
        employeeRepository.addEmployee(employee);

        String[] attrs = new String[4];
        attrs[0] = "aa";
        attrs[1] = "1953423423450";
        attrs[2] = "ASISTENT";
        attrs[3] = "360";
        try {
            controller.addEmployee(attrs);
            assertFalse(false);
        } catch (EmployeeException e) {
            assertFalse(true);
        }
    }

}
