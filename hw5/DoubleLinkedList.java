import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked linked list.
 *
 * @author Nikil Pancha
 */
public class DoubleLinkedList<T> implements Iterable<T> {
    /**
     * a reference to the first node of the double linked list
     */
    private DLNode<T> front;

    /**
     * a reference to the last node of a double linked list
     */
    private DLNode<T> back;

    /**
     * a variable to keep track of the size of the list
     */
    private int size = 0;

    /**
     * Create an empty double linked list.
     */
    public DoubleLinkedList() {
        front = back = null;
    }

    /**
     * Returns true if the list is empty.
     *
     * @return true if the list has no nodes
     */
    public boolean isEmpty() {
        return (getFront() == null);
    }

    /**
     * Returns the reference to the first node of the linked list.
     *
     * @return the first node of the linked list
     */
    protected DLNode<T> getFront() {
        return front;
    }

    /**
     * Sets the first node of the linked list.
     *
     * @param node the node that will be the head of the linked list.
     */
    protected void setFront(DLNode<T> node) {
        front = node;
    }

    /**
     * Returns the reference to the last node of the linked list.
     *
     * @return the last node of the linked list
     */
    protected DLNode<T> getBack() {
        return back;
    }

    /**
     * Sets the last node of the linked list.
     *
     * @param node the node that will be the last node of the linked list
     */
    protected void setBack(DLNode<T> node) {
        back = node;
    }

    /**
     * Add an element to the head of the linked list.
     *
     * @param element the element to add to the front of the linked list
     */
    public void addToFront(T element) {
        if (getFront() == null) {
            setFront(new DLNode<T>(element, null, getFront()));
            setBack(getFront());
        } else
            setFront(new DLNode<T>(element, null, getFront()));
        size++;
    }

    /**
     * Add an element to the tail of the linked list.
     *
     * @param element the element to add to the tail of the linked list
     */
    public void addToBack(T element) {
        if (getFront() == null) {
            setBack(new DLNode<T>(element, getBack(), null));
            setFront(getBack());
        } else
            setBack(new DLNode<T>(element, getBack(), null));
        size++;
    }

    /**
     * Remove and return the element at the front of the linked list.
     *
     * @return the element that was at the front of the linked list
     * @throws NoSuchElementException if attempting to remove from an empty list
     */
    public T removeFromFront() throws NoSuchElementException {
        if (getFront() == null)
            throw new NoSuchElementException("The list is empty");
        T element = getFront().getElement();
        setFront(getFront().getNext());
        if (getFront() == null) {
            setBack(null);
        }
        size--;
        return element;
    }

    /**
     * Remove and return the element at the back of the linked list.
     *
     * @return the element that was at the back of the linked list
     * @throws NoSuchElementException if attempting to remove from an empty list
     */
    public T removeFromBack() throws NoSuchElementException {
        if (getBack() == null) {
            throw new NoSuchElementException("The list is empty");
        }
        T element = getBack().getElement();
        setBack(getBack().getPrevious());
        if (getBack() == null) {
            setFront(null);
        }
        size--;
        return element;
    }

    /**
     * Checks for the equality of this list and an object
     *
     * @param obj object to compare to this list
     * @return true if the list and input contain the same elements in the same order, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DoubleLinkedList) || (size() != ((DoubleLinkedList) obj).size())) {
            return false;
        } else {
            ListIterator<T> thisIterator = iterator();
            ListIterator<?> otherIterator = ((DoubleLinkedList) obj).iterator();
            //traverse two lists simultaneously until a difference is found
            while (thisIterator.hasNext() && otherIterator.hasNext()) {
                if (!(thisIterator.next().equals(otherIterator.next()))) {
                    return false;
                }
            }
            //equal size, no differences
            return true;
        }
    }

    /**
     * Appends a DoubleLinkedList to this list
     *
     * @param list list to append
     */
    public void append(DoubleLinkedList<T> list) {
        if (isEmpty()) {
            setFront(list.getFront());
            setBack(list.getBack());
        } else if (!list.isEmpty()) {
            DLNode<T> mergenode = list.getFront();
            mergenode.setPrevious(getBack());
            getBack().setNext(mergenode);
            setBack(list.getBack());
        }
        size += list.size();
    }

    /**
     * Getter method for length of list
     *
     * @return length of list
     */
    public int size() {
        return size;
    }

    /**
     * ListIterator to traverse the list
     *
     * @return ListIterator of this list
     */
    @Override
    public ListIterator<T> iterator() {
        return new ListIterator<T>() {
            DLNode<T> nextptr = getFront(); //pointer to next node
            DLNode<T> prevptr = null; //pointer to previous node
            boolean prevForward = true; //false if the previous step was it.next(), true otherwise
            int index = 0; //index of next element

            /**
             * Checks if iterator has a next item
             *
             * @return true if there is a next item, false otherwise
             */
            @Override
            public boolean hasNext() {
                return nextptr != null;
            }

            /**
             * Returns the next item and moves the pointer one to the right
             *
             * @return the next item in the list
             */
            @Override
            public T next() {
                T element = nextptr.getElement();
                prevptr = nextptr;
                nextptr = nextptr.getNext();
                index++;
                prevForward = true;
                return element;
            }

            /**
             * Boolean to tell whether or not there is a previous item available to return
             *
             * @return true if there is an item before the position of the iterator, false otherwise
             */
            @Override
            public boolean hasPrevious() {
                return prevptr != null;
            }

            /**
             * Gets the previous element and moves the cursor back one space
             *
             * @return the previous element in the list
             */
            @Override
            public T previous() {
                T element = prevptr.getElement();
                nextptr = prevptr;
                prevptr = prevptr.getPrevious();
                index--;
                prevForward = false;
                return element;
            }

            /**
             * Returns the index of the element that would be returned with a call to next
             *
             * @return the index of the element that would be returned with a call to next
             */
            @Override
            public int nextIndex() {
                return index;
            }

            /**
             * Returns the index of the element that would be returned with a call to previous
             *
             * @return the index of the element that would be returned with a call to previous
             */
            @Override
            public int previousIndex() {
                return index - 1;
            }

            /**
             * Removes an element from the list.  Not implemented
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            /**
             * Sets the element that was last returned to the given value
             *
             * @param t the new value of the element
             */
            @Override
            public void set(T t) {
                if (prevptr == null && prevForward) { //no steps have been taken, no previous element to set
                    throw new IllegalStateException();
                }
                DLNode<T> replacement;
                if (prevForward) {
                    replacement = new DLNode<>(t, prevptr.getPrevious(), nextptr);
                    prevptr = replacement;
                } else {
                    replacement = new DLNode<>(t, prevptr, nextptr.getNext());
                    nextptr = replacement;
                }
                if (replacement.getPrevious() == null) {
                    setFront(replacement);
                }
                if (replacement.getNext() == null) {
                    setBack(replacement);
                }
            }

            /**
             * Adds an element to the list.  Not implemented
             * @param t element to add
             */
            @Override
            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }
}