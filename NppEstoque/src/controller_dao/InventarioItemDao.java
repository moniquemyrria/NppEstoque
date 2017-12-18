package controller_dao;

import controller_unificados.DataValidacaoController;
import controller_unificados.MensagemValidacaoController;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import model_bean.InventarioItemModel;
import model_conexao.Conexao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class InventarioItemDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(InventarioItemModel inventarioImModel) {
        con = (Connection) Conexao.getConexao();
        sql = "insert into inventario_itens(id_produto, id_inventario, lote, quantidade) values (?, ?, ?, ?)";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setInt(1, inventarioImModel.getIdProduto());
            ps.setInt(2, inventarioImModel.getIdInventario());
            ps.setString(3, inventarioImModel.getLote());
            ps.setDouble(4, inventarioImModel.getQuantidade());
            ps.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.getMessage();
            System.out.println("Não foi possivel gerar o inventario de Itens para os produtos.");
        }
        Conexao.closeConexao(con);
    }

    public List<InventarioItemModel> listarPeriodoItemNaoAgrupado(String dataInicio, String dataFinal, String tipoInventario) {
        con = (Connection) Conexao.getConexao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        DataValidacaoController data = new DataValidacaoController();
        sql = "select p.id_produto, p.codigo_radar, p.nome_produto, p.descricao_produto, ii.lote, p.local_estoque, "
                + "ii.quantidade, (substr(data_inventario,9,2)|| '/' || substr(data_inventario,6,2)|| '/' || substr(data_inventario,1,4)) as data "
                + "from root.inventario_itens ii "
                + "inner join root.inventario i on (i.id_inventario = ii.id_inventario) "
                + "inner join root.produto p on (p.id_produto = ii.id_produto) "
                + "where date(i.data_inventario) between date('" + dataInicio + "') "
                + "and date('" + dataFinal + "') "
                + "and i.tipo_inventario = '" + tipoInventario + "' "
                + "order by (p.codigo_radar)";
        List<InventarioItemModel> lista = new ArrayList<InventarioItemModel>();
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                InventarioItemModel invItemModel = new InventarioItemModel();
                invItemModel.setIdProduto(rs.getInt("id_produto"));
                invItemModel.setCodigoRadar(rs.getString("codigo_radar"));
                invItemModel.setNomeProduto(rs.getString("nome_produto"));
                invItemModel.setDescricaoProduto(rs.getString("descricao_produto"));
                invItemModel.setLote(rs.getString("lote"));
                invItemModel.setLocalEstoque(rs.getString("local_estoque"));
                invItemModel.setQuantidade(rs.getDouble("quantidade"));
                invItemModel.setDataInventario(rs.getString("data"));
                lista.add(invItemModel);
            }
        } catch (Exception e) {
            e.getMessage();
            msg.mensagemErro("Nao foi possivel Listar os dados. Verifique a conexão com o banco de dados.");
        }
        Conexao.closeConexao(con);
        return lista;
    }

    public List<InventarioItemModel> listarPeriodoItemAgrupado(String dataInicio, String dataFinal, String tipoInventario) {
        con = (Connection) Conexao.getConexao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        DataValidacaoController data = new DataValidacaoController();
        sql = "select p.id_produto, p.codigo_radar, p.nome_produto, p.descricao_produto, p.local_estoque, SUM(ii.quantidade) as quantidade "
                + "from inventario_itens ii "
                + "inner join root.inventario i on (i.id_inventario = ii.id_inventario) "
                + "inner join root.produto p on (p.id_produto = ii.id_produto) "
                + "where date(i.data_inventario) between date('" + dataInicio + "') "
                + "and date('" + dataFinal + "') "
                + "and i.tipo_inventario = '" + tipoInventario + "' "
                + "group by p.id_produto, p.codigo_radar, p.nome_produto, p.descricao_produto, p.local_estoque "
                + "order by (p.codigo_radar)";
        List<InventarioItemModel> lista = new ArrayList<InventarioItemModel>();
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                InventarioItemModel invItemModel = new InventarioItemModel();
                invItemModel.setIdProduto(rs.getInt("id_produto"));
                invItemModel.setCodigoRadar(rs.getString("codigo_radar"));
                invItemModel.setNomeProduto(rs.getString("nome_produto"));
                invItemModel.setDescricaoProduto(rs.getString("descricao_produto"));
                invItemModel.setLocalEstoque(rs.getString("local_estoque"));
                invItemModel.setQuantidade(rs.getDouble("quantidade"));
                lista.add(invItemModel);
            }
        } catch (Exception e) {
            e.getMessage();
            msg.mensagemErro("Nao foi possivel Listar os dados. Verifique a conexão com o banco de dados.");
        }
         Conexao.closeConexao(con);
        return lista;
    }

    public void parametrosImpressaoRelatorioItens(InputStream nomeArqRelatorio, HashMap parametros) {
        Connection con = null;
        con = (Connection) Conexao.getConexao();
        DataValidacaoController data = new DataValidacaoController();
        JasperPrint jasperPrint = null;

        try {
            jasperPrint = JasperFillManager.fillReport(nomeArqRelatorio, parametros, con);

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
