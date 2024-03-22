package programmingtechnology.dao.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import programmingtechnology.dao.models.Task;
import programmingtechnology.dao.models.json.TaskDeserializer;
import programmingtechnology.dao.models.json.TaskSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileTaskDAO implements TaskDAO {
    private String path;
    private Gson gson;
    private int LastId = 1;
    public FileTaskDAO(String path){
        this.path = path;
        gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Task.class, new TaskSerializer())
            .registerTypeAdapter(Task.class, new TaskDeserializer())
            .create();
    }
    @Override
    public List<Task> getAllTasks() {
        try(BufferedReader bReader = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            while (bReader.ready()){
                sb.append(bReader.readLine());
            }
            if(!(sb.toString().equals("") || sb.toString().equals("[]"))){
                List<Task> tasks = gson.fromJson(sb.toString(), new TypeToken<List<Task>>(){}.getType());
                LastId = tasks.get(tasks.size() - 1).getId() + 1;
                return tasks;
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return new ArrayList<>();
    }
    @Override
    public Task getTaskById(int id) {
        var tasks = getAllTasks();
        return tasks.stream()
                .filter((x) -> x.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void addTask(Task task) {
        task.setId(LastId);

        var tasks = getAllTasks();
        if(tasks.size() == 0)
            tasks = List.of(task);
        else
            tasks.add(task);

        try(var writer = new FileWriter(path, false))
        {
            var gsonTasks = gson.toJson(tasks);
            writer.write(gsonTasks);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void deleteTask(int id) {
        var tasks = getAllTasks();
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId() == id){
                tasks.remove(i);
                break;
            }
        }

        try(var writer = new FileWriter(path, false))
        {
            var gsonTasks = gson.toJson(tasks);
            writer.write(gsonTasks);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
