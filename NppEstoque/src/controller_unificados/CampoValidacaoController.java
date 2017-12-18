package controller_unificados;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CampoValidacaoController {

    public boolean validaCampoTxtField(JTextField txtCampo, String mensagem) {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (txtCampo.getText().isEmpty()) {
             msg.mensagemAtencao(mensagem);
            txtCampo.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    public boolean validaCampoTxtArea(JTextArea txtCampo, String mensagem) {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (txtCampo.getText().isEmpty()) {
            msg.mensagemAtencao(mensagem);
            txtCampo.requestFocus();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validaTamanhoCampoTxtField(JTextField txtCampo, int tamCampo, String nomeCampo) {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (txtCampo.getText().length() > tamCampo) {
            msg.mensagemAtencao("Tamanho do campo " + nomeCampo + " maior que o permitido. Limite " + tamCampo +" caracteres.");
            txtCampo.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}
