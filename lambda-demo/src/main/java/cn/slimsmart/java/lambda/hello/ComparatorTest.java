package cn.slimsmart.java.lambda.hello;

import java.util.Comparator;

/**
 * Created by Administrator on 2018/1/7.
 */
public class ComparatorTest {

    public static void main(String[] args) {
        Comparator<Person> comparator = (p1, p2)->p1.firstName.compareTo(p2.firstName);
        Person p1 = new Person("aa","bb");
        Person p2 = new Person("bb","aa");
        int compare = comparator.compare(p2, p1);
        System.out.println(compare);
        compare = comparator.reversed().compare(p1, p2);
        System.out.println(compare);
    }


   public static class Person{
        private String firstName;
        private String lastName;

        public Person(String firstName,String lastName){
            this.firstName = firstName;
            this.lastName= lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

       @Override
       public String toString() {
           return "Person{" +
                   "firstName='" + firstName + '\'' +
                   ", lastName='" + lastName + '\'' +
                   '}';
       }
   }
}
