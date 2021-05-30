package controller;

import dao.ProdutoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Produto;

import java.io.IOException;

@WebServlet(urlPatterns = "/produtos", name = "produtos")
public class ProdutoServletController extends HttpServlet {
    Produto produto;
    ProdutoDAO produtoDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "listar":
                doList(req, resp);
                break;
            case "cadastrar":
                doRegister(req, resp);
                break;
            case "delete":
                doDelete(req, resp);
                break;
            case "edit":
                doEdit(req, resp);
                break;
        }
    }

    protected void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        produtoDAO = new ProdutoDAO();
        req.setAttribute("produtos", produtoDAO.listProduto());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("productsActivity.jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void doRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameter("id").isEmpty()){
            doPut(req, resp);
        }else {
            String productName = req.getParameter("productName");
            String quantity = req.getParameter("quantity");
            String price = req.getParameter("price").replace("$", "").replace(".", "").replace(",", ".");

            produto = new Produto();
            produtoDAO = new ProdutoDAO();

            produto.setNomeProduto(productName);
            produto.setQuantidade(Integer.parseInt(quantity));
            produto.setPreco(Double.parseDouble(price));

            if (!produtoDAO.isProdutoExist(produto.getNomeProduto())) {
                produtoDAO.insertProduto(produto);
                System.out.println("Produto Registrado com sucesso");
                req.setAttribute("acao", "home");
                doList(req, resp);
            }else{
                req.setAttribute("msg1","Nome do produto ja existente.");
                req.setAttribute("produto",produto);
                doList(req,resp);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (new ProdutoDAO().deleteProduto(Long.parseLong(id))) {
            System.out.println("Produto deletado com sucesso");
            req.setAttribute("acao", "delete");
            doList(req, resp);
        }
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        produto = new Produto();
        produtoDAO = new ProdutoDAO();

        produto = produtoDAO.pesquisarProduto(Long.parseLong(id));
        req.setAttribute("produto",produto);
        req.setAttribute("produtos", produtoDAO.listProduto());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("productsActivity.jsp");
        requestDispatcher.forward(req,resp);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String nomeProduto = req.getParameter("productName");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price").replace("$","").replace(".","").replace(",",".");

        produto = new Produto();
        produtoDAO = new ProdutoDAO();

        produto.setId(Long.parseLong(id));
        produto.setNomeProduto(nomeProduto);
        produto.setQuantidade(Integer.parseInt(quantity));
        produto.setPreco(Double.parseDouble(price));

        if (!produtoDAO.isProdutoUpdateExist(produto)){
        produtoDAO.updateProduto(produto);
        System.out.println("Produto atualizado com sucesso.");
        doList(req,resp);
        }else{
            req.setAttribute("produto", produto);
            req.setAttribute("msg1","Produto ja foi cadastrado no sistema.");
            doList(req,resp);
        }
    }
}
