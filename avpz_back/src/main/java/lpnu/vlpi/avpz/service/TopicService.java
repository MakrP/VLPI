package lpnu.vlpi.avpz.service;

import lpnu.vlpi.avpz.model.TopicModel;

public interface TopicService {
    TopicModel addNewTopic(TopicModel topic);
    void removeTopic(String topicUid);
    TopicModel getTopicByUid(String topicUid);
}
