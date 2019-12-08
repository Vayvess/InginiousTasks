import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StudentFunctions implements StudentStreamFunction {

  public Stream<Student> findSecondAndThirdTopStudentForGivenCourse(Stream<Student> studentStream, String name){
    return studentStream
            .filter(student -> student.getCoursesResults().containsKey(name))
            .sorted((a, b) -> Double.compare(0.0, a.getCoursesResults().get(name) - b.getCoursesResults().get(name)))
            .skip(1)
            .limit(2);
  }

  public Object[] computeAverageForStudentInSection(Stream<Student> studentStream, int section){
    return studentStream.filter(student -> student.getSection() == section)
            .map(s -> new Object[] {"Student " + s.getFirstName() + " " + s.getLastName(),
                    s.getCoursesResults().values().stream()
                            .reduce(Double::sum).orElse(0.0) / s.getCoursesResults().size()
            }).toArray();
  }

  public int getNumberOfSuccessfulStudents(Stream<Student> studentStream){
    return (int) studentStream.filter(s -> s.getCoursesResults().values().stream().allMatch(v -> v > 10.0)).count();
  }

  public Student findLastInLexicographicOrder(Stream<Student> studentStream){
    return studentStream.max(Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)).orElse(null);
  }

  public double getFullSum(Stream<Student> studentStream){
    /* Credit to Samy Bettaieb, because this didn't work xD
    return studentStream.mapToDouble(s -> s.getCoursesResults().values().stream().reduce(0.0, Double::sum)).sum(); */
    Stream<Double> total =
            studentStream.map(student ->
                    student.getCoursesResults().values().stream().reduce(0.0, Double::sum));

    return total.reduce(0.0, Double::sum);
  }
}
