import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * PasswordSet.java.
 *
 * Provides an implementation of the Set interface using an
 * array as the underlying data structure. Values in the array
 * are kept in ascending natural order and, where possible,
 * methods take advantage of this. Many of the methods with parameters
 * of type PasswordSet are specifically designed to take advantage
 * of the ordered array implementation.
 *
 * @author Frank Herring (fhh0002@auburn.edu)
 * @author	Dean Hendrix (dh@auburn.edu)
 * @version	2016-10-17
 *
 */
public class PasswordSet<T extends Comparable<? super T>> implements Set<T> {

   private String username;
   private String masterPassword;
   T[] elements;
   int size;

   /**
    * Instantiates an empty set.
    */
   @SuppressWarnings("unchecked")
   public PasswordSet(String usernameIn, String masterPasswordIn) {
      username = usernameIn;
      masterPassword = masterPasswordIn;
      elements = (T[]) new Comparable[1];
      size = 0;
   }
   
   @SuppressWarnings("unchecked")
   private PasswordSet(String usernameIn, String masterPasswordIn, Comparable[] things, int sizeIn) {
      username = usernameIn;
      masterPassword = masterPasswordIn;
      elements = (T[]) things;
      size = sizeIn;
   }

   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements,
    *               false otherwise.
    */
   public boolean isEmpty() {
      return size == 0;
   }

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this PasswordSet.
    *
    * @return a string representation of this PasswordSet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "Password Set is empty.";
      }
      StringBuilder result = new StringBuilder();
      for (T element : this) {
         result.append(element.toString() + "\n");
      }
      return result.toString();
   }


   /**
    * Returns the username.
    *
    * @return a string representing the username
    */
   public String getUsername() {
      return username;
   }


   /**
    * Sets the username field.
    * 
    * @param usernameIn which will be used to set the username field
    */
   public void setUsername(String usernameIn) {
      username = usernameIn;
   }


   /**
    * Returns the master password.
    *
    * @return a string representing the master password
    */
   public String getMasterPassword() {
      return masterPassword;
   }

   /**
    *Sets the masterPassword field.
    * 
    * @param masterPasswordIn which will be used to set the masterPassword field
    */
   public void setMasterPassword(String masterPasswordIn) {
      masterPassword = masterPasswordIn;
   }
   
   
   /**
    * Return true or false depending on whether or not the array
    * is full.
    * 
    * @return true if the array is full, otherwise false
    */
   private boolean isFull() {
      return (size == elements.length);
   }
   
   
   /**
    * Resizes the elements field by a specified amount
    * while maintaining the items in the array.
    * 
    * @param capacity for the desired size of the array
    */ 
   @SuppressWarnings("unchecked")  
   private void resize(int capacity) {
      T[] a = (T[]) new Comparable[capacity];
      for (int i = 0; i < size(); i++) {
         a[i] = elements[i];
      }
      
      elements = a;
   }
   
   
   /**
    * If found, it returns the index of a specified item.
    *
    * @param element to be searched for
    * @return index if found, otherwise -1
    */
   private int locate(T element) {
      int lo = 0;
      int hi = size() - 1;
      int mid;
      while (lo <= hi) {
         mid = (lo + hi) / 2;
         if (element.completeCompare(elements[mid]) == 0) {
            return mid;
         }
         
         if (element.completeCompare(elements[mid]) < 0) {
            hi = mid - 1;
         }
         
         if (element.completeCompare(elements[mid]) > 0) {
            lo = mid + 1;
         }
      }
      
      return -1;
   }


    /**
     * Ensures the collection contains the specified element. Elements are
     * maintained in ascending natural order at all times. Neither duplicate nor
     * null values are allowed.
     *
     * @param  element  The element whose presence is to be ensured.
     * @return true if collection is changed, false otherwise.
     */
   public boolean add(T element) {
      if (element == null) {
         return false;
      }
      
      if (isFull()) {
         resize(elements.length * 2);
      }
      
      int i = 0;
      
      while (elements[i] != null && element.compareTo(elements[i]) > 0 
               && i < size()) {
         i++;
      }
      
      
      if (elements[i] == null) {
         elements[i] = element;
         size++;
         return true;
      }
      else if (element.compareTo(elements[i]) == 0) {
         return false;
      }
      
      T temp1 = elements[i];
      T temp2 = null;
      elements[i] = element;
      i++;
      
      for (; i <= size(); i++) {
         temp2 = elements[i];
         elements[i] = temp1;
         temp1 = temp2;
      }
      
      size++;
      return true;
   }

    /**
     * Ensures the collection does not contain the specified element.
     * If the specified element is present, this method removes it
     * from the collection. Elements are maintained in ascending natural
     * order at all times.
     *
     * @param   element  The element to be removed.
     * @return  true if collection is changed, false otherwise.
     */
   @SuppressWarnings("unchecked")
   public boolean remove(T element) {
      int i = 0;
      
      while (i < size() && elements[i] != null 
               && element.compareTo(elements[i]) > 0) {
         i++;
      }
      
      if (i >= elements.length || elements[i] == null) {
         return false;
      }
      else if (element.compareTo(elements[i]) == 0) {
         T[] newElements= (T[]) new Comparable[elements.length];
         int j = 0;
         for (; j < i; j++) {
            newElements[j] = elements[j];
         }
         j++;
         for (; j < size(); j++) {
            newElements[i] = elements[j];
            i++;
         }
         elements = newElements;
         size--;
         
         if (size > 0 && size() < (elements.length / 4)) {
            resize(elements.length / 2);
         }
         
         return true;
      }
      
      return false;
   }

   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection
    *                   is to be tested.
    * @return  true if this collection contains the specified element,
    *               false otherwise.
    */
   public boolean contains(T element) {
      return (locate(element) >= 0);
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      for (T each : s) {
         if (!this.contains(each)) {
            return false;
         }
      }
      
      return true;
   }

   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements
    *               as the parameter set, false otherwise
    */
   public boolean equals(PasswordSet<T> s) {
      for (int i = 0; i < size(); i++) {
         if (!(elements[i].compareTo(s.elements[i]) == 0)) {
            return false;
         }
      }
      
      return true;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    */
   public Set<T> union(Set<T> s) {
      PasswordSet<T> result = new PasswordSet<T>(username, masterPassword);
      
      for (T each : s) {
         result.add(each);
      }
      
      for (T each: elements) {
         result.add(each);
      }
      
      return result;
   }

   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and
    *            the parameter set
    */
   public Set<T> union(PasswordSet<T> s) {
      int newSize;
      if (this.size() > s.size()) {
         newSize = (this.size() * 2);
      }
      else {
         newSize = (s.size() * 2);
      }
      
      int count = 0;
      Comparable[] wumbo = new Comparable[newSize];
      
      int i = 0;
      int j = 0;
      int k = 0;
      
      boolean finished = false;
      while (!finished) {
         //i is done, j is not
         if (i > this.size() -1 ) {
            wumbo[k] = s.elements[j];
            j++;
            k++;
            count++;
         }
         
         else if (j > s.size() - 1) {
            wumbo[k] = this.elements[i];
            i++;
            k++;
            count++;
         }
         
         else if (this.elements[i].compareTo(s.elements[j]) < 0) {
            wumbo[k] = this.elements[i];
            i++;
            k++;
            count++;
         }
         
         else if (this.elements[i].compareTo(s.elements[j]) == 0) {
            wumbo[k] = this.elements[i];
            i++;
            j++;
            k++;
            count++;
         }
         
         else { //this.elements[i].compareTo(s.elements[j]) > 0)
            wumbo[k] = s.elements[j];
            j++;
            k++;
            count++;
         }
         
         if (i > this.size() -1 && j > s.size() - 1) {
            finished = true;
         }
         else {
            finished = false;
         }
      }
      
      PasswordSet<T> result = new PasswordSet<T>(username, masterPassword, wumbo, count);
      
      return result;
   }


   /**
    * Returns a set that is the intersection of this set
    * and the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      //Comparable[] things = new Comparable[size()];
      PasswordSet<T> result = new PasswordSet<T>(username, masterPassword);
      //int countOfThings = 0;
      for (T each : s) {
         if (this.contains(each)) {
            //things[countOfThings] = each;
            //countOfThings++;
            result.add(each);
         }
      }
      
      return result;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(PasswordSet<T> s) {
      int newSize;
      if (this.size() > s.size()) {
         newSize = (this.size() * 2);
      }
      else {
         newSize = (s.size() * 2);
      }
      
      int count = 0;
      Comparable[] wumbo = new Comparable[newSize];
      
      int i = 0;
      int j = 0;
      int k = 0;
      
      boolean finished = false;
      while (!finished) {
         if (s.size() == 0 || this.size() == 0) {
            break;
         }
         //i is done, j is not
         if (this.elements[i].compareTo(s.elements[j]) < 0) {
            i++;
         }
         
         else if (this.elements[i].compareTo(s.elements[j]) == 0) {
            wumbo[k] = this.elements[i];
            i++;
            j++;
            k++;
            count++;
         }
         
         else { //this.elements[i].compareTo(s.elements[j]) > 0)
            j++;
         }
         
         if (i > this.size() -1 || j > s.size() - 1) {
            finished = true;
         }
         else {
            finished = false;
         }
      }
      
      PasswordSet<T> result = new PasswordSet<T>(username, masterPassword, wumbo, count);
      
      return result;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      Set<T> ss = new PasswordSet<T>(username, masterPassword);
      Comparable[] things = new Comparable[size()];
      int countOfThings = 0;
      /*
         if (!s.contains(elements[i])) {
            things[countOfThings] = each;
            countOfThings++;
         }
      }
      */
      
      for (int i = 0; i < this.size(); i++) {
         if (!s.contains(elements[i])) {
            things[countOfThings] = elements[i];
            countOfThings++;
         }
      }
      
      PasswordSet<T> result = new PasswordSet<T>(username, masterPassword, things, countOfThings);
      
      return result;
   }

   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(PasswordSet<T> s) {
      int newSize;
      if (this.size() > s.size()) {
         newSize = (this.size() * 2);
      }
      else {
         newSize = (s.size() * 2);
      }
      
      int count = 0;
      Comparable[] wumbo = new Comparable[newSize];
      
      int i = 0;
      int j = 0;
      int k = 0;
      
      boolean finished = false;
      while (!finished) {
         if (this.size() == 0) {
            break;
         }
         
         if (j > s.size() - 1) {
            wumbo[k] = this.elements[i];
            i++;
            k++;
            count++;
         }
         
         else if (this.elements[i].compareTo(s.elements[j]) < 0) {
            wumbo[k] = this.elements[i];
            i++;
            k++;
            count++;
         }
         
         else if (this.elements[i].compareTo(s.elements[j]) == 0) {
            i++;
            j++;
         }
         
         else { //this.elements[i].compareTo(s.elements[j]) > 0)
            j++;
         }
         
         if (i > this.size() -1) {
            finished = true;
         }
         else {
            finished = false;
         }
      }
      
      PasswordSet<T> result = new PasswordSet<T>(username, masterPassword, wumbo, count);
      
      return result;
   }

   
   //Nested classes - done so that they have access to private variables
   //without exposing said variables to the outside world
   public class PasswordSetIterator<T> implements Iterator<T> {
      //The array of elements to be iterated over
      private T[] items;
      
      //The number of elements in the array
      private int count;
      
      //The current position in the iteration
      private int current;
      
      //Constructor for ArrayIterator<T> class
      public PasswordSetIterator(T[] elements, int size) {
         items = elements;
         count = size;
         current = 0;
      }
      
      
      //Methods for ArrayIterator<T> class
      public boolean hasNext() {
         return (current < count);
      }
      
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         
         return items[current++];
      }
   }


   /**
    * Returns an iterator over the elements in this PasswordSet.
    * No specific order can be assumed.
    *
    * @return  an iterator over the elements in this PasswordSet
    */
   @SuppressWarnings("unchecked")
   public Iterator<T> iterator() {
   
      // ALMOST ALL THE TESTS DEPEND ON THIS METHOD WORKING CORRECTLY.
      // MAKE SURE YOU GET THIS ONE WORKING FIRST.
      // HINT: JUST USE THE SAME CODE/STRATEGY AS THE ARRAYBAG CLASS
      // FROM LECTURE. THE ONLY DIFFERENCE IS THAT YOU'LL NEED THE
      // ARRAYITERATOR CLASS TO BE NESTED, NOT TOP-LEVEL.
   
      return new PasswordSetIterator(elements, size);
   }

   public class PasswordSetDescendingIterator<T> implements Iterator<T> {
      //The array of elements to be iterated over
      private T[] items;
      
      //The number of elements in the array
      private int count;
      
      //The current position in the iteration
      private int current;
      
      //Constructor for ArrayIterator<T> class
      public PasswordSetDescendingIterator(T[] elements, int size) {
         items = elements;
         count = size;
         current = count - 1;
      }
      
      
      //Methods for ArrayIterator<T> class
      public boolean hasNext() {
         return current > (-1);
      }
      
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         
         return items[current--];
      }
   }
   

   /**
    * Returns an iterator over the elements in this PasswordSet.
    * The elements are returned in descending order.
    *
    * @return  an iterator over the elements in this PasswordSet
    */
   @SuppressWarnings("unchecked")
   public Iterator<T> descendingIterator() {
      return new PasswordSetDescendingIterator(elements, size);
   }

   /**
    * Returns an iterator over the members of the power set
    * of this PasswordSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return null;
   }

}
