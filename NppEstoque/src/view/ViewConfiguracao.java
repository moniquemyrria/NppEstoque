package view;

import controller_unificados.CampoValidacaoController;
import controller_unificados.MensagemValidacaoController;
import controller_unificados.NumeroValidacaoController;
import java.sql.Connection;
import model_bean.ConfiguracaoModel;
import model_conexao.Conexao;
import controller_dao.ConfiguracaoDao;

public class ViewConfiguracao extends javax.swing.JFrame {
    
    
    public ViewConfiguracao() {

        initComponents();

        //Centralizar tela
        this.setLocationRelativeTo(null);

        //Retirando a Mximização da tela
        this.setResizable(false);

        //campo Codigo radar aceitando apenas numeros
        txtPorta.setDocument(new NumeroValidacaoController());

        consultaConexao();
        desabilitaCampos();
        desabilitaBotoes();
        btAlterar.setEnabled(true);
        btTestarConexao.setEnabled(true);
        
       


    }

    private void desabilitaCampos() {
        txtNomeBanco.setEditable(false);
        txtPorta.setEditable(false);
        txtSenha.setEditable(false);
        txtServidor.setEditable(false);
        txtUsuario.setEditable(false);
    }

    private void habilitaCampos() {
        txtNomeBanco.setEditable(true);
        txtPorta.setEditable(true);
        txtSenha.setEditable(true);
        txtServidor.setEditable(true);
        txtUsuario.setEditable(true);
    }

    private void desabilitaBotoes() {
        btAlterar.setEnabled(false);
        btCancelar.setEnabled(false);
        btSalvar.setEnabled(false);
        btTestarConexao.setEnabled(false);
    }

    private boolean validaCamposCadastroFormulario() {
        CampoValidacaoController campo = new CampoValidacaoController();
        if (campo.validaCampoTxtField(txtServidor, "Servidor")) {
            return false;
        } else if (campo.validaCampoTxtField(txtPorta, "Porta de Conexão")) {
            return false;
        } else if (campo.validaCampoTxtField(txtNomeBanco, "Nome do Banco de Dados")) {
            return false;
        } else if (campo.validaCampoTxtField(txtUsuario, "Usuário")) {
            return false;
        } else if (campo.validaCampoTxtField(txtSenha, "Senha")) {
            return false;
        } else {
            return true;
        }
    }

    private void salvar() {
        ConfiguracaoModel configModel = new ConfiguracaoModel();
        ConfiguracaoDao configDao = new ConfiguracaoDao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        configModel.setNomeBanco(txtNomeBanco.getText());
        configModel.setPorta(txtPorta.getText());
        configModel.setSenha(txtSenha.getText());
        configModel.setServidor(txtServidor.getText());
        configModel.setUsuario(txtUsuario.getText());
        try {
            configDao.salvar(configModel);
            msg.mensagemInformacao("Configuração salva com sucesso.");
        } catch (Exception e) {
            msg.mensagemErro("Não foi possível salvar aconfiguração.");
        }
    }

    private void consultaConexao() {
        ConfiguracaoModel configModel = new ConfiguracaoModel();
        ConfiguracaoDao configDao = new ConfiguracaoDao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        try {
            configModel = configDao.consultarArquivoConexaoDB();
            txtNomeBanco.setText(configModel.getNomeBanco());
            txtPorta.setText(configModel.getPorta());
            txtSenha.setText(configModel.getSenha());
            txtServidor.setText(configModel.getServidor());
            txtUsuario.setText(configModel.getUsuario());
        } catch (Exception e) {
            e.getMessage();
            //msg.mensagemErro("Arquivo de conexão com o Banco de Dados não localizado.\n"
            //        + "Verifique se o diretório: c:\\nppEstoque existe.\n"
            //        + "Caso não exista, o diretório deve ser criado manualmente.\nEm seguida realize a configuração de do banco de dados.");
        }
    }

    private void acaoSalvar() {
        if (validaCamposCadastroFormulario()) {
            salvar();
            acaoCancelar();
        }
    }

    private void acaoAlterar() {
        habilitaCampos();
        desabilitaBotoes();
        btCancelar.setEnabled(true);
        btSalvar.setEnabled(true);
        txtServidor.requestFocus();
    }

    private void acaoCancelar() {
        desabilitaCampos();
        desabilitaBotoes();
        btAlterar.setEnabled(true);
        btTestarConexao.setEnabled(true);
    }

