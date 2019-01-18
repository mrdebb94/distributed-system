package web.config;

import dto.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"controller", "service", "dao"})
public class WebConfig {
    @Bean
    File rentalJsonFile() {
        File file =  new File(System.getProperty("java.io.tmpdir")+"\\rentals.json");
        System.out.println(file.getAbsolutePath());
        if(!file.exists() && !file.isDirectory()) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
                writer.println("{}");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Bean
    ObjectFactory dtoFactory() {
        return new ObjectFactory();
    }
}