package com.bn.common.generic;

/**
 * Provides a generic interface for translating from one entity to another.
 *
 * @param <T> The type to translate from, unbounded.
 * @param <U> The type to translate to, unbounded.
 *
 * @author gfeigenson@book.com
 * @since 09/04/2013
 */
public interface ITranslator<T,U>
{
    /**
     * Given an entity of the type T (as specified via type parameters), attempts to produce a corresponding entity of
     * type U.
     *
     * @param entity The entity of type T to translate. Should not be null.
     *
     * @return The corresponding type U entity translation of the given input. May be null.
     */
    public U translate(T entity);
}
