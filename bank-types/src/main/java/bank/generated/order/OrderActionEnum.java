/* Generated SBE (Simple Binary Encoding) message codec. */
package bank.generated.order;

public enum OrderActionEnum
{
    SELL((short)0),

    BUY((short)1),

    /**
     * To be used to represent an unknown value from a later version.
     */
    SBE_UNKNOWN((short)255),

    /**
     * To be used to represent not present or null.
     */
    NULL_VAL((short)255);

    private final short value;

    OrderActionEnum(final short value)
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
    public static OrderActionEnum get(final short value)
    {
        switch (value)
        {
            case 0: return SELL;
            case 1: return BUY;
            case 255: return NULL_VAL;
        }

        return SBE_UNKNOWN;
    }
}
