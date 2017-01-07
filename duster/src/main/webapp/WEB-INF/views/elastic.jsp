<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Home</title>
</head>
<body>

	<P>The index search result is {{completeResult}}</P>

	<ul>
		{{#searchResult}}
		<li>{{id}} :  {{getSource}}</li>
		 <li>{{/searchResult}}</li>
	</ul>
	

</body>
</html>