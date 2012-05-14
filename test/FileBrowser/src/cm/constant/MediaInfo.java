package cm.constant;

public class MediaInfo {
	/**
     * The data stream for the file
     * <P>Type: DATA STREAM</P>
     */
    private String data;

    /**
     * The size of the file in bytes
     * <P>Type: INTEGER (long)</P>
     */
    private long size;

    /**
     * The display name of the file
     * <P>Type: TEXT</P>
     */
    private String display_name;

    /**
     * The title of the content
     * <P>Type: TEXT</P>
     */
    private String title;

    /**
     * The time the file was added to the media provider
     * Units are seconds since 1970.
     * <P>Type: INTEGER (long)</P>
     */
    private String date_added;

    /**
     * The time the file was last modified
     * Units are seconds since 1970.
     * NOTE: This is for internal use by the media scanner.  Do not modify this field.
     * <P>Type: INTEGER (long)</P>
     */
    private String date_modified;

    /**
     * The MIME type of the file
     * <P>Type: TEXT</P>
     */
    private String mime_type;

	public String getData() {
		return data;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public String getTitle() {
		return title;
	}

	public String getDate_added() {
		return date_added;
	}

	public String getDate_modified() {
		return date_modified;
	}

	public String getMime_type() {
		return mime_type;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setDisplay_name(String displayName) {
		display_name = displayName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate_added(String dateAdded) {
		date_added = dateAdded;
	}

	public void setDate_modified(String dateModified) {
		date_modified = dateModified;
	}

	public void setMime_type(String mimeType) {
		mime_type = mimeType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "MediaInfo [data=" + data + ", date_added=" + date_added
				+ ", date_modified=" + date_modified + ", display_name="
				+ display_name + ", mime_type=" + mime_type + ", size=" + size
				+ ", title=" + title + "]";
	}
    
}
