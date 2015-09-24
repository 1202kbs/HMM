package HMMObservation;

import java.util.ArrayList;
import java.util.Iterator;

public class Observations extends ObserveComponent {
	ArrayList<ObserveComponent> observeComponents = new ArrayList<ObserveComponent>();
	
	public void add(ObserveComponent observeComponent) {
		observeComponents.add(observeComponent);
	}
	
	public void remove(ObserveComponent observeComponent) {
		observeComponents.remove(observeComponent);
	}
	
	public ObserveComponent getChild(int i) {
		return observeComponents.get(i);
	}
	
	public int getSize() {
		return observeComponents.size();
	}
	
	public Iterator createIterator() {
		return new ObsIterator(observeComponents.iterator());
	}
}
