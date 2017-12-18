package controller_unificados;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class GridListagemController {

    public int contadorGridListagem(JTable tbGridListagem) {
        int qtd = 0;
        qtd = tbGridListagem.getRowCount();
        return qtd;
    }

    public void limparGridListagem(JTable tbGridListagem) {
        DefaultTableModel linha = (DefaultTableModel) tbGridListagem.getModel();
        while (linha.getRowCount() > 0) {
            linha.removeRow(0);
        }
    }

    public void excluirColunaGridListagem(JTable tbGridListagem) {
        int t = tbGridListagem.getColumnCount();
        for (int i = 0; i < t; i++) {
            tbGridListagem.removeColumn(tbGridListagem.getColumnModel().getColumn(0));
        }
    }

    public void inserirColunaGridListagem(JTable tbGridListagem, int qtdColuna) {
        for (int i = 0; i < qtdColuna; i++) {
            TableColumn coluna = new TableColumn(i);
            tbGridListagem.getColumnModel().addColumn(coluna);
        }
    }

    public void tituloGridListagem(JTable tbGridListagem, int qtdColuna, String[] vetTitulo) {
        for (int i = 0; i < qtdColuna; i++) {
            tbGridListagem.getColumnModel().getColumn(i).setHeaderValue(vetTitulo[i]);
        }
    }
}
