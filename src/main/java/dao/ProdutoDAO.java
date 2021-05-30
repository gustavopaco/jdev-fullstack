package dao;

import connection.SingleConnection;
import models.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO() {
        this.connection = SingleConnection.getConnection();
    }

    public boolean insertProduto(Produto produto) {
        try {
            String sql = "insert into produto (nome, quantidade, preco) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, produto.getNomeProduto());
            preparedStatement.setInt(2, produto.getQuantidade());
            preparedStatement.setDouble(3, produto.getPreco());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Produto> listProduto() {
        List<Produto> produtos = new ArrayList<Produto>();
        try {
            String sql = "select * from produto";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Produto produto = new Produto();
                produto.setId(resultSet.getLong("id"));
                produto.setNomeProduto(resultSet.getString("nome"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setPreco(resultSet.getDouble("preco"));

                produtos.add(produto);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return produtos;
    }

    public boolean deleteProduto(Long id) {
        try {
            String sql = "delete from produto where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Produto pesquisarProduto(Long id) {
        Produto produto = new Produto();
        try {
            String sql = "select * from produto where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                produto.setId(resultSet.getLong("id"));
                produto.setNomeProduto(resultSet.getString("nome"));
                produto.setQuantidade(resultSet.getInt("quantidade"));
                produto.setPreco(resultSet.getDouble("preco"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return produto;
    }

    public boolean updateProduto(Produto produto) {
        try {
            String sql = "update produto set nome=?,quantidade=?,preco=? where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,produto.getNomeProduto());
            preparedStatement.setInt(2,produto.getQuantidade());
            preparedStatement.setDouble(3,produto.getPreco());
            preparedStatement.setLong(4,produto.getId());
            preparedStatement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isProdutoExist(String nome) {
        try {
            String sql = "select nome from produto where nome ilike(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,nome);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean isProdutoUpdateExist(Produto produto) {
        try {
            String sql = "select nome from produto where nome ilike(?) and id <> ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,produto.getNomeProduto());
            preparedStatement.setLong(2,produto.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
