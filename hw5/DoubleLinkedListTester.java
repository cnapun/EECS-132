import org.junit.Test;

import java.util.IdentityHashMap;
import java.util.IntSummaryStatistics;
import java.util.ListIterator;

import static org.junit.Assert.*;

/**
 * A class that tests the methods of the DoubleLinkedList class.
 *
 * @author Nikil Pancha
 */
public class DoubleLinkedListTester {

    /**
     * Tests the addToFront method of DoubleLinkedList.
     * <p>Copied from lab to verify functionality</p>
     */
    @Test
    public void testAddToFront() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        DLNode<Integer> head = list.getFront();
        DLNode<Integer> tail = list.getBack();

        assertEquals("Testing first node of list", new Integer(1), head.getElement());
        assertEquals("Testing second node of list", new Integer(2), head.getNext().getElement());
        assertEquals("Testing third node of list", new Integer(3), head.getNext().getNext().getElement());
        assertEquals("Testing end of list", null, head.getNext().getNext().getNext());

        assertEquals("Testing node at back of list", new Integer(3), tail.getElement());
        assertEquals("Testing next to last node", new Integer(2), tail.getPrevious().getElement());
        assertEquals("Testing third to last node", new Integer(1), tail.getPrevious().getPrevious().getElement());
        assertEquals("Testing front of list", null, tail.getPrevious().getPrevious().getPrevious());
    }

    /**
     * Tests the addToBack method of DoubleLinkedList.
     * <p>Copied from lab to verify functionality</p>
     */
    @Test
    public void testAddToBack() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        list.addToBack(1);
        list.addToBack(2);
        list.addToBack(3);
        DLNode<Integer> head = list.getFront();
        DLNode<Integer> tail = list.getBack();

        assertEquals("Testing last node of list", new Integer(3), tail.getElement());
        assertEquals("Testing next to last node", new Integer(2), tail.getPrevious().getElement());
        assertEquals("Testing third to last node", new Integer(1), tail.getPrevious().getPrevious().getElement());
        assertEquals("Testing front of list", null, tail.getPrevious().getPrevious().getPrevious());

        assertEquals("Testing node at front of list", new Integer(1), head.getElement());
        assertEquals("Testing second node of list", new Integer(2), head.getNext().getElement());
        assertEquals("Testing third node of list", new Integer(3), head.getNext().getNext().getElement());
        assertEquals("Testing end of list", null, head.getNext().getNext().getNext());
    }

    /**
     * Tests the removeFromFront method of DoubleLinkedList.
     * <p>Copied from lab to verify functionality</p>
     */
    @Test
    public void testRemoveFromFront() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        list.addToFront(1);
        list.addToFront(2);
        list.addToFront(3);
        assertEquals("Removing element of list", new Integer(3), list.removeFromFront());
        assertEquals("Removing a second element", new Integer(2), list.removeFromFront());
        assertEquals("Removing a third element", new Integer(1), list.removeFromFront());
        assertEquals("Removed last element of list", true, list.isEmpty());
        try {
            list.removeFromFront();
            fail("Removing from empty list did not throw an exception.");
        } catch (java.util.NoSuchElementException e) {
      /* everything is good */
        } catch (Exception e) {
            fail("Removing from empty list threw the wrong type of exception.");
        }

        list.addToBack(6);
        list.addToBack(7);
        assertEquals("Removing element added to back of list", new Integer(6), list.removeFromFront());
        assertEquals("Removing second element added to back", new Integer(7), list.removeFromFront());
    }

    /**
     * Tests the removeFromBack method of DoubleLinkedList.
     * <p>Copied from lab to verify functionality</p>
     */
    @Test
    public void testRemoveFromBack() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        list.addToBack(5);
        list.addToFront(4);
        list.addToBack(6);
        assertEquals("Removing element from back of list", new Integer(6), list.removeFromBack());
        assertEquals("Removing second element from back of list", new Integer(5), list.removeFromBack());
        assertEquals("Removing element from back that was added to front", new Integer(4), list.removeFromBack());
        assertEquals("Removing last element of list", true, list.isEmpty());
        try {
            list.removeFromBack();
            fail("Removing from empty list did not throw an exception.");
        } catch (java.util.NoSuchElementException e) {
      /* everything is good */
        } catch (Exception e) {
            fail("Removing from empty list threw the wrong type of exception.");
        }
    }

    /**
     * Tests the linked list iterator going forwards.
     * <p>Copied from lab to verify functionality</p>
     */
    @Test
    public void testListIteratorBasic() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 5; i > 0; i--)
            list.addToFront(i);
    
    /* checking that we get out what we put it */
        int i = 1;
        for (Integer x : list)
            assertEquals("Testing value returned by iterator", new Integer(i++), x);

        if (i != 6)
            fail("The iterator did not run through all the elements of the list");
    }

    /**
     * Tests the previous and next features of the linked list iterator
     */
    @Test
    public void testListIteratorBothDirections() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 0; i < 5; i++) {
            list.addToFront(i);
        }
        ListIterator<Integer> it = list.iterator();
        assertFalse("No previous element at start", it.hasPrevious());
        //going through the loop forwards
        for (int i = 4; i >= 0; i--) {
            int tmp = it.next();
            assertEquals("Element accessed properly going forwards", i, tmp);
        }
        assertFalse("No previous element at the end", it.hasNext());
        //going through list backwards
        for (int i = 0; i < 5; i++) {
            assertEquals("Element accessed properly going backwards", i, (int) it.previous());
        }
        assertFalse("No previous element at start", it.hasPrevious());
    }

    /**
     * Tests the set method of the list iterator
     */
    @Test
    public void testListIteratorSet() {
        //test a bunch of different lengths
        for (int len = 0; len < 30; len++) {
            DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
            for (int i = 0; i < len; i++) {
                list.addToFront(i);
            }
            ListIterator<Integer> it = list.iterator();
            //set while going forwards
            while (it.hasNext()) {
                int tmp = it.next();
                assertEquals("Testing next index", len - tmp, it.nextIndex());
                it.set(tmp + 1);
            }
            //set while going backwards
            while (it.hasPrevious()) {
                int tmp = it.previous();
                assertEquals("testing previous index", len - tmp - 1, it.previousIndex());
                it.set(tmp + 2);
            }
            //check while going forwards
            for (int i = len + 2; i > 2; i--) {
                assertEquals("Element set correctly, checking forwards", i, (int) it.next());
            }
            //check while going backwards
            for (int i = 3; i < len + 3; i++) {
                assertEquals("Element set correctly checking backwards", i, (int) it.previous());
            }
        }
    }

    /**
     * Tests the append method of DoubleLinkedList
     */
    @Test(expected = NullPointerException.class)
    public void testAppend() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        list.addToFront(1);
        list.addToFront(0);
        DoubleLinkedList<Integer> list2 = new DoubleLinkedList<Integer>();
        list2.addToFront(2);
        list2.addToFront(3);
        list.append(list2);
        ListIterator it = list.iterator();
        Integer[] expected = new Integer[]{0, 1, 3, 2};
        for (int i : expected) {
            assertEquals("Two added to two", i, it.next());
        }
        assertEquals("Back element", 2, (int) list.removeFromBack());
        list = new DoubleLinkedList<Integer>();
        list2 = new DoubleLinkedList<Integer>();
        list.append(list2);
        assertEquals("Two empty lists", new DoubleLinkedList<Integer>(), list);
        list2.addToFront(0);
        list.append(list2);
        it = list.iterator();
        assertEquals("First list empty", 0, it.next());
        assertEquals("First list empty front element", 0, (int) list.removeFromFront());
        list = new DoubleLinkedList<Integer>();
        list2 = new DoubleLinkedList<Integer>();
        list.addToFront(0);
        list.append(list2);
        assertEquals("Second list empty", 0, it.next());
        assertEquals("Second list empty front element", 0, (int) list.removeFromFront());
        list = new DoubleLinkedList<Integer>();
        list.addToFront(1);
        list.addToFront(0);
        list2 = new DoubleLinkedList<Integer>();
        list2.addToFront(2);
        list.append(list2);
        it = list.iterator();
        assertEquals("First element", 0, it.next());
        assertEquals("Second element", 1, it.next());
        assertEquals("Third element", 2, it.next());
        assertEquals("Size", 3, list.size());
        list.append(new DoubleLinkedList<>());
        assertEquals("First element", 0, it.next());
        assertEquals("Second element", 1, it.next());
        assertEquals("Third element", 2, it.next());
        it.next(); //throws NullPointerException
    }

    /**
     * Tests the equals method
     */
    @Test
    public void testEquals() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        DoubleLinkedList<Integer> list2 = new DoubleLinkedList<Integer>();
        assertEquals("Two empty lists", list, list2);
        list.addToFront(1);
        list2.addToFront(1);
        assertEquals("One element", list, list2);
        list2.addToFront(1);
        assertFalse("Different lengths", list.equals(list2));
        list.addToFront(1);
        list.addToFront(1);
        list.addToFront(1);
        list2.addToFront(1);
        list2.addToFront(1);
        assertEquals("Many elements", list, list2);
        assertFalse("Wrong type", list.equals(5));
    }

}