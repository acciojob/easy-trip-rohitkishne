package com.driver.Repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportDB = new HashMap<>();
    HashMap<Integer, Flight> flightDB = new HashMap<>();
    HashMap<Integer, Passenger> passengerDB = new HashMap<>();
    HashMap<Integer, List<Integer>> bookingDB = new HashMap<>();
    HashMap<Integer, List<Integer>> passengerTotalBookingDB = new HashMap<>();

    public void addAirport(Airport airport)
    {
        //Extract the airport id
        String airportId = airport.getAirportName();

        //update the airport db
        airportDB.put(airportId, airport);
    }

    public void addFlight(Flight flight)
    {
        //Extract the flight id
        Integer flightId = flight.getFlightId();

        //update the flight db
        flightDB.put(flightId, flight);
    }

    public void addPassenger(Passenger passenger)
    {
        //Extract the passenger id
        Integer passengerId = passenger.getPassengerId();

        //update the passenger db
        passengerDB.put(passengerId, passenger);
    }

    public boolean alreadyFilled(Integer flightId)
    {
        if(!bookingDB.containsKey(flightId))
        {
            return false;
        }
        //find the details of flight
       Flight flightDetail = flightDB.get(flightId);

       //check the flight capacity
       int maxCapacity = flightDetail.getMaxCapacity();

       //present status of flight seat
        int presentCapcity = bookingDB.get(flightId).size();

       //check whether flight has already been filled or not
        if(presentCapcity<maxCapacity)
            return false;

       return true;
    }

    public boolean passengerBookedAlready(Integer flightId, Integer passengerId)
    {
       //find out passenger booking that already booked or not
        if(!passengerTotalBookingDB.containsKey(passengerId))
        {
            return false; // shows about that passenger does not having any booking till now
        }

        //but passenger has some booking so check for flightId in that booking
        List<Integer> flights = passengerTotalBookingDB.get(passengerId);

        for(Integer flight : flights)
        {
            if(flightId == flight)
            {
                return true; // shows that booked already
            }
        }

        return false;
    }

    public void bookATicket(Integer flightId, Integer passengerId)
    {
        //Update the booking DB
        List<Integer> passengers = new ArrayList<>();

        if(bookingDB.containsKey(flightId))
        {
            passengers = bookingDB.get(flightId);
        }

        //add a passenger to lista and update
        passengers.add(passengerId);
        bookingDB.put(flightId, passengers);

        //update the passenger bookings as well

        //Update the passengerTotalBookingDB DB
        List<Integer>  flights = new ArrayList<>();

        if(passengerTotalBookingDB.containsKey(passengerId))
        {
            passengers = passengerTotalBookingDB.get(passengerId);
        }

        //add a flight to list and update
        flights.add(flightId);
        passengerTotalBookingDB.put(passengerId, flights);

    }

    public boolean passengerBookedFlight(Integer flightId, Integer passengerId)
    {
        if(!passengerTotalBookingDB.containsKey(passengerId))
        {
            return false;
        }

        //get the flight of perticular passenger
        List<Integer> flights = passengerTotalBookingDB.get(passengerId);

        for(Integer flight : flights)
        {
            if(flight == flightId)
            {
                flights.remove(flight);
                passengerTotalBookingDB.put(passengerId, flights);
                return true;
            }
        }

        return false;
    }
    public boolean flightIsValid(Integer flightId)
    {
        if(flightDB.containsKey(flightId))
        {
            return true;
        }
        return false;
    }

    public void cancelATicket(Integer flightId, Integer passengerId)
    {
        //get all passenger of flight
        List<Integer> passengers = bookingDB.get(flightId);

        //delete the passenger
        for(Integer passenger : passengers)
        {
            if(passenger == passengerId)
            {
                passengers.remove(passenger);
                bookingDB.put(flightId, passengers);
            }
        }

    }

    public String getLargestAirportName(){

        String largestAirport = "";
        Integer terminal = 0;

        for(String airport : airportDB.keySet())
        {
            Integer airportTerminal = airportDB.get(airport).getNoOfTerminals();
            if(airportTerminal>terminal)
            {
                terminal = airportTerminal;
                largestAirport = airport;
            }
            else if(airportTerminal == terminal && airport.compareTo(largestAirport)<0)
            {
                largestAirport = airport;
            }
        }
        return largestAirport;
    }

    public int getNumberOfPeopleOn(Date date, String airportName)
    {
        int totalCrowd = 0;

        City city = airportDB.get(airportName).getCity();

        for(Integer flight : flightDB.keySet())
        {
            Date landing = flightDB.get(flight).getFlightDate();
            if(landing.equals(date))
            {
                City fromCity = flightDB.get(flight).getFromCity();
                City toCity = flightDB.get(flight).getToCity();
                if(fromCity.equals(city) || toCity.equals(city))
                {
                    totalCrowd = totalCrowd + bookingDB.get(flight).size();
                }
            }
        }

        return totalCrowd;
    }

    public int calculateFlightFare(Integer flightId)
    {
        int passenger = bookingDB.get(flightId).size();
        int totalFare = 3000 + (passenger * 50);
        return totalFare;
    }

    public String getAirportNameFromFlightId(Integer flightId)
    {
        City takeOffCity = flightDB.get(flightId).getFromCity();

        String airportName = "";

        for(String airport : airportDB.keySet())
        {
            City airportCity = airportDB.get(airport).getCity();
            if(airportCity.equals(takeOffCity))
            {
                airportName = airport;
            }
        }

        return airportName;
    }

    public int calculateRevenueOfAFlight(Integer flightId)
    {
        return calculateFlightFare(flightId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId)
    {
        int totalBooking = 0;

        if(passengerTotalBookingDB.containsKey(passengerId))
        {
            totalBooking = passengerTotalBookingDB.get(passengerId).size();
        }

        return totalBooking;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity)
    {
        double fastestFlight = Double.MAX_VALUE;

        for(Flight flight : flightDB.values())
        {
            City flightFromCity = flight.getFromCity();
            City flightToCity = flight.getToCity();

            if(flightFromCity.equals(fromCity) && flightToCity.equals(toCity))
            {
                double duration = flight.getDuration();
                if(duration<fastestFlight)
                {
                    fastestFlight = duration;
                }
            }
        }
        if(fastestFlight == Double.MAX_VALUE)
            return-1.0;
        return fastestFlight;
    }
}
