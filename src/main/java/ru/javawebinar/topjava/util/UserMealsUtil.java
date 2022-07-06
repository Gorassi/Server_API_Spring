package ru.javawebinar.topjava.util;

//import jdk.vm.ci.meta.Local;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println();
//        mealsTo.forEach(userMealWithExcess -> System.out.println(userMealWithExcess.getDateTime().toLocalDate() +
//                " " + userMealWithExcess.getDescription() + " " + userMealWithExcess.getCalories()
//                + " " + userMealWithExcess.isExcess()));

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDate, List<UserMeal>> mealsByDay = new HashMap<>();

        for(UserMeal userMeal : meals){
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            List<UserMeal> list = null;
            if(!mealsByDay.containsKey(localDate)){
                list = new ArrayList<>();
            } else {
                list = mealsByDay.get(localDate);
            }
            list.add(userMeal);
            mealsByDay.put(localDate, list);
        }

        Map<LocalDate, Boolean> mapByExcess = new HashMap<>();

        for(Map.Entry<LocalDate, List<UserMeal>> entry : mealsByDay.entrySet() ){
            int caloriesForDay = 0;
            LocalDate localDate = entry.getKey();
            List<UserMeal> list = entry.getValue();
            for(UserMeal userMeal :  list) caloriesForDay += userMeal.getCalories();
            if (caloriesForDay <= caloriesPerDay) {
                mapByExcess.put(localDate, false);
            } else {
                mapByExcess.put(localDate, true);
            }

        }

        List<UserMealWithExcess> resultList = new ArrayList<>();

        for(UserMeal  userMeal : meals){
            resultList.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                    mapByExcess.get(userMeal.getDateTime().toLocalDate())));
        }

        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams

        meals.stream().forEach(u-> System.out.println(u.getDateTime().toLocalDate() + " " + u.getDescription()));

//        Map<LocalDate, List<UserMeal>> localDateListMap = meals.stream().collect(Collectors.groupingBy(u -> u.getDateTime().toLocalDate()));
        Map<LocalDate, Long> localDateByExcess =  meals.stream()
                .collect(Collectors.groupingBy(u-> u.getDateTime().toLocalDate(), Collectors.summingLong(UserMeal::getCalories)));

        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();

        for(UserMeal userMeal : meals) {
            userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                    userMeal.getCalories(), localDateByExcess.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return userMealWithExcesses.stream().filter(u-> u.getDateTime().toLocalTime().isAfter(startTime) &&
                u.getDateTime().toLocalTime().isBefore(endTime)).collect(Collectors.toList());
    }
}
