package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.ResultRepository;
import lpnu.vlpi.avpz.model.ResultModel;
import lpnu.vlpi.avpz.service.ResultService;
import lpnu.vlpi.avpz.service.exceptions.ModelNotFountException;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<ResultModel> getByUserAndTask(String tuid, String uuid) {
        return resultRepository.getByTaskUidAndUserUidOrderByUid(tuid, uuid);
    }




    @Override
    public String getNewUid() {
        long id = Optional.ofNullable(resultRepository.getMaxUid()).orElse(0L);
        return String.valueOf(id + 1);
    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }
}
