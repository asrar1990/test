package com.bn.common.generic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
 * @param <C> generic object of type class <C>
 */
public class Triple<A,B,C> extends Pair<A,B> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public C third;
	
	public Triple() {	
	}
	
	public Triple(A first, B second, C third) {
		super(first, second);
		this.third = third;
	}
	
	public C getThird() {
		return third;
	}

	public void setThird(C third) {
		this.third = third;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Triple(").append(first).append(',').append(second).append(',').append(third).append(')');
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((third == null) ? 0 : third.hashCode());
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
		final Triple<?,?,?> other = (Triple<?,?,?>) obj;
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
		if (third == null) {
			if (other.third != null)
				return false;
		} else if (!third.equals(other.third))
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
		out.writeObject(third);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream in) throws IOException,
                                                         ClassNotFoundException {
		int s = (int)in.read();
		if (s>=1) {
			first = (A) in.readObject();
			second = (B) in.readObject();
			third = (C) in.readObject();
		}
	}

}
