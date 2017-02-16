package dao;

import java.sql.*;

/**
 * Created by Mordr on 16.02.2017.
 */
public class GroupDAO {
    private final Connection conn;

    public GroupDAO(Connection conn) {
        this.conn = conn;
    }

    private void groupToConsole(ResultSet resultSet) throws SQLException {
        System.out.println(
                resultSet.getString("name") + " " +
                resultSet.getInt("group_number")
        );
    }

    public void list() throws SQLException {
        String sql = "select * from students.group";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println("group list: ");
        while(resultSet.next()) {
            groupToConsole(resultSet);
        }
    }

    public void select(String name) throws SQLException {
        String sql = "select * from students.group where name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.print("group selected :");
        if(resultSet!=null) {
            while (resultSet.next()) {
                groupToConsole(resultSet);
            }
        }
    }

    public void update(String old_name, String new_name, Integer group_number) throws SQLException {
        String sql = "update students.group set " +
                        "name = ?," +
                        "group_number = ? " +
                        "where name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, new_name);
        preparedStatement.setInt(2, group_number);
        preparedStatement.setString(3, old_name);
        preparedStatement.executeUpdate();
        System.out.println("student " + old_name + " updated to " + new_name);
    }

    public void insert(String name, Integer group_number) throws SQLException {
        String sql = "insert into students.group (name, group_number) values (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, group_number);
        preparedStatement.executeUpdate();
        System.out.println("added group " + name);
    }

    public void delete(String name) throws SQLException {
        String sql = "delete from students.group where name = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        System.out.println("group " + name + " deleted");
    }
}
