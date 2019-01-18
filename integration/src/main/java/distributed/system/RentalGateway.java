package distributed.system;

import dto.RentalBus;
import dto.RentalCar;
import org.springframework.integration.annotation.Gateway;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Date;
import java.util.List;

public interface RentalGateway {
    /*@Gateway(payloadExpression = "''", requestChannel = "busListRequestChannel")
    List<RentalBus> listRentalBuses(@Header("from") Date from, @Header("to") Date to);

    @Gateway(payloadExpression = "''", replyChannel = "carListRequestChannel")
    List<RentalCar> listRentalCars(@Header("from") Date from, @Header("to") Date to);*/

    @Gateway(requestChannel = "vehicleListChannel")
    void selectVehicleType(String vehicleType);
}
