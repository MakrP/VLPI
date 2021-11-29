package lpnu.vlpi.avpz.dto.statistic;

import lombok.Getter;
import lombok.Setter;
import lpnu.vlpi.avpz.dto.user.MainUserInfoDto;

@Getter
@Setter
public class StatisticDto {
    private int totalTaskComplete;
    private float totalTimeOnTasks;
    private float averageTimeOnTasks;
    private float averageMark;
}
