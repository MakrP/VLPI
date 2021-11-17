package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.StatisticRepository;
import lpnu.vlpi.avpz.model.StatisticModel;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.service.StatisticService;
import lpnu.vlpi.avpz.service.exceptions.StatisticNotFountException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultStatisticService implements StatisticService {

    private StatisticRepository statisticRepository;

    public DefaultStatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public StatisticModel getGeneralUserStatistic(String userUid) {
        Optional<StatisticModel> statisticModelOptional = statisticRepository.findByUserUidAndModuleIsNullAndTaskIsNull(userUid);
        if (statisticModelOptional.isEmpty()) {
            throw new StatisticNotFountException();
        }
        return statisticModelOptional.get();
    }

    @Override
    public StatisticModel getModuleUserStatistic(String userUid, String moduleUid) {
        Optional<StatisticModel> statisticModelOptional = statisticRepository.findByUserUidAndModuleUid(userUid, moduleUid);
        if (statisticModelOptional.isEmpty()) {
            throw new StatisticNotFountException();
        }
        return statisticModelOptional.get();
    }

    @Override
    public StatisticModel getTaskUserStatistic(String userUid, String taskUid) {
        Optional<StatisticModel> statisticModelOptional = statisticRepository.findByUserUidAndTaskUid(userUid, taskUid);
        if (statisticModelOptional.isEmpty()) {
            throw new StatisticNotFountException();
        }
        return statisticModelOptional.get();
    }

    @Override
    public StatisticModel updateUserStatistic(String uid, StatisticModel statistic) {
        return null;
    }

    @Override
    public void removeUserGeneralStatistic(UserModel userMode) {

    }

    @Override
    public void removeUserModuleStatistic(String userUid, String moduleUid) {

    }

    @Override
    public void removeUserTopicStatistic(String userUid, String topicUid) {

    }

    @Override
    public void removeUserTaskStatistic(String userUid, String taskUid) {

    }
}
