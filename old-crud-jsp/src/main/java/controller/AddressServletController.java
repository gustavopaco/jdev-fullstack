package controller;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Endereco;
import models.Usuario;

import java.io.IOException;

@WebServlet(urlPatterns = "/address")
public class AddressServletController extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private Endereco endereco;
    private Usuario usuario;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "ownerAddress" -> doOwnerAddress(req, resp);
            case "listar" -> doList(req, resp);
            case "delete" -> doDelete(req, resp);
            case "cadastrar" -> doRegister(req, resp);
            case "edit" -> doEdit(req, resp);
        }
    }

    protected void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usuarioDAO = new UsuarioDAO();
        usuario = (Usuario) req.getSession().getAttribute("ownerEscolhido");
        req.setAttribute("enderecos", usuarioDAO.listEndereco(usuario.getId()));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("enderecosActivity.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        usuarioDAO = new UsuarioDAO();
        String id_endereco = req.getParameter("id_endereco");
        if (usuarioDAO.deleteEndereco(Long.parseLong(id_endereco))) {
            System.out.println("Endereco deletado com sucesso");
            doList(req, resp);
        }
    }

    protected void doRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String token1 = req.getParameter("TOKEN");
        String token2 = (String) req.getSession().getAttribute("TOKEN");

        if (token1.equals(token2)) {
            req.getSession().setAttribute("TOKEN", String.valueOf((req.getSession().getCreationTime()) / Math.random()));
            if (!req.getParameter("id_address").isEmpty()) {
                req.setAttribute("id_address",req.getParameter("id_address"));
                doPut(req, resp);
            } else {
                String zipcode = req.getParameter("zipcode");
                String address = req.getParameter("address");
                String number = req.getParameter("number");
                String address2 = req.getParameter("address2");
                String neighborhood = req.getParameter("neighborhood");
                String city = req.getParameter("city");
                String state = req.getParameter("state");
                Usuario usuario = (Usuario) req.getSession().getAttribute("ownerEscolhido");

                endereco = new Endereco();
                usuarioDAO = new UsuarioDAO();

                endereco.setEnd_cep(zipcode);
                endereco.setEnd_rua(address);
                endereco.setEnd_numero(Integer.parseInt(number));
                endereco.setEnd_complemento(address2);
                endereco.setEnd_bairro(neighborhood);
                endereco.setEnd_cidade(city);
                endereco.setEnd_estado(state);
                endereco.setId_usuario(usuario.getId());

                if (usuarioDAO.insertEndereco(endereco)) {
                    System.out.println("Endereco cadastrado com sucesso");
                    doList(req, resp);
                } else {
                    req.setAttribute("endereco", endereco);
                    doList(req, resp);
                }
            }
        } else {
            doList(req, resp);
        }
    }

    protected void doOwnerAddress(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_usuario = req.getParameter("id_usuario");
        usuarioDAO = new UsuarioDAO();
        usuario = new Usuario();
        usuario = usuarioDAO.pesquisarUsuario(Long.parseLong(id_usuario));
        req.getSession().setAttribute("TOKEN", String.valueOf(req.getSession().getCreationTime() / Math.random()));
        req.getSession().setAttribute("ownerEscolhido", usuario);
        doList(req, resp);
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_endereco = req.getParameter("id_endereco");
        Endereco endereco = new UsuarioDAO().pesquisaEndereco(Long.parseLong(id_endereco));
        req.setAttribute("endereco", endereco);
        doList(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String zipcode = req.getParameter("zipcode");
        String address = req.getParameter("address");
        String number = req.getParameter("number");
        String address2 = req.getParameter("address2");
        String neighborhood = req.getParameter("neighborhood");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String id_address = req.getParameter("id_address");

        endereco = new Endereco();
        usuarioDAO = new UsuarioDAO();

        endereco.setEnd_cep(zipcode);
        endereco.setEnd_rua(address);
        endereco.setEnd_numero(Integer.parseInt(number));
        endereco.setEnd_complemento(address2);
        endereco.setEnd_bairro(neighborhood);
        endereco.setEnd_cidade(city);
        endereco.setEnd_estado(state);
        endereco.setId_endereco(Long.parseLong(id_address));

        if (!usuarioDAO.isAddressAlreadyExist(endereco)) {
            usuarioDAO.updateEndereco(endereco);
            System.out.println("Endereco alterado com sucesso");
        }else{
            System.out.println("Nao foi possivel alterar o endereco");
            req.setAttribute("endereco", endereco);
            req.setAttribute("msg1","Endereco ja cadastrado");
        }
        doList(req,resp);
    }
}
