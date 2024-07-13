package twitterclone;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import login.LoginServlet;
import models.Chirp;
import models.ChirpModel;
import models.User;
import models.UserModel;

/**
 *
 * @author jadel
 */
@WebServlet(name = "UserPage", urlPatterns = {"/user/*"})
public class UserPage extends HttpServlet {

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
            HttpSession session = request.getSession();

//            String username = (String) session.getAttribute("targetUsername");

            String requestUri = request.getRequestURI();

            String[] uriParts = requestUri.split("/");

            String username = uriParts[uriParts.length - 1];

            int currentUserId = (int) session.getAttribute("currentUserId");

            String action = request.getParameter("action");

            String src = request.getParameter("src");

            String chirpIdString = request.getParameter("chirpId");

            User userPage = UserModel.findUser(username, currentUserId);

            if (action != null && action.equalsIgnoreCase("follow")) {
                UserModel.addFollow(currentUserId, userPage.getId());

                response.sendRedirect(username);

                return;
            }

            if (action != null && action.equalsIgnoreCase("unfollow")) {
                UserModel.deleteFollow(currentUserId, userPage.getId());

                response.sendRedirect(username);

                return;
            }

            if (action != null && action.equalsIgnoreCase("like")) {
                int chirpId = Integer.parseInt(chirpIdString);
                ChirpModel.addLike(chirpId, currentUserId);

                if (src != null) {
                    if (src.equalsIgnoreCase("feed")) {
                        response.sendRedirect("/TwitterClone/feed");

                    } else if (src.equalsIgnoreCase("userPage")) {
                        response.sendRedirect(username);
                    }
                }

                return;
            }

            if (action != null && action.equalsIgnoreCase("unlike")) {
                int chirpId = Integer.parseInt(chirpIdString);
                ChirpModel.removeLike(chirpId, currentUserId);

                if (src != null) {
                    if (src.equalsIgnoreCase("feed")) {
                        response.sendRedirect("/TwitterClone/feed");

                    } else if (src.equalsIgnoreCase("userPage")) {
                        response.sendRedirect(username);
                    }
                }

                return;
            }

            if (userPage != null) {
                request.setAttribute("userPage", userPage);

                ArrayList<Chirp> userPageChirps = ChirpModel.getUserChirps(currentUserId, userPage.getId());
                request.setAttribute("userPageChirps", userPageChirps);

                String url = "/UserPage.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);

            } else {
                request.setAttribute("error", "User not found.");

                String url = "/Error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }

        } else {
            response.sendRedirect("/TwitterClone/login");
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
