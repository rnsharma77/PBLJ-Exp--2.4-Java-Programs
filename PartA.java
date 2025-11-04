import java.sql.*;

public class FetchEmployees {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/college_db";
        String user = "root";
        String password = "your_password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");

            System.out.println("EmpID\tName\t\tSalary");
            System.out.println("--------------------------------");
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.println(id + "\t" + name + "\t" + salary);
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
