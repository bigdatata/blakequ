package cm.constant;

public class VideoInfo extends MediaInfo {

	/**
     * The duration of the video file, in ms
     * <P>Type: INTEGER (long)</P>
     */
    private long duration;

    /**
     * The artist who created the video file, if any
     * <P>Type: TEXT</P>
     */
    private String artist;

    /**
     * The album the video file is from, if any
     * <P>Type: TEXT</P>
     */
    private String album;

    /**
     * The resolution of the video file, formatted as "XxY"
     * <P>Type: TEXT</P>
     */
    private String resolution;

    /**
     * The description of the video recording
     * <P>Type: TEXT</P>
     */
    private String description;

    /**
     * The user-added tags associated with a video
     * <P>Type: TEXT</P>
     */
    private String tags;

    /**
     * The YouTube category of the video
     * <P>Type: TEXT</P>
     */
    private String category;

    /**
     * The language of the video
     * <P>Type: TEXT</P>
     */
    private String language;

	public long getDuration() {
		return duration;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public String getResolution() {
		return resolution;
	}

	public String getDescription() {
		return description;
	}

	public String getTags() {
		return tags;
	}

	public String getCategory() {
		return category;
	}

	public String getLanguage() {
		return language;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "VideoInfo [album=" + album + ", artist=" + artist
				+ ", category=" + category + ", description=" + description
				+ ", duration=" + duration + ", language=" + language
				+ ", resolution=" + resolution + ", tags=" + tags + "]";
	}
    
}
