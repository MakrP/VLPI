package lpnu.vlpi.avpz.converter.statistic;

import lpnu.vlpi.avpz.dto.statistic.StatisticDto;
import lpnu.vlpi.avpz.model.StatisticModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;



@Component
public class StatistiсConverter implements Converter<StatisticModel, StatisticDto> {
    @Override
    public StatisticDto convert(StatisticModel source) {
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setAverageMark(source.getAverageMark());
        statisticDto.setAverageTimeOnTasks(source.getAverageTime());
        statisticDto.setTotalTaskComplete(source.getTotalTaskComplete());
        statisticDto.setUser(source.getUser());
        return statisticDto;
    }
}

