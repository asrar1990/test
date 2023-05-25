package com.bn.common.generic;

/**
 * Provides a class for the times where you're not sure what "null" actually means.<p/>
 *
 * For cases where null might mean that the value was omitted (reflection, web services etc) you can interrogate this
 * class via {@link #isValueSet()} to see if we're expecting a value. If so, then the value from {@link #getValue()}
 * actually means that the value was set to null. If {@link #isValueSet()} is false, then callers can know that a
 * null {@link #getValue()} actually means that no value was provided.
 *
 * @author gfeigenson@book.com
 * @since 09/03/2014
 */
public class Maybe<T>
{
    /**
     * Holds whether or not the value has actually been set.
     */
    private boolean _valueSet;

    /**
     * Holds the value. If this has been set, it implies that {@link #isValueSet()} should be <code>true</code>.
     */
    private T _value;

    /**
     * Constructs a given {@link Maybe} with the a given value.<p/>
     *
     * This is equivalent of calling the following:
     * <ol>
     *  <li>{@link Maybe#Maybe()}</li>
     *  <li>{@link Maybe#setValue(Object)}</li>
     * </ol>
     *
     * @param value The value to set.
     *
     * @see #setValue(Object)
     */
    public Maybe(final T value)
    {
        setValue(value);
    }

    /**
     * Provides a default, no argument constructor.
     */
    public Maybe()
    {

    }

    /**
     * Gets whether or not a value has been set on this instance.
     *
     * @return <code>True</code> if a value has been set, otherwise <code>false</code>. If <code>false</code>, then the
     * resulting value from {@link #getValue()} should be discarded.
     */
    public boolean isValueSet()
    {
        return _valueSet;
    }

    /**
     * Sets whether or not the value has been set on this instance.
     *
     * @param valueSet <code>True</code> if a value has been set, otherwise <code>false</code>.
     */
    public void setValueSet(final boolean valueSet)
    {
        _valueSet = valueSet;
    }

    /**
     * Gets the value associated with this instance.<p/>
     *
     * Please note that if {@link #isValueSet()} is <code>false</code>, then the result of this call should be ignored.
     *
     * @return The value associated with this instance, IFF {@link #isValueSet()} is <code>true</code>.
     */
    public T getValue()
    {
        return _value;
    }

    /**
     * Sets the value associated with this instance.<p/>
     *
     * Please note that calling this setter will invoke {@link #setValueSet(boolean)} with a value of <code>true</code>,
     * letting callers know that a value has been set and not to discard the value from {@link #getValue()}.
     *
     * @param value The value.
     */
    public void setValue(final T value)
    {
        _value = value;
        setValueSet(true);
    }
}
