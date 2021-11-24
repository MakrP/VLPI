package lpnu.vlpi.avpz.controller;

import lpnu.vlpi.avpz.dto.task.TaskDTO;
import lpnu.vlpi.avpz.dto.task.TaskPreviewDTO;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.service.TaskService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vlpi/v1/tasks/")
public class TaskController {
    private final Converter<TaskModel, TaskDTO> taskConverter;
    private final Converter<TaskModel, TaskPreviewDTO> taskPreviewDTOConverter;
    private TaskService taskService;

    public TaskController(TaskService taskService, Converter<TaskModel, TaskDTO> taskConverter, Converter<TaskModel, TaskPreviewDTO> taskPreviewDTOConverter) {
        this.taskService = taskService;
        this.taskConverter = taskConverter;
        this.taskPreviewDTOConverter = taskPreviewDTOConverter;
    }

    @GetMapping("/{taskUid}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("taskUid") String taskUid) {
        TaskDTO taskDTO = taskConverter.convert(taskService.getTaskByUid(taskUid));
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @GetMapping("/topic/{topicUid}/{level}")
    public ResponseEntity<List<TaskPreviewDTO>> getTopicTasksByLevel(@PathVariable("topicUid") String topicUid, @PathVariable("level") String level, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<TaskPreviewDTO> taskPreviewDTOS = new ArrayList<>();
        for (TaskModel topicTask : taskService.getTopicTasksByLevel(topicUid, Level.valueOf(level), page, size)) {
            taskPreviewDTOS.add(taskPreviewDTOConverter.convert(topicTask));
        }
        return new ResponseEntity<>(taskPreviewDTOS, HttpStatus.OK);
    }

    @GetMapping("/topic/{topicUid}")
    public ResponseEntity<List<TaskPreviewDTO>> getTopicTasks(@PathVariable("topicUid") String topicUid, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<TaskPreviewDTO> taskPreviewDTOS = new ArrayList<>();
        for (TaskModel topicTask : taskService.getTopicTasks(topicUid, page, size)) {
            taskPreviewDTOS.add(taskPreviewDTOConverter.convert(topicTask));
        }
        return new ResponseEntity<>(taskPreviewDTOS, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TaskPreviewDTO>> getTasks(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<TaskPreviewDTO> taskPreviewDTOS = new ArrayList<>();
        for (TaskModel topicTask : taskService.getTopics(page, size)) {
            taskPreviewDTOS.add(taskPreviewDTOConverter.convert(topicTask));
        }
        return new ResponseEntity<>(taskPreviewDTOS, HttpStatus.OK);
    }

}
