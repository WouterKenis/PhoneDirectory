import java.util.ArrayList;
import java.util.List;

public class Contact {

    private String name;
    private List<String> telefoonNummers;

    public Contact(String name) {
        this.name = name;
        this.telefoonNummers = new ArrayList<>();
    }
    public Contact(String name, List<String> telefoonNummers) {
        this.name = name;
        this.telefoonNummers = new ArrayList<>();
        this.telefoonNummers = telefoonNummers;

    }

    public void addNumber(String number) {
        telefoonNummers.add(number);
    }

    public String getName() {
        return name;
    }

    public List<String> getTelefoonNummers() {
        return telefoonNummers;
    }
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(name + " ");

        for (String s : telefoonNummers) {
            sb.append(s + " - ");
        }
        return sb.toString().substring(0, sb.toString().length() - 3);
    }
}