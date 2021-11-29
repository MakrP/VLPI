package lpnu.vlpi.avpz.service;


import lpnu.vlpi.avpz.converter.evaluation.EvaluationDTO;
import lpnu.vlpi.avpz.dto.result.ResultDTO;

public interface EvaluationService extends BaseService {
    EvaluationDTO evaluate(ResultDTO resultDTO);
}
