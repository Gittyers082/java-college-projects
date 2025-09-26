package IT212prelims_LabProject;

import java.util.NoSuchElementException;

// Custom exception for list overflow
class ListOverflowException extends Exception {
    public ListOverflowException(String message) {
        super(message);
    }
}

// MyList interface
interface MyList<E> {
    public int getSize();
    public void insert(E data) throws ListOverflowException;
    public E getElement(E data) throws NoSuchElementException;
    public boolean delete(E data); // Returns false if the data is not deleted in the list.
    public int search(E data); // Returns index of data, else -1 is returned.
}

// Node class for LinkedList implementation
class Node<E> {
    E data;
    Node<E> next;

    public Node(E data) {
        this.data = data;
        this.next = null;
    }
}

// LinkedList implementation of MyList
class MyLinkedList<E> implements MyList<E> {
    private Node<E> head;
    private int size;
    private final int maxSize; // Optional: set maximum size limit

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
        this.maxSize = Integer.MAX_VALUE; // No limit by default
    }

    public MyLinkedList(int maxSize) {
        this.head = null;
        this.size = 0;
        this.maxSize = maxSize;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void insert(E data) throws ListOverflowException {
        if (size >= maxSize) {
            throw new ListOverflowException("List has reached maximum capacity of " + maxSize);
        }

        Node<E> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
        } else {
            // Insert at the end
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public E getElement(E data) throws NoSuchElementException {
        Node<E> current = head;

        while (current != null) {
            if (current.data != null && current.data.equals(data)) {
                return current.data;
            }
            current = current.next;
        }

        throw new NoSuchElementException("Element not found: " + data);
    }

    @Override
    public boolean delete(E data) {
        if (head == null) {
            return false;
        }

        // If head node contains the data
        if (head.data != null && head.data.equals(data)) {
            head = head.next;
            size--;
            return true;
        }

        // Search for the node to delete
        Node<E> current = head;
        while (current.next != null) {
            if (current.next.data != null && current.next.data.equals(data)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    @Override
    public int search(E data) {
        Node<E> current = head;
        int index = 0;

        while (current != null) {
            if (current.data != null && current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1; // Not found
    }

    // Additional helper methods
    public E getElementAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Get all elements as array (for display purposes)
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<E> current = head;
        int index = 0;

        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }

        return array;
    }

    // Clear all elements
    public void clear() {
        head = null;
        size = 0;
    }
}

// Data class for storing key-value pairs (to replace HashMap functionality)
class KeyValuePair {
    private String key;
    private String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        KeyValuePair that = (KeyValuePair) obj;
        return key != null ? key.equals(that.key) : that.key == null;
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}

// Custom Map implementation using MyLinkedList
class MyMap {
    private MyLinkedList<KeyValuePair> list;

    public MyMap() {
        this.list = new MyLinkedList<>();
    }

    public void put(String key, String value) {
        try {
            // Check if key already exists
            KeyValuePair searchPair = new KeyValuePair(key, null);
            int index = list.search(searchPair);

            if (index != -1) {
                // Key exists, update value
                list.delete(searchPair);
                list.insert(new KeyValuePair(key, value));
            } else {
                // New key, insert
                list.insert(new KeyValuePair(key, value));
            }
        } catch (ListOverflowException e) {
            System.err.println("Cannot add more items to the map: " + e.getMessage());
        }
    }

    public String get(String key) {
        try {
            KeyValuePair searchPair = new KeyValuePair(key, null);
            KeyValuePair found = list.getElement(searchPair);
            return found.getValue();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean remove(String key) {
        KeyValuePair searchPair = new KeyValuePair(key, null);
        return list.delete(searchPair);
    }

    public boolean containsKey(String key) {
        KeyValuePair searchPair = new KeyValuePair(key, null);
        return list.search(searchPair) != -1;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.getSize();
    }

    public Object[] entrySet() {
        return list.toArray();
    }

    public void clear() {
        list.clear();
    }
}
