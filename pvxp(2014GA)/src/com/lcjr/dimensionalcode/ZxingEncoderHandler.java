package com.lcjr.dimensionalcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * ����ͼƬ���ܶ�ά�������Ϣ
 * 
 * @blog http://sjsky.iteye.com
 * @author Michael
 */
public class ZxingEncoderHandler {

	/**
	 * ����
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public void encode(String contents, int width, int height, String imgPath) {
		HashMap hints = new HashMap();

		// ָ������ȼ�
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// ָ�������ʽ
		hints.put(EncodeHintType.CHARACTER_SET, "GBK");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

			MatrixToImageWriter.writeToFile(bitMatrix, "png", new File(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String decode(String imgPath) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(imgPath));
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Hashtable hints = new Hashtable();
			hints.put(DecodeHintType.CHARACTER_SET, "GBK");

			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// // ��ά���ͼƬ��ַ
		// String imgPath = "D:/1.png";
		// // ��ά���Ҫ����Ϣ
		// String contents = "Hello zhangjun,welcome to Zxing!"
		// + "\nzhangjun��s blog [ http://sjsky.iteye.com ]"
		// + "\nEMail [ sjsky007@gmail.com ]" + "\nTwitter [ @suncto ]";
		// // ��ά��Ŀ�ȸ߶�
		// int width = 300, height = 300;
		ZxingEncoderHandler handler = new ZxingEncoderHandler();
		// // ���ɶ�ά��
		// handler.encode(contents, width, height, imgPath);

		System.out.println("Michael ,you have finished zxing encode.");

		// ������ά��
		String decodeImgPath = "D:/1.png";
		String decodeContent = handler.decode(decodeImgPath);
		System.out.println("�����������£�");
		System.out.println(decodeContent);
		System.out.println("Michael ,you have finished zxing decode.");
	}
}