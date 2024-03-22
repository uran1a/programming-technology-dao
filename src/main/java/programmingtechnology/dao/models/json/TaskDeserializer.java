package programmingtechnology.dao.models.json;

import com.google.gson.*;
import programmingtechnology.dao.models.Task;

import java.lang.reflect.Type;

public class TaskDeserializer implements JsonDeserializer<Task> {
    @Override
    public Task deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return new Task(
                jsonObject.get("id").getAsInt(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("time").getAsLong(),
                jsonObject.get("isFinished").getAsBoolean()
        );
    }
}
