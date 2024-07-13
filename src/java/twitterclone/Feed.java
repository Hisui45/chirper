package twitterclone;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import login.LoginServlet;
import models.Chirp;
import models.ChirpModel;
import models.User;
import models.UserModel;
import util.HashingUtil;

/**
 *
 * @author jadel
 */
@WebServlet(name = "Feed", urlPatterns = {"/feed"})
@MultipartConfig(maxFileSize = 16177215)
public class Feed extends HttpServlet {

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
            Integer currentUserId = (Integer) session.getAttribute("currentUserId");
            User currentUser = UserModel.getUser(currentUserId);
            request.setAttribute("user", currentUser);

            ArrayList<Chirp> chirps = ChirpModel.getFeedChirps(currentUserId);
            request.setAttribute("chirps", chirps);

            String action = request.getParameter("action");

            String src = request.getParameter("src");

            String usernameParam = request.getParameter("username");

            if (action == null && usernameParam == null) {

                String url = "/Feed.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } else if (action != null && action.equalsIgnoreCase("chirp")) {
                String text = request.getParameter("chirpText");

                //https://www.codejava.net/coding/upload-files-to-database-servlet-jsp-mysql
                InputStream inputStream = null; // input stream of the upload file
                String filename = null;

                // obtains the upload file part in this multipart request
                Part filePart = request.getPart("chirpImg");
                if (filePart != null) {
                    filename = HashingUtil.extractFileName(filePart);
                    inputStream = filePart.getInputStream();
                }

                ChirpModel.addChirp(new Chirp(text, currentUser), inputStream, filename);
                
                if (src != null) {
                    if (src.equalsIgnoreCase("feed")) {
                        response.sendRedirect("feed");

                    } else if (src.equalsIgnoreCase("userPage")) {
                        response.sendRedirect("/TwitterClone/user/"+usernameParam);
                    }
                }

            } else if (usernameParam != null) {
                session.setAttribute("targetUsername", usernameParam);
                response.sendRedirect("user/" + usernameParam);
            }
        } else {
            response.sendRedirect("login");
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
