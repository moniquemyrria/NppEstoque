package view;

import controller_dao.InventarioItemDao;
import controller_unificados.CampoValidacaoController;
import controller_unificados.GridListagemController;
import controller_unificados.DataValidacaoController;
import controller_unificados.MensagemValidacaoController;
import controller_unificados.NumeroValidacaoController;
import controller_unificados.PesquisaProdutoController;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import model_bean.ProdutoModel;
import controller_dao.ProdutoDao;
import controller_dao.ProdutoEstoqueDao;
import java.io.InputStream;

public class ViewProduto extends javax.swing.JFrame implements KeyListener {

    ProdutoDao conexaoProduto = new ProdutoDao();
    int statusAcao = 0; //1 - Novo Produto / 2 - Alterar / 3 - Excluir

    public ViewProduto() {
        initComponents();

        //campo Codigo radar aceitando apenas numeros
        txtCodigoRadar.setDocument(new NumeroValidacaoController());

        //Centralizar tela
        this.setLocationRelativeTo(null);

        //Retirando a Mximização da tela
        this.setResizable(false);

        //Carrega Pesquisa em Codigo Radar
        rbtPesqCodRadar.setSelected(true);

        //Carrega Definição Tamanho Grid de Listagem - apenas para vizualização estetivca do usuario
        tamanhoColunasGridListagem();

        //Adciona comando as Setas [Pra baixo] e [Pra cima] para carregar registros do grid de listagem nos campos de texto
        this.addKeyListener(this);
        tbGridListagemProd.addKeyListener(this);

        //Inicializando a Tela com comandos particulares
        desabilitaCampos();
        desabilitaBotoes();
        btNovo.setEnabled(true);
        habilitaPesquisa();

        //campo Codigo radar aceitando apenas numeros
        txtCodigoRadar.setDocument(new NumeroValidacaoController());
    }

    private void limparCampos() {
        txtCodigoRadar.setText("");
        txtNomeProduto.setText("");
        txtDescricaoProd.setText("");
        txtLocalEst.setText("");
        lbQtdeProdutoSelecionado.setText("Quantidade de produtos");
    }

    public void acaoBotaoNovoProduto() {
        GridListagemController gridListagem = new GridListagemController();
        statusAcao = 1; //1 - cadastro de Novo Produto
        limparCampos();
        habilitaCampos();
        desabilitaBotoes();
        desabilitaPesquisa();
        gridListagem.limparGridListagem(tbGridListagemProd);
        btSalvar.setEnabled(true);
        btCancelar.setEnabled(true);
    }

    private void acaoBotaoAlterarProduto() {
        statusAcao = 2; //2 - Alterar Produto
        habilitaCampos();
        desabilitaBotoes();
        desabilitaPesquisa();
        tbGridListagemProd.setEnabled(true);
        tbGridListagemProd.setRowSelectionAllowed(true);
        btSalvar.setEnabled(true);
        btCancelar.setEnabled(true);
    }

    private void acaoBotaoExcluirProduto() {
        statusAcao = 3; //3 - Excluir
        desabilitaBotoes();
        limparCampos();
        //limparGridListagemProduto();
        desabilitaBotoes();
        desabilitaCampos();
        habilitaPesquisa();
        listarPesquisa();
        habilitaAcaoExcluirAlterar();
        btNovo.setEnabled(true);
    }

    private void acaoBotaoCancelar() {
        desabilitaBotoes();
        limparCampos();
        //limparGridListagemProduto();
        desabilitaBotoes();
        desabilitaCampos();
        habilitaPesquisa();
        btNovo.setEnabled(true);
    }

    private void refreshSalvarProduto() {
        rbtPesqCodRadar.setSelected(true);
        rbtPesqDescrProd.setSelected(false);
        rbtPesqLocalEst.setSelected(false);
        rbtPesqNomeProd.setSelected(false);
        txtPesquisa.setText(txtCodigoRadar.getText());
        listarPesquisa();
        limparCampos();
        desabilitaCampos();
        desabilitaBotoes();
        habilitaPesquisa();
        btNovo.setEnabled(true);
        statusAcao = 0;
    }

