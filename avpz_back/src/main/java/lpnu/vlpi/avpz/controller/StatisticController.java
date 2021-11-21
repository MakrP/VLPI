package lpnu.vlpi.avpz.controller;

import lpnu.vlpi.avpz.dto.statistic.GeneralStatisticDto;
import lpnu.vlpi.avpz.dto.statistic.StatisticModuleDto;
import lpnu.vlpi.avpz.dto.statistic.StatisticTaskDto;
import lpnu.vlpi.avpz.model.StatisticModel;
import lpnu.vlpi.avpz.service.StatisticService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vlpi/v1/user/{userUid}/statistic")
public class StatisticController {

    private final Converter<StatisticModel, GeneralStatisticDto> generalConverter;
    private final Converter<StatisticModel, StatisticModuleDto> moduleConverter;
    private final Converter<StatisticModel, StatisticTaskDto> taskConverter;
    private StatisticService statisticService;


    public StatisticController(StatisticService statisticService,
                               @Qualifier("generalStatisticConverter") Converter<StatisticModel, GeneralStatisticDto> generalConverter,
                               @Qualifier("statisticModuleConverter") Converter<StatisticModel, StatisticModuleDto> moduleConverter,
                               @Qualifier("statisticTaskConverter") Converter<StatisticModel, StatisticTaskDto> taskConverter) {
        this.statisticService = statisticService;
        this.generalConverter = generalConverter;
        this.moduleConverter = moduleConverter;
        this.taskConverter = taskConverter;
    }

    @GetMapping
    public ResponseEntity<GeneralStatisticDto> getUserGeneralStatistic(@PathVariable("userUid") String uid) {
        GeneralStatisticDto generalStatisticDto = generalConverter.convert(statisticService.getGeneralUserStatistic(uid));
        return new ResponseEntity<>(generalStatisticDto, HttpStatus.OK);
    }

    @GetMapping("/module/{moduleUid}")
    public ResponseEntity<StatisticModuleDto> getUserModuleStatistic(@PathVariable("userUid") String userUid, @PathVariable("moduleUid") String moduleUid) {
        StatisticModuleDto generalStatisticDto = moduleConverter.convert(statisticService.getModuleUserStatistic(userUid, moduleUid));
        return new ResponseEntity<>(generalStatisticDto, HttpStatus.OK);
    }

    @GetMapping("/task/{taskUid}")
    public ResponseEntity<StatisticTaskDto> getUserTaskStatistic(@PathVariable("userUid") String userUid, @PathVariable("taskUid") String taskUid) {
        StatisticTaskDto statisticTaskDto = taskConverter.convert(statisticService.getTaskUserStatistic(userUid, taskUid));
        return new ResponseEntity<>(statisticTaskDto, HttpStatus.OK);
    }


}