<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>

<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<c:url value='/welcome' />">Profile</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                    </ul>
                    <form class="nav navbar-nav navbar-centr">
                        <li><a href="<c:url value='/welcome' />">Welcome ${pageContext.request.userPrincipal.name}</a></li>
                        <c:forEach var="role" items="${user.roles}">
                            <c:if test="${role.id == 1}">
                                <li><a href="<c:url value='/statistics' />">Statistics</a></li>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="role" items="${user.roles}">
                            <c:if test="${role.id == 2}">
                                <li><a href="<c:url value='/change' />">Statistics</a></li>
                            </c:if>
                        </c:forEach>
                    </form>
                    <ul class="nav navbar-nav navbar-right">

                        <li>
                            <a href="#" onclick="document.forms['logoutForm'].submit()">Logout</a>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

    </c:if>
    <table class = "table" id = "profile">
        <tr>
            <td><span style="align:right" class="label label-default">login:</span></td>
            <td></h>${user.username}</h></td>
        </tr>
        <tr>
            <td><span style="align:right" class="label label-default">first name:</span></td>
            <td><h>${user.firstname}</h></td>
        </tr>
        <tr>
            <td><span style="align:right" class="label label-default">surname:</span></td>
            <td><h>${user.lastname}</h></td>
        </tr>
        <c:if test="${user.profession != null}">
        <tr>
            <td><span style="align:right" class="label label-default">profession:</span></td>
            <td><h>${user.profession.name}</h></td>
        </tr>
        <tr>
            <td><span style="align:right" class="label label-default">fee:</span></td>
            <td><h>${user.profession.fee}</h></td>
        </tr>
        </c:if>
    </table>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>