    private void habilitaAcaoExcluirAlterar() {
        if (tbGridListagemProd.getRowCount() > 0) {
            if (txtCodigoRadar.getText().isEmpty()) {
            } else {
                btAlterar.setEnabled(true);
                btExcluir.setEnabled(true);
            }
        } else {
            btAlterar.setEnabled(false);
            btExcluir.setEnabled(false);
        }
    }

    private void pesquisa() {
        GridListagemController contaGrid = new GridListagemController();
        limparCampos();
        listarPesquisa();
        habilitaAcaoExcluirAlterar();
        lbQtdeProdutoSelecionado.setText("Quantidade: " + contaGrid.contadorGridListagem(tbGridListagemProd));
    }

    private void habilitaPesquisa() {
        txtPesquisa.requestFocus();
        rbtPesqCodRadar.setEnabled(true);
        rbtPesqDescrProd.setEnabled(true);
        rbtPesqLocalEst.setEnabled(true);
        rbtPesqNomeProd.setEnabled(true);
        txtPesquisa.setEnabled(true);
        btPesquisar.setEnabled(true);
        tbGridListagemProd.setEnabled(true);
        tbGridListagemProd.setRowSelectionAllowed(true);
    }

    private void habilitaCampos() {
        txtCodigoRadar.setEditable(true);
        txtNomeProduto.setEditable(true);
        txtDescricaoProd.setEditable(true);
        txtLocalEst.setEditable(true);

    }

    private void desabilitaCampos() {
        txtCodigoRadar.setEditable(false);
        txtNomeProduto.setEditable(false);
        txtDescricaoProd.setEditable(false);
        txtLocalEst.setEditable(false);
    }

    private void desabilitaPesquisa() {
        txtPesquisa.setText("");
        rbtPesqCodRadar.setEnabled(false);
        rbtPesqDescrProd.setEnabled(false);
        rbtPesqLocalEst.setEnabled(false);
        rbtPesqNomeProd.setEnabled(false);
        txtPesquisa.setEnabled(false);
        btPesquisar.setEnabled(false);
        tbGridListagemProd.setEnabled(false);
        tbGridListagemProd.setRowSelectionAllowed(false);
    }

    private void desabilitaBotoes() {
        btAlterar.setEnabled(false);
        btExcluir.setEnabled(false);
        btNovo.setEnabled(false);
        btSalvar.setEnabled(false);
        btPesquisar.setEnabled(false);
        btCancelar.setEnabled(false);
    }

    public void parametroRelatorio(String descricaoRelatorio, String campoSQL) {

        DataValidacaoController data = new DataValidacaoController();
        ProdutoDao prodDao = new ProdutoDao();
        GridListagemController gridListagem = new GridListagemController();
        InventarioItemDao invtItemDao = new InventarioItemDao();
        HashMap<String, Object> parametro = new HashMap<String, Object>();

        //Parametros de dados do relatorio
        parametro.put("descricaoRelatorio", descricaoRelatorio);
        parametro.put("quantidadeItens", "Quantidade de Itens: " + Integer.toString(gridListagem.contadorGridListagem(tbGridListagemProd)));
        
        InputStream nomeArqRelatorio = getClass().getResourceAsStream("/relatorios/ProdutoAtivo.jasper");
        prodDao.parametrosImpressaoRelatorio(nomeArqRelatorio, parametro, campoSQL, txtPesquisa.getText().toUpperCase());
        
    }
    
