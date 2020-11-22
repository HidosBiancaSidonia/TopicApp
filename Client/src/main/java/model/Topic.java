package model;

import java.io.Serializable;

public class Topic implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Integer id_topic;
    private String topicName;

    /**
     * Constructor without parameters that sets all null
     */
    public Topic(){
        this.id_topic = 0;
        this.topicName = null;
    }

    /**
     * Constructor that sets all class variables
     * @param id_topic
     * @param topicName
     */
    public Topic(Integer id_topic, String topicName) {
        this.id_topic = id_topic;
        this.topicName = topicName;
    }

    /**
     * Constructor that sets topicName variable
     * @param topicName
     */
    public Topic(String topicName) {
        this.topicName = topicName;
    }

    /**
     * @return id_topic
     */
    public Integer getId_topic() {
        return id_topic;
    }

    /**
     * Sets the id_topic variable
     * @param id_topic
     */
    public void setId_topic(Integer id_topic) {
        this.id_topic = id_topic;
    }

    /**
     * @return topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * Sets the topicName variable
     * @param topicName
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * Converts the Topic object to a string
     * @return
     */
    @Override
    public String toString() {
        int nr=0;

        return "\r\n"  + nr++ + ". " + topicName +" \r\n" ;
    }
}
