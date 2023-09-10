package com.curso.praticaweb.web_mvn_praticanova_jsp.controller;

import com.curso.praticaweb.web_mvn_praticanova_jsp.connection.SingleConnection;
import com.curso.praticaweb.web_mvn_praticanova_jsp.dao.UsuarioDAORepository;
import com.curso.praticaweb.web_mvn_praticanova_jsp.model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/registerUser"})
public class RegisterUserServletController extends HttpServlet {
    private UsuarioDAORepository usuarioDAORepository = new UsuarioDAORepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FOI CHAMADO DO GET" + response.getStatus());
        request.getRequestDispatcher("principal/registerUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("cadastroerror");
        request.getSession().removeAttribute("cadastrosucesso");
        String user_email = request.getParameter("user_email");
//        String action = request.getParameter("action");
//        if (action != null && action.equals("validaEmail")) {
//            boolean isUserAlreadyExist = (usuarioDAORepository.getUsuario(user_email).getUser_email() != null);
//            if (isUserAlreadyExist){
//                response.setStatus(500);
//                response.getWriter().write("Email ja existe");
//            }
//        }else{
//        }
        String user_name = request.getParameter("user_name");
        String user_password = request.getParameter("user_password");
        String id_usuario = request.getParameter("id_usuario");
        boolean isUserAlreadyExist = (usuarioDAORepository.getUsuario(user_email).getUser_email() != null);

        Usuario usuario = new Usuario();

        usuario.setId_usuario((id_usuario != null && !id_usuario.isEmpty()) ? Long.parseLong(id_usuario) : null);
        usuario.setUser_name(user_name);
        usuario.setUser_email(user_email);
        usuario.setUser_password(user_password);

        if (isUserAlreadyExist && usuario.getId_usuario() == null) {
            // Mensagem que email ja foi utilizado
            request.getSession().setAttribute("cadastroerror", "Email ja existe");
        } else if (isUserAlreadyExist && usuario.getId_usuario() != null) {
            if (!usuarioDAORepository.isEmailOnUpdateAlreadyTaken(usuario)) {
                usuarioDAORepository.updateUser(usuario);
                request.getSession().setAttribute("cadastrosucesso", "Usuario Alterado com sucesso");
            } else {
                request.getSession().setAttribute("cadastroerror", "Email em uso por outro usuario");
            }
        } else if (usuarioDAORepository.insertUser(usuario)) {
            request.getSession().setAttribute("cadastrosucesso", "Novo usuario registrado com sucesso");
            usuario = usuarioDAORepository.getUsuario(usuario.getUser_email());
        }
        request.getSession().setAttribute("usuarioEscolhido", usuario);
        response.sendRedirect("registerUser");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        if (userID == null || userID.isEmpty()) {
            response.setStatus(500);
            response.getWriter().write("Erro ao deletar usuario, por favor cadastre um Usuario antes.");
        } else {
            usuarioDAORepository.deleteUser(Long.parseLong(userID));
            response.setStatus(200);
            response.getWriter().write("Usuario deletado com sucesso.");
        }
    }
}
