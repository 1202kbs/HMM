package HMMState;

import java.util.ArrayList;
import java.util.Iterator;

public class States extends StateComponent {

	ArrayList<StateComponent> stateComponents = new ArrayList<StateComponent>();
	
	public void add(StateComponent stateComponent) {
		stateComponents.add(stateComponent);
	}
	
	public void remove(StateComponent stateComponent) {
		stateComponents.remove(stateComponent);
	}
	
	public StateComponent getChild(int i) {
		return stateComponents.get(i);
	}
	
	public int getSize() {
		return stateComponents.size();
	}
	
	public Iterator createIterator() {
		return new StatesIterator(stateComponents.iterator());
	}
}
