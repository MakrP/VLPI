package lpnu.vlpi.avpz.service;


import lpnu.vlpi.avpz.model.ResultModel;

import java.util.List;

public interface ResultService extends BaseService{
    ResultModel getByUid(String uid);
    ResultModel createResult(ResultModel resultModel);
    List<ResultModel> getByUserAndTask(String tuid, String uuid);


}
