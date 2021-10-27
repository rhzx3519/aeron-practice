package bank.types.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ZhengHao Lou
 * Date    2021/10/27
 */
@Data
@AllArgsConstructor
public class CarDto {
    private String make;
    private int seatCount;
    private String type;
}
