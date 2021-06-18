<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Счетчик кликов</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}resources/css/style.css">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}" method="post">
    <table id="ClickCounterTable"> <!--ClickCounterTable-->
        <caption></caption>
        <tr>
            <th id="header">
            Счетчик кликов
            </th>
        </tr>
        <tr>
            <td id="counter">
                ${counter}
            </td>
        </tr>
        <tr>
            <td>
                <input id="ClickCounterSubmit" type="submit" value="Кликать тут">
            </td>
        </tr>
    </table>
</form>
<script src="${pageContext.request.contextPath}resources/js/script.js"></script>
</body>
</html>