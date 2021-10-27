package bank.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import bank.types.CarType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private String make;
    private Integer numberOfSeats;
    private CarType type;
 
    //constructor, getters, setters etc.
}