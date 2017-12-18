package controller_dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import controller_unificados.MensagemValidacaoController;
import java.io.InputStream;
import model_conexao.Conexao;
import java.util.*;
import javax.swing.JOptionPane;
import model_bean.ProdutoModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ProdutoDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(ProdutoModel produto) {
        con = (Connection) Conexao.getConexao();
        //sql = "insert into root.produto(codigo_radar, nome_produto, descricao_produto, local_estoque, situacao_deletado) values (?, ?, ?, ?,'N')";
        sql = "insert into produto (codigo_radar, nome_produto, descricao_produto, local_estoque, situacao_deletado) "
                + "values (?, ?, ?, ?, 'N')";
        MensagemValidacaoController msg = new MensagemValidacaoController();
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, produto.getCodigoRadar());
            ps.setString(2, produto.getNomeProduto());
            ps.setString(3, produto.getDescricaoProduto());
            ps.setString(4, produto.getLocalEstoque());
            ps.execute();
            msg.mensagemInformacao("Novo produto cadastrado com sucesso!");
        } catch (Exception e) {
            e.getMessage();
            msg.mensagemErro("SALVAR-PRODUTO - Não foi possível cadastrar o produto. Verifique a conexão com o banco de dados");
        }
        Conexao.closeConexao(con);
    }

    public ProdutoModel consultar(String stringPesquisa, String id, int tipoPesquisa) {
        con = (Connection) Conexao.getConexao();
        ProdutoModel prodModel = new ProdutoModel();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        //1 - Pesquisa da Tela de Produtos
        if (tipoPesquisa == 1) {
            sql = "select *from produto where codigo_radar = '" + stringPesquisa + "' and id_produto <> " + id;
        }
        //2 - Pesquisa da Tela de Inventario (Por Codigo Radar)
        if (tipoPesquisa == 2) {
            sql = "select *from produto where codigo_radar = '" + stringPesquisa + "' and situacao_deletado = 'N'";
        }
        //1 - Pesquisa da Tela de Produtos - Verifica Nome produto duplicado
        if (tipoPesquisa == 3) {
            sql = "select *from produto where nome_produto = '" + stringPesquisa + "' and id_produto <> " + id;
        }

        //sql = "select *from produto where codigo_radar = '" + codRadar + "' and situacao_deletado = 'N' and id <> '" + id + "'";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = (ResultSet) ps.executeQuery();
            if (rs.next()) {
                prodModel = new ProdutoModel();
                prodModel.setIdProduto(rs.getInt("id_produto"));
                prodModel.setCodigoRadar(rs.getString("codigo_radar"));
                prodModel.setNomeProduto(rs.getString("nome_produto"));
                prodModel.setDescricaoProduto(rs.getString("descricao_produto"));
                prodModel.setLocalEstoque(rs.getString("local_estoque"));
                prodModel.setSituacaoDeletadoProduto(rs.getString("situacao_deletado"));
            }
        } catch (Exception e) {
            e.getMessage();
            msg.mensagemErro("CONSULTAR-PRODUTO - Não foi possível conusltar o produto. Verifique a conexão com o banco de dados.");
        }
        Conexao.closeConexao(con);
        return prodModel;
    }

    public List<ProdutoModel> listar(String stringPesquisa, String coluna) {
        con = (Connection) Conexao.getConexao();
        MensagemValidacaoController msg = new MensagemValidacaoController();

        sql = "select p.id_produto, p.codigo_radar, p.nome_produto,p.descricao_produto, p.local_estoque, p.situacao_deletado, pe.quantidade_estoque "
                + "from produto p inner join produto_estoque pe on (pe.id_produto=p.id_produto) "
                + "where " + coluna + " = '" + stringPesquisa + "' and situacao_deletado = 'N'";

        List<ProdutoModel> lista = new ArrayList<ProdutoModel>();
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = (ResultSet) ps.executeQuery();
            while (rs.next()) {
                ProdutoModel prodModel = new ProdutoModel();
                prodModel.setIdProduto(rs.getInt("id_produto"));
                prodModel.setCodigoRadar(rs.getString("codigo_radar"));
                prodModel.setNomeProduto(rs.getString("nome_produto"));
                prodModel.setDescricaoProduto(rs.getString("descricao_produto"));
                prodModel.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
                prodModel.setLocalEstoque(rs.getString("local_estoque"));
                lista.add(prodModel);
            }
        } catch (Exception e) {
            e.getMessage();
            msg.mensagemErro("LISTAR-PRODUTO - Nao foi possivel Listar os dados. Verifique a conexão com o banco de dados.");
        }
        Conexao.closeConexao(con);
        return lista;
    }
    
    public List<ProdutoModel> listarTodos() {
        con = (Connection) Conexao.getConexao();
        MensagemValidacaoController msg = new MensagemValidacaoController();

        sql = "select p.id_produto, p.codigo_radar, p.nome_produto,p.descricao_produto, p.local_estoque, p.situacao_deletado, pe.quantidade_estoque "
                + "from produto p inner join produto_estoque pe on (pe.id_produto=p.id_produto) "
                + "where situacao_deletado = 'N' order by p.codigo_radar";

        List<ProdutoModel> lista = new ArrayList<ProdutoModel>();
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = (ResultSet) ps.executeQuery();
            while (rs.next()) {
                ProdutoModel prodModel = new ProdutoModel();
                prodModel.setIdProduto(rs.getInt("id_produto"));
                prodModel.setCodigoRadar(rs.getString("codigo_radar"));
                prodModel.setNomeProduto(rs.getString("nome_produto"));
                prodModel.setDescricaoProduto(rs.getString("descricao_produto"));
                prodModel.setQuantidadeEstoque(rs.getDouble("quantidade_estoque"));
                prodModel.setLocalEstoque(rs.getString("local_estoque"));
                lista.add(prodModel);
            }
        } catch (Exception e) {
            e.getMessage();
            msg.mensagemErro("LISTAR-PRODUTO - Nao foi possivel Listar os dados. Verifique a conexão com o banco de dados.");
        }
        Conexao.closeConexao(con);
        return lista;
    }

    public void alterar(ProdutoModel prodModel, int tipoAlterar) {
        con = (Connection) Conexao.getConexao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        //1 - Alteraçao de dados do registro
        if (tipoAlterar == 1) {
            sql = "update produto set codigo_radar = ?, nome_produto = ?, descricao_produto = ?, local_estoque = ? where id_produto = ?";
            try {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, prodModel.getCodigoRadar());
                ps.setString(2, prodModel.getNomeProduto());
                ps.setString(3, prodModel.getDescricaoProduto());
                ps.setString(4, prodModel.getLocalEstoque());
                ps.setInt(5, prodModel.getIdProduto());
                ps.execute();
                msg.mensagemInformacao("Dados do produto altedado com sucesso.");
            } catch (Exception e) {
                e.getMessage();
                msg.mensagemErro("ALTERAR-PRODUTO - Não foi possível alterar o produto. Verifique a conexão com o banco de dados.");
            }
        }

        //2 - Restaura produtos deletado
        if (tipoAlterar == 2) {
            sql = "update produto set situacao_deletado = 'N' where id_produto = ?";
            try {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setInt(1, prodModel.getIdProduto());
                ps.execute();
                msg.mensagemInformacao("Produto restaurado com sucesso!");
            } catch (Exception e) {
                e.getMessage();
                msg.mensagemErro("DELETAR-PRODUTO - Não foi possível restaurar o produto. Verifique a conexão com o banco de dados.");
            }
        }

        Conexao.closeConexao(con);
    }

    public void excluir(ProdutoModel prodModel) {
        con = (Connection) Conexao.getConexao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        sql = "update produto set situacao_deletado = 'S' where id_produto = ?";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, prodModel.getIdProduto());
            ps.execute();
            msg.mensagemInformacao("Produto excluído com sucesso.");
        } catch (Exception e) {
            e.getMessage();
            msg.mensagemErro("Não foi possíel excluír o produto. Verifique a conexão com o banco de dados.");
        }
        Conexao.closeConexao(con);
    }

    public int consultaUltimoIdProduto() {
        con = (Connection) Conexao.getConexao();
        int idProd = 0;
        sql = "select max(id_produto) as id_produto from root.produto order by id_produto desc";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = (ResultSet) ps.executeQuery();
            if (rs.next()) {
                idProd = rs.getInt("id_produto");
            }
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Não foi consultar o Id do ultimo inventario.");
        }
        return idProd;
    }

    public void parametrosImpressaoRelatorio(InputStream nomeArqRelatorio, HashMap parametros, String campoSQL, String pesquisa) {
        Connection con = null;
        con = (Connection) Conexao.getConexao();

        sql = "select p.id_produto, p.codigo_radar, p.nome_produto,p.descricao_produto, p.local_estoque, "
                + "p.situacao_deletado, pe.quantidade_estoque from produto p inner join produto_estoque pe on(pe.id_produto=p.id_produto) "
                + "where p.situacao_deletado = 'N' "
                + "and " + campoSQL + " = '" + pesquisa + "'";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = (ResultSet) ps.executeQuery();

        } catch (Exception e) {
            e.getMessage();
        }

        JasperPrint jasperPrint = null;
        try {
            JRDataSource jrds = new JRResultSetDataSource(rs);
            parametros.put("REPORT_CONNECTION", con);
            jasperPrint = JasperFillManager.fillReport(nomeArqRelatorio, parametros, jrds);
        } catch (JRException e) {
            System.out.println("Erro " + e);
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        JasperViewer view = new JasperViewer(jasperPrint, false);
        view.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
        view.setTitle("Relatório de Estoque");
        view.setVisible(true);
    }

}
