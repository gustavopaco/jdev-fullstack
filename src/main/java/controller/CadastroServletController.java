package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Usuario;
import util.Constantes;

@WebServlet("/cadastroCtl")
public class CadastroServletController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        switch (action) {
            case "register":
                doRegister(req, resp);
                break;
            case "delete":
                doDelete(req, resp);
                break;
            case "edit":
                doEdit(req, resp);
                break;
            case "update":
                doPut(req, resp);
                break;
            case "home":
                doHome(req, resp);
                break;
        }
    }

    protected void doRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String name = firstName.concat(" ").concat(last_name);
        String data = req.getParameter("birthday");
        LocalDate birthday = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String gender = req.getParameter("gender");
        String cpf = req.getParameter("cpf");
        String phone = req.getParameter("phone");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();

            usuario.setName(name);
            usuario.setBirthday(birthday);
            switch (Integer.parseInt(gender)) {
                case 1:
                    usuario.setGender(Constantes.MASCULINO);
                    break;
                case 2:
                    usuario.setGender(Constantes.FEMININO);
                    break;
            }
            usuario.setCpf(cpf);
            usuario.setPhone(phone);
            usuario.setLogin(login);
            usuario.setPassword(password);

        if (usuarioDAO.isLoginValid(login) && usuarioDAO.isCPFValid(cpf)) {
            try {
                usuarioDAO.cadastrarUsuario(usuario);
                System.out.println("Usuario Cadastrado com Sucesso.");
                req.setAttribute("usuarios", usuarioDAO.listarUsuarios());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
                requestDispatcher.forward(req, resp);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("cadastroActivity.jsp");
                requestDispatcher.forward(req, resp);
            }
        } else {
            if (!usuarioDAO.isLoginValid(login)) {
                req.setAttribute("msg1", "Login ja existe.*");
            }
            if (!usuarioDAO.isCPFValid(cpf)) {
                req.setAttribute("msg2", "CPF ja existe.*");
            }
            req.setAttribute("user",usuario);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("cadastroActivity.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        usuarioDAO = new UsuarioDAO();
        usuarioDAO.deletarUsuario(Long.parseLong(id));
        req.setAttribute("usuarios", usuarioDAO.listarUsuarios());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        usuarioDAO = new UsuarioDAO();
        usuario = new Usuario();
        usuario = usuarioDAO.pesquisarUsuario(id);
        req.setAttribute("user", usuario);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("detalhesActivity.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();
        usuario.setId(Long.parseLong(req.getParameter("id")));
        usuario.setLogin(req.getParameter("login"));
        if (req.getParameter("password").isEmpty() || req.getParameter("password") == null) {
            usuario.setPassword(req.getParameter("oldpassword"));
        } else {
            usuario.setPassword(req.getParameter("password"));
        }
        usuario.setName(req.getParameter("first_name").concat(" ").concat(req.getParameter("last_name")));
        LocalDate data = LocalDate.parse(req.getParameter("birthday"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        usuario.setBirthday(data);

        switch (Integer.parseInt(req.getParameter("gender"))) {
            case 1:
                usuario.setGender(Constantes.MASCULINO);
                break;
            case 2:
                usuario.setGender(Constantes.FEMININO);
                break;
        }
        usuario.setCpf(req.getParameter("cpf"));
        usuario.setPhone(req.getParameter("phone"));

        if (usuarioDAO.updateUsuario(usuario)) {
            System.out.println("Usuario alterado com sucesso.");
            req.setAttribute("usuarios", usuarioDAO.listarUsuarios());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            System.out.println("Usuario econtrou algum problema ao ser alterado");
            doEdit(req, resp);
        }

    }

    protected void doHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("usuarios", new UsuarioDAO().listarUsuarios());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
        requestDispatcher.forward(req, resp);
    }
}
