package lpnu.vlpi.avpz.controller.builder;

import lpnu.vlpi.avpz.dao.VariantRepository;
import lpnu.vlpi.avpz.model.TaskModel;
import lpnu.vlpi.avpz.model.TopicModel;
import lpnu.vlpi.avpz.model.VariantModel;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskBuilder {
    private TaskModel instance;

    @Autowired
    private TopicService topicService;

    @Autowired
    private VariantRepository variantRepository;

    public TaskBuilder() {
        instance = new TaskModel();
    }

    public void buildLevel(Level level) {
       instance.setLevel(level);
    }

    public void buildVariant(String varinatName) {
        if(instance.getVariants() == null) {
            instance.setVariants(new ArrayList<>());
        }
        VariantModel variantModel = new VariantModel();
        variantModel.setDisplayName(varinatName);
        instance.getVariants().add(variantRepository.save(variantModel));
    }

    public void buildTopic(String topicDN) {
        TopicModel topicModel = new TopicModel();
        topicModel.setDisplayName(topicDN);
        topicService.addNewTopic(topicModel);
        instance.setTopic(topicModel);
    }

    public TaskModel build() {
        return instance;
    }



    
}
