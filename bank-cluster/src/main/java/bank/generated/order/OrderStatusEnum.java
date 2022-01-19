/* Generated SBE (Simple Binary Encoding) message codec. */
package bank.generated.order;

public enum OrderStatusEnum
{
    NEW((short)0),

    FILLED((short)2),

    CANCELED((short)4),

    REPLACED((short)5),

    REJECTED((short)8),

    /**
     * To be used to represent an unknown value from a later version.
     */
    SBE_UNKNOWN((short)255),

    /**
     * To be used to represent not present or null.
     */
    NULL_VAL((short)255);

    private final short value;

    OrderStatusEnum(final short value)
    {
        this.value = value;
    }

    /**
     * The raw encoded value in the Java type representation.
     *
     * @return the raw value encoded.
     */
    public short value()
    {
        return value;
    }

    /**
     * Lookup the enum value representing the value.
     *
     * @param value encoded to be looked up.
     * @return the enum value representing the value.
     */
    public static OrderStatusEnum get(final short value)
    {
        switch (value)
        {
            case 0: return NEW;
            case 2: return FILLED;
            case 4: return CANCELED;
            case 5: return REPLACED;
            case 8: return REJECTED;
            case 255: return NULL_VAL;
        }

        return SBE_UNKNOWN;
    }
}
