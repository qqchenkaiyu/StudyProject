import Spring.ApplicaionContext;
import domain.Person;

public class Starter {
    public static void main(String[] args) {
        ApplicaionContext context=new ApplicaionContext("beans.setting");
        Person person=context.getBean(Person.class);
    }
}
