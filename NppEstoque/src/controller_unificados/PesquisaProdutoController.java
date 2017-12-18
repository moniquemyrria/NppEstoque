package controller_unificados;

import javax.swing.JTextField;

public class PesquisaProdutoController {

    public boolean validaCampoPesquisa(JTextField txtPesquisa) {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (txtPesquisa.getText().trim().length() == 0) {
            msg.mensagemAtencao("Informe os dados para realizar a pesquisa.");
            txtPesquisa.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
