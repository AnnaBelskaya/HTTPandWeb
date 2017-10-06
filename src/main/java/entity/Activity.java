package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    public ContentDetails contentDetails;
    public Snippet snippet;
    public Statistics statistics;
}
