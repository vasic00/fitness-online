package etf.ip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import etf.ip.model.AccountStatus;
import etf.ip.model.Admin;
import etf.ip.model.Advisor;
import etf.ip.model.CategoryDTO;
import etf.ip.model.SpecificAttributeDTO;
import etf.ip.model.UserDTO;
import etf.ip.services.AdminService;
import etf.ip.services.AdvisorService;
import etf.ip.services.CategoryService;
import etf.ip.services.SpecificAttributeService;
import etf.ip.services.StatisticsService;
import etf.ip.services.UserService;

@WebServlet("/Controller")
public class Controller extends HttpServlet{

	private final AdminService adminService = new AdminService();
	private final CategoryService categoryService = new CategoryService();
	private final UserService userService = new UserService();
	private final SpecificAttributeService specificAttributeService = new SpecificAttributeService();
	private final StatisticsService statisticsService = new StatisticsService();
	private final AdvisorService advisorService = new AdvisorService();
	/**
	 * 
	 */
	private static final long serialVersionUID = 2284770513667471955L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Admin admin = (Admin)session.getAttribute("admin");
		setActive(null);
		String address = "";
		String notification = "";
		String action = req.getParameter("action");
		if (action == null || "".equals(action) || (!"login".equals(action) && admin == null)) {
			address = "WEB-INF/admin_login.jsp";
		}
		else {
			if ("login".equals(action)) {
				String username = req.getParameter("username");
				String password = req.getParameter("password");
				if (username == null || password == null || "".equals(username) || "".equals(password)) {
					notification = "Please provide all necessary credentials.";
					address = "WEB-INF/admin_login.jsp";
				}
				else {
					admin = adminService.login(username, password);
					if (admin == null)
					{
						notification = "Invalid credentials.";
						address = "WEB-INF/admin_login.jsp";
					}
					else {
						session.setAttribute("admin", admin);
						address = "WEB-INF/admin_homepage.jsp";
					}
				}
			}
			else if ("homepage".equals(action)) {
				address="WEB-INF/admin_homepage.jsp";
			}
			else if ("categories".equals(action)) {
				List<CategoryDTO> categories = categoryService.getAll();
				if (categories != null) {
					req.setAttribute("categories", categories);
					address = "WEB-INF/categories.jsp";
					setActive("categoriesButton");
				}
				else {
					notification = "Error fetching categories.";
					address = "WEB-INF/admin_homepage.jsp";
				}
			}
			else if ("users".equals(action)) {
				List<UserDTO> users = userService.getAll();
				if (users != null) {
					req.setAttribute("users", users);
					address = "WEB-INF/users.jsp";
					setActive("usersButton");
				}
				else {
					notification = "Error fetching users.";
					address = "WEB-INF/admin_homepage.jsp";
				}
			}
			else if ("category".equals(action)) {
				CategoryDTO category = categoryService.findByName(req.getParameter("category"));
				if (category != null) {
					req.setAttribute("category", category);
					address = "WEB-INF/category.jsp";
				}
				else {
					notification = "Error fetching category.";
					req.setAttribute("categories", categoryService.getAll());
					address = "WEB-INF/categories.jsp";
				}
				setActive("categoriesButton");
			}
			else if ("updateCategory".equals(action)) {
				CategoryDTO category = categoryService.findByName(req.getParameter("category"));
				if (category != null) {
					for (SpecificAttributeDTO sa : category.getSpecificAttributes()) {
						sa.setName(req.getParameter(String.valueOf(sa.getId())));
						specificAttributeService.update(sa);
					}
					category.setName(req.getParameter("categoryName"));
					Object ob = categoryService.update(category);
					if (ob == null)
						notification = "Category with that name already exists.";
					address = "WEB-INF/category.jsp";
					req.setAttribute("category", category);
				}
				else {
					notification = "Cannot update category.";
					address = "WEB-INF/categories.jsp";
					req.setAttribute("categories", categoryService.getAll());
				}
				setActive("categoriesButton");
			}
			else if ("deleteAttribute".equals(action)) {
				boolean flag = specificAttributeService.delete(Long.parseLong(req.getParameter("attribute")));
				CategoryDTO category = categoryService.findByName(req.getParameter("category"));
				address = "WEB-INF/category.jsp";
				req.setAttribute("category", category);
				if (!flag) {
					notification = "Cannnot find attribute.";
				}
				setActive("categoriesButton");
			}
			else if ("addAttribute".equals(action)) {
				SpecificAttributeDTO saDTO = new SpecificAttributeDTO();
				saDTO.setName(req.getParameter("newAttribute"));
				saDTO.setCategoryId(Long.parseLong(req.getParameter("categoryId")));
				Object ob = specificAttributeService.add(saDTO);
				address = "WEB-INF/category.jsp";
				req.setAttribute("category", categoryService.findByName(req.getParameter("categoryName")));
				if (ob == null) {
					notification = "Cannot create new attribute.";
				}
				setActive("categoriesButton");
			}
			else if ("addCategory".equals(action)) {
				CategoryDTO category = new CategoryDTO();
				category.setName(req.getParameter("category"));
				Object ob = categoryService.add(category);
				List<CategoryDTO> categories = categoryService.getAll();
				req.setAttribute("categories", categories);
				address = "WEB-INF/categories.jsp";
				if (ob == null) {
					notification = "Category with that name already exists.";
				}
				setActive("categoriesButton");
			}
			else if ("deleteCategory".equals(action)) {
				boolean flag = categoryService.delete(Long.parseLong(req.getParameter("category")));
				address = "WEB-INF/categories.jsp";
				req.setAttribute("categories", categoryService.getAll());
				if (!flag) {
					notification = "Cannot delete category.";
				}
				setActive("categoriesButton");
			}
			else if ("addUser".equals(action)) {
				UserDTO user = new UserDTO();
				user.setFirstname(req.getParameter("firstname"));
				user.setLastname(req.getParameter("lastname"));
				user.setUsername(req.getParameter("username"));
				user.setPassword(req.getParameter("password"));
				user.setEmail(req.getParameter("email"));
				user.setCity(req.getParameter("city"));
				Object ob = userService.register(user);
				if (ob == null) {
					notification = "User with that username already exists.";
				}
				List<UserDTO> users = userService.getAll();
				req.setAttribute("users", users);
				address="WEB-INF/users.jsp";
				setActive("usersButton");
			}
			else if ("deleteUser".equals(action)) {
				boolean flag = userService.delete(Long.parseLong(req.getParameter("user")));
				address = "WEB-INF/users.jsp";
				req.setAttribute("users", userService.getAll());
				if (!flag) {
					notification = "Cannot delete user.";
				}
				setActive("usersButton");
			}
			else if ("changeStatus".equals(action)) {
				UserDTO user = userService.get(Long.parseLong(req.getParameter("user")));
				if (user != null) {
					if (user.getStatus().equals(AccountStatus.BLOCKED) || user.getStatus().equals(AccountStatus.NOT_ACTIVATED)) {
						user.setStatus(AccountStatus.ACTIVATED);
					}
					else {
						user.setStatus(AccountStatus.BLOCKED);
					}
					if (userService.update(user) == null)
						notification = "Cannot change user's status.";
				}
				else notification = "Cannot find user.";
				address = "WEB-INF/users.jsp";
				req.setAttribute("users", userService.getAll());
				setActive("usersButton");
			}
			else if ("user".equals(action)) {
				UserDTO user = userService.get(Long.parseLong(req.getParameter("user")));
				if (user == null) {
					address = "WEB-INF/users.jsp";
					req.setAttribute("users", userService.getAll());
					notification = "Error fetching user.";
				}
				else {
					address = "WEB-INF/user.jsp";
					req.setAttribute("user", user);
				}
				setActive("usersButton");
			}
			else if ("updateUser".equals(action)) {
				UserDTO user = userService.get(Long.parseLong(req.getParameter("user")));
				if (user != null) {
					user.setFirstname(req.getParameter("firstname"));
					user.setLastname(req.getParameter("lastname"));
					user.setUsername(req.getParameter("username"));
					String password = req.getParameter("password");
					if (password != null && password != "")
						user.setPassword(password);
					user.setEmail(req.getParameter("email"));
					user.setCity(req.getParameter("city"));
					UserDTO ob = userService.update(user);
					address = "WEB-INF/user.jsp";
					if (ob == null) {
						notification = "User with that username already exists.";
						req.setAttribute("user", userService.get(Long.parseLong(req.getParameter("user"))));
					}
					else {
						req.setAttribute("user", ob);
					}
				}
				else {
					notification = "Error fetching user.";
					address = "WEB-INF/users.jsp";
					req.setAttribute("users", userService.getAll());
				}
				setActive("usersButton");
			}
			else if ("statistics".equals(action)) {
				List<String> statistics = statisticsService.get();
				if (statistics == null)
					statistics = new ArrayList<>();
				address="WEB-INF/statistics.jsp";
				req.setAttribute("statistics", statistics);
				setActive("logsButton");
			}
			else if ("advisors".equals(action)) {
				address = "WEB-INF/advisors.jsp";
				req.setAttribute("advisors", advisorService.getAll());
				setActive("advisorsButton");
			}
			else if ("addAdvisor".equals(action)) {
				Advisor advisor = new Advisor();
				advisor.setFirstname(req.getParameter("firstname"));
				advisor.setLastname(req.getParameter("lastname"));
				advisor.setUsername(req.getParameter("username"));
				advisor.setPassword(req.getParameter("password"));
				Object ob = advisorService.openAdvisorAccount(advisor);
				if (ob == null) {
					notification = "Advisor with that username already exists";
				}
				address = "WEB-INF/advisors.jsp";
				req.setAttribute("advisors", advisorService.getAll());
				setActive("advisorsButton");
			}
		}
		
		req.setAttribute("notification", notification);
		req.getRequestDispatcher(address).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	private void setActive(String button) {
		getServletContext().setAttribute("categoriesButton", "");
		getServletContext().setAttribute("usersButton", "");
		getServletContext().setAttribute("logsButton", "");
		getServletContext().setAttribute("advisorsButton", "");
		if (button != null)
			getServletContext().setAttribute(button, "active");
	}

}
