package controller;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Telefone;
import models.Usuario;

import java.io.IOException;

@WebServlet(urlPatterns = "/phone")
public class TelefoneServletController extends HttpServlet {
    Usuario usuario;
    Telefone telefone;
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "ownerPhone" -> doOwnerPhone(req, resp);
            case "listar" -> doList(req, resp);
            case "cadastrar" -> doRegister(req, resp);
            case "delete" -> doDelete(req, resp);
            case "edit" -> doEdit(req, resp);
        }
    }

    protected void doRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        telefone = new Telefone();
        usuario = new Usuario();
        usuarioDAO = new UsuarioDAO();

        String token = req.getParameter("TOKEN");
        String token2 = (String) req.getSession().getAttribute("TOKEN");
        if (token.equals(token2)) {
            if (!req.getParameter("id_telefone").isEmpty()) {
                doPut(req, resp);
            } else {
                req.getSession().setAttribute("TOKEN", String.valueOf(req.getSession().getCreationTime() / Math.random()));
                usuario = (Usuario) req.getSession().getAttribute("ownerEscolhido");
                String phone = req.getParameter("phone");
                String phone_type = req.getParameter("phone_type");

                telefone.setId_usuario(usuario.getId());
                telefone.setTel_numero(phone);
                telefone.setTel_tipo(phone_type);

                if (!usuarioDAO.isTelefoneInsertAlreadyExist(telefone)) {
                    if (usuarioDAO.insertTelefone(telefone)) {
                        System.out.println("Telefone cadastrado com sucesso.");
                    } else {
                        System.out.println("Ocorreu um erro ao cadastrar novo telefone");
                    }
                } else {
                    req.setAttribute("msg1", "Telefone ja foi cadastrado no sistema.");
                    req.setAttribute("telefone", telefone);
                }
            }
        }
            doList(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        telefone = new Telefone();
        usuarioDAO = new UsuarioDAO();
        String id_telefone = req.getParameter("id_telefone");
        if (usuarioDAO.deleteTelefone(Long.parseLong(id_telefone))) {
            System.out.println("Telefone deletado com sucesso.");
        }
        doList(req, resp);
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_telefone = req.getParameter("id_telefone");
        usuarioDAO = new UsuarioDAO();
        telefone = usuarioDAO.pesquisarByTelefoneID(Long.parseLong(id_telefone));
        req.setAttribute("telefone", telefone);
        doList(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_telefone = req.getParameter("id_telefone");
        String tel_numero = req.getParameter("phone");
        String tel_tipo = req.getParameter("phone_type");

        telefone = new Telefone();
        usuarioDAO = new UsuarioDAO();

        telefone.setId_telefone(Long.parseLong(id_telefone));
        telefone.setTel_numero(tel_numero);
        telefone.setTel_tipo(tel_tipo);

        if (!usuarioDAO.isTelefoneUpdateAlreadyExist(telefone)) {
            usuarioDAO.updateTelefone(telefone);
            System.out.println("Telefone atualizado com sucesso.");
        } else {
            req.setAttribute("msg1", "Telefone ja cadastrado no sistema");
            req.setAttribute("telefone",telefone);
        }
    }

    protected void doOwnerPhone(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_usuario = req.getParameter("id_usuario");
        usuarioDAO = new UsuarioDAO();
        usuario = new Usuario();
        usuario = usuarioDAO.pesquisarUsuario(Long.parseLong(id_usuario));
        req.getSession().setAttribute("ownerEscolhido", usuario);
        req.getSession().setAttribute("TOKEN", String.valueOf(req.getSession().getCreationTime() / Math.random()));
        doList(req, resp);
    }

    protected void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usuarioDAO = new UsuarioDAO();
        usuario = (Usuario) req.getSession().getAttribute("ownerEscolhido");
        req.setAttribute("telefones", usuarioDAO.getListTelefone(usuario.getId()));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("telefonesActivity.jsp");
        requestDispatcher.forward(req, resp);
    }
}