    private void acaoTestarConexao() {
        Connection con = null;
        MensagemValidacaoController msg = new MensagemValidacaoController();
        con = (Connection) Conexao.getConexao();
            System.out.println(con);
        try {
            
            if (con != null) {
                msg.mensagemInformacao("Conexão com o Banco de dados " + txtNomeBanco.getText() + " estabelecida com sucesso.");
            } else {
                msg.mensagemErro("Não foi possível conectar ao Banco de dados " + txtNomeBanco.getText() + ". Verifique se todos os dados de conexão estão corretos.");
            }
        } catch (Exception e) {
            msg.mensagemErro("Não foi possível conectar ao Banco de dados " + txtNomeBanco.getText() + ". Verifique se todos os dados de conexão estão corretos.");
        }
        //Conexao.closeConexao(con);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlDados = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtServidor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPorta = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNomeBanco = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JTextField();
        pnlAcao2 = new javax.swing.JPanel();
        btSalvar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        pnlAcao = new javax.swing.JPanel();
        btTestarConexao = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Configuração do Banco de Dados");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
        });

        pnlDados.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Dados de Conexão com o Banco de Dados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel3.setText("Nome Servidor ou IP (Ex.: localhost / Servidor/ 192.168.0.1)");

        txtServidor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtServidorActionPerformed(evt);
            }
        });
        txtServidor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtServidorKeyPressed(evt);
            }
        });

        jLabel4.setText("Nome Banco de Dados");

        jLabel5.setText("Usuário de Conexão com o DB (Ex.: root)");

        txtUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });

        txtPorta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtPorta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPortaActionPerformed(evt);
            }
        });
        txtPorta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPortaKeyPressed(evt);
            }
        });

        jLabel6.setText("Porta de Conexão (Ex. MySQL: 3306 / Ex. JavaDB - Derby: 1527))");

        txtNomeBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtNomeBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeBancoActionPerformed(evt);
            }
        });
        txtNomeBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeBancoKeyPressed(evt);
            }
        });

        jLabel7.setText("Senha (Ex.: 1234)");

        txtSenha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlDadosLayout = new javax.swing.GroupLayout(pnlDados);
        pnlDados.setLayout(pnlDadosLayout);
        pnlDadosLayout.setHorizontalGroup(
            pnlDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDadosLayout.createSequentialGroup()
                        .addGroup(pnlDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeBanco)
                            .addGroup(pnlDadosLayout.createSequentialGroup()
                                .addGroup(pnlDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(pnlDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtPorta, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtServidor, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(jLabel4)
                                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(pnlDadosLayout.createSequentialGroup()
                        .addGroup(pnlDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlDadosLayout.setVerticalGroup(
            pnlDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlAcao2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Salvar.png"))); // NOI18N
        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/cancelar.png"))); // NOI18N
        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Alterar.png"))); // NOI18N
        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAcao2Layout = new javax.swing.GroupLayout(pnlAcao2);
        pnlAcao2.setLayout(pnlAcao2Layout);
        pnlAcao2Layout.setHorizontalGroup(
            pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcao2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAcao2Layout.setVerticalGroup(
            pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcao2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlAcao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btTestarConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Salvar.png"))); // NOI18N
        btTestarConexao.setText("Testar Conexão");
        btTestarConexao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTestarConexaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAcaoLayout = new javax.swing.GroupLayout(pnlAcao);
        pnlAcao.setLayout(pnlAcaoLayout);
        pnlAcaoLayout.setHorizontalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcaoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btTestarConexao, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAcaoLayout.setVerticalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btTestarConexao, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(186, 190, 198));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Configuração");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Banco de Dados");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAcao2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAcao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlAcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAcao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServidorActionPerformed

    }//GEN-LAST:event_txtServidorActionPerformed

    private void txtServidorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtServidorKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtPorta.requestFocus();
        }
    }//GEN-LAST:event_txtServidorKeyPressed

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtSenha.requestFocus();
        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtPortaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPortaActionPerformed

    }//GEN-LAST:event_txtPortaActionPerformed

    private void txtPortaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPortaKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtNomeBanco.requestFocus();
        }
    }//GEN-LAST:event_txtPortaKeyPressed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        acaoSalvar();
    }//GEN-LAST:event_btSalvarActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        acaoCancelar();
    }//GEN-LAST:event_btCancelarActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        acaoAlterar();
    }//GEN-LAST:event_btAlterarActionPerformed

    private void txtNomeBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeBancoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeBancoActionPerformed

    private void txtNomeBancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeBancoKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtUsuario.requestFocus();
        }
    }//GEN-LAST:event_txtNomeBancoKeyPressed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            acaoSalvar();
        }
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void btTestarConexaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTestarConexaoActionPerformed
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -jar \"lib\\derbyrun.jar\" server start");
            Conexao.getConexao();
            acaoTestarConexao();
        
        } catch (Throwable t) {
            t.printStackTrace();
       
        }
    }//GEN-LAST:event_btTestarConexaoActionPerformed

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden

        
    }//GEN-LAST:event_formComponentHidden

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
 
                
    }//GEN-LAST:event_formWindowClosed

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified

    }//GEN-LAST:event_formWindowIconified

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        ViewMenu telaMenu = new ViewMenu();
        telaMenu.Teste = "PASSOU AQUI";
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(ViewConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new ViewConfiguracao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JButton btTestarConexao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlAcao;
    private javax.swing.JPanel pnlAcao2;
    private javax.swing.JPanel pnlDados;
    private javax.swing.JTextField txtNomeBanco;
    private javax.swing.JTextField txtPorta;
    private javax.swing.JTextField txtSenha;
    private javax.swing.JTextField txtServidor;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
