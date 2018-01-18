<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;">
    <title>Register</title>
    <%@include file="/resources/styles.jsp" %>
</head>
<body>

<%@include file="/resources/bar2.jsp" %>

<form:form id="registerForm" modelAttribute="user" action="register" method="post">
    <table align="center">
        <tr>
            <td>
                <form:label path="login">login: </form:label>
            </td>
            <td>
                <form:input path="login" name="login" id="login" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="password">Password:</form:label>
            </td>
            <td>
                <form:password path="password" name="password" id="password" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="email">Email:</form:label>
            </td>
            <td>
                <form:input type="email" path="email" name="email" id="email" />
            </td>
        </tr>
        <tr>
            <td>
                <form:label path="phone">Phone:</form:label>
            </td>
            <td>
                <form:input type="number" path="phone" name="phone" id="phone" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td align="left">
                <form:button id="register" name="register">Login</form:button>
            </td>
        </tr>
        <tr></tr>
        <tr>
            <td></td>

        </tr>
    </table>
</form:form>



</body>
</html>