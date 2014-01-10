package cm.exchange.util;

/**
 * 
 * @author qh
 *
 */
public interface PrimaryActivity {
	
	/**
	 * Initialize the title
	 * Note:you must rewrite the method when use the title
	 */
	public void initTitle();
	
	/**
	 * set the title of tab
	 * @param leftId left imageButton image resource
	 * @param midId middle textView string resource
	 * @param rightId right imageButton image Resource
	 */
	void changeTitle(int leftId, int midId, int rightId);

	/**
	 *the action when clicked right button of title
	 */
	public void onRightButtonClicked();
}
