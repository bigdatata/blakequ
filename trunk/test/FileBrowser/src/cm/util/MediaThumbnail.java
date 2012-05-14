package cm.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class MediaThumbnail {
	/**
	 * ������ƵUri��ַȡ��ָ������Ƶ����ͼ
	 * @param cr  
	 * @param uri  ������ƵUri��ʾ
	 * @return ����bitmap��������
	 */
	public static Bitmap getVideoThumbnail(ContentResolver cr, Uri uri) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Cursor cursor = cr.query(uri, new String[] { MediaStore.Video.Media._ID }, null, null, null);

		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}
		cursor.moveToFirst();
		String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID)); // image id in image table.s

		if (videoId == null) {
			return null;
		}
		cursor.close();
		long videoIdLong = Long.parseLong(videoId);
		bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,
				Images.Thumbnails.MICRO_KIND, options);

		return bitmap;
	}
	
	
}
