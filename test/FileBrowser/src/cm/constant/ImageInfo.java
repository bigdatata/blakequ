package cm.constant;

public class ImageInfo  extends MediaInfo{
	/**
     * The description of the image
     * <P>Type: TEXT</P>
     */
    private String description;

    /**
     * The date & time that the image was taken in units
     * of milliseconds since jan 1, 1970.
     * <P>Type: INTEGER</P>
     */
    private String datetaken;

	public String getDescription() {
		return description;
	}

	public String getDatetaken() {
		return datetaken;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDatetaken(String datetaken) {
		this.datetaken = datetaken;
	}

	@Override
	public String toString() {
		return "ImageInfo [datetaken=" + datetaken + ", description="
				+ description + "]";
	}
    
    
}
