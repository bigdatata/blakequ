package com.hao;

import android.app.Activity;
import android.os.Bundle;

/**
 * ϵͳ���ƴ�������--android.view.GestureDetector
 * ��Ҫ��ϵͳ�����Ʋ������򵥵Ļ���(����)�����ĳ��̵ȼ򵥵�����
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