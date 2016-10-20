package engine;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class Terminal implements Runnable {

	public static void main(String[] args) {

		Terminal t1 = new Terminal();

		Thread t = new Thread(t1);
		t.start();

	}

	public static void click(int x, int y, Robot robot) {
		robot.mouseMove(x, y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public static int[] scanForColor(int red, int green, int blue, Robot robot) {

		Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage bufferedImage = robot.createScreenCapture(captureSize);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		for (int i = 0; i < screen.getHeight(); i++) {
			for (int a = 0; a < screen.getWidth(); a++) {

				int color = bufferedImage.getRGB(a, i);
				int r = (color & 0x00ff0000) >> 16;
				int g = (color & 0x0000ff00) >> 8;
				int b = color & 0x000000ff;

				if (r == red && g == green && b == blue) {
					return new int[] { a, i };
				}

			}
		}
		return null;

	}

	@Override
	public void run() {

		boolean run = true;
		
		while (run) {

			Robot robot;
			try {
				robot = new Robot();
				System.out.println("Waiting for que[...]");

				int[] coordinates = scanForColor(229, 116, 0, robot);

//				System.out.println(robot.getPixelColor(MouseInfo.getPointerInfo().getLocation().x,
//						MouseInfo.getPointerInfo().getLocation().y));

				
				if (coordinates != null) {
					
					System.out.println("Check.");
					click(coordinates[0], coordinates[1], robot);
					run = false;
				}
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
