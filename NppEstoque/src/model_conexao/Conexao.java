package model_conexao;

import controller_dao.ConfiguracaoDao;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model_bean.ConfiguracaoModel;
import org.apache.derby.impl.drda.NetworkServerControlImpl;

public class Conexao {

    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String URL = null; //porta padrão
    private static String USER = null;
    private static String SENHA = null;

    public static Connection getConexao() {
        ConfiguracaoModel configModel = new ConfiguracaoModel();
        ConfiguracaoDao configDao = new ConfiguracaoDao();
        configModel = configDao.consultarArquivoConexaoDB();

        URL = "jdbc:derby://" + configModel.getServidor() + ":" + configModel.getPorta() + "/" + configModel.getNomeBanco();// + ";create=false"; //"jdbc:derby://localhost:1527/nppEstoque";
        USER = configModel.getUsuario();
        SENHA = configModel.getSenha();
        Connection con = null;
        
        //Iniciar o banco sem precisar o fazer manualmente toda vez que executar e sem usar o netbeans
        try {
            System.setProperty("derby.system.home", "\\nppEstoque\\.netbeans-derby");
            // Crie uma pasta onde você quiser que fique o seu banco e set aqui
            //pode ser qualquer diretorio incluindo a pasta onde estara o executavel(.jar)
            //nesse Caso: C:\\MyDB\\.netbeans-derby
            NetworkServerControlImpl networkServer = new NetworkServerControlImpl();
            networkServer.start(new PrintWriter(System.out));
            System.out.println("Banco Iniciado");
        } catch (Exception ex) {
            System.out.println("Não conseguiu iniciar banco de dados.");

        }

        try {
            Class.forName(DRIVER);
            con = java.sql.DriverManager.getConnection(URL, USER, SENHA);

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Erro na conecção: ", ex);
        } catch (SQLException ex2) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex2);
        }
        
        return con;
    }

    public static void closeConexao(Connection con) {

        try {
            if (con != null) {
                con.close();
                System.out.println("Conexão finalizada!");
            }
        } catch (SQLException ex) {
            //menssagem
        }

    }

    public static void closeConexao(Connection con, PreparedStatement stmt) {

        closeConexao(con);

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            //menssagem
        }

    }

    public static void closeConexao(Connection con, PreparedStatement stmt, ResultSet rs) {

        closeConexao(con, stmt);

        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            //menssagem
        }

    }
    
    /*
    public static void Fazbackup(Connection con) {

        try {

            String backupdirectory = "\\nppEstoque\\nipponEstoqueDB\\.netbeans-derby";
            CallableStatement cs = con.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE (?)");
            cs.setString(1, backupdirectory);
            cs.execute();
            cs.close();
            JOptionPane.showMessageDialog(null, "Backup feito com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no backup" + ex);
        }

    }

    public static void LerBackup(String diretorio) {

        String a = diretorio;

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            DriverManager.getConnection("jdbc:derby:BancoGerenciador;restoreFrom=" + a);
            JOptionPane.showMessageDialog(null, "Backing Up Realizado com sucesso!");
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(null, "Erro no backing Up" + ex);
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, "Erro no backing Up" + ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro no backing Up" + ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro no backing Up" + ex);
        }

    }

    */
    
    public static void main(String[] args) {
        Conexao.getConexao();

    }

}
