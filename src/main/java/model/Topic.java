package model;

import java.io.Serializable;

public class Topic implements Serializable {
    private static final long serializeVersionUID = 1L;
    private Integer id_topic;
    private String topicName;

    public Topic(Integer id_topic, String topicName) {
        this.id_topic = id_topic;
        this.topicName = topicName;
    }

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Integer getId_topic() {
        return id_topic;
    }

    public void setId_topic(Integer id_topic) {
        this.id_topic = id_topic;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicName='" + topicName + '\'' +
                '}';
    }
}
