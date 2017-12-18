package view;

import controller_unificados.MensagemValidacaoController;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class ViewMenu extends javax.swing.JFrame {

    public ViewMenu() {
        initComponents();

        //Centralizar tela
        this.setLocationRelativeTo(null);

        //Retirando a Mximização da tela
        this.setResizable(false);

        carregaOpcaoMenu();
        //consultaConexao();
    }
    
    String Teste = null;
    
    ViewConfiguracao telaConfiguracao = null;
    ViewProduto telaProduto = null;
    ViewInventario telaInventario = null;
    ViewInventarioGerenciar telaInventarioGerenciar = null;

    void abrirTelaConfiguracao() {
        System.out.println(Teste);
        try {
            if (telaConfiguracao == null) {
                telaConfiguracao = new ViewConfiguracao();
                telaConfiguracao.setVisible(true);
            }
            if (telaConfiguracao.isVisible()) {
            } else {   
                telaConfiguracao = null;
                telaConfiguracao.setVisible(true);
            }
        } catch (Exception e) {
            e.getMessage();
        }
             


    }

    void abrirTelaProduto() {
        try {
            if (telaProduto == null) {
                telaProduto = new ViewProduto();
                telaProduto.setVisible(true);
            }
            if (telaProduto.isVisible()) {
            } else {
                telaProduto = null;
                telaProduto.setVisible(true);
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    void abrirTelaInventario(String tipoInventario) {
        try {
            if (telaInventario == null) {
                telaInventario = new ViewInventario(tipoInventario);
                telaInventario.setVisible(true);
            }
            if (telaInventario.isVisible()) {
            } else {
                telaInventario = null;
                telaInventario = new ViewInventario(tipoInventario);
                telaInventario.setVisible(true);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    void abrirTelaInventarioGerenciar() {
        try {
            if (telaInventarioGerenciar == null) {
                telaInventarioGerenciar = new ViewInventarioGerenciar();
                telaInventarioGerenciar.setVisible(true);
            }
            if (telaInventarioGerenciar.isVisible()) {
            } else {
                telaInventarioGerenciar = null;
                telaInventarioGerenciar.setVisible(true);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void carregaOpcaoMenu() {
        btGerenciar.setText("<html><body><b>Gerenciar</b><br>Entradas e Saídas</br><br&gtcom HTML!</body></html>");
        btProduto.setText("<html><body><b>Cadastro</b><br>de Produtos</br><br&gtcom HTML!</body></html>");
        btInventario.setText("<html><body><b>Entrada e Saída</b><br>de Produtos</br><br&gtcom HTML!</body></html>");
        btConfiguracao.setText("<html><body><b>Configurações</b><br>do Sistema</br><br&gtcom HTML!</body></html>");
    }

    public void desabilitaOpcaoMenu() {
        btGerenciar.setEnabled(false);
        btProduto.setEnabled(false);
        btInventario.setEnabled(false);
        btConfiguracao.setEnabled(false);

    }

    public void habilitaOpcaoMenu() {
        btGerenciar.setEnabled(true);
        btProduto.setEnabled(true);
        btInventario.setEnabled(true);
        btConfiguracao.setEnabled(true);

    }

    private void acaoBotaoConfiguracao() {
        MensagemValidacaoController msg = new MensagemValidacaoController();

        // ***********************
        String senha = "100216";
        // ***********************

        //Criando tela de Solicitação de Senha
        JPasswordField pswSenhaInformada = new JPasswordField(20);
        JLabel lbTitulo = new JLabel("Informe a senha de acesso");
        JPanel entUsuario = new JPanel();
        entUsuario.add(lbTitulo);
        entUsuario.add(pswSenhaInformada);

        //JOption com JPanel
        JOptionPane.showMessageDialog(null, entUsuario, "Acesso ao Configurações de Sistema", JOptionPane.QUESTION_MESSAGE);
        String senhaValidar = new String(pswSenhaInformada.getPassword());
        if (senhaValidar.equals(senha)) {
            //ViewConfiguracao telaConfiguracao = new ViewConfiguracao();
            //telaConfiguracao.setVisible(true);
            abrirTelaConfiguracao();
        } else {
            msg.mensagemErro("Senha incorreta.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        btConfiguracao = new javax.swing.JButton();
        btInventario = new javax.swing.JButton();
        btGerenciar = new javax.swing.JButton();
        btProduto = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Estoque de Produtos");
        setBackground(javax.swing.UIManager.getDefaults().getColor("Button.highlight"));

        jPanel2.setBackground(java.awt.Color.white);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("C O N T R O L E");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblTitulo.setText("ESTOQUE DE PRODUTOS");
        lblTitulo.setToolTipText("");
        lblTitulo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTitulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        btConfiguracao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btConfiguracao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/database.png"))); // NOI18N
        btConfiguracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfiguracaoActionPerformed(evt);
            }
        });

        btInventario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/box (1).png"))); // NOI18N
        btInventario.setActionCommand("Entrada e Saída \\n de Produtos");
        btInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInventarioActionPerformed(evt);
            }
        });

        btGerenciar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btGerenciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/delivery-cart.png"))); // NOI18N
        btGerenciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGerenciarActionPerformed(evt);
            }
        });

        btProduto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barcode (2).png"))); // NOI18N
        btProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btGerenciar, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btConfiguracao, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btGerenciar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(btConfiguracao, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProdutoActionPerformed
        abrirTelaProduto();
    }//GEN-LAST:event_btProdutoActionPerformed

    private void btInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInventarioActionPerformed
        //Criando tela de Solicitação de Senha
        JPasswordField pswSenhaInformada = new JPasswordField(20);
        JComboBox cbOpInventario = new JComboBox();
        JLabel lbTitulo = new JLabel("Opção de Inventário");
        cbOpInventario.addItem("Entrada de Produtos");
        cbOpInventario.addItem("Saída de Produtos");
        JPanel entUsuario = new JPanel();
        entUsuario.add(lbTitulo);
        entUsuario.add(cbOpInventario);
        //JOption com JPanel
        JOptionPane.showMessageDialog(null, entUsuario, "Inventário de Produtos", JOptionPane.QUESTION_MESSAGE);
        if (cbOpInventario.getSelectedItem().equals("Entrada de Produtos")) {
            abrirTelaInventario("E");
        }
        if (cbOpInventario.getSelectedItem().equals("Saída de Produtos")) {
            abrirTelaInventario("S");

        }

    }//GEN-LAST:event_btInventarioActionPerformed

    private void btGerenciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGerenciarActionPerformed
        abrirTelaInventarioGerenciar();
    }//GEN-LAST:event_btGerenciarActionPerformed

    private void btConfiguracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfiguracaoActionPerformed

        acaoBotaoConfiguracao();
    }//GEN-LAST:event_btConfiguracaoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btConfiguracao;
    private javax.swing.JButton btGerenciar;
    private javax.swing.JButton btInventario;
    private javax.swing.JButton btProduto;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

}
