package pdfUtil.portlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;



public class iTextUtil {



    public static byte[] createHelloWorld() {
    	ByteArrayOutputStream stream=new ByteArrayOutputStream();
    	try {
    		Document document = getDocument(stream);

            Paragraph p = new Paragraph();
            p.add("This is my paragraph 1");
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            Paragraph p2 = new Paragraph();
            p2.add("This is my paragraph 2"); //no alignment
            document.add(p2);
            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);
            document.add(new Paragraph("This is my paragraph 3", f));
            document.close();
            System.out.println("Done creating PDF");
        } catch (Exception e) {
            System.out.println("Exception creating PDF");
            e.printStackTrace();
        }
    	return stream.toByteArray();
    	

    }


    public static byte[] createPDFfromHTMLStream(OutputStream baos, ServletContext servletContext){
    	InputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) baos).toByteArray());
    	ByteArrayOutputStream outStream= new ByteArrayOutputStream();
    	try {
    		Document document = getDocument(outStream);
    	    HTMLWorker htmlWorker = new HTMLWorker(document);
    	    //document.add(createImage(servletContext,LOGO)); //this would put it in the background
    	    htmlWorker.parse(new InputStreamReader(inputStream, "UTF-8"));
    	    //document.add(createImage(servletContext,LOGO)); //this would put it in the background
    	    document.close();
            LOG.info("Done creating PDF from HTML");
    	} catch (Exception e) {
            LOG.error("Exception creating PDF from HTML");
            LOG.error(baos.toString());
    	    e.printStackTrace();
    	}
    	return outStream.toByteArray();
    }


	private static Image createImage(ServletContext servletContext,String path) {
		Image img=null;
		try {
			InputStream stream = servletContext.getResourceAsStream(path);
			byte[] bytes = IOUtils.toByteArray(stream);
			img = Image.getInstance(bytes);//TODO: uncomment
        	img.scaleToFit(770, 523);
        	float offsetX = (770 - img.getScaledWidth()) / 2;
        	float offsetY = (523 - img.getScaledHeight()) / 2;
        	//img.setAbsolutePosition(36 + offsetX, 36 + offsetY);
        	img.setAbsolutePosition(0, 0);
		} catch (Exception e1) {
			e1.printStackTrace(); 
		}
		return img;
	}


	private static Document getDocument(ByteArrayOutputStream byteArrayOutputStream) throws DocumentException, FileNotFoundException {
		Document document = new Document();
		PdfWriter.getInstance(document, byteArrayOutputStream);  // Do this BEFORE document.open()
        document.open();
		return document;
	}
	
    private static final Log LOG = LogFactoryUtil.getLog(iTextUtil.class);

 

}