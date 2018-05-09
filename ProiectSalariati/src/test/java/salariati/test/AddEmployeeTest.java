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

public class AddEmployeeTest {

	private EmployeeRepositoryInterface employeeRepository;
	private EmployeeController controller;
	private EmployeeValidator employeeValidator;
	
	@Before
	public void setUp() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("employeeDB/test-employee.txt"));
        writer.print("");
        writer.close();

		employeeRepository = new EmployeeImpl("employeeDB/test-employee.txt");
		employeeValidator  = new EmployeeValidator();
		controller         = new EmployeeController(employeeRepository, employeeValidator);
	}
	
//	@Test
//	public void testRepositoryMock() {
//		assertTrue(controller.getEmployeesList().isEmpty());
//		assertEquals(0, controller.getEmployeesList().size());
//	}
	
	@Test
	public void testAddValidEmployee() {
		Employee newEmployee = new Employee("ValidLastName", "1910509055057", DidacticFunction.ASISTENT, "3000");
		assertTrue(employeeValidator.isValid(newEmployee));
		String[] attrs = new String[4];
		attrs[0] = "ValidLastName";
		attrs[1] = "1910509055057";
		attrs[2] = "ASISTENT";
		attrs[3] = "3000";
		try {
			controller.addEmployee(attrs);
			assertEquals(1, controller.getEmployeesList().size());
			assertTrue(newEmployee.equals(controller.getEmployeesList().get(controller.getEmployeesList().size() - 1)));
		} catch (EmployeeException e) {
			e.printStackTrace();
			assertFalse("this employee should be valid",true);
		}
	}

	@Test
    public void testAddInvalidEmployeeDueNullSalary(){
        Employee newEmployee = new Employee("ValidLastName", "1910509055057", DidacticFunction.ASISTENT, "3000");
        assertTrue(employeeValidator.isValid(newEmployee));
        String[] attrs = new String[4];
        attrs[0] = "ValidLastName";
        attrs[1] = "1910509055057";
        attrs[2] = "ASISTENT";
        attrs[3] = null;
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }


    @Test
    public void testAddInvalidEmployeeDueNullFunction(){
        Employee newEmployee = new Employee("ValidLastName", "1910509055057", DidacticFunction.ASISTENT, "3000");
        assertTrue(employeeValidator.isValid(newEmployee));
        String[] attrs = new String[4];
        attrs[0] = "ValidLastName";
        attrs[1] = "1910509055057";
        attrs[2] = null;
        attrs[3] = "342";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueInvalidCharacterInSalaryString(){
        String[] attrs = new String[4];
        attrs[0] = "Florin";
        attrs[1] = "1910509055057";
        attrs[2] = "ASISTENT";
        attrs[3] = "34d";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueInvalidNameWithSpaces(){
        Employee newEmployee = new Employee("ValidLastName", "1910509055057", DidacticFunction.ASISTENT, "3000");
        assertTrue(employeeValidator.isValid(newEmployee));
        String[] attrs = new String[4];
        attrs[0] = "M A";
        attrs[1] = "1910509055057";
        attrs[2] = "ASISTENT";
        attrs[3] = "342";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueTooLongName(){
        Employee newEmployee = new Employee("ValidLastName", "1910509055057", DidacticFunction.ASISTENT, "3000");
        assertTrue(employeeValidator.isValid(newEmployee));
        String[] attrs = new String[4];
        String name = new String(new char[256]).replace('\0', 'a');
        attrs[0] = name;
        attrs[1] = "1910509055057";
        attrs[2] = "ASISTENT";
        attrs[3] = "342";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueInvalidCnpCharacter(){
        String[] attrs = new String[4];
        attrs[0] = "Ion";
        attrs[1] = "123533423423a";
        attrs[2] = "ASISTENT";
        attrs[3] = "342";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueTooManyCnpChars(){
        String[] attrs = new String[4];
        attrs[0] = "Ion";
        attrs[1] = "12353342342343";
        attrs[2] = "ASISTENT";
        attrs[3] = "342";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueTooFewCnpChars(){
        String[] attrs = new String[4];
        attrs[0] = "Andrei";
        attrs[1] = "123534234234";
        attrs[2] = "ASISTENT";
        attrs[3] = "342";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueCnp1stChar(){
        String[] attrs = new String[4];
        attrs[0] = "Andrei";
        attrs[1] = "0432523453245";
        attrs[2] = "ASISTENT";
        attrs[3] = "342";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testAddValidEmployee2(){
        String[] attrs = new String[4];
        attrs[0] = "Sebastian";
        attrs[1] = "4432523453245";
        attrs[2] = "ASISTENT";
        attrs[3] = "544";
        try {
            controller.addEmployee(attrs);
            assertTrue(true);
        } catch (EmployeeException e) {
            assertFalse("this employee should be fail due null salary",true);
        }
    }

    @Test
    public void testAddInvalidEmployeeDueInvalidDidacticFunction(){
        String[] attrs = new String[4];
        attrs[0] = "Marius";
        attrs[1] = "1432523453245";
        attrs[2] = "ASISTENTTEACHER";
        attrs[3] = "11";
        try {
            controller.addEmployee(attrs);
            assertFalse("this employee should be fail due null salary",true);

        } catch (EmployeeException e) {
            assertTrue(true);
        }
    }
}
