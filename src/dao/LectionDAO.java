package dao;

import java.sql.*;

/**
 * Created by Mordr on 16.02.2017.
 */
public class LectionDAO {
    private final Connection conn;

    public LectionDAO(Connection conn) {
        this.conn = conn;
    }

    private void lectionToConsole(ResultSet resultSet) throws SQLException {
        System.out.println(
                resultSet.getString("name") + " " +
                resultSet.getString("text") + " " +
                resultSet.getString("subject")
        );
    }

    public void list() throws SQLException {
        String sql = "select * from lection";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("lection list: ");
        while(resultSet.next()) {
            lectionToConsole(resultSet);
        }
    }

    public void select(String name) throws SQLException {
        String sql = "select * from lection where name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.print("lection selected :");
        if(resultSet!=null) {
            while (resultSet.next()) {
                lectionToConsole(resultSet);
            }
        }
    }

    public void update(String old_name, String new_name, String text, String subject) throws SQLException {
        String sql = "update lection set " +
                "name = ?," +
                "text = ?," +
                "subject = ? " +
                "where name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, new_name);
        preparedStatement.setString(2, text);
        preparedStatement.setString(3, subject);
        preparedStatement.setString(4, old_name);
        preparedStatement.executeUpdate();
        System.out.println("lection " + old_name + " updated to " + new_name);
    }

    public void insert(String name, String text, String subject) throws SQLException {
        String sql = "insert into lection (name, text, subject) values (?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, text);
        preparedStatement.setString(3, subject);
        preparedStatement.executeUpdate();
        System.out.println("added lection " + name);
    }

    public void delete(String name) throws SQLException {
        String sql = "delete from lection where name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        System.out.println("lection " + name + " deleted");
    }
}
