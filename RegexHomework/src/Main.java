import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        String input = """
                John Doe, 28 years old, john.doe@example.com
                Emily Smith, 34 years old, emily.smith@example.net
                Michael Brown, 45 years old, michael.brown@example.org
                Sarah Johnson, 22 years old, sarah.johnson@example.com
                David Williams, 30 years old, david.williams@example.net""";

        List<Person> people = parsePeople(input);

        for (Person person : people) {
            System.out.println(person.getEmail());
        }
    }

    private static List<Person> parsePeople(String input) {
        List<Person> people = new ArrayList<>();

        String regex = "([a-zA-Z]+)\\s+([a-zA-Z]+),\\s+(\\d+)\\s+years\\s+old,\\s+([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String firstName = matcher.group(1);
            String lastName = matcher.group(2);
            int age = Integer.parseInt(matcher.group(3));
            String email = matcher.group(4);

            people.add(new Person(firstName, lastName, email, age));
        }

        return people;
    }
}
