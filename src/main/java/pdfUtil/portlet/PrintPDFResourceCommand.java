package pdfUtil.portlet;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import pdfUtil.constants.PdfUtilPortletKeys;

@Component(immediate = true, property = { "javax.portlet.name=" + PdfUtilPortletKeys.PdfUtil,
		"mvc.command.name=/offering/printPDF" }, service = MVCResourceCommand.class)
public class PrintPDFResourceCommand implements MVCResourceCommand {
	
	private String templateLocation="/META-INF/resources/freemarker/template.ftl";


	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		//String html="<html><body> This is my Project </body></html>";
		Map<String,Object> data=new HashMap<String,Object>();
		Map<String,Object> myMap=new HashMap<String,Object>();
		myMap.put("myVariable1", "1");
		myMap.put("myVariable2", "2");

		OutputStream baos=new ByteArrayOutputStream();

		OutputStream outputStream=null;
		try {
			resourceResponse.setContentType("application/pdf");
			outputStream = resourceResponse.getPortletOutputStream();

	        Configuration config = new Configuration();
	        config.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
			data.put("name", "My Name");
			data.put("body", "Lorem ipsum ...... 1, 2, 3, 4, 5");
			data.put("myMap", myMap);

	        Template template = config.getTemplate(templateLocation);
			template.process(data,new OutputStreamWriter(baos));
			byte[] bytes=iTextUtil.createPDFfromHTMLStream(baos);
	
			outputStream.write(bytes);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}