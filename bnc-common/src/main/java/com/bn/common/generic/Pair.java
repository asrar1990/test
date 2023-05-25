package com.bn.common.generic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This is a generic container class.  It encapsulates to objects of any type.  This can be used
 * for transferring of information between classes within a project, without having the need to
 * create temporary storage classes.  It is not recommended between projects, because project
 * owners may not understand what information is contained in the generic type.
 *   
 * @author ktran
 *
 * @param <A> generic object of type class <A>
 * @param <B> generic object of type class <B>
 */
public class Pair<A,B> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public A first;
	public B second;
	
	public Pair() {
	}
	
	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}
	
	// infer type parameters
	public static <A, B> Pair<A, B> newPair(A first, B second) {
		return new Pair<A, B>(first, second);
	}
	
	public static <A, B> Pair<A, B>[] newPairArray(Pair<A, B>... pairs) {
		return pairs;
	}
	
	public A getFirst() {
		return first;
	}
	
	public void setFirst(A first) {
		this.first = first;
	}

	public B getSecond() {
		return second;
	}

	public void setSecond(B second) {
		this.second = second;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Pair(").append(first).append(',').append(second).append(')');
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Pair<?,?> other = (Pair<?, ?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	///////////////////////////////////////////////////////////////////////////	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.write((int)serialVersionUID);
		out.writeObject(first);
		out.writeObject(second);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws IOException,
                                                         ClassNotFoundException {
		int s = (int)in.read();
		if (s>=1) {
			first = (A) in.readObject();
			second = (B) in.readObject();
		}
	}
}
