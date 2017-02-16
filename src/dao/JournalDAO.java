package dao;

import java.sql.*;

/**
 * Created by Mordr on 16.02.2017.
 */
public class JournalDAO {
    private final Connection conn;

    public JournalDAO(Connection conn) {
        this.conn = conn;
    }

    private void journalToConsole(ResultSet resultSet) throws SQLException {
        System.out.println(
                resultSet.getString("student_name") + " " +
                resultSet.getString("lection_name") + " " +
                resultSet.getDate("date")
        );
    }

    public void list() throws SQLException {
        String sql = "select b.name student_name,c.name lection_name,a.date from journal a " +
                    "inner join student b on a.id_student=b.id " +
                    "inner join lection c on a.id_lection=c.id";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("journal list: ");
        while(resultSet.next()) {
            journalToConsole(resultSet);
        }
    }

    public void select(String student_name, String lection_name) throws SQLException {
        String sql = "select b.name student_name,c.name lection_name,a.date from journal a " +
                    "inner join student b on a.id_student=b.id " +
                    "inner join lection c on a.id_lection=c.id " +
                    "where b.name = ? and c.name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, student_name);
        preparedStatement.setString(2, lection_name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet!=null) {
            System.out.print("Student " + student_name + " attended lection " + lection_name + " ");
            while (resultSet.next()) {
                journalToConsole(resultSet);
            }
        } else {
            System.out.print("Student " + student_name + " absent lection " + lection_name);
        }
    }

    public void update(
            String old_student_name, String old_lection_name,
            String new_student_name, String new_lection_name, Date date
    ) throws SQLException {
        String sql = "update journal " +
                    "set id_student = (select id from student where name=?), " +
                    "id_lection = (select id from lection where name=?), " +
                    "date = ? " +
                    "where " +
                    "id_student = (select id from student where name=?) and " +
                    "id_lection = (select id from lection where name=?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, new_student_name);
        preparedStatement.setString(2, new_lection_name);
        preparedStatement.setDate(3, date);
        preparedStatement.setString(4, old_student_name);
        preparedStatement.setString(5, old_lection_name);
        preparedStatement.executeUpdate();
        System.out.println(
                "attendance " + old_student_name + "/" + old_lection_name +
                " updated to " +
                date + " " + new_student_name + " " + new_lection_name);
    }

    public void insert(String student_name, String lection_name, Date date) throws SQLException {
        String sql = "insert into journal (id_student, id_lection, date) " +
                "values (" +
                "(select id from student where name = ?), " +
                "(select id from lection where name = ?)," +
                "?" +
                ")";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, student_name);
        preparedStatement.setString(2, lection_name);
        preparedStatement.setDate(3, date);
        preparedStatement.executeUpdate();
        System.out.println("added journal record  for " + student_name + " " + lection_name + " " + date);
    }

    public void delete(String student_name, String lection_name, Date date) throws SQLException {
        String sql = "delete from journal where " +
                    "id_student = (select id from student where name = ?) and " +
                    "id_lection = (select id from lection where name = ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, student_name);
        preparedStatement.setString(2, lection_name);
        preparedStatement.executeUpdate();
        System.out.println("journal record for " + student_name + " " + lection_name + " deleted");
    }
}
