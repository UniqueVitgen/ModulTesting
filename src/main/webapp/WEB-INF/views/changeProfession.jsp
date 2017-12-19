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

<%@page pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
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
                    <a class="navbar-brand" href="<c:url value='/welcome' />">Профиль</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                    </ul>
                    <form class="nav navbar-nav navbar-centr">
                        <li><a href="<c:url value='/welcome' />">Приветсвуем ${pageContext.request.userPrincipal.name}</a></li>
                        <c:forEach var="role" items="${user.roles}">
                            <c:if test="${role.id == 1}">
                                <li><a href="<c:url value='/statistics' />">Статистика профессий</a></li>
                            </c:if>
                        </c:forEach>
                        <c:forEach var="role" items="${user.roles}">
                            <c:if test="${role.id == 2}">
                                <li><a href="<c:url value='/change' />">Настройка профессий</a></li>
                            </c:if>
                        </c:forEach>
                    </form>
                    <ul class="nav navbar-nav navbar-right">

                        <li>
                            <a href="#" onclick="document.forms['logoutForm'].submit()">Выход</a>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

    </c:if>
    <table class = "table" id = "profile">
        <thead>
        <tr><td>Название</td><td>Количество человек</td><td>Заработная плата</td></tr>
        </thead>
        <tbody>
        <c:forEach var="profession" items="${professions}">
            <tr>
                <td>${profession.name}</td>
                <td>${profession.users.size()}</td>
                <td>${profession.fee}</td>
                <td><button onclick="window.location.href= '/change-${profession.id}'" type="button" class="btn btn-info">Изменить</button></td>
                <td><button onclick="window.location.href= '/delete-${profession.id}'" type="button" class="btn btn-danger">Удалить</button></td>
                <td><button onclick="window.location.href= '/history-${profession.id}'" type="button" class="btn btn-danger">История</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <button onclick="window.location.href= '/add'" type="button" class="btn btn-success">Добавить</button>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
<body>

</body>
</html>
