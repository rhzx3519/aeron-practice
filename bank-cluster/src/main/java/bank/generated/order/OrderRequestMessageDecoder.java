/* Generated SBE (Simple Binary Encoding) message codec. */
package bank.generated.order;

import org.agrona.DirectBuffer;
import org.agrona.sbe.*;


/**
 * Order request, include [place, replace, cancel]
 */
@SuppressWarnings("all")
public final class OrderRequestMessageDecoder implements MessageDecoderFlyweight
{
    public static final int BLOCK_LENGTH = 79;
    public static final int TEMPLATE_ID = 1;
    public static final int SCHEMA_ID = 688;
    public static final int SCHEMA_VERSION = 1;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private final OrderRequestMessageDecoder parentMessage = this;
    private DirectBuffer buffer;
    private int initialOffset;
    private int offset;
    private int limit;
    int actingBlockLength;
    int actingVersion;

    public int sbeBlockLength()
    {
        return BLOCK_LENGTH;
    }

    public int sbeTemplateId()
    {
        return TEMPLATE_ID;
    }

    public int sbeSchemaId()
    {
        return SCHEMA_ID;
    }

    public int sbeSchemaVersion()
    {
        return SCHEMA_VERSION;
    }

    public String sbeSemanticType()
    {
        return "";
    }

    public DirectBuffer buffer()
    {
        return buffer;
    }

    public int initialOffset()
    {
        return initialOffset;
    }

    public int offset()
    {
        return offset;
    }

    public OrderRequestMessageDecoder wrap(
        final DirectBuffer buffer,
        final int offset,
        final int actingBlockLength,
        final int actingVersion)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.initialOffset = offset;
        this.offset = offset;
        this.actingBlockLength = actingBlockLength;
        this.actingVersion = actingVersion;
        limit(offset + actingBlockLength);

