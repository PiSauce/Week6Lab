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
                    request.setAttribute("message", "Successfully logged out.");
                    getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                    return;
                }
            }
            
            ArrayList<String> arr = (ArrayList<String>) session.getAttribute("list");
            if (arr != null) {
                String itemList = "";
                for (String item : arr) {
                    itemList += "<li><input type='radio' name='item' value='apples'>" + item + "</li>";
                }
                session.setAttribute("itemList", itemList);
            }
            
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
            // Initialize variables
            String item = request.getParameter("item");
            ArrayList<String> arr = (ArrayList<String>) session.getAttribute("list");
            String itemList = "";
            
            // Check if shopping list is empty
            if (arr == null) {
                arr = new ArrayList<>();
            }
            
            // Add item to shopping list
            if (item != null && !item.equals("")) {
                arr.add(item);
            }
            
            // Create unordered list
            for (String i : arr) {
                itemList += "<li><input type='radio' name='item' value='" + i + "'>" + i + "</li>";
            }
            
            // Update attributes
            request.setAttribute("itemList", itemList);
            session.setAttribute("list", arr);
            
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
            return;
        }
        
        if (action.equals("delete")) {
            // Initialize variables
            String item = request.getParameter("item");
            ArrayList<String> arr = (ArrayList<String>) session.getAttribute("list");
            String itemList = "";
            
            // Check if shopping list is empty
            if (arr == null) {
                getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
                return;
            }
            
            // Remove item from shopping list
            if (item != null && !item.equals("")) {
                arr.remove(item);
            }
            
            // Create unordered list
            for (String i : arr) {
                itemList += "<li><input type='radio' name='item' value='" + i + "'>" + i + "</li>";
            }
            
            // Update attributes
            request.setAttribute("itemList", itemList);
            session.setAttribute("list", arr);
            
            getServletContext().getRequestDispatcher("/WEB-INF/shoppingList.jsp").forward(request, response);
            return;
        }
        
        if (action.equals("logout")) {
            session.invalidate();
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

}
