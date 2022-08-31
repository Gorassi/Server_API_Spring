package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            System.out.println("Bean definition names : ");
            AtomicInteger num = new AtomicInteger(1);
            Arrays.stream(appCtx.getBeanDefinitionNames()).sorted().forEach(m-> System.out.println(num.getAndIncrement() + " : " + m));
            System.out.println("ln 26 quantity of beans = " + appCtx.getBeanDefinitionNames().length );
 //           UserRepository userRepository = appCtx.getBean(UserRepository.class);
 //           System.out.println("ln 28 class = " + userRepository.getClass().getSimpleName() );

            System.out.println();
            System.out.println("ln 29 " + "List of Users: " );
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
 //           adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.USER));
            adminUserController.getAll().forEach(System.out::println);

            System.out.println();
            System.out.println("ln 33 Meal :" );
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            Meal meal200 = mealController.get(100010);
            System.out.println("meal 100010 = "+ meal200);
            System.out.println();

//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2010, Month.JANUARY, 30), LocalTime.of(7, 0),
//                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
//            filteredMealsWithExcess.forEach(System.out::println);
//            System.out.println();
//            System.out.println(mealController.getBetween(null, null, null, null));
            System.out.println("ln 38 ");
            mealController.getAll();
            System.out.println();

            System.out.println("ln51");
 //           mealController.create(new Meal(LocalDateTime.now(), "obed on air", 222));
            meal200.setCalories(734);
            mealController.update(meal200, meal200.getId());
            System.out.println("new meal 100010 = "+ meal200);

            mealController.create(new Meal( LocalDateTime.now(), "obed on air", 555));
            mealController.create(new Meal( LocalDateTime.now(), "for delete", 345));
            mealController.getAll();

            System.out.println();
            System.out.println("ln 67 : try DELETE");
            mealController.delete(100012);
            System.out.println();
            mealController.getAll();

        }
    }
}
