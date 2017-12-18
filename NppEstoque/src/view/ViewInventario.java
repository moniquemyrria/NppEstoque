package view;

import controller_unificados.CampoValidacaoController;
import controller_unificados.GridListagemController;
import controller_unificados.CorController;
import controller_unificados.DataValidacaoController;
import controller_unificados.LayoutController;
import controller_unificados.MensagemValidacaoController;
import controller_unificados.NumeroValidacaoController;
import controller_unificados.PesquisaProdutoController;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model_bean.InventarioItemModel;
import model_bean.ProdutoEstoqueModel;
import model_bean.ProdutoModel;
import controller_dao.InventarioDao;
import controller_dao.InventarioItemDao;
import controller_dao.ProdutoDao;
import controller_dao.ProdutoEstoqueDao;

public class ViewInventario extends javax.swing.JFrame implements KeyListener {

    //Object Itens selecionados do Inventario
    List<InventarioItemModel> listaProdutosSelecionados = new ArrayList<InventarioItemModel>();
    //Objecto produto
    ProdutoModel prodModel = new ProdutoModel();
    //Tipos: E - Entrada / S - Saida
    String tipoInventario = null;
    String idProdutoPesquisa = null;

    public ViewInventario(String tipoInv) {
        initComponents();
        //Centralizar tela
        this.setLocationRelativeTo(null);
        tipoInventario = tipoInv;

        //Mximização da tela
        //this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);

        //Carrega Definição Tamanho Grid de Listagem - apenas para vizualização estetivca do usuario
        tamanhoColunasGridListagem();
        bloquearCampoDadoProduto();
        btRemover.setEnabled(false);

        //campo Codigo radar aceitando apenas numeros
        txtQtde.setDocument(new NumeroValidacaoController());

        //Data atual
        DataValidacaoController data = new DataValidacaoController();
        dcDataEmissao.setDate(data.dataAtual());
        ((JTextField) dcDataEmissao.getDateEditor()).setEditable(false);

        //Iniciar
        txtPesquisa.requestFocus();

        //Carrega Pesquisa em Codigo Radar
        rbtPesqCodRadar.setSelected(true);

        //Define titulo
        if (tipoInventario.equals("E")) {
            lblTitulo.setText("Entrada");
            btConfirmaInventario.setText("Confirmar Entrada");
            this.setTitle("Entrada de Produtos");
        }
        if (tipoInventario.equals("S")) {
            lblTitulo.setText("Saída");
            btConfirmaInventario.setText("Confirmar Saída");
            this.setTitle("Saída de Produtos");
        }
    }

    private void bloquearCampoDadoProduto() {
        txtCodigoRadar.setEditable(false);
        txtNomeProd.setEditable(false);
        txtDescricaoProd.setEditable(false);
        txtLocalEst.setEditable(false);
        txtQtdeEstAtual.setEditable(false);
    }

    private void limparCampoDadoProduto() {
        CorController cor = new CorController();
        ((JTextField) dcDataEmissao.getDateEditor()).setEditable(false);
        txtCodigoRadar.setText("");
        txtNomeProd.setText("");
        txtDescricaoProd.setText("");
        txtLocalEst.setText("");
        txtNumLote.setText("");
        txtQtde.setText("");
        txtQtdeEstAtual.setText("");
        txtQtdeEstAtual.setBackground(cor.cinzaPadrao());
        txtPesquisa.setText("");
        //Carrega Pesquisa em Codigo Radar
        rbtPesqCodRadar.setSelected(true);

    }

