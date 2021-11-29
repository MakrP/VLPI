package lpnu.vlpi.avpz.service.exceptions;


import lpnu.vlpi.avpz.model.VariantModel;
import lpnu.vlpi.avpz.service.BaseService;

public interface VariantService extends BaseService {
    VariantModel getByUid(String uid);
    VariantModel create(VariantModel variantModel);
}
