package bank.domain.mapper;

import bank.domain.entity.Order;
import bank.types.command.OrderRequestCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    Order orderRequestCommandToOrder(OrderRequestCommand orderRequestCommand);
}
