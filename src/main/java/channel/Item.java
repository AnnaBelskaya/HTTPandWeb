package channel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    public String id;
    public Snippet snippet;
    public Statistics statistics;
}
