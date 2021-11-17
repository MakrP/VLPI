package lpnu.vlpi.avpz.converter.variant;

import lpnu.vlpi.avpz.dto.task.VariantDTO;
import lpnu.vlpi.avpz.model.VariantModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VariantConverter implements Converter<VariantModel, VariantDTO> {
    @Override
    public VariantDTO convert(VariantModel source) {
        VariantDTO variantDTO = new VariantDTO();
        variantDTO.setTaskUid(source.getUid());
        variantDTO.setDisplayName(source.getDisplayName());
        return variantDTO;
    }
}
