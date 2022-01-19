/* Generated SBE (Simple Binary Encoding) message codec. */
package bank.generated.order;

import org.agrona.MutableDirectBuffer;
import org.agrona.sbe.*;


/**
 * Order request, include [place, replace, cancel]
 */
@SuppressWarnings("all")
public final class OrderRequestMessageEncoder implements MessageEncoderFlyweight
{
    public static final int BLOCK_LENGTH = 79;
    public static final int TEMPLATE_ID = 1;
    public static final int SCHEMA_ID = 688;
    public static final int SCHEMA_VERSION = 1;
    public static final java.nio.ByteOrder BYTE_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;

    private final OrderRequestMessageEncoder parentMessage = this;
    private MutableDirectBuffer buffer;
    private int initialOffset;
    private int offset;
    private int limit;

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

    public MutableDirectBuffer buffer()
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

    public OrderRequestMessageEncoder wrap(final MutableDirectBuffer buffer, final int offset)
    {
        if (buffer != this.buffer)
        {
            this.buffer = buffer;
        }
        this.initialOffset = offset;
        this.offset = offset;
        limit(offset + BLOCK_LENGTH);

        return this;
    }

    public OrderRequestMessageEncoder wrapAndApplyHeader(
        final MutableDirectBuffer buffer, final int offset, final MessageHeaderEncoder headerEncoder)
    {
        headerEncoder
            .wrap(buffer, offset)
            .blockLength(BLOCK_LENGTH)
            .templateId(TEMPLATE_ID)
            .schemaId(SCHEMA_ID)
            .version(SCHEMA_VERSION);

        return wrap(buffer, offset + MessageHeaderEncoder.ENCODED_LENGTH);
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

    public OrderRequestMessageEncoder correlationId(final long value)
    {
        buffer.putLong(offset + 0, value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
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

    public OrderRequestMessageEncoder orderId(final long value)
    {
        buffer.putLong(offset + 8, value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
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

    public OrderRequestMessageEncoder userId(final long value)
    {
        buffer.putLong(offset + 16, value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
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

    public OrderRequestMessageEncoder accountId(final long value)
    {
        buffer.putLong(offset + 24, value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
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

    public OrderRequestMessageEncoder eventType(final EventTypeEnum value)
    {
        buffer.putByte(offset + 32, (byte)value.value());
        return this;
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

    public OrderRequestMessageEncoder orderType(final OrderTypeEnum value)
    {
        buffer.putByte(offset + 33, (byte)value.value());
        return this;
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

    public OrderRequestMessageEncoder orderAction(final OrderActionEnum value)
    {
        buffer.putByte(offset + 34, (byte)value.value());
        return this;
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

    public OrderRequestMessageEncoder limitPrice(final double value)
    {
        buffer.putDouble(offset + 35, value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
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


    public OrderRequestMessageEncoder symbol(final int index, final byte value)
    {
        if (index < 0 || index >= 32)
        {
            throw new IndexOutOfBoundsException("index out of range: index=" + index);
        }

        final int pos = offset + 43 + (index * 1);
        buffer.putByte(pos, value);

        return this;
    }

    public static String symbolCharacterEncoding()
    {
        return "ASCII";
    }

    public OrderRequestMessageEncoder putSymbol(final byte[] src, final int srcOffset)
    {
        final int length = 32;
        if (srcOffset < 0 || srcOffset > (src.length - length))
        {
            throw new IndexOutOfBoundsException("Copy will go out of range: offset=" + srcOffset);
        }

        buffer.putBytes(offset + 43, src, srcOffset, length);

        return this;
    }

    public OrderRequestMessageEncoder symbol(final String src)
    {
        final int length = 32;
        final int srcLength = null == src ? 0 : src.length();
        if (srcLength > length)
        {
            throw new IndexOutOfBoundsException("String too large for copy: byte length=" + srcLength);
        }

        buffer.putStringWithoutLengthAscii(offset + 43, src);

        for (int start = srcLength; start < length; ++start)
        {
            buffer.putByte(offset + 43 + start, (byte)0);
        }

        return this;
    }

    public OrderRequestMessageEncoder symbol(final CharSequence src)
    {
        final int length = 32;
        final int srcLength = null == src ? 0 : src.length();
        if (srcLength > length)
        {
            throw new IndexOutOfBoundsException("CharSequence too large for copy: byte length=" + srcLength);
        }

        for (int i = 0; i < srcLength; ++i)
        {
            final char charValue = src.charAt(i);
            final byte byteValue = charValue > 127 ? (byte)'?' : (byte)charValue;
            buffer.putByte(offset + 43 + i, byteValue);
        }

        for (int i = srcLength; i < length; ++i)
        {
            buffer.putByte(offset + 43 + i, (byte)0);
        }

        return this;
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

    public OrderRequestMessageEncoder totalQuantity(final int value)
    {
        buffer.putInt(offset + 75, value, java.nio.ByteOrder.LITTLE_ENDIAN);
        return this;
    }


    public String toString()
    {
        if (null == buffer)
        {
            return "";
        }

        return appendTo(new StringBuilder()).toString();
    }

    public StringBuilder appendTo(final StringBuilder builder)
    {
        if (null == buffer)
        {
            return builder;
        }

        final OrderRequestMessageDecoder decoder = new OrderRequestMessageDecoder();
        decoder.wrap(buffer, initialOffset, BLOCK_LENGTH, SCHEMA_VERSION);

        return decoder.appendTo(builder);
    }
}
