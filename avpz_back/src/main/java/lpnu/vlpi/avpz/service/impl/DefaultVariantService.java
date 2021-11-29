package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.VariantRepository;
import lpnu.vlpi.avpz.model.VariantModel;
import lpnu.vlpi.avpz.service.exceptions.ModelNotFountException;
import lpnu.vlpi.avpz.service.exceptions.VariantService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultVariantService implements VariantService {

    private VariantRepository variantRepository;

    public DefaultVariantService(VariantRepository variantRepository) {
        this.variantRepository = variantRepository;
    }

    public VariantModel getByUid(String uid) {
        Optional<VariantModel> variantModelOptional = variantRepository.getByUid(uid);
        if (variantModelOptional.isEmpty()) {
            throw new ModelNotFountException(String.format("Variant not found with uid", uid));
        }
        return variantModelOptional.get();
    }

    @Override
    public String getNewUid() {
        long id = Optional.ofNullable(variantRepository.getMaxUid()).orElse(0L);
        return String.valueOf(id);    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }
}