        return this;
    }

    public OrderRequestMessageDecoder wrapAndApplyHeader(
        final DirectBuffer buffer,
        final int offset,
        final MessageHeaderDecoder headerDecoder)
    {
        headerDecoder.wrap(buffer, offset);

        final int templateId = headerDecoder.templateId();
        if (TEMPLATE_ID != templateId)
        {
            throw new IllegalStateException("Invalid TEMPLATE_ID: " + templateId);
        }

        return wrap(
            buffer,
            offset + MessageHeaderDecoder.ENCODED_LENGTH,
            headerDecoder.blockLength(),
            headerDecoder.version());
    }

    public OrderRequestMessageDecoder sbeRewind()
    {
        return wrap(buffer, initialOffset, actingBlockLength, actingVersion);
    }

    public int encodedLength()
    {
        return limit - offset;
    }

    public int limit()
    {
        return limit;
    }

    public void limit(final int limit)
    {
        this.limit = limit;
    }

    public static int correlationIdId()
    {
        return 1;
    }

    public static int correlationIdSinceVersion()
    {
        return 0;
    }

    public static int correlationIdEncodingOffset()
    {
        return 0;
    }

    public static int correlationIdEncodingLength()
    {
        return 8;
    }

    public static String correlationIdMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static long correlationIdNullValue()
    {
        return -9223372036854775808L;
    }

    public static long correlationIdMinValue()
    {
        return -9223372036854775807L;
    }

    public static long correlationIdMaxValue()
    {
        return 9223372036854775807L;
    }

    public long correlationId()
    {
        return buffer.getLong(offset + 0, java.nio.ByteOrder.LITTLE_ENDIAN);
    }


    public static int orderIdId()
    {
        return 2;
    }

    public static int orderIdSinceVersion()
    {
        return 0;
    }

    public static int orderIdEncodingOffset()
    {
        return 8;
    }

    public static int orderIdEncodingLength()
    {
        return 8;
    }

    public static String orderIdMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static long orderIdNullValue()
    {
        return -9223372036854775808L;
    }

    public static long orderIdMinValue()
    {
        return -9223372036854775807L;
    }

    public static long orderIdMaxValue()
    {
        return 9223372036854775807L;
    }

    public long orderId()
    {
        return buffer.getLong(offset + 8, java.nio.ByteOrder.LITTLE_ENDIAN);
    }


    public static int userIdId()
    {
        return 3;
    }

    public static int userIdSinceVersion()
    {
        return 0;
    }

    public static int userIdEncodingOffset()
    {
        return 16;
    }

    public static int userIdEncodingLength()
    {
        return 8;
    }

    public static String userIdMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static long userIdNullValue()
    {
        return -9223372036854775808L;
    }

    public static long userIdMinValue()
    {
        return -9223372036854775807L;
    }

    public static long userIdMaxValue()
    {
        return 9223372036854775807L;
    }

    public long userId()
    {
        return buffer.getLong(offset + 16, java.nio.ByteOrder.LITTLE_ENDIAN);
    }


    public static int accountIdId()
    {
        return 4;
    }

    public static int accountIdSinceVersion()
    {
        return 0;
    }

    public static int accountIdEncodingOffset()
    {
        return 24;
    }

    public static int accountIdEncodingLength()
    {
        return 8;
    }

    public static String accountIdMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static long accountIdNullValue()
    {
        return -9223372036854775808L;
    }

    public static long accountIdMinValue()
    {
        return -9223372036854775807L;
    }

    public static long accountIdMaxValue()
    {
        return 9223372036854775807L;
    }

    public long accountId()
    {
        return buffer.getLong(offset + 24, java.nio.ByteOrder.LITTLE_ENDIAN);
    }


    public static int eventTypeId()
    {
        return 5;
    }

    public static int eventTypeSinceVersion()
    {
        return 0;
    }

    public static int eventTypeEncodingOffset()
    {
        return 32;
    }

    public static int eventTypeEncodingLength()
    {
        return 1;
    }

    public static String eventTypeMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public short eventTypeRaw()
    {
        return ((short)(buffer.getByte(offset + 32) & 0xFF));
    }

    public EventTypeEnum eventType()
    {
        return EventTypeEnum.get(((short)(buffer.getByte(offset + 32) & 0xFF)));
    }


    public static int orderTypeId()
    {
        return 6;
    }

    public static int orderTypeSinceVersion()
    {
        return 0;
    }

    public static int orderTypeEncodingOffset()
    {
        return 33;
    }

    public static int orderTypeEncodingLength()
    {
        return 1;
    }

    public static String orderTypeMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public short orderTypeRaw()
    {
        return ((short)(buffer.getByte(offset + 33) & 0xFF));
    }

    public OrderTypeEnum orderType()
    {
        return OrderTypeEnum.get(((short)(buffer.getByte(offset + 33) & 0xFF)));
    }


    public static int orderActionId()
    {
        return 7;
    }

    public static int orderActionSinceVersion()
    {
        return 0;
    }

    public static int orderActionEncodingOffset()
    {
        return 34;
    }

    public static int orderActionEncodingLength()
    {
        return 1;
    }

    public static String orderActionMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public short orderActionRaw()
    {
        return ((short)(buffer.getByte(offset + 34) & 0xFF));
    }

    public OrderActionEnum orderAction()
    {
        return OrderActionEnum.get(((short)(buffer.getByte(offset + 34) & 0xFF)));
    }


    public static int limitPriceId()
    {
        return 8;
    }

    public static int limitPriceSinceVersion()
    {
        return 0;
    }

    public static int limitPriceEncodingOffset()
    {
        return 35;
    }

    public static int limitPriceEncodingLength()
    {
        return 8;
    }

    public static String limitPriceMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static double limitPriceNullValue()
    {
        return Double.NaN;
    }

    public static double limitPriceMinValue()
    {
        return 4.9E-324d;
    }

    public static double limitPriceMaxValue()
    {
        return 1.7976931348623157E308d;
    }

    public double limitPrice()
    {
        return buffer.getDouble(offset + 35, java.nio.ByteOrder.LITTLE_ENDIAN);
    }


    public static int symbolId()
    {
        return 9;
    }

    public static int symbolSinceVersion()
    {
        return 0;
    }

    public static int symbolEncodingOffset()
    {
        return 43;
    }

    public static int symbolEncodingLength()
    {
        return 32;
    }

    public static String symbolMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static byte symbolNullValue()
    {
        return (byte)0;
    }

    public static byte symbolMinValue()
    {
        return (byte)32;
    }

    public static byte symbolMaxValue()
    {
        return (byte)126;
    }

    public static int symbolLength()
    {
        return 32;
    }


    public byte symbol(final int index)
    {
        if (index < 0 || index >= 32)
        {
            throw new IndexOutOfBoundsException("index out of range: index=" + index);
        }

        final int pos = offset + 43 + (index * 1);

        return buffer.getByte(pos);
    }


    public static String symbolCharacterEncoding()
    {
        return "ASCII";
    }

    public int getSymbol(final byte[] dst, final int dstOffset)
    {
        final int length = 32;
        if (dstOffset < 0 || dstOffset > (dst.length - length))
        {
            throw new IndexOutOfBoundsException("Copy will go out of range: offset=" + dstOffset);
        }

        buffer.getBytes(offset + 43, dst, dstOffset, length);

        return length;
    }

    public String symbol()
    {
        final byte[] dst = new byte[32];
        buffer.getBytes(offset + 43, dst, 0, 32);

        int end = 0;
        for (; end < 32 && dst[end] != 0; ++end);

        return new String(dst, 0, end, java.nio.charset.StandardCharsets.US_ASCII);
    }


    public int getSymbol(final Appendable value)
    {
        for (int i = 0; i < 32; ++i)
        {
            final int c = buffer.getByte(offset + 43 + i) & 0xFF;
            if (c == 0)
            {
                return i;
            }

            try
            {
                value.append(c > 127 ? '?' : (char)c);
            }
            catch (final java.io.IOException ex)
            {
                throw new java.io.UncheckedIOException(ex);
            }
        }

        return 32;
    }


    public static int totalQuantityId()
    {
        return 10;
    }

    public static int totalQuantitySinceVersion()
    {
        return 0;
    }

    public static int totalQuantityEncodingOffset()
    {
        return 75;
    }

    public static int totalQuantityEncodingLength()
    {
        return 4;
    }

    public static String totalQuantityMetaAttribute(final MetaAttribute metaAttribute)
    {
        if (MetaAttribute.PRESENCE == metaAttribute)
        {
            return "required";
        }

        return "";
    }

    public static int totalQuantityNullValue()
    {
        return -2147483648;
    }

    public static int totalQuantityMinValue()
    {
        return -2147483647;
    }

    public static int totalQuantityMaxValue()
    {
        return 2147483647;
    }

    public int totalQuantity()
    {
        return buffer.getInt(offset + 75, java.nio.ByteOrder.LITTLE_ENDIAN);
    }


    public String toString()
    {
        if (null == buffer)
        {
            return "";
        }

        final OrderRequestMessageDecoder decoder = new OrderRequestMessageDecoder();
        decoder.wrap(buffer, initialOffset, actingBlockLength, actingVersion);

        return decoder.appendTo(new StringBuilder()).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        if (null == buffer)
        {
            return builder;
        }

        final int originalLimit = limit();
        limit(initialOffset + actingBlockLength);
        builder.append("[OrderRequestMessage](sbeTemplateId=");
        builder.append(TEMPLATE_ID);
        builder.append("|sbeSchemaId=");
        builder.append(SCHEMA_ID);
        builder.append("|sbeSchemaVersion=");
        if (parentMessage.actingVersion != SCHEMA_VERSION)
        {
            builder.append(parentMessage.actingVersion);
            builder.append('/');
        }
        builder.append(SCHEMA_VERSION);
        builder.append("|sbeBlockLength=");
        if (actingBlockLength != BLOCK_LENGTH)
        {
            builder.append(actingBlockLength);
            builder.append('/');
        }
        builder.append(BLOCK_LENGTH);
        builder.append("):");
        builder.append("correlationId=");
        builder.append(correlationId());
        builder.append('|');
        builder.append("orderId=");
        builder.append(orderId());
        builder.append('|');
        builder.append("userId=");
        builder.append(userId());
        builder.append('|');
        builder.append("accountId=");
        builder.append(accountId());
        builder.append('|');
        builder.append("eventType=");
        builder.append(eventType());
        builder.append('|');
        builder.append("orderType=");
        builder.append(orderType());
        builder.append('|');
        builder.append("orderAction=");
        builder.append(orderAction());
        builder.append('|');
        builder.append("limitPrice=");
        builder.append(limitPrice());
        builder.append('|');
        builder.append("symbol=");
        for (int i = 0; i < symbolLength() && symbol(i) > 0; i++)
        {
            builder.append((char)symbol(i));
        }
        builder.append('|');
        builder.append("totalQuantity=");
        builder.append(totalQuantity());

        limit(originalLimit);

        return builder;
    }
}
