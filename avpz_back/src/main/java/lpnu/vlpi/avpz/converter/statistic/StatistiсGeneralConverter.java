package lpnu.vlpi.avpz.converter.statistic;

import lpnu.vlpi.avpz.dto.statistic.GeneralStatisticDto;
import lpnu.vlpi.avpz.model.StatisticModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Qualifier("generalStatisticConverter")
@Component
public class StatistiсGeneralConverter implements Converter<StatisticModel, GeneralStatisticDto> {
    @Override
    public GeneralStatisticDto convert(StatisticModel source) {
        GeneralStatisticDto generalStatisticDto = new GeneralStatisticDto();
        generalStatisticDto.setAverageMark(source.getAverageMark());
        generalStatisticDto.setAverageTimeOnTasks(source.getAverageTime());
        generalStatisticDto.setTotalTaskComplete(source.getTotalTaskComplete());
        return generalStatisticDto;
    }
}

