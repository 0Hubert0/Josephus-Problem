package sample;

public class Person {
    private Person next;
    private Person previous;
    private final int X;
    private final int Y;

    Person(int X, int Y)
    {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Person getNext() {
        return next;
    }

    public void setNext(Person next) {
        this.next = next;
    }

    public Person getPrevious() {
        return previous;
    }

    public void setPrevious(Person previous) {
        this.previous = previous;
    }
}
