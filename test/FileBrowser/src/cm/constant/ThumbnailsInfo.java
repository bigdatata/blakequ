package cm.constant;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * ��������ͼ����Ϣ����Ҫר�Ų�ѯ
 * {@link android.app.SearchManager#QUERY}
 * {@link android.provider.MediaStore.Images.Thumbnails}����
 * {@link android.provider.MediaStore.Video.Thumbnails}�е�
 * query(ContentResolver cr, Uri uri, String[] projection)������ȡ��
 * Ȼ��ͨ��Bitmap getThumbnail(ContentResolver cr, long origId, int kind,
                    BitmapFactory.Options options)��ȡ����ͼ
 * @author Administrator
 *
 */
public class ThumbnailsInfo {
	/**
     * The data stream for the thumbnail
     * <P>Type: DATA STREAM</P>
     */
    private String data;

    /**
     * The original image or video for the thumbnal
     * <P>Type: INTEGER (ID from Images table)</P>
     */
    private String id;
    
    /**
     * The kind of the thumbnail
     * <P>Type: INTEGER (One of the values below)</P>
     * int MINI_KIND = 1;
     * int FULL_SCREEN_KIND = 2;
     * int MICRO_KIND = 3;
     */
    private String kind;
    /**
     * The width of the thumbnal
     * <P>Type: INTEGER (long)</P>
     */
    private String width;

    /**
     * The height of the thumbnail
     * <P>Type: INTEGER (long)</P>
     */
    private String height;

	public String getData() {
		return data;
	}

	public String getId() {
		return id;
	}

	public String getKind() {
		return kind;
	}

	public String getWidth() {
		return width;
	}

	public String getHeight() {
		return height;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setHeight(String height) {
		this.height = height;
	}
    
    
}
