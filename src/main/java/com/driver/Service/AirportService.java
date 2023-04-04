package com.driver.Service;

import com.driver.Repository.AirportRepository;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport)
    {
        airportRepository.addAirport(airport);
    }

    public void addFlight(Flight flight)
    {
        airportRepository.addFlight(flight);
    }
    public void addPassenger(Passenger passenger)
    {
        airportRepository.addPassenger(passenger);
    }

    public boolean alreadyFilled(Integer flightId)
    {
        return airportRepository.alreadyFilled(flightId);
    }

    public boolean passengerBookedAlready(Integer flightId, Integer passengerId)
    {
        return airportRepository.passengerBookedAlready(flightId, passengerId);
    }
    public void bookATicket(Integer flightId, Integer passengerId)
    {
        airportRepository.bookATicket(flightId, passengerId);
    }

    public boolean passengerBookedFlight(Integer flightId, Integer passengerId)
    {
        return airportRepository.passengerBookedFlight(flightId, passengerId);
    }

    public boolean flightIsValid(Integer flightId)
    {
        return airportRepository.flightIsValid(flightId);
    }

    public void cancelATicket(Integer flightId, Integer passengerId)
    {
        airportRepository.cancelATicket(flightId, passengerId);
    }

    public String getLargestAirportName(){
        return airportRepository.getLargestAirportName();
    }

    public int getNumberOfPeopleOn(Date date, String airportName)
    {
        return airportRepository.getNumberOfPeopleOn(date, airportName);
    }

    public int calculateFlightFare(Integer flightId)
    {
        return airportRepository.calculateFlightFare(flightId);
    }

    public String getAirportNameFromFlightId(Integer flightId)
    {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId)
    {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId)
    {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity)
    {
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
    }

}