    private boolean validaCampoPreenchido() {
        CampoValidacaoController campo = new CampoValidacaoController();
        if (campo.validaCampoTxtField(txtCodigoRadar, "Nenhum produto selecionado.")) {
            txtPesquisa.requestFocus();
            return false;
        }
        if (campo.validaCampoTxtField(txtQtde, "Informe a quantidade para lançamento no estoque deste produto.")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validaCampoQuantidade() {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (Double.parseDouble(txtQtde.getText()) <= 0) {
            msg.mensagemErro("Quantidade invalida.");
            return false;
        } else {
            if (tipoInventario.equals("S")) {
                if (Double.parseDouble(txtQtde.getText()) > Double.parseDouble(txtQtdeEstAtual.getText())) {
                    msg.mensagemErro("Quantidade em estoque atual é insuficiente para a quantidade solicitada na saída.");
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    private void tamanhoColunasGridListagem() {
        tbGridListagemProd.setAutoResizeMode(0); //Desabilita tamanho automatico coluna
        tbGridListagemProd.getColumnModel().getColumn(0).setPreferredWidth(50);  //Sequencia
        tbGridListagemProd.getColumnModel().getColumn(1).setPreferredWidth(150); //Codigo Radar
        tbGridListagemProd.getColumnModel().getColumn(2).setPreferredWidth(200); //nome produto
        tbGridListagemProd.getColumnModel().getColumn(3).setPreferredWidth(450); //Descrição do Produto
        tbGridListagemProd.getColumnModel().getColumn(4).setPreferredWidth(150); //Lote
        tbGridListagemProd.getColumnModel().getColumn(5).setPreferredWidth(150); //Quantidade
    }

    private void consultaProdutoPesquisa(String stringPesquisa, int tipoPesquisa) {
        limparCampoDadoProduto();
        ProdutoDao prodDao = new ProdutoDao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        CorController cor = new CorController();
        if (tipoPesquisa == 1) {
            prodModel = prodDao.consultar(stringPesquisa, "0", 2);
        }
        if (tipoPesquisa == 3) {
            prodModel = prodDao.consultar(stringPesquisa, "0", 3);
        }

        if (prodModel.getIdProduto() > 0) {
            ProdutoEstoqueDao prodEstDao = new ProdutoEstoqueDao();
            idProdutoPesquisa = Integer.toString(prodModel.getIdProduto());
            txtCodigoRadar.setText(prodModel.getCodigoRadar());
            txtNomeProd.setText(prodModel.getNomeProduto());
            txtDescricaoProd.setText(prodModel.getDescricaoProduto());
            txtLocalEst.setText(prodModel.getLocalEstoque());
            txtQtdeEstAtual.setText(Double.toString(prodEstDao.consultaQtdeAtualProduto(prodModel.getIdProduto())));
            if (Double.parseDouble(txtQtdeEstAtual.getText()) == 0) {
                txtQtdeEstAtual.setForeground(Color.WHITE);
                txtQtdeEstAtual.setBackground(cor.nimbusRed());
            } else {
                txtQtdeEstAtual.setForeground(Color.WHITE);
                txtQtdeEstAtual.setBackground(cor.nimbusInfoBlue());
            }
            txtNumLote.requestFocus();
        } else {
            msg.mensagemAtencao("Nenhum registro localizado com este Código Radar.");
        }
    }

    private void addObjetoLista(InventarioItemModel dadosInventario) {
        listaProdutosSelecionados.add(dadosInventario);
    }

    private void addObjetoInventario() {
        InventarioItemModel dadosInventario = new InventarioItemModel();
        //InventarioDao inventarioDao = new InventarioDao();
        try {
            dadosInventario.setIdProduto(prodModel.getIdProduto());
            dadosInventario.setCodigoRadar(prodModel.getCodigoRadar());
            dadosInventario.setNomeProduto(prodModel.getNomeProduto());
            dadosInventario.setDescricaoProduto(prodModel.getDescricaoProduto());
            dadosInventario.setSituacaoDeletadoInventario("N");
            dadosInventario.setLote(txtNumLote.getText().toUpperCase());
            dadosInventario.setQuantidade(Double.parseDouble(txtQtde.getText()));
            addObjetoLista(dadosInventario); //Insere um objeto a lista 
            adcionaProdutoGridListagem(dadosInventario); //Insere um objeto no grid
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void habilitaAcaoRemoverProdGrid() {
        if (tbGridListagemProd.getRowCount() > 0) {
            btRemover.setEnabled(true);
        } else {
            btRemover.setEnabled(false);
        }
    }

    private void adcionaProdutoGridListagem(InventarioItemModel dadosInventario) {
        DefaultTableModel linha = (DefaultTableModel) tbGridListagemProd.getModel();
        try {
            linha.addRow(new Object[]{
                dadosInventario.getIdProduto(),
                dadosInventario.getCodigoRadar(),
                dadosInventario.getNomeProduto(),
                dadosInventario.getDescricaoProduto(),
                dadosInventario.getLote(),
                dadosInventario.getQuantidade(),});
        } catch (Exception e) {
            e.getMessage();
            System.out.println("Nao foi possivel carregar a Listagem n o grid");
        }
    }

    private Double verificaEstoqueReserva(String idProdPesquisa) { //InventarioItemModel dadosInventario
        Double reserva = 0.0;
        String id = null;
        for (int i = 0; i < tbGridListagemProd.getRowCount(); i++) {
            id = (tbGridListagemProd.getModel().getValueAt(i, 0).toString());
            if (id.equals(idProdPesquisa)) {//txtCodigoRadar.getText()))
                reserva = reserva + (Double.parseDouble(tbGridListagemProd.getModel().getValueAt(i, 5).toString()));
            }
        }
        System.out.println("Recerva: " + reserva);
        return reserva;
    }

    private boolean verificaEstoqueSaldo() {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        Double reservaProdutosSelecionados = 0.0, saldoEstoqueAtual = 0.0, saldo = 0.0;
        reservaProdutosSelecionados = verificaEstoqueReserva(idProdutoPesquisa);
        saldoEstoqueAtual = Double.parseDouble(txtQtdeEstAtual.getText());
        saldo = saldoEstoqueAtual - reservaProdutosSelecionados;
        if (Double.parseDouble(txtQtde.getText()) > saldo) {
            msg.mensagemErro("Não há estoque suficiente para inserir na Saída de Produtos \n\n"
                    + "Saldo Atual: " + txtQtdeEstAtual.getText() + "\n"
                    + "Saldo Reservado (produtos selecionados): " + reservaProdutosSelecionados + "\n"
                    + "Quantidade disponivel para saída: " + saldo
            );
            return false;
        } else {
            return true;
        }

    }

    private void removerProdutoGridListagem() {
        GridListagemController contaGrid = new GridListagemController();
        int linhaSelecionada = (tbGridListagemProd.getSelectedRow());
        System.out.println(linhaSelecionada);
        listaProdutosSelecionados.remove(linhaSelecionada);
        DefaultTableModel linha = (DefaultTableModel) tbGridListagemProd.getModel();
        linha.removeRow(linhaSelecionada);
        habilitaAcaoRemoverProdGrid();
        lbQtdeProdutoSelecionado.setText("Produtos selecionados: " + contaGrid.contadorGridListagem(tbGridListagemProd));
        //contadorGridListagemProduto();
    }

    private void addProdutoGridListagem() {
        GridListagemController contaGrid = new GridListagemController();
        if (validaCampoPreenchido()) {
            if (validaCampoQuantidade()) {
                if (tipoInventario.equals("E")) {
                    addObjetoInventario();
                    limparCampoDadoProduto();
                    txtPesquisa.requestFocus();
                    lbQtdeProdutoSelecionado.setText("Produtos selecionados: " + contaGrid.contadorGridListagem(tbGridListagemProd));
                }
                if (tipoInventario.equals("S")) {
                    if (verificaEstoqueSaldo()) {
                        addObjetoInventario();
                        limparCampoDadoProduto();
                        txtPesquisa.requestFocus();
                        lbQtdeProdutoSelecionado.setText("Produtos selecionados: " + contaGrid.contadorGridListagem(tbGridListagemProd));
                        idProdutoPesquisa = null;
                    }
                }
            }
        }

    }

    private void geraInventario() {
        InventarioDao inventarioDao = new InventarioDao();
        DataValidacaoController data = new DataValidacaoController();
        inventarioDao.salvar(data.converteDataDB(dcDataEmissao), tipoInventario);
    }

    private void insereIdInventarioItensSelecionados() {
        InventarioDao inventarioDao = new InventarioDao();
        int id = inventarioDao.consultaUltimoIdInventario();
        for (InventarioItemModel prodSelecionado : listaProdutosSelecionados) {
            prodSelecionado.setIdInventario(id);
        }
    }

    private void salvarInventarioItens() {
        InventarioItemDao inventarioItmDao = new InventarioItemDao();
        ProdutoModel prodModel = new ProdutoModel();
        for (InventarioItemModel prodSelecionado : listaProdutosSelecionados) {
            inventarioItmDao.salvar(prodSelecionado);
            prodModel = dadosEstoqueProduto(prodSelecionado);
            atualizarEstoqueProduto(prodModel);
        }
    }

    private ProdutoModel dadosEstoqueProduto(InventarioItemModel prodSelecionado) {
        ProdutoModel prodModel = new ProdutoModel();
        DataValidacaoController data = new DataValidacaoController();
        prodModel.setIdProduto(prodSelecionado.getIdProduto());
        prodModel.setDataUltimoInventario(data.converteDataDB(dcDataEmissao));
        prodModel.setQuantidadeEstoque(prodSelecionado.getQuantidade());
        return prodModel;
    }

    private void atualizarEstoqueProduto(ProdutoModel prodModel) {
        ProdutoEstoqueDao prodEstDao = new ProdutoEstoqueDao();
        Double qtdeAtualEstoque = prodEstDao.consultaQtdeAtualProduto(prodModel.getIdProduto());
        if (tipoInventario.equals("E")) {
            prodEstDao.atualizarEstoqueEntrada(prodModel, qtdeAtualEstoque);
        }
        if (tipoInventario.equals("S")) {
            prodEstDao.atualizarEstoqueSaida(prodModel, qtdeAtualEstoque);
        }

    }

    private void confirmaInventario() {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        GridListagemController gridListagem = new GridListagemController(); 
        DataValidacaoController data = new DataValidacaoController();
        try {
            if (gridListagem.contadorGridListagem(tbGridListagemProd) > 0) {
                geraInventario();
                insereIdInventarioItensSelecionados();
                salvarInventarioItens();
                limparCampoDadoProduto();
                gridListagem.limparGridListagem(tbGridListagemProd);
                btRemover.setEnabled(false);
                dcDataEmissao.setDate(data.dataAtual());
                if (tipoInventario.equals("E")) {
                    msg.mensagemInformacao("Entrada de produtos realizada com sucesso.");
                }
                if (tipoInventario.equals("S")) {
                    msg.mensagemInformacao("Saída de produtos realizada com sucesso.");
                }
                lbQtdeProdutoSelecionado.setText("Quantidade de produtos selecionados");
                txtPesquisa.requestFocus();
                listaProdutosSelecionados.clear();
            } else {
                if (tipoInventario.equals("E")) {
                    msg.mensagemAtencao("Nenhum produto selecionado para realização da entrada.");
                }
                if (tipoInventario.equals("S")) {
                    msg.mensagemAtencao("Nenhum produto selecionado para realização de saída.");
                }
            }
        } catch (Exception e) {
            e.getMessage();
            if (tipoInventario.equals("E")) {
                msg.mensagemErro("Não foi possível realizar a entrada.");
            }
            if (tipoInventario.equals("S")) {
                msg.mensagemErro("Não foi possível realizar a saída.");
            }
        }
    }

    private void carregaItemSelecionado() {
        try {
            String id = (tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 0).toString());
            txtCodigoRadar.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 1).toString());
            txtNomeProd.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 2).toString());
            txtDescricaoProd.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 3).toString());
            try {
                txtLocalEst.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 4).toString());
            } catch (Exception e) {
                e.getMessage();
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void pesquisa() {
        PesquisaProdutoController pesquisaProd = new PesquisaProdutoController();

        if (pesquisaProd.validaCampoPesquisa(txtPesquisa)) {
            //Tipo 1 - Pesquisa por Codigo Radar
            if (rbtPesqCodRadar.isSelected()) {
                consultaProdutoPesquisa(txtPesquisa.getText(), 1);
            }
            //Tipo 2 - Pesquisa por Nome do Produto
            if (rbtPesqNomeProd.isSelected()) {
                consultaProdutoPesquisa(txtPesquisa.getText(), 3);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlPesquisa = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btPesquisar = new javax.swing.JButton();
        btLimparPesquisa = new javax.swing.JButton();
        rbtPesqCodRadar = new javax.swing.JRadioButton();
        rbtPesqNomeProd = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        txtCodigoRadar = new javax.swing.JTextField();
        txtNomeProd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtLocalEst = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtQtdeEstAtual = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDescricaoProd = new javax.swing.JTextField();
        btCadastrar = new javax.swing.JButton();
        pnlAcao2 = new javax.swing.JPanel();
        btAddProd = new javax.swing.JButton();
        btRemover = new javax.swing.JButton();
        txtNumLote = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtQtde = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGridListagemProd = new javax.swing.JTable();
        pnlAcao3 = new javax.swing.JPanel();
        btConfirmaInventario = new javax.swing.JButton();
        lbQtdeProdutoSelecionado = new javax.swing.JLabel();
        pnlAcao = new javax.swing.JPanel();
        dcDataEmissao = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(186, 190, 198));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblTitulo.setText("Inventario");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("de Produtos ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitulo)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Pesquisa de Produtos ...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N

        txtPesquisa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });

        btPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Pesquisa.png"))); // NOI18N
        btPesquisar.setText("Pesquisar");
        btPesquisar.setToolTipText("Clique para realizar a pesquisa.");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        btLimparPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Clean3.png"))); // NOI18N
        btLimparPesquisa.setText(" Limpar");
        btLimparPesquisa.setToolTipText("Limpar campo Pesquisa");
        btLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparPesquisaActionPerformed(evt);
            }
        });

