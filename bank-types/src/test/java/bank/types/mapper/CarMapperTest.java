package bank.types.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import bank.types.Car;
import bank.types.CarType;
import bank.types.dto.CarDto;
import org.junit.Test;

/**
 * @author ZhengHao Lou
 * Date    2021/10/27
 */
public class CarMapperTest {

    @Test
    public void test() {
        //given
        Car car = new Car( "Morris", 5, CarType.SEDAN );

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

        //then
        assertThat( carDto ).isNotNull();
        assertThat( carDto.getMake() ).isEqualTo( "Morris" );
        assertThat( carDto.getSeatCount() ).isEqualTo( 5 );
        assertThat( carDto.getType() ).isEqualTo( "SEDAN" );
    }
}
