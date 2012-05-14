package cm.constant;

public class AudioInfo extends MediaInfo{
	/**
     * A non human readable key calculated from the TITLE, used for
     * searching, sorting and grouping
     * <P>Type: TEXT</P>
     */
    public String title_key;

    /**
     * The duration of the audio file, in ms
     * <P>Type: INTEGER (long)</P>
     */
    public long duration;

    /**
     * The artist who created the audio file, if any
     * <P>Type: TEXT</P>
     */
    public String artist;

    /**
     * The album the audio file is from, if any
     * <P>Type: TEXT</P>
     */
    public String album;

    /**
     * The year the audio file was recorded, if any
     * <P>Type: INTEGER</P>
     */
    public int year;

    /**
     * Non-zero if the audio file is music
     * <P>Type: INTEGER (boolean)</P>
     */
    public boolean is_music;

    /**
     * Non-zero id the audio file may be a ringtone
     * <P>Type: INTEGER (boolean)</P>
     */
    public boolean is_ringtone;

	public String getTitle_key() {
		return title_key;
	}

	public long getDuration() {
		return duration;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public int getYear() {
		return year;
	}

	public boolean isIs_music() {
		return is_music;
	}

	public boolean isIs_ringtone() {
		return is_ringtone;
	}

	public void setTitle_key(String titleKey) {
		title_key = titleKey;
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

	public void setYear(int year) {
		this.year = year;
	}

	public void setIs_music(boolean isMusic) {
		is_music = isMusic;
	}

	public void setIs_ringtone(boolean isRingtone) {
		is_ringtone = isRingtone;
	}

	@Override
	public String toString() {
		return "AudioInfo [album=" + album + ", artist=" + artist
				+ ", duration=" + duration + ", is_music=" + is_music
				+ ", is_ringtone=" + is_ringtone + ", title_key=" + title_key
				+ ", year=" + year + "]";
	}
    
}
