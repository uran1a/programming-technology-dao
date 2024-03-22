package programmingtechnology.dao.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import programmingtechnology.dao.dao.TaskDAO;
import programmingtechnology.dao.dao.TaskFactory;
import programmingtechnology.dao.models.Task;

import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MainController implements Initializable {
    @FXML
    private Button addTask;

    @FXML
    private TextField nameValue;

    @FXML
    private TextField timeValue;

    @FXML
    private Button deleteRowTableTasks;

    @FXML
    private TableView<Task> tableTasks;
    @FXML
    private ComboBox<String> sources;

    private TaskDAO factory;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = TaskFactory.createTaskDAO(TaskFactory.sources.get(0));

        addColumnsTableView();
        updateTableView(factory.getAllTasks());

        sources.setItems(FXCollections.observableArrayList(TaskFactory.sources));
        sources.setValue(sources.getItems().get(0));
        sources.setOnAction(this::changeValueComboBoxHandler);
        addTask.setOnMouseClicked(this::addTaskTableViewHandler);
        deleteRowTableTasks.setOnMouseClicked(this::deleteRowTableViewHandler);
    }

    private void addTaskTableViewHandler(MouseEvent event) {
        if(!isValidStringTextField(nameValue)) {
            displayWarningMessage("Неверное введенные данные! (цифры, числа, _)");
            return;
        }
        if(!isValidTimeTextField(timeValue)) {
            displayWarningMessage("Неверное введенные данные! (XX:XX)");
            return;
        }

        Task task = new Task(
                0, nameValue.getText().trim(),  LocalTime.parse(timeValue.getText()).toNanoOfDay(), false
        );
        factory.addTask(task);
        updateTableView(factory.getAllTasks());
    }

    private boolean isValidTimeTextField(TextField textField){
        String text = textField.getText().trim();
        boolean res = Pattern.matches("^\\d{2}:\\d{2}$", text);
        return res;
    }
    private boolean isValidStringTextField(TextField textField){
        String text = textField.getText().trim();
        boolean res = Pattern.matches("^\\w+$", text);
        return res;
    }
    private void changeValueComboBoxHandler(ActionEvent actionEvent) {
        factory = TaskFactory.createTaskDAO(sources.getValue());


        var tasks = factory.getAllTasks();
        if (tasks != null) {
            updateTableView(tasks);
        } else {
            updateTableView(new ArrayList<>());
            displayWarningMessage("Источник пустой");
        }
    }

    private void addColumnsTableView(){
        TableColumn<Task, Integer> idColumn = new TableColumn<>("#");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Task, String> taskColumn = new TableColumn<>("Задание");
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Task, Integer> timeColumn = new TableColumn<>("Время");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Task, String> isFinishedColumn = new TableColumn<>("Статус");
        isFinishedColumn.setCellValueFactory(new PropertyValueFactory<>("isFinished"));

        tableTasks.getColumns().clear();
        tableTasks.getColumns().addAll(idColumn, taskColumn, timeColumn, isFinishedColumn);
    }

    private void updateTableView(List<Task> tasks){
        tableTasks.setItems(FXCollections.observableArrayList(tasks));
    }


    private void deleteRowTableViewHandler(MouseEvent event) {
        if(tableTasks.getSelectionModel().getSelectedIndex() == -1){
            displayWarningMessage("Выберите задание, которое хотите удалить!");
            return;
        }

        var tasks = factory.getAllTasks();
        Task removedTask = tasks.get(tableTasks.getSelectionModel().getSelectedIndex());
        factory.deleteTask(removedTask.getId());
        tasks.remove(removedTask);
        updateTableView(tasks);
    }

    private void displayWarningMessage(String text){
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Сообщение");
        alert.setHeaderText("Предупреждение");
        alert.setContentText(text);
        alert.showAndWait();
    }
}