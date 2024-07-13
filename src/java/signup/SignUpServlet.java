package signup;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import login.LoginServlet;
import models.User;
import models.UserModel;
import static util.HashingUtil.getSHA;
import static util.HashingUtil.toHexString;

@WebServlet(name = "SignUpServlet", urlPatterns = {"/sign-up"})
public class SignUpServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (LoginServlet.isLoggedIn(request)) {
            response.sendRedirect("/TwitterClone/feed");
        } else {
            String action = request.getParameter("action");

            if (action == null) {
                String url = "/CreateAccount.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } else if (action.equalsIgnoreCase("sign-up")) {

                String name = request.getParameter("name");
                String username = request.getParameter("username");
                String email_address = request.getParameter("email_address");
                String password = request.getParameter("password");

                try {

                    if (UserModel.findUser(username) == null) {
                        String hashedPassword = toHexString(getSHA(password));
                        User user = new User(name, username, email_address, hashedPassword);

                        UserModel.addUser(user);

                        int userId = UserModel.findUser(user.getEmailAddress(), user.getPasswordHash());

                        HttpSession session = request.getSession();

                        session.setAttribute("currentUserId", userId);
                        response.sendRedirect("feed");
                    } else {
                        request.setAttribute("usernameTaken", "Username is taken.");

                        String url = "/CreateAccount.jsp";
                        getServletContext().getRequestDispatcher(url).forward(request, response);
                    }

                } catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
