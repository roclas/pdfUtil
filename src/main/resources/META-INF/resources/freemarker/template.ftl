<html>
	<body>
		This PDF has been created from a freemarker template<br><br><br><br><br>
		<b>name=${name} (written in bold)</b><br><br><br>
		<p>${body}</p><br><br><br>
		<table>
		<#assign keys = myMap?keys>
		<#list keys as key>
			<tr>
    		<td>${key}</td><td>${myMap[key]}</td>
			</tr>
		</#list>
		</table>
	</body>
</html>