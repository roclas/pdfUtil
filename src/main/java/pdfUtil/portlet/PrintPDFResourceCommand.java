package pdfUtil.portlet;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.PortalUtil;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import pdfUtil.constants.PdfUtilPortletKeys;

@Component(immediate = true, property = { "javax.portlet.name=" + PdfUtilPortletKeys.PdfUtil,
		"mvc.command.name=/offering/printPDF" }, service = MVCResourceCommand.class)
public class PrintPDFResourceCommand implements MVCResourceCommand {
	
	private String templateLocation="/META-INF/resources/freemarker/template.ftl";
	private static String logoLocation="freemarker/logo.png";

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		//String html="<html><body> This is my Project </body></html>";
		Map<String,Object> data=new HashMap<String,Object>();
		Map<String,Object> myMap=new HashMap<String,Object>();
		for(int i=0;i<15;i++) myMap.put(String.format("My variable %02d", i),i);

		OutputStream baos=new ByteArrayOutputStream();

		OutputStream outputStream=null;
		try {
			resourceResponse.setContentType("application/pdf");
			outputStream = resourceResponse.getPortletOutputStream();

	        Configuration config = new Configuration();
	        config.setTemplateLoader(new ClassTemplateLoader(getClass(), "/"));
	        
	        
	        HttpServletRequest request = PortalUtil.getHttpServletRequest(resourceRequest);
	        ServletContext servletContext = request.getSession().getServletContext();


	        Template template = config.getTemplate(templateLocation);
	        //config.getTemplate(templateLocation).getSourceName();
			data.put("basepath", servletContext.getContextPath().toString());
			data.put("port", (new Integer(request.getLocalPort())).toString());
			data.put("server", request.getServerName());
			data.put("logo", logoLocation);
			data.put("data", myMap);

			template.process(data,new OutputStreamWriter(baos));
			byte[] bytes=iTextUtil.createPDFfromHTMLStream(baos, servletContext);
			//byte[] bytes=PdfUtil.createPDFfromHTMLString(baos.toString());
			outputStream.write(bytes);
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}