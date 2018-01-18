<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;">
    <title>Login</title>
    <%@include file="/resources/styles.jsp" %>
    <style>

        button {
            background-color: #4CAF50; /* Green */
            border: none;
            color: white;
            padding: 7px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border-radius: 6px;
        }

        .left {
            float: left;
            width: 50%;
        }

        .right {
            margin-left: 50%;
        }

    </style>
</head>
<body>

<%@include file="/resources/bar2.jsp" %>

<div style="text-align: center;" class="left">
    <h1>Register</h1>
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
                    <form:button id="register" name="register">Register</form:button>
                </td>
            </tr>
            <tr></tr>
            <tr>
                <td></td>

            </tr>
        </table>
    </form:form>
</div>

<div style="text-align: center;" class="right">
    <h1>Login</h1>
    <form:form id="loginForm" modelAttribute="login" action="login" method="post">
        <table align="center">
            <tr>
                <td>
                    <form:label path="username">Username: </form:label>
                </td>
                <td>
                    <form:input path="username" name="username" id="username" />
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
                <td></td>
                <td align="left">
                    <form:button id="login" name="login">Login</form:button>
                </td>
            </tr>
            <tr></tr>
            <tr>
            <td></td>

            </tr>
        </table>
    </form:form>
</div>





</body>
</html>