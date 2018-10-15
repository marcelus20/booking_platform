import java.sql.*;


public class Main {




    public static void main(String... args){
        try{
            Connection myConn = DriverManager
                    .getConnection("jdbc:mysql://localhost/booking_platform", "root", "");


            Statement myStmt = myConn.createStatement();

            String query = new StringBuilder().append("SELECT * FROM admin").toString();

            ResultSet rs = myStmt.executeQuery(query);

            while (rs.next()){
                System.out.println(rs.getString("password"));
                System.out.println(rs.getString("email"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
