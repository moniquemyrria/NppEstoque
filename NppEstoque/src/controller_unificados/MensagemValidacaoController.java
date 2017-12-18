package controller_unificados;

import javax.swing.JOptionPane;

public class MensagemValidacaoController {

    public int retornoMensagemPergunta(String pergunta) {
        Object[] opcao = {"  Sim  ", "  Não  "};
        int op = JOptionPane.showOptionDialog(null, pergunta + "?\n\n", "Atenção", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]);
        return op;
    }

    public void mensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void mensagemAtencao(String mensagem){
         JOptionPane.showMessageDialog(null, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
    
    public void mensagemInformacao(String mensagem){
         JOptionPane.showMessageDialog(null, mensagem, "Confirmação", JOptionPane.INFORMATION_MESSAGE);
    }
}
