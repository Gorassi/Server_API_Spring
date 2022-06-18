package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyMealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MyMealServlet.class);
    public static final Integer caloriesPerDay = 2000;
    public static List<MealTo> mealList;
    public static Integer id = 1;

    @Override
    public void init() throws ServletException {
         mealList = new ArrayList<>();
 //       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("")
        mealList.add(new MealTo(id++, LocalDateTime.of(2022,06,14,07,00,00), "breakfast", 500, true));
        mealList.add(new MealTo(id++, LocalDateTime.of(2022,06,14,12,00,00), "lunch", 1000, true));
        mealList.add(new MealTo(id++, LocalDateTime.of(2022,06,14,17,00,00), "tea", 150, true));
        mealList.add(new MealTo(id++, LocalDateTime.of(2022,06,14,19,00,00), "dinner", 500, true));
        mealList.add(new MealTo(id++, LocalDateTime.of(2022,06,15,07,00,00), "breakfast", 500, false));
        mealList.add(new MealTo(id++, LocalDateTime.of(2022,06,15,12,00,00), "lunch", 1000, false));
        mealList.add(new MealTo(id++, LocalDateTime.of(2022,06,15,19,00,00), "dinner", 500, false));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("myInfo: Method doGet from MyMealsServlet");
        request.setAttribute("list", mealList);
        getServletContext().getRequestDispatcher("/mymeals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("myINFO: Method doPOST from MyMealsServlet");
        Integer id = Integer.valueOf(req.getParameter("update"));
        System.out.println("id = " + id );
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
        System.out.println("datetime = " + dateTime);
        String description = req.getParameter("description");
        System.out.println("description = " + description);
        int calories = Integer.parseInt(req.getParameter("calories"));
        System.out.println("calories = " + calories);
        boolean isExcess = Boolean.parseBoolean(req.getParameter("excess"));
        System.out.println("isExcess = " + isExcess);
        mealList.remove(id-1);
        mealList.add(id-1, new MealTo(id,dateTime, description,calories,isExcess));

        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("myInfo: Method doDELETE from MyMealsServlet");

    }
}
