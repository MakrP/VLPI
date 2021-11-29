package lpnu.vlpi.avpz.service.impl;

import lpnu.vlpi.avpz.dao.TopicRepository;
import lpnu.vlpi.avpz.model.TopicModel;
import lpnu.vlpi.avpz.service.TopicService;
import lpnu.vlpi.avpz.service.exceptions.TopicNotFountException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultTopicService implements TopicService {

    private TopicRepository topicRepository;

    public DefaultTopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public TopicModel addNewTopic(TopicModel topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void removeTopic(String topicUid) {
        topicRepository.deleteByUid(topicUid);
    }

    @Override
    public TopicModel getTopicByUid(String topicUid) {
        Optional<TopicModel> topicModel = topicRepository.findByUid(topicUid);
        if (topicModel.isEmpty()) {
            throw new TopicNotFountException(topicUid);
        }
        return topicModel.get();
    }

    @Override
    public String getNewUid() {
        long id = Optional.ofNullable(topicRepository.getMaxUid()).orElse(0L);
        return String.valueOf(id + 1);
    }

    @Override
    public int getPagesCount(long size) {
        return 0;
    }
}
