package lpnu.vlpi.avpz.service;


import lpnu.vlpi.avpz.model.ResultModel;

public interface ResultService extends BaseService{
    ResultModel getByUid(String uid);
    ResultModel createResult(ResultModel resultModel);
    ResultModel getByUser(String uuid);
    ResultModel getByTask(String tuid);


}
