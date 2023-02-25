package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShoppingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        if (session.getAttribute("username") != null) {
            if (action != null) {
                if (action.equals("logout")) {
                    getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                    return;
                }
            }
            
            ArrayList<String> arr = (ArrayList<String>) session.getAttribute("list");
            String itemList = "";
            for (String item : arr) {
                itemList += "<li><input type='radio' name='item' value='apples'>" + item + "</li>";
            }
            session.setAttribute("itemList", itemList);
            
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        String username = request.getParameter("username");
        
        if (action.equals("register")) {
            if (username != null && !username.equals("")) {
                session.setAttribute("username", username);
                
                getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Please enter a username.");
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            }
            return;
        }
        
        if (action.equals("add")) {
            String item = request.getParameter("item");
            if (item != null && !item.equals("")) {
                ArrayList<String> arr = (ArrayList<String>) session.getAttribute("list");
                String itemList = "";
            
                for (String i : arr) {
                    itemList += "<li><input type='radio' name='item' value='apples'>" + i + "</li>";
                }
                session.setAttribute("itemList", itemList);
            }
            
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
            return;
        }
        
        if (action.equals("delete")) {
                
        }
        
        if (action.equals("logout")) {
            session.invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

}
