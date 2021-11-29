package lpnu.vlpi.avpz.controller;

import lpnu.vlpi.avpz.dto.task.TaskAdminDto;
import lpnu.vlpi.avpz.dto.task.TaskDTO;
import lpnu.vlpi.avpz.dto.task.TaskPreviewDTO;
import lpnu.vlpi.avpz.model.ResultModel;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.service.ResultService;
import lpnu.vlpi.avpz.service.TaskService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static lpnu.vlpi.avpz.model.enums.Level.HARD;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 360000, allowCredentials = "true")
@RestController
@RequestMapping("/vlpi/v1/tasks")
public class TaskController {

    private static final String TASK_REMOVED_MESSAGE = "Task with uid %s removed";
    private static final String TASK_REMOVE_ERROR_MESSAGE = "Error remove task with uid %s";

    private final Converter<TaskModel, TaskDTO> taskConverter;
    private final Converter<TaskModel, TaskPreviewDTO> taskPreviewDTOConverter;
    private final Converter<TaskModel, TaskAdminDto> taskAdminDtoConverter;
    private final Converter<ResultModel, TaskPreviewDTO> taskPreviewDTOFromResulConverter;

    private TaskService taskService;
    private ResultService resultService;

    public TaskController(TaskService taskService, Converter<TaskModel, TaskDTO> taskConverter, Converter<TaskModel, TaskPreviewDTO> taskPreviewDTOConverter, Converter<TaskModel, TaskAdminDto> taskAdminDtoConverter, Converter<ResultModel, TaskPreviewDTO> taskPreviewDTOFromResulConverter, ResultService resultService) {
        this.taskService = taskService;
        this.taskConverter = taskConverter;
        this.taskPreviewDTOConverter = taskPreviewDTOConverter;
        this.taskAdminDtoConverter = taskAdminDtoConverter;
        this.taskPreviewDTOFromResulConverter = taskPreviewDTOFromResulConverter;
        this.resultService = resultService;
    }

    @GetMapping("/{taskUid}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("taskUid") String taskUid, @RequestParam("level") String level) {
        TaskDTO taskDTO = taskConverter.convert(taskService.getTaskByUid(taskUid));
        if (Level.valueOf(level).equals(HARD)) {
            taskDTO.getVariantDTOList().forEach(v -> v.setTooltip(null));
        }
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
    public ResponseEntity getTasks(@RequestParam("page") int page, @RequestParam("size") int size, HttpSession httpSession) {
        List<TaskPreviewDTO> taskPreviewDTOS = new ArrayList<>();
        UserModel curreentUrer = (UserModel) httpSession.getAttribute("currentUser");
        for (TaskModel topicTask : taskService.getTopics(page, size)) {
            List<ResultModel> result = resultService.getByUserAndTask(topicTask.getUid(), curreentUrer.getUid());
            if(result.isEmpty()) {
                taskPreviewDTOS.add(taskPreviewDTOConverter.convert(topicTask));
            } else {
                taskPreviewDTOS.add(taskPreviewDTOFromResulConverter.convert(result.get(0)));
            }
        }
        return new ResponseEntity<>(taskPreviewDTOS, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<TaskAdminDto>> getTasksForAdmin(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<TaskAdminDto> taskAdminDtos = new ArrayList<>();
        for (TaskModel task : taskService.getTopics(page, size)) {
            taskAdminDtos.add(taskAdminDtoConverter.convert(task));
        }
        return new ResponseEntity<>(taskAdminDtos, HttpStatus.OK);
    }

    @GetMapping("/pages")
    public ResponseEntity<Integer> getPageSize(@RequestParam("size") long size) {
        return new ResponseEntity<>(taskService.getPagesCount(size), HttpStatus.OK);
    }

    @DeleteMapping("/{taskUid}")
    public ResponseEntity<String> removeTask(@PathVariable("taskUid") String uid) {
        taskService.removeTask(uid);
        return new ResponseEntity<>(String.format(TASK_REMOVED_MESSAGE, uid), HttpStatus.OK);
    }

}
