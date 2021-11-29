package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.model.ModuleModel;
import lpnu.vlpi.avpz.model.ResultModel;
import lpnu.vlpi.avpz.model.StatisticModel;
import lpnu.vlpi.avpz.model.UserModel;

import java.util.List;

public interface StatisticService extends BaseService{
    StatisticModel getUserStatistic(String userUid);
    StatisticModel updateUserStatistic(ResultModel resultModel);
    List<StatisticModel> getAll();
}

