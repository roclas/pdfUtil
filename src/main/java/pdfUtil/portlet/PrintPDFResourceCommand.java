package pdfUtil.portlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import pdfUtil.constants.PdfUtilPortletKeys;

@Component(immediate = true, property = { "javax.portlet.name=" + PdfUtilPortletKeys.PdfUtil,
		"mvc.command.name=/offering/printPDF" }, service = MVCResourceCommand.class)
public class PrintPDFResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String html="<html><body> This is my Project </body></html>";
		byte[] bytes=iTextUtil.createPDFfromHTML(html);
		OutputStream outputStream=null;
		try {
			resourceResponse.setContentType("application/pdf");
			outputStream = resourceResponse.getPortletOutputStream();
			outputStream.write(bytes);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}