package controller_dao;


import model_conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventarioDao {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    public void salvar(String dataInventario, String tipoInventario) {
        con = (Connection) Conexao.getConexao();
        sql = "insert into inventario(data_inventario, tipo_inventario, situacao_deletado) values ('" + dataInventario + "', '" + tipoInventario + "', 'N')";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.execute();
            System.out.println("Novo inventario gerado com sucesso.");
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Não foi possivel gerar o ID  do inventario.");
        }
        Conexao.closeConexao(con);
    }

    public int consultaUltimoIdInventario() {
        con = (Connection) Conexao.getConexao();
        int idEntrada = 0;
        sql = "select max(id_inventario) as id_inventario from inventario order by id_inventario desc";
        try {
            ps = (PreparedStatement) con.prepareStatement(sql);
            rs = (ResultSet) ps.executeQuery();
            if (rs.next()) {
                idEntrada = rs.getInt("id_inventario");
            }
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Não foi consultar o Id do inventario.");
        }
        return idEntrada;
    }

    
}
