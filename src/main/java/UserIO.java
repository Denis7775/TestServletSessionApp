import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserIO {

    static Map<String, User> map = new HashMap<>();

    public static User getUser(String emailAddress) {
        return map.get(emailAddress);
    }

    public static void add(String emailAddress, User user) {
        map.put(emailAddress, user);
    }

}
