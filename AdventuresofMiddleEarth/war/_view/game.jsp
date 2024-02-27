<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html>
<head>
    <title>Game</title>
    <style type="text/css">
        .error {
            color: red;
        }
        td.label {
            text-align: right;
        }
    </style>
</head>

<body>
	<div>
		<ol>
			<c:forTokens items = "${dialog}" delims = ";" var = "item" >
				<li><c:out value = "${item}"/></li>
			</c:forTokens>
		</ol>
	</div>
	<span id="end"></span>
</body>
</html>