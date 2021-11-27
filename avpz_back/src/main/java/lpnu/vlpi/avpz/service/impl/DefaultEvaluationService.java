package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.converter.evaluation.AnswertDtoConverter;
import lpnu.vlpi.avpz.dao.CategoryRepository;
import lpnu.vlpi.avpz.dao.ChosenAnswersRepository;
import lpnu.vlpi.avpz.dto.result.AnswerDto;
import lpnu.vlpi.avpz.dto.result.ResultDTO;
import lpnu.vlpi.avpz.model.ChosenAnswersModel;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.VariantModel;
import lpnu.vlpi.avpz.service.EvaluationService;
import lpnu.vlpi.avpz.service.StatisticService;
import lpnu.vlpi.avpz.service.TaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultEvaluationService implements EvaluationService {

    private StatisticService statisticService;
    private TaskService taskService;
    private ChosenAnswersRepository chosenAnswersRepository;
    private CategoryRepository categoryRepository;
    private AnswertDtoConverter answertDtoConverter;

    public DefaultEvaluationService(StatisticService statisticService, TaskService taskService, ChosenAnswersRepository chosenAnswersRepository, CategoryRepository categoryRepository, AnswertDtoConverter answertDtoConverter) {
        this.statisticService = statisticService;
        this.taskService = taskService;
        this.chosenAnswersRepository = chosenAnswersRepository;
        this.categoryRepository = categoryRepository;
        this.answertDtoConverter = answertDtoConverter;
    }

    @Value("${task.max_mark}")
    private static float MAX_MARK;

    @Override
    public String getNewUid() {
        return null;
    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }

    @Override
    public float evaluate(String userUid ,ResultDTO resultDTO) {
        TaskModel taskModel = taskService.getTaskByUid(resultDTO.getTaskUid());
        List<ChosenAnswersModel> chosenAnswersModels = answertDtoConverter.convert(resultDTO.getAnswers());
        float mark = getMarkBasedOnAnswers(taskModel, chosenAnswersModels);
        statisticService.updateUserStatistic(userUid,mark, 0);
        return mark;
    }

    public float getMarkBasedOnAnswers(TaskModel taskModel,List<ChosenAnswersModel> chosenAnswers) {
        int totalAnswers = taskModel.getVariants().size();
        int rightAnswers = getRightAnswersCount(taskModel, chosenAnswers);
        float res = ((float)rightAnswers / totalAnswers) * MAX_MARK;
        return res;
    }


    private int getRightAnswersCount(TaskModel taskModel, List<ChosenAnswersModel> chosenAnswers) {
        int res = 0;
        for (VariantModel variant : taskModel.getVariants()) {
            boolean isRight = chosenAnswers.stream().filter(c -> c.getVariants().contains(variant)).findFirst().get().getUid().equals(variant.getCategory().getUid());
            res =  isRight ? ++res : res;
        }
        return res;
    }



}