        rbtPesqCodRadar.setText("Codigo Radar");
        rbtPesqCodRadar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPesqCodRadarActionPerformed(evt);
            }
        });

        rbtPesqNomeProd.setText("Nome do Produto");
        rbtPesqNomeProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPesqNomeProdActionPerformed(evt);
            }
        });

        jLabel3.setText("Código Radar");

        txtCodigoRadar.setBackground(new java.awt.Color(240, 240, 240));
        txtCodigoRadar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCodigoRadar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoRadarActionPerformed(evt);
            }
        });

        txtNomeProd.setBackground(new java.awt.Color(240, 240, 240));
        txtNomeProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtNomeProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeProdActionPerformed(evt);
            }
        });

        jLabel10.setText("Nome do Produto ");

        jLabel4.setText("Descrição do Produto");

        txtLocalEst.setBackground(new java.awt.Color(240, 240, 240));
        txtLocalEst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtLocalEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocalEstActionPerformed(evt);
            }
        });

        jLabel5.setText("Local de Estoque");

        txtQtdeEstAtual.setBackground(new java.awt.Color(240, 240, 240));
        txtQtdeEstAtual.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtQtdeEstAtual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQtdeEstAtual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtQtdeEstAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdeEstAtualActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Estoque Atual");

        txtDescricaoProd.setBackground(new java.awt.Color(240, 240, 240));
        txtDescricaoProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtDescricaoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescricaoProdActionPerformed(evt);
            }
        });

        btCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add3.png"))); // NOI18N
        btCadastrar.setText("Cadastrar");
        btCadastrar.setToolTipText("Limpar campo Pesquisa");
        btCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPesquisaLayout = new javax.swing.GroupLayout(pnlPesquisa);
        pnlPesquisa.setLayout(pnlPesquisaLayout);
        pnlPesquisaLayout.setHorizontalGroup(
            pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisaLayout.createSequentialGroup()
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtCodigoRadar, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeProd, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescricaoProd, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLocalEst, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQtdeEstAtual)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlPesquisaLayout.createSequentialGroup()
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                                .addComponent(rbtPesqCodRadar)
                                .addGap(18, 18, 18)
                                .addComponent(rbtPesqNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtPesquisa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPesquisaLayout.setVerticalGroup(
            pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtPesqCodRadar)
                        .addComponent(rbtPesqNomeProd))
                    .addGroup(pnlPesquisaLayout.createSequentialGroup()
                        .addGap(0, 20, Short.MAX_VALUE)
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtLocalEst, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtQtdeEstAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescricaoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigoRadar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        pnlAcao2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btAddProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Add2.png"))); // NOI18N
        btAddProd.setText("Adcionar");
        btAddProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddProdActionPerformed(evt);
            }
        });

        btRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Excluir.png"))); // NOI18N
        btRemover.setText("Remover");
        btRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverActionPerformed(evt);
            }
        });

        txtNumLote.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtNumLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumLoteActionPerformed(evt);
            }
        });
        txtNumLote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumLoteKeyPressed(evt);
            }
        });

        jLabel6.setText("Nº do Lote *");

        txtQtde.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtQtde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtdeActionPerformed(evt);
            }
        });
        txtQtde.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtdeKeyPressed(evt);
            }
        });

        jLabel7.setText("Quantidade *");

        javax.swing.GroupLayout pnlAcao2Layout = new javax.swing.GroupLayout(pnlAcao2);
        pnlAcao2.setLayout(pnlAcao2Layout);
        pnlAcao2Layout.setHorizontalGroup(
            pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcao2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtNumLote, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAddProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAcao2Layout.setVerticalGroup(
            pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcao2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btAddProd, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAcao2Layout.createSequentialGroup()
                        .addGroup(pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumLote, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQtde, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        tbGridListagemProd.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 204, 204))); // NOI18N
        tbGridListagemProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cod. Radar", "Nome do Produto", "Descrição do Produto", "Lote", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbGridListagemProd.getTableHeader().setReorderingAllowed(false);
        tbGridListagemProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbGridListagemProdMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbGridListagemProd);

        pnlAcao3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btConfirmaInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Salvar.png"))); // NOI18N
        btConfirmaInventario.setText("Confirmar Inventario");
        btConfirmaInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmaInventarioActionPerformed(evt);
            }
        });

        lbQtdeProdutoSelecionado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbQtdeProdutoSelecionado.setText("Quantidade de produtos selecionados");

        javax.swing.GroupLayout pnlAcao3Layout = new javax.swing.GroupLayout(pnlAcao3);
        pnlAcao3.setLayout(pnlAcao3Layout);
        pnlAcao3Layout.setHorizontalGroup(
            pnlAcao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcao3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbQtdeProdutoSelecionado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btConfirmaInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAcao3Layout.setVerticalGroup(
            pnlAcao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcao3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btConfirmaInventario, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbQtdeProdutoSelecionado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlAcao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Dados do Inventário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N

        dcDataEmissao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dcDataEmissaoKeyPressed(evt);
            }
        });

        jLabel9.setText("Data Emissão");

        javax.swing.GroupLayout pnlAcaoLayout = new javax.swing.GroupLayout(pnlAcao);
        pnlAcao.setLayout(pnlAcaoLayout);
        pnlAcaoLayout.setHorizontalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcaoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAcaoLayout.setVerticalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcaoLayout.createSequentialGroup()
                .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dcDataEmissao, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAcao2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAcao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPesquisa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAcao3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlAcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAcao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlAcao3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        pesquisa();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void txtNumLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumLoteActionPerformed

    private void btAddProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddProdActionPerformed
        addProdutoGridListagem();
    }//GEN-LAST:event_btAddProdActionPerformed

    private void tbGridListagemProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGridListagemProdMouseClicked
        habilitaAcaoRemoverProdGrid();
    }//GEN-LAST:event_tbGridListagemProdMouseClicked

    private void btConfirmaInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmaInventarioActionPerformed
        confirmaInventario();
        //verificaEstoqueReservaDisponivel();
     }//GEN-LAST:event_btConfirmaInventarioActionPerformed

    private void txtCodigoRadarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoRadarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoRadarActionPerformed

    private void txtLocalEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocalEstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLocalEstActionPerformed

    private void btLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparPesquisaActionPerformed
        limparCampoDadoProduto();
        txtPesquisa.setText("");
        txtPesquisa.requestFocus();
    }//GEN-LAST:event_btLimparPesquisaActionPerformed

    private void btRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverActionPerformed
        removerProdutoGridListagem();
    }//GEN-LAST:event_btRemoverActionPerformed

    private void txtNomeProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeProdActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            pesquisa();
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed

    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtNumLoteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumLoteKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtQtde.requestFocus();
        }
    }//GEN-LAST:event_txtNumLoteKeyPressed

    private void txtQtdeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtdeKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            addProdutoGridListagem();
        }
    }//GEN-LAST:event_txtQtdeKeyPressed

    private void txtQtdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtdeActionPerformed

    private void dcDataEmissaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dcDataEmissaoKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtPesquisa.requestFocus();
        }
    }//GEN-LAST:event_dcDataEmissaoKeyPressed

    private void rbtPesqCodRadarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPesqCodRadarActionPerformed
        rbtPesqNomeProd.setSelected(false);
        txtPesquisa.requestFocus();
    }//GEN-LAST:event_rbtPesqCodRadarActionPerformed

    private void rbtPesqNomeProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPesqNomeProdActionPerformed
        rbtPesqCodRadar.setSelected(false);
        txtPesquisa.requestFocus();
    }//GEN-LAST:event_rbtPesqNomeProdActionPerformed

    private void txtQtdeEstAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtdeEstAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQtdeEstAtualActionPerformed

    private void txtDescricaoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescricaoProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescricaoProdActionPerformed

    private void btCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarActionPerformed
        ViewProduto telaCadProduto = new ViewProduto();
        telaCadProduto.acaoBotaoNovoProduto();
        telaCadProduto.setVisible(true);
    }//GEN-LAST:event_btCadastrarActionPerformed

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
            java.util.logging.Logger.getLogger(ViewInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LayoutController layoutView = new LayoutController();
                layoutView.layout();
                //new ViewInventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddProd;
    private javax.swing.JButton btCadastrar;
    private javax.swing.JButton btConfirmaInventario;
    private javax.swing.JButton btLimparPesquisa;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btRemover;
    private com.toedter.calendar.JDateChooser dcDataEmissao;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbQtdeProdutoSelecionado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlAcao;
    private javax.swing.JPanel pnlAcao2;
    private javax.swing.JPanel pnlAcao3;
    private javax.swing.JPanel pnlPesquisa;
    private javax.swing.JRadioButton rbtPesqCodRadar;
    private javax.swing.JRadioButton rbtPesqNomeProd;
    private javax.swing.JTable tbGridListagemProd;
    private javax.swing.JTextField txtCodigoRadar;
    private javax.swing.JTextField txtDescricaoProd;
    private javax.swing.JTextField txtLocalEst;
    private javax.swing.JTextField txtNomeProd;
    private javax.swing.JTextField txtNumLote;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtQtde;
    private javax.swing.JTextField txtQtdeEstAtual;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int teclaSeta = ke.getKeyCode();
        if (teclaSeta == KeyEvent.VK_UP) {
            habilitaAcaoRemoverProdGrid();
        }
        if (teclaSeta == KeyEvent.VK_DOWN) {
            habilitaAcaoRemoverProdGrid();
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
