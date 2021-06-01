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
import models.Endereco;
import models.Telefone;
import models.Usuario;
import util.Constantes;

@WebServlet("/cadastroCtl")
public class CadastroServletController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private Endereco endereco;
    private Telefone telefone;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        switch (action) {
            case "register" -> doRegister(req, resp);
            case "delete" -> doDelete(req, resp);
            case "edit" -> doEdit(req, resp);
            case "update" -> doPut(req, resp);
            case "home" -> doHome(req, resp);
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
        String zipcode = req.getParameter("zipcode");
        String phone = req.getParameter("phone");
        String phone_type = req.getParameter("phone_type");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String number = req.getParameter("number");
        String address2 = req.getParameter("address2");
        String state = req.getParameter("state");
        String neighborhood = req.getParameter("neighborhood");
        String city = req.getParameter("city");

        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();
        endereco = new Endereco();
        telefone = new Telefone();

            usuario.setName(name);
            usuario.setBirthday(birthday);
        switch (Integer.parseInt(gender)) {
            case 1 -> usuario.setGender(Constantes.MASCULINO);
            case 2 -> usuario.setGender(Constantes.FEMININO);
        }
            usuario.setCpf(cpf);
            usuario.setLogin(login);
            usuario.setPassword(password);

        if (usuarioDAO.isLoginValid(login) && usuarioDAO.isCPFValid(cpf)) {
            try {
                usuarioDAO.cadastrarUsuario(usuario);
                System.out.println("Usuario Cadastrado com Sucesso.");
                usuario = usuarioDAO.pesquisarUsuarioByLogin(usuario.getLogin());

                endereco.setEnd_cep(zipcode);
                endereco.setEnd_rua(address);
                endereco.setEnd_numero(Integer.parseInt(number));
                endereco.setEnd_complemento(address2);
                endereco.setEnd_bairro(neighborhood);
                endereco.setEnd_cidade(city);
                endereco.setEnd_estado(state);
                endereco.setId_usuario(usuario.getId());

                usuarioDAO.insertEndereco(endereco);
                System.out.println("Endereco cadastrado com Sucesso.");

                telefone.setTel_numero(phone);
                telefone.setTel_tipo(phone_type);
                telefone.setId_usuario(usuario.getId());

                usuarioDAO.insertTelefone(telefone);
                System.out.println("Telefone cadastrado com sucesso.");

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
        req.getSession().setAttribute("usuarioEscolhido",usuario);
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
            case 1 -> usuario.setGender(Constantes.MASCULINO);
            case 2 -> usuario.setGender(Constantes.FEMININO);
        }
        usuario.setCpf(req.getParameter("cpf"));

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