    private void impressao(){
        GridListagemController gridListagem = new GridListagemController();
        
        if (gridListagem.contadorGridListagem(tbGridListagemProd) > 0) {
            //Tipo 1 - Pesquisa por Codigo Radar
            if (rbtPesqCodRadar.isSelected()) {
                parametroRelatorio("Filtro por Descrição do Produto: " + txtPesquisa.getText().toUpperCase(), "p.codigo_radar");
            }
            
           //Tipo 2 - Pesquisa por Descrição do Produto
            if (rbtPesqDescrProd.isSelected()) {
                parametroRelatorio("Filtro por Descrição do Produto: " + txtPesquisa.getText().toUpperCase(), "p.descricao_produto");
            }
            
            //Tipo 3 - Nome Produto
            if (rbtPesqNomeProd.isSelected()) {
                parametroRelatorio("Filtro por Nome do Produto: " + txtPesquisa.getText().toUpperCase(), "p.nome_produto");
            }
            
            //Tipo 4 - Pesquisa por Local de Estoque
            if (rbtPesqLocalEst.isSelected()) {
                parametroRelatorio("Filtro por Local de Estoque: " + txtPesquisa.getText().toUpperCase(), "p.local_estoque");
                //carregaGridListagemProduto(txtPesquisa.getText().toUpperCase(), "local_estoque");
            }
        }
        else{
            MensagemValidacaoController msg = new MensagemValidacaoController();
            msg.mensagemAtencao("Não há nenhum registro para impressão.");
        }       
    }

    private void cadastrar() {
        ProdutoModel prodModel = new ProdutoModel();
        ProdutoDao prodDao = new ProdutoDao();
        prodModel.setCodigoRadar(txtCodigoRadar.getText().toUpperCase());
        prodModel.setNomeProduto(txtNomeProduto.getText().toUpperCase());
        prodModel.setDescricaoProduto(txtDescricaoProd.getText().toUpperCase());
        prodModel.setLocalEstoque(txtLocalEst.getText().toUpperCase());
        prodDao.salvar(prodModel);
        cadastrarEstoqueProduto(prodModel);
    }

    private void cadastrarEstoqueProduto(ProdutoModel prodModel) {
        //ProdutoModel prodEstModel = new ProdutoModel();
        ProdutoEstoqueDao prodEstDao = new ProdutoEstoqueDao();
        ProdutoDao prodDao = new ProdutoDao();
        DataValidacaoController data = new DataValidacaoController();
        int id = prodDao.consultaUltimoIdProduto();
        prodModel.setIdProduto(id);
        prodModel.setQuantidadeEstoque(0.0);
        prodModel.setDataUltimoInventario(data.converteDataSimplesDB(data.dataAtual()));
        prodEstDao.salvaEstoque(prodModel);
    }

    private void recuperarRegistroDuplicado(ProdutoModel prodModel) {
        ProdutoDao prodDao = new ProdutoDao();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (prodModel.getSituacaoDeletadoProduto().equals("S")) {
            int op = msg.retornoMensagemPergunta("Este produto ja foi cadastrado e posteriormente excluído. Deseja restaurar os dados do produto?");
            if ((op == 0)) {
                prodDao.alterar(prodModel, 2); //2 - Restaurar produto deletado
                refreshSalvarProduto();
            }
        }
    }

