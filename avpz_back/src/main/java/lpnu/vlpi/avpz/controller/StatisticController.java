package lpnu.vlpi.avpz.controller;

import lpnu.vlpi.avpz.dto.statistic.StatisticDto;
import lpnu.vlpi.avpz.model.StatisticModel;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.service.StatisticService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 360000, allowCredentials = "true")
@RequestMapping("/vlpi/v1/statistic")
public class StatisticController {

    private final Converter<StatisticModel, StatisticDto> statisticConverter;

    private StatisticService statisticService;


    public StatisticController(Converter<StatisticModel, StatisticDto> statisticConverter, StatisticService statisticService) {
        this.statisticConverter = statisticConverter;
        this.statisticService = statisticService;
    }

    @GetMapping
    public ResponseEntity<StatisticDto> getUserGeneralStatistic(HttpSession httpSession) {
        UserModel user = (UserModel) httpSession.getAttribute("currentUser");
        StatisticDto statisticDto = statisticConverter.convert(statisticService.getUserStatistic(user.getUid()));
        return new ResponseEntity<>(statisticDto, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<StatisticDto>> getForAdmin() {
        List<StatisticDto> res = new ArrayList<>();
        for (StatisticModel statisticModel : statisticService.getAll()) {
            res.add(statisticConverter.convert(statisticModel));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

//    @GetMapping("/module/{moduleUid}")
//    public ResponseEntity<StatisticModuleDto> getUserModuleStatistic(@PathVariable("userUid") String userUid, @PathVariable("moduleUid") String moduleUid) {
//        StatisticModuleDto generalStatisticDto = moduleConverter.convert(statisticService.getModuleUserStatistic(userUid, moduleUid));
//        return new ResponseEntity<>(generalStatisticDto, HttpStatus.OK);
//    }
//
//    @GetMapping("/task/{taskUid}")
//    public ResponseEntity<StatisticTaskDto> getUserTaskStatistic(@PathVariable("userUid") String userUid, @PathVariable("taskUid") String taskUid) {
//        StatisticTaskDto statisticTaskDto = taskConverter.convert(statisticService.getTaskUserStatistic(userUid, taskUid));
//        return new ResponseEntity<>(statisticTaskDto, HttpStatus.OK);
//    }


}
