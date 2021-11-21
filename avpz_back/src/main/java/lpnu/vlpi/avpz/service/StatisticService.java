package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.model.ModuleModel;
import lpnu.vlpi.avpz.model.StatisticModel;
import lpnu.vlpi.avpz.model.UserModel;

public interface StatisticService {
    StatisticModel getGeneralUserStatistic(String userUid);
    StatisticModel getModuleUserStatistic(String userUid, String moduleUid);
    StatisticModel getTaskUserStatistic(String userUid, String taskUid);
    StatisticModel updateUserStatistic(String uid, StatisticModel statistic);
    void removeUserGeneralStatistic(UserModel userMode);
    void removeUserModuleStatistic(String userUid, String moduleUid);
    void removeUserTopicStatistic(String userUid, String topicUid);
    void removeUserTaskStatistic(String userUid, String taskUid);
}
