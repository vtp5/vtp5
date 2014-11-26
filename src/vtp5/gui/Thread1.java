package vtp5.gui;

public class Thread1 extends VTP5 implements Runnable{

	@Override
	public void run() {
		
		scaler = Math.min(
				newSize.getWidth() / FrameListener.originalSize.getWidth(),
				newSize.getHeight() / FrameListener.originalSize.getHeight());
	}

}
