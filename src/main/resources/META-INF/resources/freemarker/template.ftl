<html>
	<body>
		<div>
			<span><img src="http://${server}:${port}${basepath}/${logo}" alt="Header" /></span><span><h1>Header</h1></span>
		</div>
		<table>
		<#assign keys = data?keys?sort>
		<#list keys as key>
			<tr>
    		<td><b>${key}</b></td><td>${data[key]}</td>
			</tr>
		</#list>
		</table>
		<div>
			<span><img src="http://${server}:${port}${basepath}/${logo}" alt="Footer" /></span><span><h1>Footer</h1></span>
		</div>
	</body>
</html>