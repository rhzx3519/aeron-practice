package bank.domain.mapper;

import bank.domain.types.Quote;
import bank.types.command.QuoteCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ZhengHao Lou
 * Date    2022/01/13
 */
@Mapper
public interface QuoteMapper {
    QuoteMapper INSTANCE = Mappers.getMapper( QuoteMapper.class );

    Quote quoteCommandToQuote(QuoteCommand quoteCommand);
}
