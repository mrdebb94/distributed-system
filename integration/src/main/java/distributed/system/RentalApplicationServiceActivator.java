package distributed.system;

import dto.*;
import dto.error.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import utils.Converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class RentalApplicationServiceActivator {

    public void listFreeCars(List<RentalCompany> rentalCompany) {

    }

    @ServiceActivator
    public Message<?> listFreeVehicles() {
        String fromTime=null, toTime=null;
        BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("From time (YYYY-mm-dd): ");
            fromTime = bReader.readLine();

            System.out.println("To time (YYYY-mm-dd): ");
            toTime = bReader.readLine();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Message<String> message = MessageBuilder.withPayload("")
                .setHeader("from", fromTime).setHeader("to", toTime).build();

        return message;
    }


    @ServiceActivator
    public Message<?>  selectFreeBuses(RentalCompany rentalCompany[]) {
        ObjectFactory dtoFactory = new ObjectFactory();
        Rental rental = dtoFactory.createRental();

        Message<Rental> message = null;

        for(int i=0;i<rentalCompany.length; i++) {
            System.out.println("Company id " +  rentalCompany[i].getId() + " Company Name " + rentalCompany[i].getName() + " ");
            List<RentalBus> rentalBuses = rentalCompany[i].getRentalBuses();
            for (int j = 0; j < rentalBuses.size(); j++) {
                RentalBus rentalBus = rentalBuses.get(j);
                System.out.print("Rental Bus Id " + rentalBus.getId() + " Brand: " + rentalBus.getBus().getBrand() + " Name: " + rentalBus.getBus().getPlateNumber() +
                        " Kilometers: " + rentalBus.getBus().getKilometers() + " Seats: " + rentalBus.getBus().getSeats()
                        + " Bus type " + rentalBus.getBus().getBusType() + " Transport type " + rentalBus.getBus().getTransportType() +
                        " Plate number " + rentalBus.getBus().getPlateNumber());
            }
            System.out.println();
        }


            String fromTimeString=null, toTimeString=null;
            BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.println("Company ID: ");
                String companyId = bReader.readLine();

                System.out.println("Rental Bus ID: ");
                String rentalBusId = bReader.readLine();

                System.out.println("Customer ID: ");
                String customerId = bReader.readLine();

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                System.out.println("From time (yyyy-MM-dd): ");
                fromTimeString = bReader.readLine();

                Date fromDate = formatter.parse( fromTimeString);

                System.out.println("To time (yyyy-MM-dd): ");
                toTimeString = bReader.readLine();

                Date toDate = formatter.parse( toTimeString);

                System.out.println("Price: ");
                String price = bReader.readLine();

                rental.setCustomerId(Integer.parseInt(customerId));
                rental.setRentalBusId(Integer.parseInt(rentalBusId));
                rental.setRentalTime(Converter.DateToGregorianCalendar(fromDate));
                rental.setExpirationDate(Converter.DateToGregorianCalendar(toDate));
                rental.setPrice(Integer.parseInt(price));

                message = MessageBuilder.withPayload(rental)
                        .setHeader("companyId", companyId).build();

            }
            catch (ParseException e) {
                e.printStackTrace();
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return message;
    }

    @ServiceActivator
    public void successfullyBusesRental(ResponseEntity responseEntity) {
        System.out.println(" Bus rental successfully! " + responseEntity.getStatusCode());
    }

    @ServiceActivator
    public void errorBusRental(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getCode());
        System.out.println(errorMessage.getCause());
    }


    @ServiceActivator
    public Message<?>  selectFreeCars(RentalCompany rentalCompany[]) {
        ObjectFactory dtoFactory = new ObjectFactory();
        Rental rental = dtoFactory.createRental();

        Message<Rental> message = null;

        for(int i=0;i<rentalCompany.length; i++) {
            System.out.println("Company id " +  rentalCompany[i].getId() + " Company Name " + rentalCompany[i].getName() + " ");
            List<RentalCar> rentalCars = rentalCompany[i].getRentalCars();
            for (int j = 0; j < rentalCars.size(); j++) {
                RentalCar rentalCar = rentalCars.get(j);
                System.out.print("Rental Car Id " + rentalCar.getId() + " Brand: " + rentalCar.getCar().getBrand() + " Name: " + rentalCar.getCar().getPlateNumber() +
                        " Kilometers: " + rentalCar.getCar().getKilometers() + " Seats: " + rentalCar.getCar().getSeats()
                       + " Plate number " + rentalCar.getCar().getPlateNumber());
            }
            System.out.println();
        }


        String fromTimeString=null, toTimeString=null;
        BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Company ID: ");
            String companyId = bReader.readLine();

            System.out.println("Rental Car ID: ");
            String rentalCarId = bReader.readLine();

            System.out.println("Customer ID: ");
            String customerId = bReader.readLine();

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("From time (yyyy-MM-dd): ");
            fromTimeString = bReader.readLine();

            Date fromDate = formatter.parse( fromTimeString);

            System.out.println("To time (yyyy-MM-dd): ");
            toTimeString = bReader.readLine();

            Date toDate = formatter.parse( toTimeString);

            System.out.println("Price: ");
            String price = bReader.readLine();

            rental.setCustomerId(Integer.parseInt(customerId));
            rental.setRentalCarId(Integer.parseInt(rentalCarId));
            rental.setRentalTime(Converter.DateToGregorianCalendar(fromDate));
            rental.setExpirationDate(Converter.DateToGregorianCalendar(toDate));
            rental.setPrice(Integer.parseInt(price));

            message = MessageBuilder.withPayload(rental)
                    .setHeader("companyId", companyId).build();

        }
        catch (ParseException e) {
            e.printStackTrace();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return message;
    }

    @ServiceActivator
    public void successfullyCarsRental(ResponseEntity responseEntity) {
        System.out.println(" Bus rental successfully! " + responseEntity.getStatusCode());
    }

    @ServiceActivator
    public void errorCarRental(ErrorMessage errorMessage) {
        System.out.println(errorMessage.getCode());
        System.out.println(errorMessage.getCause());
    }
}