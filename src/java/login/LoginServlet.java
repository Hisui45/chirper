package login;

import models.UserModel;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.HashingUtil;
import static util.HashingUtil.getSHA;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

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
            String action = request.getParameter("action");

            if (action != null && action.equalsIgnoreCase("logout")) {
                HttpSession session = request.getSession();
                
                session.removeAttribute("currentUserId");
                
                response.sendRedirect("/TwitterClone/");
            } else {
                response.sendRedirect("/TwitterClone/feed");

            }

        } else {
            String action = request.getParameter("action");

            if (action == null) {
                String url = "/Login.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } else if (action.equalsIgnoreCase("login")) {

                String email_address = request.getParameter("email_address");
                String password = request.getParameter("password");

                try {
                    String hashedPassword = HashingUtil.toHexString(getSHA(password));

                    int id = UserModel.findUser(email_address, hashedPassword);

                    if (id == 0) {
                        request.setAttribute("loginError", "Incorrect email or password.");

                        String url = "/Login.jsp";
                        getServletContext().getRequestDispatcher(url).forward(request, response);
                    } else {
                        HttpSession session = request.getSession();

                        session.setAttribute("currentUserId", id);
                        response.sendRedirect("feed");
                    }

                } catch (NoSuchAlgorithmException ex) {
                    System.out.println(ex);
                }

            }

        }

    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Integer currentUserId = (Integer) session.getAttribute("currentUserId");

        return currentUserId != null;
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
