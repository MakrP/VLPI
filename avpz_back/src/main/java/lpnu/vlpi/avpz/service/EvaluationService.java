package lpnu.vlpi.avpz.service;


import lpnu.vlpi.avpz.dto.result.ResultDTO;

public interface EvaluationService extends BaseService {
    float evaluate(String userUid, ResultDTO resultDTO);
}
