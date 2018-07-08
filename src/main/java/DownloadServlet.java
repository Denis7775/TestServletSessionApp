import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * смысл кукис: куки это hashmap, нужен для того чтобы ассоциировать клиента с компьютером и тд, чтобы
 * при последующем входе узнать клиента, достать инфу о нем и кука или откуда то еще и вместо клиента ввести его данные
 * Так что getSession() можем работаеть и без прямого создания класса Cookie, оббъект session
 * все это делает внутри себя, сессия ассоциирует клиента с запросом(request), так что передаем его по всему приложению
 */

@WebServlet(urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
/*        if (req.getParameter("delete") != null)
            deleteCookies(req, resp);*/

        if (action == null)
            action = "viewAlbums";

        String url = "/WEB-INF/views/index.jsp";

        if (action.equals("viewAlbums"))
            url = "/WEB-INF/views/index.jsp";
        else if (action.equals("checkUser"))
            url = checkUser(req, resp);

        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String url = "/WEB-INF/views/index.jsp";
        if (action.equals("registerUser"))
            url = registerUser(req, resp);



        getServletContext().getRequestDispatcher(url).forward(req, resp);
    }

    private String checkUser(HttpServletRequest request, HttpServletResponse response) {
        String productCode = request.getParameter("productCode");
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(10);
        session.setAttribute("productCode", productCode);
        User user = (User) session.getAttribute("user");

        String url;

        if (user == null) {
            url = "/WEB-INF/views/register.jsp";
/*            Cookie[] cookies = request.getCookies();
            String emailAddress = CookieUtil.getCookieValue(cookies, "emailCookie");
            if (emailAddress == null || emailAddress.equals(""))
                url = "/WEB-INF/views/register.jsp";
            else {
                ServletContext sc = getServletContext();
                //String path = sc.getRealPath("/WEB-INF/EmailList.txt");
                user = UserIO.getUser(emailAddress);
                session.setAttribute("user", user);
                url = "/WEB-INF/views/soundtracks/" + productCode + "_" + "download.jsp";
            }*/
        }
        else
            url = "/WEB-INF/views/soundtracks/" + productCode + "_" + "download.jsp";
        return url;
    }

    private String registerUser(HttpServletRequest request, HttpServletResponse response) {

        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        User user = new User(email, firstName, lastName);

        ServletContext sc = getServletContext();
        //String path = sc.getRealPath("/WEB-INF/EmailList.txt");
        UserIO.add(email, user);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

/*        Cookie c = new Cookie("emailCookie", email);
        //c.setMaxAge(3600*24*365*2);
        //c.setMaxAge(0); ///////////////////////////////cookie doesn't save because of age
        c.setMaxAge(3600*24*365*2);
        c.setPath("/");
        response.addCookie(c);*/

        String productCode = (String) session.getAttribute("productCode");
        String url = "/WEB-INF/views/soundtracks/" + productCode + "_download.jsp";
        return url;
    }
}
