package com.lcjr.dimensionalcode;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 合并图片
 * 
 * @author
 * @version VTM_MANAGER
 * @date 2014-10-24
 */
public class SyntheticPicture {

    /**
     * 
     * @param x
     * @param y
     *            嵌入图片的嵌入坐标 mainImagePath
     * @param mainImagePath
     *            主图片--待嵌入二维码的图片
     * @param subImagePath
     *            二维码图片地址
     * @param complexImagePath
     *            复合 合并后的图片地址
     */
    public void createPicTwo(int x, int y, String mainImagePath,
	    String subImagePath, String complexImagePath) {
	try {
	    // 读取第一张图片，背景图片
	    File fileOne = new File(mainImagePath);
	    BufferedImage ImageOne = ImageIO.read(fileOne);
	    int width = ImageOne.getWidth();// 图片宽度
	    int height = ImageOne.getHeight();// 图片高度
	   
	    // 从图片中读取RGB
	    int[] ImageArrayOne = new int[width * height];
	    ImageArrayOne = ImageOne.getRGB(0, 0, width, height, ImageArrayOne,
		    0, width);
	   
	    // 第二张图片为二维码图片
	    File fileTwo = new File(subImagePath);
	    BufferedImage ImageTwo = ImageIO.read(fileTwo);
	    int widthTwo = ImageTwo.getWidth();// 图片宽度
	    int heightTwo = ImageTwo.getHeight();// 图片高度

	    int[] ImageArrayTwo = new int[widthTwo * heightTwo];
	    ImageArrayTwo = ImageTwo.getRGB(0, 0, widthTwo, heightTwo,
		    ImageArrayTwo, 0, widthTwo);

	    // 生成新图片
	    BufferedImage ImageNew = new BufferedImage(width, height,
		    BufferedImage.TYPE_INT_RGB);
	    ImageNew.setRGB(0, 0, width, height, ImageArrayOne, 0, width);// 设置左半部分的RGB
	    ImageNew.setRGB(x, y, widthTwo, heightTwo, ImageArrayTwo, 0,
		    widthTwo);// 设置右半部分的RGB
	    File outFile = new File(complexImagePath);
	    ImageIO.write(ImageNew, "png", outFile);// 写图片

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /*
     * 将二维码切割出来
     */
    /**
     * @param srcImageFile
     *            待切割的图片 file 切割出来的图片的存放 w 切割图片的宽度 h 切割图片的高度 x1 y1 切割的图片的坐标
     */
    public void cut(String srcImageFile, File file, int w, int h, int x1,

    int y1) {

	try {

	    File fileOne = new File(srcImageFile);
	    BufferedImage ImageOne;
	    int sw = 0;
	    int sh = 0;
	    try {
		ImageOne = ImageIO.read(fileOne);
		sw = ImageOne.getWidth();// 图片宽度
		sh = ImageOne.getHeight();// 图片高度
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    Image img;

	    ImageFilter cropFilter;

	    // 读取源图像

	    BufferedImage bi = ImageIO.read(new File(srcImageFile));

	    if (sw >= w && sh >= h) {

		Image image = bi.getScaledInstance(sw, sh, Image.SCALE_DEFAULT);

		// 剪切起始坐标点

		int x = x1;

		int y = y1;

		int destWidth = w; // 切片宽度

		int destHeight = h; // 切片高度

		// 图片比例

		double pw = sw;

		double ph = sh;

		double m = (double) sw / pw;

		double n = (double) sh / ph;

		int wth = (int) (destWidth * m);

		int hth = (int) (destHeight * n);

		int xx = (int) (x * m);

		int yy = (int) (y * n);

		// 四个参数分别为图像起点坐标和宽高

		// 即: CropImageFilter(int x,int y,int width,int height)

		cropFilter = new CropImageFilter(xx, yy, wth, hth);

		img = Toolkit.getDefaultToolkit().createImage(

		new FilteredImageSource(image.getSource(), cropFilter));

		BufferedImage tag = new BufferedImage(w, h,

		BufferedImage.TYPE_INT_RGB);

		Graphics g = tag.getGraphics();

		g.drawImage(img, 0, 0, null); // 绘制缩小后的图

		g.dispose();

		// 输出为文件

		ImageIO.write(tag, "JPEG", file);

	    }

	} catch (Exception e) {

	    e.printStackTrace();

	}

    }

    public static void main(String args[]) {
	// 合并图片
	SyntheticPicture sp = new SyntheticPicture();
	String mainImagePath, subImagePath, complexImagePath;
	mainImagePath = "D:\\1.jpg";
	subImagePath = "D:\\2.png";
	complexImagePath = "D:\\new.png";
	sp.createPicTwo(0, 0, mainImagePath, subImagePath, complexImagePath);

	// 切割图片
	File fileOne = new File("d:/1.jpg");
	BufferedImage ImageOne;
	int width = 0;
	int height = 0;
	try {
	    ImageOne = ImageIO.read(fileOne);
	    width = ImageOne.getWidth();// 图片宽度
	    height = ImageOne.getHeight();// 图片高度
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// 指定要切割图片
	String decodeImgPath = "D:\\new.png";
	// 切割后生成的图片
	File file = new File("d:/cut.jpg");
	sp.cut(decodeImgPath, file, 300, 300, 0, 0);
    }
}