    private void alterar() {
        ProdutoModel prodModel = new ProdutoModel();
        ProdutoDao prodDao = new ProdutoDao();
        prodModel.setIdProduto(Integer.parseInt(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 0).toString()));
        prodModel.setCodigoRadar(txtCodigoRadar.getText().toUpperCase());
        prodModel.setNomeProduto(txtNomeProduto.getText().toUpperCase());
        prodModel.setDescricaoProduto(txtDescricaoProd.getText().toUpperCase());
        try {
            prodModel.setLocalEstoque(txtLocalEst.getText().toUpperCase());
        } catch (Exception e) {
            prodModel.setLocalEstoque("");
        }
        prodDao.alterar(prodModel, 1); //1 - Alterar dados dos produtos
    }

    private void excluir() {
        ProdutoModel prodModel = new ProdutoModel();
        ProdutoDao prodDao = new ProdutoDao();
        prodModel.setIdProduto(Integer.parseInt(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 0).toString()));
        prodDao.excluir(prodModel);
    }

    private void tamanhoColunasGridListagem() {
        tbGridListagemProd.setAutoResizeMode(0); //Desabilita tamanho automatico coluna
        tbGridListagemProd.getColumnModel().getColumn(0).setPreferredWidth(50);  //id
        tbGridListagemProd.getColumnModel().getColumn(1).setPreferredWidth(100); //Codigo Radar
        tbGridListagemProd.getColumnModel().getColumn(2).setPreferredWidth(200); //Nome Produto
        tbGridListagemProd.getColumnModel().getColumn(3).setPreferredWidth(400); //Descrição do Produto
        tbGridListagemProd.getColumnModel().getColumn(4).setPreferredWidth(100); //Quantidade
        tbGridListagemProd.getColumnModel().getColumn(5).setPreferredWidth(200); //Local Estoque
    }

    private void carregaGridListagemProduto(String stringPesquisa, String colunaPesquisa) {
        ProdutoDao prodDao = new ProdutoDao();
        List<ProdutoModel> lista = new ArrayList<ProdutoModel>();
        if (stringPesquisa.equals("") && colunaPesquisa.equals("")){
            lista = prodDao.listarTodos();
        }else{
            lista = prodDao.listar(stringPesquisa, colunaPesquisa);
        }
        tamanhoColunasGridListagem();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        GridListagemController gridListagem = new GridListagemController();
        try {
            Object coluna[] = {
                "Id",
                "Cod. Radar",
                "Nome do Produto",
                "Descrição do Produto",
                "Quantidade",
                "Local de Estoque"
            };
        } catch (Exception e) {
        }

        gridListagem.limparGridListagem(tbGridListagemProd);
        DefaultTableModel linha = (DefaultTableModel) tbGridListagemProd.getModel();
        try {
            for (ProdutoModel prodModel : lista) {
                linha.addRow(new Object[]{
                    prodModel.getIdProduto(),
                    prodModel.getCodigoRadar(),
                    prodModel.getNomeProduto(),
                    prodModel.getDescricaoProduto(),
                    prodModel.getQuantidadeEstoque(),
                    prodModel.getLocalEstoque()
                });
            }

        } catch (Exception e) {
            e.getMessage();
            System.out.println("Nao foi possivel carregar a Listagem n o grid");
        }

        if ((lista.size() == 0) && (statusAcao != 3)) {
            msg.mensagemAtencao("Nenhum registro localizado.");
        }
    }

    private void carregaItemSelecionado() {
        try {
            String id = (tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 0).toString());
            txtCodigoRadar.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 1).toString());
            txtNomeProduto.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 2).toString());
            txtDescricaoProd.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 3).toString());
            try {
                txtLocalEst.setText(tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 5).toString());
            } catch (Exception e) {
                e.getMessage();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private boolean validaCamposCadastroFormulario() {
        CampoValidacaoController campo = new CampoValidacaoController();
        if (campo.validaCampoTxtField(txtCodigoRadar, "Código Radar")) {
            return false;
        } else if (campo.validaCampoTxtField(txtNomeProduto, "Nome do Produto")) {
            return false;
        } else {
            return true;
        }

    }

    /*private boolean validaCampoPesquisa() {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (txtPesquisa.getText().trim().length() == 0) {
            msg.mensagemAtencao("Informe os dados para realizar a pesquisa!");
            txtPesquisa.requestFocus();
            return false;
        } else {
            return true;
        }
    }*/
    private boolean validaTamanhoCamposFormulario() {
        int tamCodRadar, tamDescProd, tamLocalEst, tamNomeProd;
        tamCodRadar = txtCodigoRadar.getText().length();
        tamDescProd = txtDescricaoProd.getText().length();
        tamLocalEst = txtLocalEst.getText().length();
        tamNomeProd = txtNomeProduto.getText().length();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (tamCodRadar > 100) {
            msg.mensagemAtencao("Tamanho do campo Codigo Radar maior que o permitido. Limite 100 caracteres.");
            txtCodigoRadar.requestFocus();
            return false;
        } else if (tamNomeProd > 100) {
            msg.mensagemAtencao("Tamanho do campo Nome do Produto maior que o permitido. Limite 100 caracteres.");
            txtNomeProduto.requestFocus();
            return false;
        } else if (tamDescProd > 1000) {
            msg.mensagemAtencao("Tamanho do campo Descrição do Produto maior que o permitido. Limite 100 caracteres.");
            txtDescricaoProd.requestFocus();
            return false;
        } else if (tamLocalEst > 100) {
            msg.mensagemAtencao("Tamanho do campo Local de Estoque maior que o permitido. Limite 100 caracteres.");
            txtLocalEst.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean verificaProdutoDuplicado(ProdutoModel prodModel) {
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (prodModel.getCodigoRadar() != null) {
            msg.mensagemAtencao("Este produto já está cadastrado no sistema."
                    + "\n\n ID: " + prodModel.getIdProduto()
                    + "\nCODIGO RADAR: " + prodModel.getCodigoRadar()
                    + "\nNOME PRODUTO: " + prodModel.getNomeProduto()
                    + "\nDESCRIÇÃO DO PRODUTO: " + prodModel.getDescricaoProduto()
            );
            return true;
        } else {
            return false;
        }
    }

    private void listarPesquisa() {
        PesquisaProdutoController pesqProduto = new PesquisaProdutoController();

        if ((pesqProduto.validaCampoPesquisa(txtPesquisa)) && (rbtPesqTodos.isSelected() == false)) {
            //Tipo 1 - Pesquisa por Codigo Radar
            if (rbtPesqCodRadar.isSelected()) {
                carregaGridListagemProduto(txtPesquisa.getText().toUpperCase(), "codigo_radar");
            }

            //Tipo 2 - Pesquisa por Descrição do Produto
            if (rbtPesqDescrProd.isSelected()) {
                carregaGridListagemProduto(txtPesquisa.getText().toUpperCase(), "descricao_produto");
            }

            //Tipo 3 - Pesquisa por Local de Estoque
            if (rbtPesqLocalEst.isSelected()) {
                carregaGridListagemProduto(txtPesquisa.getText().toUpperCase(), "local_estoque");
            }

            //Tipo 4 - Nome Produto
            if (rbtPesqNomeProd.isSelected()) {
                carregaGridListagemProduto(txtPesquisa.getText().toUpperCase(), "nome_produto");
            }
        }else if ((pesqProduto.validaCampoPesquisa(txtPesquisa)) && (rbtPesqTodos.isSelected())){
             //Tipo 5 - Todos os Produtos
            if (rbtPesqTodos.isSelected()) {
                txtPesquisa.setText("");
                carregaGridListagemProduto("", "");
            }
        }
    }

    private void salvar() {
        if (validaCamposCadastroFormulario()) {
            ProdutoDao prodDao = new ProdutoDao();
            ProdutoModel prodModel = new ProdutoModel();
            //1 - Novo Produto
            if (statusAcao == 1) {
                if (validaTamanhoCamposFormulario()) {
                    //Verifica se codigo radar esta duplicado
                    prodModel = prodDao.consultar(txtCodigoRadar.getText().toUpperCase(), "0", 1);
                    if (verificaProdutoDuplicado(prodModel)) {
                        try {
                            txtCodigoRadar.requestFocus();
                            recuperarRegistroDuplicado(prodModel);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else {
                        //Verifica se nome produto esta duplicado
                        prodModel = prodDao.consultar(txtNomeProduto.getText().toUpperCase(), "0", 3);
                        if (verificaProdutoDuplicado(prodModel)) {
                            try {
                                txtNomeProduto.requestFocus();
                                recuperarRegistroDuplicado(prodModel);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                        } else {
                            cadastrar();
                            refreshSalvarProduto();
                        }
                    }
                }
            }

            //alterar produto
            if (statusAcao == 2) {
                if (validaTamanhoCamposFormulario()) {
                    String id = (tbGridListagemProd.getValueAt(tbGridListagemProd.getSelectedRow(), 0).toString());
                    prodModel = prodDao.consultar(txtCodigoRadar.getText(), id, 1);
                    if (verificaProdutoDuplicado(prodModel)) {
                        txtCodigoRadar.requestFocus();
                    } else {
                        //Verifica se nome produto esta duplicado
                        prodModel = prodDao.consultar(txtNomeProduto.getText().toUpperCase(), id, 3);
                        if (verificaProdutoDuplicado(prodModel)) {
                            txtNomeProduto.requestFocus();
                        } else {
                            alterar();
                            refreshSalvarProduto();
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAcao2 = new javax.swing.JPanel();
        btSalvar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        pnlDadosProd = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigoRadar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLocalEst = new javax.swing.JTextField();
        txtNomeProduto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDescricaoProd = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlAcao = new javax.swing.JPanel();
        btNovo = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        pnlPesquisa = new javax.swing.JPanel();
        rbtPesqCodRadar = new javax.swing.JRadioButton();
        rbtPesqDescrProd = new javax.swing.JRadioButton();
        rbtPesqLocalEst = new javax.swing.JRadioButton();
        txtPesquisa = new javax.swing.JTextField();
        rbtPesqNomeProd = new javax.swing.JRadioButton();
        btPesquisar = new javax.swing.JButton();
        rbtPesqTodos = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGridListagemProd = new javax.swing.JTable();
        pnlAcao3 = new javax.swing.JPanel();
        lbQtdeProdutoSelecionado = new javax.swing.JLabel();
        btImprimir = new javax.swing.JButton();

        setTitle("Cadastro de Produtos");

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

        javax.swing.GroupLayout pnlAcao2Layout = new javax.swing.GroupLayout(pnlAcao2);
        pnlAcao2.setLayout(pnlAcao2Layout);
        pnlAcao2Layout.setHorizontalGroup(
            pnlAcao2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcao2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlDadosProd.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Dados do Produto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N

        jLabel3.setText("Código Radar *");

        txtCodigoRadar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtCodigoRadar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoRadarActionPerformed(evt);
            }
        });
        txtCodigoRadar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoRadarKeyPressed(evt);
            }
        });

        jLabel4.setText("Descrição do Produto *");

        jLabel5.setText("Local de Estoque");

        txtLocalEst.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtLocalEst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLocalEstKeyPressed(evt);
            }
        });

        txtNomeProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtNomeProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeProdutoActionPerformed(evt);
            }
        });
        txtNomeProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeProdutoKeyPressed(evt);
            }
        });

        jLabel6.setText("Nome do Produto *");

        txtDescricaoProd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtDescricaoProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescricaoProdKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlDadosProdLayout = new javax.swing.GroupLayout(pnlDadosProd);
        pnlDadosProd.setLayout(pnlDadosProdLayout);
        pnlDadosProdLayout.setHorizontalGroup(
            pnlDadosProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosProdLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDadosProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDadosProdLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(421, 421, 421))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLocalEst, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescricaoProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomeProduto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoRadar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnlDadosProdLayout.setVerticalGroup(
            pnlDadosProdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosProdLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoRadar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDescricaoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtLocalEst, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(186, 190, 198));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Cadastro");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("de Produtos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        pnlAcao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Add2.png"))); // NOI18N
        btNovo.setText("Novo Produto");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        btAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Alterar.png"))); // NOI18N
        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Excluir.png"))); // NOI18N
        btExcluir.setText("Excluír");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAcaoLayout = new javax.swing.GroupLayout(pnlAcao);
        pnlAcao.setLayout(pnlAcaoLayout);
        pnlAcaoLayout.setHorizontalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAcaoLayout.setVerticalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pnlPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Pesquisa de Produtos ... ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N

        rbtPesqCodRadar.setText("Codigo Radar");
        rbtPesqCodRadar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPesqCodRadarActionPerformed(evt);
            }
        });

        rbtPesqDescrProd.setText("Descrição Produto");
        rbtPesqDescrProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPesqDescrProdActionPerformed(evt);
            }
        });

        rbtPesqLocalEst.setText("Local de Estoque");
        rbtPesqLocalEst.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rbtPesqLocalEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPesqLocalEstActionPerformed(evt);
            }
        });

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

        rbtPesqNomeProd.setText("Nome Produto");
        rbtPesqNomeProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPesqNomeProdActionPerformed(evt);
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

        rbtPesqTodos.setText("Todos");
        rbtPesqTodos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rbtPesqTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtPesqTodosActionPerformed(evt);
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
                        .addComponent(rbtPesqCodRadar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rbtPesqNomeProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(rbtPesqDescrProd)
                        .addGap(18, 18, 18)
                        .addComponent(rbtPesqLocalEst, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(rbtPesqTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(pnlPesquisaLayout.createSequentialGroup()
                        .addComponent(txtPesquisa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        pnlPesquisaLayout.setVerticalGroup(
            pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtPesqCodRadar)
                    .addComponent(rbtPesqDescrProd)
                    .addComponent(rbtPesqLocalEst)
                    .addComponent(rbtPesqNomeProd)
                    .addComponent(rbtPesqTodos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbGridListagemProd.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 204, 204))); // NOI18N
        tbGridListagemProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cod. Radar", "Nome do Produto", "Descrição do Produto", "Quantidade", "Local de Estoque"
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

        lbQtdeProdutoSelecionado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbQtdeProdutoSelecionado.setText("Quantidade de produtos");
        lbQtdeProdutoSelecionado.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/rgb (1).png"))); // NOI18N
        btImprimir.setText("Imprimir");
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAcao3Layout = new javax.swing.GroupLayout(pnlAcao3);
        pnlAcao3.setLayout(pnlAcao3Layout);
        pnlAcao3Layout.setHorizontalGroup(
            pnlAcao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcao3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbQtdeProdutoSelecionado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlAcao3Layout.setVerticalGroup(
            pnlAcao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAcao3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlAcao3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbQtdeProdutoSelecionado))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlAcao2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAcao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDadosProd, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAcao3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pnlDadosProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlAcao3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAcao2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_btSalvarActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        acaoBotaoCancelar();
    }//GEN-LAST:event_btCancelarActionPerformed

    private void txtCodigoRadarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoRadarActionPerformed

    }//GEN-LAST:event_txtCodigoRadarActionPerformed

    private void txtCodigoRadarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoRadarKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtNomeProduto.requestFocus();
        }
    }//GEN-LAST:event_txtCodigoRadarKeyPressed

    private void txtLocalEstKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLocalEstKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            salvar();
        }
    }//GEN-LAST:event_txtLocalEstKeyPressed

    private void txtNomeProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeProdutoActionPerformed

    }//GEN-LAST:event_txtNomeProdutoActionPerformed

    private void txtNomeProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeProdutoKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtDescricaoProd.requestFocus();
        }
    }//GEN-LAST:event_txtNomeProdutoKeyPressed

    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
        acaoBotaoNovoProduto();
    }//GEN-LAST:event_btNovoActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        acaoBotaoAlterarProduto();
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        MensagemValidacaoController msg = new MensagemValidacaoController();
        if (msg.retornoMensagemPergunta("Deseja confirmar a exclusão do regitro " + txtCodigoRadar.getText()) == 0) {
            excluir();
            acaoBotaoExcluirProduto();
            statusAcao = 0;
        }
    }//GEN-LAST:event_btExcluirActionPerformed

    private void rbtPesqCodRadarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPesqCodRadarActionPerformed
        rbtPesqDescrProd.setSelected(false);
        rbtPesqLocalEst.setSelected(false);
        rbtPesqNomeProd.setSelected(false);
        rbtPesqTodos.setSelected(false);
        txtPesquisa.requestFocus();
    }//GEN-LAST:event_rbtPesqCodRadarActionPerformed

    private void rbtPesqDescrProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPesqDescrProdActionPerformed
        rbtPesqCodRadar.setSelected(false);
        rbtPesqLocalEst.setSelected(false);
        rbtPesqNomeProd.setSelected(false);
        rbtPesqTodos.setSelected(false);
        txtPesquisa.requestFocus();
    }//GEN-LAST:event_rbtPesqDescrProdActionPerformed

    private void rbtPesqLocalEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPesqLocalEstActionPerformed
        rbtPesqCodRadar.setSelected(false);
        rbtPesqDescrProd.setSelected(false);
        rbtPesqNomeProd.setSelected(false);
        rbtPesqTodos.setSelected(false);
        txtPesquisa.requestFocus();
    }//GEN-LAST:event_rbtPesqLocalEstActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed

    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            pesquisa();
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void rbtPesqNomeProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPesqNomeProdActionPerformed
        rbtPesqCodRadar.setSelected(false);
        rbtPesqDescrProd.setSelected(false);
        rbtPesqLocalEst.setSelected(false);
        rbtPesqTodos.setSelected(false);
        txtPesquisa.requestFocus();
    }//GEN-LAST:event_rbtPesqNomeProdActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        pesquisa();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void tbGridListagemProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGridListagemProdMouseClicked
        carregaItemSelecionado();
        habilitaAcaoExcluirAlterar();
    }//GEN-LAST:event_tbGridListagemProdMouseClicked

    private void txtDescricaoProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoProdKeyPressed
        int teclaSeta = evt.getKeyCode();
        if (teclaSeta == evt.VK_ENTER) {
            txtLocalEst.requestFocus();
        }
    }//GEN-LAST:event_txtDescricaoProdKeyPressed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        impressao();
    }//GEN-LAST:event_btImprimirActionPerformed

    private void rbtPesqTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtPesqTodosActionPerformed
        rbtPesqCodRadar.setSelected(false);
        rbtPesqDescrProd.setSelected(false);
        rbtPesqLocalEst.setSelected(false);
        rbtPesqNomeProd.setSelected(false);
        btPesquisar.requestFocus();
    }//GEN-LAST:event_rbtPesqTodosActionPerformed

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
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JButton btSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbQtdeProdutoSelecionado;
    private javax.swing.JPanel pnlAcao;
    private javax.swing.JPanel pnlAcao2;
    private javax.swing.JPanel pnlAcao3;
    private javax.swing.JPanel pnlDadosProd;
    private javax.swing.JPanel pnlPesquisa;
    private javax.swing.JRadioButton rbtPesqCodRadar;
    private javax.swing.JRadioButton rbtPesqDescrProd;
    private javax.swing.JRadioButton rbtPesqLocalEst;
    private javax.swing.JRadioButton rbtPesqNomeProd;
    private javax.swing.JRadioButton rbtPesqTodos;
    private javax.swing.JTable tbGridListagemProd;
    private javax.swing.JTextField txtCodigoRadar;
    private javax.swing.JTextField txtDescricaoProd;
    private javax.swing.JTextField txtLocalEst;
    private javax.swing.JTextField txtNomeProduto;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int teclaSeta = ke.getKeyCode();
        if (teclaSeta == KeyEvent.VK_UP) {
            carregaItemSelecionado();
            habilitaAcaoExcluirAlterar();
        }
        if (teclaSeta == KeyEvent.VK_DOWN) {
            carregaItemSelecionado();
            habilitaAcaoExcluirAlterar();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
