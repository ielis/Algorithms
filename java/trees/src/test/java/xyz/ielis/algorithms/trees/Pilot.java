package xyz.ielis.algorithms.trees;

import java.util.Objects;

public class Pilot {

    private final String name;

    private final String surname;

    Pilot(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pilot pilot = (Pilot) o;
        return Objects.equals(name, pilot.name) &&
                Objects.equals(surname, pilot.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, surname);
    }

    @Override
    public String toString() {
        return  name + " " + surname ;
    }
}
