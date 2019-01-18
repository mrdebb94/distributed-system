package distributed.system;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        context.start();

        RentalGateway rentalGateway = context.getBean("rentalService", RentalGateway.class);

        BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Which type of vehicle want you rental? (BUS/CAR)");
            String vehicleType = bReader.readLine();
            rentalGateway.selectVehicleType(vehicleType);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
