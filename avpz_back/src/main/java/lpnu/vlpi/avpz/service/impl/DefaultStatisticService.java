package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.StatisticRepository;
import lpnu.vlpi.avpz.dao.UserRepository;
import lpnu.vlpi.avpz.model.ResultModel;
import lpnu.vlpi.avpz.model.StatisticModel;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.service.ResultService;
import lpnu.vlpi.avpz.service.StatisticService;
import lpnu.vlpi.avpz.service.UserService;
import lpnu.vlpi.avpz.service.exceptions.StatisticNotFountException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultStatisticService implements StatisticService {

    private StatisticRepository statisticRepository;
    private UserService userService;
    private UserRepository userRepository;

    public DefaultStatisticService(StatisticRepository statisticRepository, UserService userService, ResultService resultService, UserRepository userRepository) {
        this.statisticRepository = statisticRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public StatisticModel getUserStatistic(String userUid) {
        Optional<StatisticModel> statisticModelOptional = statisticRepository.findByUserUid(userUid);
        if (statisticModelOptional.isEmpty()) {
            throw new StatisticNotFountException();
        }
        return statisticModelOptional.get();
    }


    @Override
    public StatisticModel updateUserStatistic(ResultModel resultModel) {
        UserModel user = userService.getUserByUid(resultModel.getUser().getUid());
        StatisticModel statisticModel = statisticRepository.findByUserUid(user.getUid()).orElse(null);
        if (statisticModel == null) {
            statisticModel = createUserStatistic(user);
        }
        statisticModel.setTotalTaskComplete(statisticModel.getTotalTaskComplete() + 1);
        updateTime(statisticModel, resultModel);
        updateMark(statisticModel, resultModel);
        statisticModel = statisticRepository.save(statisticModel);
        user.setStatistic(statisticModel);
        userRepository.save(user);
        return statisticModel;
    }

    @Override
    public List<StatisticModel> getAll() {
        return statisticRepository.findAll();
    }

    private StatisticModel createUserStatistic(UserModel userModel) {
        StatisticModel statisticModel = new StatisticModel();
        statisticModel.setUid(getNewUid());
        statisticModel.setUser(userModel);
        return statisticRepository.save(statisticModel);
    }

    private void updateTime(StatisticModel statisticModel, ResultModel resultModel) {
        float avarageTime = statisticModel.getAverageTime();
        statisticModel.setAverageTime((avarageTime + resultModel.getCompletionTime()) / statisticModel.getTotalTaskComplete());
    }

    private void updateMark(StatisticModel statisticModel, ResultModel resultModel) {
        float mark = resultModel.getGrade();
        statisticModel.setAverageMark((mark + statisticModel.getAverageMark()) / statisticModel.getTotalTaskComplete());
    }

    @Override
    public String getNewUid() {
        long id = Optional.ofNullable(statisticRepository.getMaxUid()).orElse(0L);
        return String.valueOf(id + 1);
    }


    @Override
    public int getPagesCount(long size) {
        return 0;
    }
}
