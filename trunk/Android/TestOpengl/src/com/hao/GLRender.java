package com.hao;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GLRender implements Renderer{

	 float rotateTri,rotateQuad;
	    int one=0x10000;
	    
	    //�����ε�һ������
	    private int[] trigger = new int[]{
	            0,one,0,     //�϶���
	            -one,-one,0,    //�󶥵�
	            one,-one,0    //���µ�
	    };
	    private IntBuffer triggerBuffer;
	    
	    
	    //�����ε��ĸ�����
	    private int[] quate = new int[]{
	            one,one,0,
	            -one,-one,0,
	            one,-one,0,
	            -one,-one,0
	    };
	    private IntBuffer quateBuffer;
	    
	    private int[] color = new int[]{
	            one,0,0,one,
	            0,one,0,one,
	            0,0,one,one
	    };
	    private IntBuffer colorBuffer;
	    
	    
	    
	    @Override
	    public void onDrawFrame(GL10 gl) {
	        // TODO Auto-generated method stub
	    	triggerBuffer = ByteBuffer.allocateDirect(trigger.length*8).order(ByteOrder.nativeOrder()).asIntBuffer();
	    	triggerBuffer.put(trigger);
	    	triggerBuffer.position(0);
	    	
	    	quateBuffer = ByteBuffer.allocateDirect(trigger.length*8).order(ByteOrder.nativeOrder()).asIntBuffer();
	    	quateBuffer.put(quate);
	    	quateBuffer.position(0);
	    	
	    	colorBuffer = ByteBuffer.allocateDirect(trigger.length*8).order(ByteOrder.nativeOrder()).asIntBuffer();
	    	colorBuffer.put(color);
	    	colorBuffer.position(0);
	    	
	        // �����Ļ����Ȼ���
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        // ���õ�ǰ��ģ�͹۲����
	        gl.glLoadIdentity();


	        // ���� 1.5 ��λ����������Ļ 6.0
	        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
	         //������ת
	        gl.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);
	        
	        //���ö�������
	        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	        //������ɫ����
	        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	        
	        gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
	        // ���������ζ���
	        gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
	        //����������
	        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	        
	        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	        
	        //���������ν���
	        gl.glFinish();
	        
	        /***********************/
	        /* ��Ⱦ������ */
	        // ���õ�ǰ��ģ�͹۲����
	        gl.glLoadIdentity();
	        
	        // ���� 1.5 ��λ����������Ļ 6.0
	        gl.glTranslatef(1.5f, 0.0f, -6.0f);
	        
	        // ���õ�ǰɫΪ��ɫ
	        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
	        //������ת
	        gl.glRotatef(rotateQuad, 1.0f, 0.0f, 0.0f);
	        
	        //���úͻ���������
	        gl.glVertexPointer(3, GL10.GL_FIXED, 0, quateBuffer);
	        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	        
	        //���������ν���
	        gl.glFinish();

	        //ȡ����������
	        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	        
	        //�ı���ת�ĽǶ�
	        rotateTri += 0.5f;
	        rotateQuad -= 0.5f;
	    }

	    @Override
	    public void onSurfaceChanged(GL10 gl, int width, int height) {
	        // TODO Auto-generated method stub
	        
	        float ratio = (float) width / height;
	        //����OpenGL�����Ĵ�С
	        gl.glViewport(0, 0, width, height);
	        //����ͶӰ����
	        gl.glMatrixMode(GL10.GL_PROJECTION);
	        //����ͶӰ����
	        gl.glLoadIdentity();
	        // �����ӿڵĴ�С
	        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
	        // ѡ��ģ�͹۲����
	        gl.glMatrixMode(GL10.GL_MODELVIEW);    
	        // ����ģ�͹۲����
	        gl.glLoadIdentity();    
	        
	    }

	    @Override
	    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	        // TODO Auto-generated method stub
	        // ������Ӱƽ��
	        gl.glShadeModel(GL10.GL_SMOOTH);
	        
	        // ��ɫ����
	        gl.glClearColor(0, 0, 0, 0);
	        
	        // ������Ȼ���
	        gl.glClearDepthf(1.0f);                            
	        // ������Ȳ���
	        gl.glEnable(GL10.GL_DEPTH_TEST);                        
	        // ������Ȳ��Ե�����
	        gl.glDepthFunc(GL10.GL_LEQUAL);                            
	        
	        // ����ϵͳ��͸�ӽ�������
	        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
	    }

}
