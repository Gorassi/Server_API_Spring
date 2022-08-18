package ru.javawebinar.topjava;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class JdbcMain {

    public static class MyRowMapper implements RowMapper<Meal>{

        @Override
        public Meal mapRow(ResultSet rs, int rowNum) throws SQLException {

            Meal tempMeal = null;
                int id = (rs.getInt("id"));
                String discription = (rs.getString( "description"));
                int calories = (rs.getInt( "calories"));
                String stringDate = rs.getString("date_time");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date = formatter.parse(stringDate);
                    LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
 //                   System.out.println(stringDate + "  " + localDateTime);
                    tempMeal = new Meal(id, localDateTime, discription, calories);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//
//            }
//            mealList.forEach(System.out::println);
            return tempMeal;
//            return new Meal(100055, LocalDateTime.now(), "plov in forest", 299);
//            return new Meal( LocalDateTime.now(), rs.getString("description"), rs.getInt("calories"));
        }
    }

    public static void main(String[] args) {
        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/db/postgres.properties");
            properties.load(fis);
            fis.close();
            String url = properties.getProperty("database.url");
            String username = properties.getProperty("database.username");
            String password = properties.getProperty("database.password");
            System.out.println("url = " + url);

            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
 //           List<Meal> mealList = jdbcTemplate.query("select * from meals", new MyRowMapper());
            List<Meal> mealList = jdbcTemplate.query("select * from meals where id >  ? ", new MyRowMapper(),
                    100001);
            mealList.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
