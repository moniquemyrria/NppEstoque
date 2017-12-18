package model_conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import model_bean.ConfiguracaoModel;
import controller_dao.ConfiguracaoDao;
import org.apache.derby.impl.drda.NetworkServerControlImpl;
import java.io.PrintWriter;

public class ConexaoInterna {

    public static Connection getConexao1() {
        ConfiguracaoModel configModel = new ConfiguracaoModel();
        ConfiguracaoDao configDao = new ConfiguracaoDao();
        configModel = configDao.consultarArquivoConexaoDB();
        Connection con = null;
        String url = "jdbc:derby://" + configModel.getServidor() + ":" + configModel.getPorta() + "/" + configModel.getNomeBanco() + ";create=true";// + ";create=false"; //"jdbc:derby://localhost:1527/nppEstoque";
        String user = configModel.getUsuario();
        String senha = configModel.getSenha();
        Runtime rt = Runtime.getRuntime();
        
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            con = DriverManager.getConnection(url, configModel.getUsuario(), configModel.getSenha());//user, senha);
            Process proc = rt.exec("java -jar \"lib\\derbyrun.jar\" server start");
            System.out.println("Conexao estabelecida com sucesso!");

        } catch (Exception erro) {
            erro.getMessage();
            System.out.println("Não conectado ao banco de dados!");

        }

       
        return con;
    }

    public static void closeConexao(Connection con) {
        try {
            con.close();
            System.out.println("Conexão finalizada!");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        ConexaoInterna.getConexao1();

    }
}
