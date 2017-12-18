package view;

import controller_dao.ConfiguracaoDao;
import controller_unificados.MensagemValidacaoController;
import model_bean.ConfiguracaoModel;
import model_conexao.Conexao;

public class ViewPrincipal {

    private void consultaConexao() {
        ConfiguracaoModel configModel = new ConfiguracaoModel();
        ConfiguracaoDao configDao = new ConfiguracaoDao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        configModel = configDao.consultarArquivoConexaoDB();

        if (configModel == null) {
            msg.mensagemErro("Arquivo de conexão com o Banco de Dados não localizado.\n"
                    + "Verifique se o diretório: c:\\nppEstoque existe.\n"
                    + "Caso não exista, o diretório deve ser criado manualmente.\nEm seguida realize as configuração de conexão com o banco de dados.\n\n"
                    + "Botão de opção: CONFIGURAÇÕES");
            ViewConfiguracao telaConfig = new ViewConfiguracao();
            telaConfig.setVisible(true);

        } else {
            try {
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec("java -jar \"lib\\derbyrun.jar\" server start");
                Conexao.getConexao();
                ViewMenu telaMenu = new ViewMenu();
                telaMenu.setVisible(true);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        ViewPrincipal principal = new ViewPrincipal();
        principal.consultaConexao();

    }

}
