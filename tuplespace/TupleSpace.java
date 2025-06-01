package tuplespace;

import java.util.*;

public class TupleSpace {

	private Hashtable tuples = new Hashtable();

	// deposits data in tuple space (ts)
	public synchronized void out (String tag, Object data) {

		Vector v = (Vector) tuples.get(tag);
		if (v==null) {
			v=new Vector();
			tuples.put(tag,v);
		}
		v.addElement(data);
		notifyAll();
	}
	
	private Object get(String tag, boolean remove) {

		Vector v = (Vector) tuples.get(tag);
		if (v==null) return null;
		if (v.size()==0) return null;
		Object o = v.firstElement();
		if (remove) v.removeElementAt(0);
		return o;
	}
	//extracts tagged object from ts, blocks if unavailable
	public synchronized Object in (String tag)
	throws InterruptedException {

		Object o;
		while ((o=get(tag,true))==null) wait();
		return o;
	}
	//reads tagged object from ts, blocks if unavailable
	public synchronized Object rd (String tag)
	throws InterruptedException {

		Object o;
		while ((o=get(tag,false))==null) wait();
		return o;
	}
}

