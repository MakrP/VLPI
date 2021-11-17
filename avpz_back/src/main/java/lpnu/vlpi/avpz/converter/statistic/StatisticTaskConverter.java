package lpnu.vlpi.avpz.converter.statistic;

import lpnu.vlpi.avpz.dto.statistic.StatisticModuleDto;
import lpnu.vlpi.avpz.dto.statistic.StatisticTaskDto;
import lpnu.vlpi.avpz.model.StatisticModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Qualifier("statisticModuleConverter")
@Component
public class StatisticTaskConverter implements Converter<StatisticModel, StatisticTaskDto> {

    @Override
    public StatisticTaskDto convert(StatisticModel source) {
        StatisticTaskDto statisticTaskDto = new StatisticTaskDto();
        statisticTaskDto.setAverageMark(source.getAverageMark());
        statisticTaskDto.setAverageTimeOnTasks(source.getAverageTime());
        statisticTaskDto.setTotalTaskComplete(source.getTotalTaskComplete());
        statisticTaskDto.setTaskTitle(source.getTask().getDisplayName());
        return statisticTaskDto;
    }
}
