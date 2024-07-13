/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package twitterclone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import models.ChirpModel;
import models.DatabaseConnection;
import models.User;
import models.UserModel;
import util.HashingUtil;

/**
 *
 * @author jadel
 */
@WebServlet(name = "GetImage")
@MultipartConfig(maxFileSize = 16177215)
public class GetImage extends HttpServlet {

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

        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("display")) {
            System.out.println("ow");

            //https://www.codejava.net/coding/upload-files-to-database-servlet-jsp-mysql
            //https://www.geeksforgeeks.org/how-to-convert-inputstream-to-byte-array-in-java/#
            String filename = null;

            Part filePart = request.getPart("chirpImg");

            if (filePart != null) {
                filename = HashingUtil.extractFileName(filePart);

                String contentType = this.getServletContext().getMimeType(filename);

                response.setContentType(contentType);

                try ( InputStream inputStream = filePart.getInputStream();  OutputStream os = response.getOutputStream();) {
                    int byteRead;
                    byte[] buffer = new byte[4024];
                    while ((byteRead = inputStream.read(buffer))
                            != -1) {
                        os.write(buffer, 0,
                                byteRead);
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }

        } else {
            int id = Integer.parseInt(request.getParameter("chirpId"));
            try {
                Connection connection = DatabaseConnection.getConnection();

                String query = "SELECT image, filename from chirp WHERE id = ?";

                PreparedStatement statement = connection.prepareStatement(query);

                statement.setInt(1, id);

                ResultSet results = statement.executeQuery();
                Blob blob = null;
                String filename = "";

                while (results.next()) {
                    blob = results.getBlob("image");
                    filename = results.getString("filename");

                    if (blob != null) {
                        byte[] imageBytes = blob.getBytes(1, (int) blob.length());

                        String contentType = this.getServletContext().getMimeType(filename);

                        response.setHeader("Content-type", contentType);

                        try {
                            OutputStream os = response.getOutputStream();
                            os.write(imageBytes);
                            os.flush();
                            os.close();
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }

                }

                results.close();
                statement.close();
                connection.close();
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println(ex);
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
