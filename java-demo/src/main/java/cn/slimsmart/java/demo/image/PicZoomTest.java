package cn.slimsmart.java.demo.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class PicZoomTest {

	public static void main(String[] args) throws Exception {
		BufferedImage img = PicZoom.zoom("src/main/resources/123.png");
		ImageIO.write(img, "png", new File("src/main/resources/123_thumbnails.png"));
	}
}
