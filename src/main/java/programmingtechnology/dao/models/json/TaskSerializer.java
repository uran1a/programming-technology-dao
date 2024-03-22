package programmingtechnology.dao.models.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import programmingtechnology.dao.models.Task;

import java.lang.reflect.Type;

public class TaskSerializer implements JsonSerializer<Task> {
    @Override
    public JsonElement serialize(Task task, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();

        result.addProperty("id", task.getId());
        result.addProperty("name", task.getName());
        result.addProperty("time", task.getNanoTime());
        result.addProperty("isFinished", task.getIsFinished());

        return result;
    }
}
