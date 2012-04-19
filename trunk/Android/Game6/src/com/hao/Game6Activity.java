package com.hao;

import android.app.Activity;
import android.os.Bundle;

/**
 * 系统手势触屏操作--android.view.GestureDetector
 * 主要是系统的手势操作，简单的滑动(快慢)，按的长短等简单的手势
 * @author Administrator
 *http://xiaominghimi.blog.51cto.com/2614927/606775
 */
public class Game6Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GestrueView(this, null));
    }
}