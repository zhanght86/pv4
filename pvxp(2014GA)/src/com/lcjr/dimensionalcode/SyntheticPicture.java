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
 * �ϲ�ͼƬ
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
     *            Ƕ��ͼƬ��Ƕ������ mainImagePath
     * @param mainImagePath
     *            ��ͼƬ--��Ƕ���ά���ͼƬ
     * @param subImagePath
     *            ��ά��ͼƬ��ַ
     * @param complexImagePath
     *            ���� �ϲ����ͼƬ��ַ
     */
    public void createPicTwo(int x, int y, String mainImagePath,
	    String subImagePath, String complexImagePath) {
	try {
	    // ��ȡ��һ��ͼƬ������ͼƬ
	    File fileOne = new File(mainImagePath);
	    BufferedImage ImageOne = ImageIO.read(fileOne);
	    int width = ImageOne.getWidth();// ͼƬ���
	    int height = ImageOne.getHeight();// ͼƬ�߶�
	   
	    // ��ͼƬ�ж�ȡRGB
	    int[] ImageArrayOne = new int[width * height];
	    ImageArrayOne = ImageOne.getRGB(0, 0, width, height, ImageArrayOne,
		    0, width);
	   
	    // �ڶ���ͼƬΪ��ά��ͼƬ
	    File fileTwo = new File(subImagePath);
	    BufferedImage ImageTwo = ImageIO.read(fileTwo);
	    int widthTwo = ImageTwo.getWidth();// ͼƬ���
	    int heightTwo = ImageTwo.getHeight();// ͼƬ�߶�

	    int[] ImageArrayTwo = new int[widthTwo * heightTwo];
	    ImageArrayTwo = ImageTwo.getRGB(0, 0, widthTwo, heightTwo,
		    ImageArrayTwo, 0, widthTwo);

	    // ������ͼƬ
	    BufferedImage ImageNew = new BufferedImage(width, height,
		    BufferedImage.TYPE_INT_RGB);
	    ImageNew.setRGB(0, 0, width, height, ImageArrayOne, 0, width);// ������벿�ֵ�RGB
	    ImageNew.setRGB(x, y, widthTwo, heightTwo, ImageArrayTwo, 0,
		    widthTwo);// �����Ұ벿�ֵ�RGB
	    File outFile = new File(complexImagePath);
	    ImageIO.write(ImageNew, "png", outFile);// дͼƬ

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /*
     * ����ά���и����
     */
    /**
     * @param srcImageFile
     *            ���и��ͼƬ file �и������ͼƬ�Ĵ�� w �и�ͼƬ�Ŀ�� h �и�ͼƬ�ĸ߶� x1 y1 �и��ͼƬ������
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
		sw = ImageOne.getWidth();// ͼƬ���
		sh = ImageOne.getHeight();// ͼƬ�߶�
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    Image img;

	    ImageFilter cropFilter;

	    // ��ȡԴͼ��

	    BufferedImage bi = ImageIO.read(new File(srcImageFile));

	    if (sw >= w && sh >= h) {

		Image image = bi.getScaledInstance(sw, sh, Image.SCALE_DEFAULT);

		// ������ʼ�����

		int x = x1;

		int y = y1;

		int destWidth = w; // ��Ƭ���

		int destHeight = h; // ��Ƭ�߶�

		// ͼƬ����

		double pw = sw;

		double ph = sh;

		double m = (double) sw / pw;

		double n = (double) sh / ph;

		int wth = (int) (destWidth * m);

		int hth = (int) (destHeight * n);

		int xx = (int) (x * m);

		int yy = (int) (y * n);

		// �ĸ������ֱ�Ϊͼ���������Ϳ��

		// ��: CropImageFilter(int x,int y,int width,int height)

		cropFilter = new CropImageFilter(xx, yy, wth, hth);

		img = Toolkit.getDefaultToolkit().createImage(

		new FilteredImageSource(image.getSource(), cropFilter));

		BufferedImage tag = new BufferedImage(w, h,

		BufferedImage.TYPE_INT_RGB);

		Graphics g = tag.getGraphics();

		g.drawImage(img, 0, 0, null); // ������С���ͼ

		g.dispose();

		// ���Ϊ�ļ�

		ImageIO.write(tag, "JPEG", file);

	    }

	} catch (Exception e) {

	    e.printStackTrace();

	}

    }

    public static void main(String args[]) {
	// �ϲ�ͼƬ
	SyntheticPicture sp = new SyntheticPicture();
	String mainImagePath, subImagePath, complexImagePath;
	mainImagePath = "D:\\1.jpg";
	subImagePath = "D:\\2.png";
	complexImagePath = "D:\\new.png";
	sp.createPicTwo(0, 0, mainImagePath, subImagePath, complexImagePath);

	// �и�ͼƬ
	File fileOne = new File("d:/1.jpg");
	BufferedImage ImageOne;
	int width = 0;
	int height = 0;
	try {
	    ImageOne = ImageIO.read(fileOne);
	    width = ImageOne.getWidth();// ͼƬ���
	    height = ImageOne.getHeight();// ͼƬ�߶�
	} catch (IOException e) {
	    e.printStackTrace();
	}
	// ָ��Ҫ�и�ͼƬ
	String decodeImgPath = "D:\\new.png";
	// �и�����ɵ�ͼƬ
	File file = new File("d:/cut.jpg");
	sp.cut(decodeImgPath, file, 300, 300, 0, 0);
    }
}
