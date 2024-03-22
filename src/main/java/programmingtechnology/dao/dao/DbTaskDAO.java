package programmingtechnology.dao.dao;

import programmingtechnology.dao.models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbTaskDAO implements TaskDAO{
    private final String url = "jdbc:postgresql://localhost:5432/ProjectTasks";
    private Connection conn;
    public DbTaskDAO(){

    }
    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url, "postgres", "12345");

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getLong("time"),
                        rs.getBoolean("isFinished")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }

    @Override
    public void addTask(Task task) {
        try{
            conn = DriverManager.getConnection(url, "postgres", "12345");
            PreparedStatement ps = conn.prepareStatement("insert into tasks (name, time, \"isFinished\") VALUES ('"+ task.getName() +"', "+ task.getNanoTime() +", false);");
            int rows = ps.executeUpdate();
            System.out.printf("Добавлено %d задач", rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void deleteTask(int id) {
        try{
            conn = DriverManager.getConnection(url, "postgres", "12345");
            PreparedStatement ps = conn.prepareStatement("delete from tasks where id = " + id + ";");
            int rows = ps.executeUpdate();
            System.out.printf("Удалено %d задач", rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
