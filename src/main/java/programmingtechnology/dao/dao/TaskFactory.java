package programmingtechnology.dao.dao;

import java.util.List;

public class TaskFactory {
    public static List<String> sources = List.of("Файл", "База данных");

    public static TaskDAO createTaskDAO(String type) {

        if (type.equalsIgnoreCase(sources.get(0))) {
            return new FileTaskDAO("N:\\Student\\3 курс\\6 семестр\\Java\\dao\\src\\main\\resources\\files\\tasks.txt");//имя файла
        } else if (type.equalsIgnoreCase(sources.get(1))) {
            return new DbTaskDAO();
        } else {
            throw new IllegalArgumentException("Invalid datasource type!");
        }
    }
}
