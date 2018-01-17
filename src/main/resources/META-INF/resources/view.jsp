<%@ include file="/init.jsp" %>

<p>
	<portlet:resourceURL id="/offering/printPDF" var="printPDFResourceURL" />
	<form action="<%=printPDFResourceURL%>" method="post" name="<portlet:namespace />fm" >
		<input type="submit" value="<liferay-ui:message key="pdfUtil.printPDF" />"/>
	</form>
</p>