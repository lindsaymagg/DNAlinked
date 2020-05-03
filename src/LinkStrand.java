
public class LinkStrand implements IDnaStrand {
	
	private int myIndex;
	private int myLocalIndex;
	private Node myCurrent;
	
	   private class Node {
		   	String info;
		   	Node next;
		   	public Node(String s) {
		      	info = s;
		      	next = null;
		   	}
		   }
		   private Node myFirst;
		   private Node myLast;
		   private long mySize;
		   private int myAppends;
		   
	/**
	 * Construct a LinkStrand object with a String parameter that 
	 * creates a single node after the initialize method is called.   
	 * @param s is a String that has a sequence of characters
	 */
	public LinkStrand(String s) {
		initialize(s);
	}
	
	/**
	 * Construct a default LinkStrand object with a String parameter of ""
	 */
	public LinkStrand() {
		this("");
	}
	
	/**
	 * Initializes the class invariants.
	 */
	@Override
	public void initialize(String source) {
		Node initialized = new Node(source);
		myAppends = 0;
		myFirst = initialized;
		myLast = initialized;
		mySize = source.length();
		myIndex = 0;
		myLocalIndex = 0;
		myCurrent = myFirst;	
	}
	
	/**
	 * @return the number of characters in ALL nodes combined
	 */
	@Override
	public long size() {
		return mySize;
	}

	/**
	 * This method creates one new node to the end of the 
	 * internal linked list and updates instance variables to 
	 * maintain class invariants as described below. 
	 * @param dna is a String of characters corresponding to a DNA strand
	 * @return newly modified IDnaStrand object 
	 */
	@Override
	public IDnaStrand append(String dna) {
		Node appendedNode = new Node(dna);
		myLast.next = appendedNode;
		myLast = myLast.next;
		//Update mySize
		mySize += dna.length();
		//Update myAppends
		myAppends += 1;
		return this;
	}
	
	/**
	 * This method creates and updates a single StringBuilder object by 
	 * appending each Node's information. It converts the DNA strand into a String type. 
	 * @return String representation of the entire DNA strand
	 */
	@Override
	public String toString() {
		Node dummy = this.myFirst;
		StringBuilder myStringStrand = new StringBuilder("");
		while (dummy != null) {
			myStringStrand.append(dummy.info);
			dummy = dummy.next;
		}
		return myStringStrand.toString();
	}
	
	/**
	 * Return this LinkStrand object
	 * @param source is data from which an object is constructed
	 * @return LinkStrand object 
	 */
	@Override
	public IDnaStrand getInstance(String source) {
		return new LinkStrand(source);
	}

	/**
	 * Creates a new LinkStrand that reverses the order of the internal linked 
	 * list as well as the contents within each string in each node of the linked list.
	 * @return a new reversed LinkStrand 
	 */
	@Override
	public IDnaStrand reverse() {
		//make dummy, make it the same as myFirst
		Node dummy = this.myFirst;
		//create LinkStrand object whose first node is that is myFirst.info but reversed
		LinkStrand newBoy = new LinkStrand(reverseString(dummy.info));
		//go to next node of OG LinkStrand
		dummy = dummy.next;
		//for each node in the OG LinkStrand, reverse the info and add a new node
		if (dummy == null) {
			return newBoy;
		}
		while (newBoy.myFirst.next == null && dummy != null) {
			newBoy.myFirst.next = new Node(newBoy.myFirst.info);
			newBoy.myFirst.info = reverseString(dummy.info);
			dummy = dummy.next;
		}
		while (dummy != null) {
			Node theOne;
			//make newBoy.next be a new Node, which is a copy of newBoy
			//Node copyBoy = new Node(newBoy.myFirst.info);
			//newBoy.myFirst.next = copyBoy;
			theOne = newBoy.myFirst.next;
			newBoy.myFirst.next	= new Node(newBoy.myFirst.info);
			newBoy.myFirst.next.next = theOne;
			newBoy.myFirst.info = reverseString(dummy.info);
			dummy = dummy.next;
		}
		newBoy.mySize = this.mySize;
		return newBoy;
	}
	
	/**
	 * Helper function that reverses a String. Implemented in the 
	 * reverse() function 
	 * @param s is the String stored in each Node
	 * @return String with characters reversed
	 */
	private String reverseString(String s) {
		String retString = "";
		String[] arrayVersion = new String[s.length()];
		for (int i = 0; i < s.length(); i++) {
			arrayVersion[i] = s.substring(i, i+1);
		}
		for (String c : arrayVersion) {
			retString = c + retString;
		}		
		return retString;
	}

	/**
	 * @return the number of times that the .append() 
	 * method was called
	 */
	@Override
	public int getAppendCount() {
		return myAppends;
	}

	/**
	 * Efficient method to return the character within the string in a Node 
	 * at a specified index.
	 * @param index of the String within the Node
	 * @return character at the specified index, 
	 * @throws IndexOutOfBoundsException if not valid index
	 */
	@Override
	public char charAt(int index) {
		if (index < 0 | index >= mySize) {
			throw new IndexOutOfBoundsException();
		}
		//if the index that you're searching for is less than the index you're
		//currently at, you have to start over from the beginning of the list again
		if (index < myIndex) {
		    myIndex = 0; //total index of linked list
		    myLocalIndex = 0; //sub-index within the nodes
		    myCurrent = this.myFirst; //current node being referenced for an index
		    while (myIndex != index) {
		    	myIndex ++;
		    	myLocalIndex ++;
		    	if (myLocalIndex >= myCurrent.info.length()) {
		    		myLocalIndex = 0;
		    		myCurrent = myCurrent.next;
		    	}
		    }
		    return myCurrent.info.charAt(myLocalIndex);
		}
		//if index is higher than myIndex, start from index
		//and set myIndex to index
		else if (index == myIndex+1) {
			if (myLocalIndex + 1 >= myCurrent.info.length()) {
	    		myCurrent = myCurrent.next;
	    		myLocalIndex = 0;
	    		myIndex = index;
	    		return myCurrent.info.charAt(0);
	    	}
			else {
			return myCurrent.info.charAt(myLocalIndex+1);
			}
		}
		else {
			 while (myIndex != index) {
			    	myIndex ++;
			    	myLocalIndex ++;
			    	if (myLocalIndex >= myCurrent.info.length()) {
			    		myLocalIndex = 0;
			    		myCurrent = myCurrent.next;
			    	}
			    }
			    return myCurrent.info.charAt(myLocalIndex);
		}
	}
}
