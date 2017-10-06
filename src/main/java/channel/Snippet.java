package channel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Snippet {
    public String title;
    public String description;
    public String publishedAt;
    public Thumbnails thumbnails;
}
