package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.service.EvaluationService;
import org.springframework.stereotype.Service;

@Service
public class DefaultEvaluationService implements EvaluationService {

    @Override
    public void evaluate() {

    }

    @Override
    public String getNewUid() {
        return null;
    }

    @Override
    public int getPagesCount(int size) {
        return 0;
    }
}
