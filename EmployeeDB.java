import java.sql.*;
import java.util.Scanner;

public class EmployeeDB {

    private static final String URL = "jdbc:mysql://localhost:3306/employee_db"; 
    private static final String USER = "root"; 
    private static final String PASS = "password";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private static void addEmployee() {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)")) {

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            ps.setString(1, name);
            ps.setString(2, dept);
            ps.setDouble(3, salary);

            int rows = ps.executeUpdate();
            System.out.println(rows + " Employee Added.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewEmployees() {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM employees");
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                        rs.getString("name") + " | " +
                        rs.getString("department") + " | " +
                        rs.getDouble("salary"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateEmployee() {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE employees SET name=?, department=?, salary=? WHERE id=?")) {

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Employee ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("New Name: ");
            String name = sc.nextLine();

            System.out.print("New Department: ");
            String dept = sc.nextLine();

            System.out.print("New Salary: ");
            double salary = sc.nextDouble();

            ps.setString(1, name);
            ps.setString(2, dept);
            ps.setDouble(3, salary);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            System.out.println(rows + " Employee Updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteEmployee() {
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(
                     "DELETE FROM employees WHERE id=?")) {

            Scanner sc = new Scanner(System.in);

            System.out.print("Enter Employee ID to Delete: ");
            int id = sc.nextInt();

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows + " Employee Deleted.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Employee Database Menu ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }
}
