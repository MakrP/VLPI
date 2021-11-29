package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.ResultRepository;
import lpnu.vlpi.avpz.model.ResultModel;
import lpnu.vlpi.avpz.service.ResultService;
import lpnu.vlpi.avpz.service.exceptions.ModelNotFountException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultResultService implements ResultService {
    private ResultRepository resultRepository;

    public DefaultResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @Override
    public ResultModel getByUid(String uid) {
        Optional<ResultModel> resultModel = resultRepository.getByUid(uid);
        if(resultModel.isEmpty()) {
            throw new ModelNotFountException("result nf");
        }
        return resultModel.get();
    }

    @Override
    public ResultModel createResult(ResultModel resultModel) {
        return resultRepository.save(resultModel);
    }

    @Override
    public ResultModel getByUserAndTask(String tuid, String uuid) {
        Optional<ResultModel> resultModel = resultRepository.getByTaskUidAndUserUid(tuid, uuid);
        if(resultModel.isEmpty()) {
            throw new ModelNotFountException("result nf");
        }
        return resultModel.get();
    }




    @Override
    public String getNewUid() {
        long id = Optional.ofNullable(resultRepository.getMaxUid()).orElse(0L);
        return String.valueOf(id);
    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }
}
