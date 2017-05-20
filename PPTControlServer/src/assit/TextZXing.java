package assit;

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class TextZXing {  
    private static final int BLACK = 0xFF000000;  
    private static final int WHITE = 0xFFFFFFFF;  
	
    public static BufferedImage createQRCode(String str,int widthAndHeight) throws WriterException {
		//内容所使用的编码
    	Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();  
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
        
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
		
		int width = matrix.getWidth();
		int height = matrix.getHeight();
   		
		 BufferedImage image = new BufferedImage(width, height,  
	                BufferedImage.TYPE_INT_RGB);  
		 for (int x = 0; x < width; x++) {  
	            for (int y = 0; y < height; y++) {  
	                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
	            }  
	        }  
		return image;
	}
}  

