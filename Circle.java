package sample;

public class Circle {
    private int count=0;
    private Person last;
    private Person first;

    Circle()
    {

    }

    public int getCount() {
        return count;
    }

    public Person getFirst() {
        return first;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "count=" + count +
                ", first=" + first +
                ", last=" + last +
                '}';
    }

    public void add(Person person)
    {
        count++;
        if(count==1)
        {
            person.setNext(null);
            person.setPrevious(null);
            last=person;
            first=person;
        }
        if(count>1)
        {
            person.setNext(first);
            person.setPrevious(last);
            person.getPrevious().setNext(person);
            person.getNext().setPrevious(person);
            last=person;
        }
    }

    public void pop(Person person)
    {
        count--;
        if(count>1)
        {
            person.getNext().setPrevious(person.getPrevious());
            person.getPrevious().setNext(person.getNext());
            if(last==person)
            {
                last=person.getPrevious();
            }
            if(first==person)
            {
                first=person.getNext();
            }
        }
        else{
            person.setPrevious(null);
            person.setNext(null);
            last=person;
            first=person;
        }
    }

    public void empty()
    {
        if(count!=0){
        for (int i = 0; i <count; i++) {
            pop(getFirst());
        }
        count=0;
        }
    }

    public Person find(Person person, int number)
    {
        if(number==0)
        {
            return person;
        }
        else return find(person.getNext(), number-1);
    }
}
