package controller_dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model_bean.ProdutoModel;
import model_conexao.Conexao;

public class ProdutoEstoqueDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvaEstoque(ProdutoModel prodEstModel) {
        con = (Connection) Conexao.getConexao();
        sql = "insert into produto_estoque(id_produto, quantidade_estoque, data_ultimo_inventario) values (?, ?, ?)";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, prodEstModel.getIdProduto());
            ps.setDouble(2, prodEstModel.getQuantidadeEstoque());
            ps.setString(3, prodEstModel.getDataUltimoInventario());
            ps.execute();
            System.out.println("Estoque do produto cadastrado com sucesso.");
        } catch (Exception e) {
            e.getMessage();
            System.out.println("SALVAR-ESTOQUE-PRODUTO - Não foi possível cadastrar o estoque do produto.");
        }
        Conexao.closeConexao(con);
    }

    public Double consultaQtdeAtualProduto(int idProd) {
        con = (Connection) Conexao.getConexao();
        Double qtdeEstoqProd = 0.0;
        sql = "select quantidade_estoque from produto_estoque where id_produto = " + idProd;
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                qtdeEstoqProd = rs.getDouble("quantidade_estoque");
            }
            System.out.println("Estoque atual do produto: " + qtdeEstoqProd);
        } catch (Exception e) {
            e.getMessage();
            System.out.println("CONSULTA-QTDE-ATUAL-ESTOQUE-PRODUTO - Não foi consultar o estoque do produto.");
        }
         Conexao.closeConexao(con);
        return qtdeEstoqProd;
    }

    public void atualizarEstoqueEntrada(ProdutoModel prodEstModel, Double qtdeEstoqueAtual) {
        con = (Connection) Conexao.getConexao();
        sql = "update root.produto_estoque set quantidade_estoque = ?, data_ultimo_inventario = ? where id_produto = ?";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setDouble(1, (prodEstModel.getQuantidadeEstoque() + qtdeEstoqueAtual));
            ps.setString(2, prodEstModel.getDataUltimoInventario());
            ps.setInt(3, prodEstModel.getIdProduto());
            ps.execute();
            System.out.println("Entrada em estoque do Produto atualizado com sucesso");
        } catch (Exception e) {
            e.getMessage();
            System.out.println("ATUALIZA-ESTOQUE-ENTRADA-PRODUTO - Não foi possível atualizar o estoque do produto para entrada.");
        }
         Conexao.closeConexao(con);
    }
    
    public void atualizarEstoqueSaida(ProdutoModel prodEstModel, Double qtdeEstoqueAtual) {
        con = (Connection) Conexao.getConexao();
        sql = "update produto_estoque set quantidade_estoque = ?, data_ultimo_inventario = ? where id_produto = ?";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setDouble(1, (qtdeEstoqueAtual - prodEstModel.getQuantidadeEstoque()));
            ps.setString(2, prodEstModel.getDataUltimoInventario());
            ps.setInt(3, prodEstModel.getIdProduto());
            ps.execute();
            System.out.println("Entrada em estoque do Produto atualizado com sucesso");
        } catch (Exception e) {
            e.getMessage();
            System.out.println("ATUALIZA-ESTOQUE-SAIDA-PRODUTO - Não foi possível atualizar o estoque do produto para entrada.");
        }
         Conexao.closeConexao(con);
    }
    
}
