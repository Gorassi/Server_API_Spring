<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Марина
  Date: 16.06.2022
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My meals</title>
</head>
<body>
<h2> Meals table </h2>
<%
    List<MealTo> mealToList = (List<MealTo>) request.getAttribute("list");
//    mealToList.forEach(System.out::println);
    System.out.println("Attemps 2:");
%>
<table border=1>
    <thead>
    <tr style="color: brown">
        <th>id</th>
        <th>dateTime</th>
        <th>description</th>
        <th>calories</th>
        <th>isExcess</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    </thead>
<c:forEach var="meal" items="${list}">

    <tr style=" color: ${(meal.isExcess() ? 'red' : 'green' ) }; background-color: lightblue">
        <th>${meal.getId()}</th>
        <th>${meal.getDateTime().toLocalDate()}</th>
        <th>${meal.getDescription()}  </th>
        <th>${meal.getCalories()} </th>
        <th>${meal.isExcess()}</th>
        <th><form method = get name="update" action="/topjava/change.jsp">

             <input name="update" value="${meal.getId()}">
             <button type="submit">update</button>


        </form> </th>
        <th><a href="meals" onclick="location.href='/users'">delete</a>
        </th>
    </tr>
</c:forEach>
</table>
</body>
</html>
