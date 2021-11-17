package lpnu.vlpi.avpz.converter.statistic;

import lpnu.vlpi.avpz.dto.statistic.GeneralStatisticDto;
import lpnu.vlpi.avpz.dto.statistic.StatisticModuleDto;
import lpnu.vlpi.avpz.model.StatisticModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Qualifier("statisticModuleConverter")
@Component
public class StatisticModuleConverter implements Converter<StatisticModel,StatisticModuleDto> {

    @Override
    public StatisticModuleDto convert(StatisticModel source) {
        StatisticModuleDto statisticModuleDto = new StatisticModuleDto();
        statisticModuleDto.setAverageMark(source.getAverageMark());
        statisticModuleDto.setAverageTimeOnTasks(source.getAverageTime());
        statisticModuleDto.setTotalTaskComplete(source.getTotalTaskComplete());
        statisticModuleDto.setModuleTitle(source.getModule().getDisplayName());
        return statisticModuleDto;
    }
}
