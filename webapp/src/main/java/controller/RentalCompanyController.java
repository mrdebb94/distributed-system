package controller;

import dto.*;
import dto.error.ErrorMessage;
import exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.RentalService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/companies")
public class RentalCompanyController {
    private RentalService rentalService;
    private ObjectFactory dtoFactory;

    public RentalCompanyController(@Autowired RentalService rentalService, @Autowired ObjectFactory dtoFactory) {
        this.rentalService = rentalService;
        this.dtoFactory = dtoFactory;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<RentalCompany> listRentalCompanies() {
        List<RentalCompany> rentalCompanies = this.rentalService.getRentalCompanies();
        return rentalCompanies;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public void addRentalCompany(@RequestBody RentalCompany rentalCompany) {
        this.rentalService.addRentalCompany(rentalCompany);
    }

    @RequestMapping(value = "{companyId}/rentalcars/add", method = RequestMethod.POST)
    @ResponseBody
    public void addRentalCarToCompany(@PathVariable("companyId") int companyId,
                                      @RequestBody Car car) throws RentalCompanyNotFoundException {
        RentalCompany rentalCompany = dtoFactory.createRentalCompany();
        rentalCompany.setId(companyId);

        this.rentalService.addCarToRentalCompany(rentalCompany,car);
    }

    @RequestMapping(value = "{companyId}/rentalbuses/add", method = RequestMethod.POST)
    @ResponseBody
    public void addRentalBusToCompany(@PathVariable("companyId") int companyId,
                                      @RequestBody Bus bus) throws RentalCompanyNotFoundException {
        RentalCompany rentalCompany = dtoFactory.createRentalCompany();
        rentalCompany.setId(companyId);
        this.rentalService.addBusToRentalCompany(rentalCompany,bus);
    }

    @RequestMapping(value = "{companyId}/rentals/add", method = RequestMethod.POST)
    @ResponseBody
    public void addRentalToCompany(@PathVariable("companyId") int companyId,
                                      @RequestBody Rental rental) throws RentalBusNotFoundException, RentalCarNotFoundException, RentalIntervalInvalidException, RentalCompanyNotFoundException, CustomerNotFoundException {
        RentalCompany rentalCompany = dtoFactory.createRentalCompany();
        rentalCompany.setId(companyId);
        System.out.println(rental.getRentalTime());
        System.out.println(rental.getExpirationDate());

        this.rentalService.addRentalToRentalCompany(rentalCompany,rental);
    }

    @RequestMapping(value = "all/buses/free/list", method = RequestMethod.GET)
    @ResponseBody
    List<RentalCompany> listFreeBusesFromAllRentalCompany(
            @RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
            @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to) throws RentalCompanyNotFoundException {
        return this.rentalService.getFreeBusesFromAllRentalCompanyBetweenDates(from, to);
    }

    @RequestMapping(value = "all/cars/free/list", method = RequestMethod.GET)
    @ResponseBody
    List<RentalCompany> listFreeCarsFromAllRentalCompany(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                                         @RequestParam("to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to) throws RentalCompanyNotFoundException {
        return this.rentalService.getFreeCarsFromAllRentalCompanyBetweenDates(from, to);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorMessage handleError(HttpServletRequest request, Exception ex) {
        ErrorMessage errorMessage  = new ErrorMessage();
        errorMessage.setCause(ex.getClass().getSimpleName());
        errorMessage.setCode(100);

        return errorMessage;
    }
}