package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.model.TopicModel;

public interface TopicService extends BaseService {
    TopicModel addNewTopic(TopicModel topic);
    void removeTopic(String topicUid);
    TopicModel getTopicByUid(String topicUid);
}
