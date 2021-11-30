package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.converter.evaluation.AnswertDtoConverter;
import lpnu.vlpi.avpz.converter.evaluation.EvaluationDTO;
import lpnu.vlpi.avpz.dao.ChosenAnswersRepository;
import lpnu.vlpi.avpz.dto.result.ResultDTO;
import lpnu.vlpi.avpz.model.*;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.service.*;
import lpnu.vlpi.avpz.service.exceptions.VariantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DefaultEvaluationService implements EvaluationService {

    private static float MAX_MARK;
    private StatisticService statisticService;
    private TaskService taskService;
    private VariantService variantService;
    private AnswertDtoConverter answertDtoConverter;
    private UserService userService;
    private ResultService resultService;

    public DefaultEvaluationService(StatisticService statisticService, TaskService taskService, ChosenAnswersRepository chosenAnswersRepository, CategoryService categoryService, VariantService variantService, AnswertDtoConverter answertDtoConverter, UserService userService, ResultService resultService) {
        this.statisticService = statisticService;
        this.taskService = taskService;
        this.variantService = variantService;
        this.answertDtoConverter = answertDtoConverter;
        this.userService = userService;
        this.resultService = resultService;
    }

    @Override
    public String getNewUid() {
        return null;
    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }

    @Override
    public EvaluationDTO evaluate(ResultDTO resultDTO) {
        TaskModel taskModel = taskService.getTaskByUid(resultDTO.getTaskUid());
        List<ChosenAnswersModel> chosenAnswersModels = answertDtoConverter.convert(resultDTO.getAnswers());
        float mark =  getMarkBasedOnAnswers(taskModel, chosenAnswersModels);
        mark = updateMarkBaseOnLevel(mark, Level.valueOf(resultDTO.getLevel()));
        int finalMark = (int)updateMarkBaseOnTime(mark, taskModel.getExecutionTime(), resultDTO.getTime());

        finalMark = sliceMark(finalMark);
        ResultModel resultModel = saveResult(resultDTO, chosenAnswersModels, finalMark);
        statisticService.updateUserStatistic(resultModel);
        EvaluationDTO evaluationDTO = new EvaluationDTO();
        evaluationDTO.setScore(finalMark);
        return evaluationDTO;
    }

    private int sliceMark(int mark) {
        if (mark > MAX_MARK) {
            return (int) MAX_MARK;
        } else if (mark < 0) {
            return 0;
        }
        return mark;
    }

    private float updateMarkBaseOnTime(float mark, float wantedTime, float realTime) {
        if (realTime > wantedTime) {
            mark /= ((realTime - wantedTime) / 100);
        } else if (realTime < wantedTime) {
            mark *= ((realTime - wantedTime) / 100);
        } else {
            mark = mark;
        }
        return mark;
    }

    private float getMarkBasedOnAnswers(TaskModel taskModel, List<ChosenAnswersModel> chosenAnswers) {
        int totalAnswers = taskModel.getVariants().size();
        float rightAnswers = getRightAnswersCount(taskModel, chosenAnswers);
        float res = (rightAnswers / totalAnswers) * MAX_MARK;
        return res;
    }

    private float updateMarkBaseOnLevel(float mark, Level level) {
        switch (level) {
            case NORMAL:
                mark += 2;
                break;
            case HARD:
                mark += 5;
        }
        return mark;
    }


    private int getRightAnswersCount(TaskModel taskModel, List<ChosenAnswersModel> chosenAnswers) {
        int res = 0;
        for (ChosenAnswersModel chosenAnswer : chosenAnswers) {
            CategoryModel categoryModel = chosenAnswer.getCategory();
            for (VariantModel variant : chosenAnswer.getVariants()) {
                VariantModel etalonVarian = variantService.getByUid(variant.getUid());
                if (etalonVarian.getCategory().equals(categoryModel)) {
                    res++;
                }
            }
        }
        return res;
    }

    @Value("${task.max_mark}")
    private void setMaxMark(int mark) {
        MAX_MARK = mark;
    }

    private ResultModel saveResult(ResultDTO resultDTO, List<ChosenAnswersModel> chosenAnswers, int mark) {
        ResultModel resultModel = new ResultModel();
        resultModel.setUid(resultService.getNewUid());
        resultModel.setGrade(mark);
        resultModel.setCompletionTime(resultDTO.getTime());
        resultModel.setUser(userService.getUserByUid(resultDTO.getUserUid()));
        resultModel.setTask(taskService.getTaskByUid(resultDTO.getTaskUid()));
        resultModel.setCompleted(true);
        resultModel.setChosenAnswers(chosenAnswers);
        resultModel.setCompletitionDate(new Date());
        return resultService.createResult(resultModel);
    }
}
