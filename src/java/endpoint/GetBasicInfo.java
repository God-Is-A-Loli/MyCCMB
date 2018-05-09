package endpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import database.DBAccess;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import patientinfo.BasicInfo;
import java.sql.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.*;

/**
 *
 * @author student
 */
public class GetBasicInfo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String sql = "SELECT * FROM BasicInfo";
    
    private String getBasicInfoJSON(String userID, DBAccess db) {
        String basicInfoJSON = null;
        
        try 
        {
            ResultSet rs = db.executeQuery(sql);

            ObjectMapper mapper = new ObjectMapper();
            BasicInfo basicInfo = null;
            
            while(rs.next()) {
                if(rs.getString("email").equals(userID)) {
                    basicInfo = new BasicInfo(rs.getString("first_name"), 
                                              rs.getString("last_name"),
                                              rs.getString("gender"),
                                              rs.getString("date_of_birth"),
                                              rs.getString("phone"), 
                                              rs.getString("email"));
                    break;
                }
            }
            
            if(basicInfo != null)
                basicInfoJSON = mapper.writeValueAsString(basicInfo);
        } catch (SQLException e) {
            System.err.println("database error: " + e.getMessage());
        } catch (JsonProcessingException e) {
            System.err.println("Json error: " + e.getMessage());
        }
        
        return basicInfoJSON;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(!request.isRequestedSessionIdValid()) {
            response.sendError(403);
            
            return;
        }
        
        HttpSession session = request.getSession();
        String userID = (String)session.getAttribute("userID");
        DBAccess db = (DBAccess)session.getAttribute("db");
        String JSONBody = getBasicInfoJSON(userID, db);
        PrintWriter bodyWriter = response.getWriter();

        bodyWriter.print(JSONBody);
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