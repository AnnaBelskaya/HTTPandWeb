package channel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics {
    public String viewCount;
    public String commentCount;
    public String subscriberCount;
    public boolean hiddenSubscriberCount;
    public String videoCount;

}
