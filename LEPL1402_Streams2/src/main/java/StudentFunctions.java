import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StudentFunctions implements StudentStreamFunction {

  public Stream<Student> matching (Stream<Student> studentsStream, Map<String, Predicate<?>> conditions){
    for (Map.Entry<String, Predicate<?>> condition : conditions.entrySet()) {
      String criterion = condition.getKey();
      switch (criterion){
        case "firstName":
          Predicate<String> p1 = (Predicate<String>) condition.getValue();
          studentsStream = studentsStream.filter( student -> p1.test(student.getFirstName()) ); break;
        case "lastName":
          Predicate<String> p2 = (Predicate<String>) condition.getValue();
          studentsStream = studentsStream.filter( student -> p2.test(student.getLastName()) ); break;
        case "section":
          Predicate<Integer> p3 = (Predicate<Integer>) condition.getValue();
          studentsStream = studentsStream.filter( student -> p3.test(student.getSection()) ); break;
        case "courses_results":
          Predicate<Map<String, Double>> s1 = (Predicate<Map<String, Double>>) condition.getValue();
          studentsStream = studentsStream.filter( student -> s1.test(student.getCourses_results())); break;
        default: break;
      }
    }
    return studentsStream;
  }

  public Student findFirst(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions){
    return matching(studentsStream, conditions).findFirst().orElse(null);
  }

  public Student[] findAll(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions){
    return matching(studentsStream, conditions).toArray(Student[]::new);
  }

  public boolean exists(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions, int n) {
    return matching(studentsStream, conditions).count() >= n;
  }

  public Student[] filterThenSort(Stream<Student> studentsStream, Map<String, Predicate<?>> conditions, Comparator<Student> comparator)
  {
    return matching(studentsStream, conditions).sorted(comparator).toArray(Student[]::new);
  }

  public static void main(String[] args) throws NoSuchFieldException {
    HashMap<String, Predicate<?>> test = new HashMap<>();
    Predicate<String> fn = str -> str.equals("Robert");
    test.put("firstName", fn);

    HashMap<String, Double> robertResults = new HashMap<>();
    Student Robert = new Student("Robert", "Larousse", 0, robertResults);

    Stream<Student> studentStream = Stream.of(Robert);
    StudentFunctions toTest = new StudentFunctions();

    System.out.println(toTest.exists(studentStream, test, 1));
  }
}
