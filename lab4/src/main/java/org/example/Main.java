package org.example;


import java.util.Objects;
import java.util.Arrays;
import java.util.regex.Pattern;

class Passenger {
    private String name;
    private String passportNumber;

    public Passenger(String name, String passportNumber) {
        this.name = name;
        this.passportNumber = passportNumber;
    }

    @Override
    public String toString() {
        return "Пасажир{" +
                "ім'я='" + name + '\'' +
                ", номер паспорта='" + passportNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name) && Objects.equals(passportNumber, passenger.passportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, passportNumber);
    }
}

class Airplane {
    private String model;
    private int capacity;

    public Airplane(String model, int capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Літак{" +
                "модель='" + model + '\'' +
                ", кількість місць=" + capacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return capacity == airplane.capacity && Objects.equals(model, airplane.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, capacity);
    }

    static class AirplaneBuilder {
        private String model;
        private int capacity;

        public AirplaneBuilder setModel(String model) {
            if (model == null || model.isEmpty()) {
                throw new IllegalArgumentException("Модель повинна бути вказана");
            }
            this.model = model;
            return this;
        }

        public AirplaneBuilder setCapacity(int capacity) {
            if (capacity < 1) {
                throw new IllegalArgumentException("Кількість місць повинна бути більше або дорівнювати 1");
            }
            this.capacity = capacity;
            return this;
        }

        public Airplane build() {
            if (model == null || model.isEmpty()) {
                throw new IllegalArgumentException("Модель повинна бути вказана");
            }
            return new Airplane(model, capacity);
        }
    }
}

class Airport {
    private String name;
    private String code;
    private Airplane airplane;
    private Passenger[] passengers;

    public Airport(String name, String code, Airplane airplane, Passenger[] passengers) {
        this.name = name;
        this.code = code;
        this.airplane = airplane;
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Аеропорт{" +
                "назва='" + name + '\'' +
                ", код='" + code + '\'' +
                ", літак=" + airplane +
                ", пасажири=" + Arrays.toString(passengers) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(name, airport.name) && Objects.equals(code, airport.code) &&
                Objects.equals(airplane, airport.airplane) && Arrays.equals(passengers, airport.passengers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, code, airplane);
        result = 31 * result + Arrays.hashCode(passengers);
        return result;
    }

    static class AirportBuilder {
        private String name;
        private String code;
        private Airplane airplane;
        private Passenger[] passengers;

        public AirportBuilder setName(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Назва повинна бути вказана");
            }
            this.name = name;
            return this;
        }

        public AirportBuilder setCode(String code) {
            Pattern codePattern = Pattern.compile("[A-Z]{3}");
            if (!codePattern.matcher(code).matches()) {
                throw new IllegalArgumentException("Код повинен складатися з трьох великих літер");
            }
            this.code = code;
            return this;
        }

        public AirportBuilder setAirplane(Airplane airplane) {
            if (airplane == null) {
                throw new IllegalArgumentException("Літак повинен бути вказаний");
            }
            this.airplane = airplane;
            return this;
        }

        public AirportBuilder setPassengers(Passenger[] passengers) {
            if (passengers == null || passengers.length < 1) {
                throw new IllegalArgumentException("Повинен бути наданий принаймні один пасажир");
            }
            this.passengers = passengers;
            return this;
        }

        public Airport build() {
            if (name == null || name.isEmpty() || code == null || code.isEmpty()) {
                throw new IllegalArgumentException("Необхідно вказати назву та код");
            }
            return new Airport(name, code, airplane, passengers);
        }
    }
}


public class Main2 {
    public static void main(String[] args) {
        Passenger passenger1 = new Passenger("John Doe", "AB123456");
        Passenger passenger2 = new Passenger("Alice", "CD789012");

        System.out.println("Пасажир 1 дорівнює Пасажиру 2: " + passenger1.equals(passenger2));

        Airplane airplane = new Airplane.AirplaneBuilder()
                .setModel("Boeing 747")
                .setCapacity(500)
                .build();

        Passenger[] passengers = {
                new Passenger("Alice", "CD789012"),
                new Passenger("Bob", "EF345678")
        };

        Airport airport1 = new Airport.AirportBuilder()
                .setName("Міжнародний аеропорт")
                .setCode("ABC")
                .setAirplane(airplane)
                .setPassengers(passengers)
                .build();

        Airport airport2 = new Airport.AirportBuilder()
                .setName("Міжнародний аеропорт")
                .setCode("ABC")
                .setAirplane(airplane)
                .setPassengers(passengers)
                .build();

        System.out.println("Аеропорт 1 дорівнює Аеропорту 2: " + airport1.equals(airport2));

        System.out.println("Хеш-код для Пасажира 1: " + passenger1.hashCode());
        System.out.println("Хеш-код для Аеропорту 1: " + airport1.hashCode());

        System.out.println("Аеропорт 1: " + airport1.toString());
        System.out.println("Аеропорт 2: " + airport2.toString());
    }
}
