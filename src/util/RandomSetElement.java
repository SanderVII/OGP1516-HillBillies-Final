package util;

import java.util.Random;
import java.util.Set;

public class RandomSetElement<E extends Object> {
	
	public RandomSetElement() {
		
	}
	
	public E getRandomElement(Set<E> set) {
		int randomIndex = new Random().nextInt(set.size());
		int count = 0;
		for (E object: set) {
			if (count == randomIndex) 
				return object;
			else
				count++;
		}
		// should only happen if the size of the set is 0.
		return null;
	}
}
