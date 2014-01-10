package cm.exchange.util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * set custom text in the textview or other
 * @author qh
 *
 */
public class CustomTextViewUtil {

	/**
	 * set the style of string from start to end 
	 * @param wbText TextView
	 * @param string the start character
	 * @param string2 the end character
	 */
	public static void textHighlight(TextView textView,String start,String end){
		String text = textView.getText().toString();
		Spannable sp = new SpannableString(text);
		int n = 0;
		int s = -1;
		int e = -1;
		while (n < text.length()) {
			s = text.indexOf(start, n);
			if (s != -1) {
				e = text.indexOf(end, s + start.length());
				if (e != -1) {
					e = e + end.length();
				} else {
					e = text.length();
				}
				n = e;
				sp.setSpan(new ForegroundColorSpan(Color.BLUE), s, e,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				s = e = -1;
			} else {
				n = text.length();
			}
		}
		textView.setText(sp);
		}
}
