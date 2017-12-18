package view;

import controller_unificados.DataValidacaoController;
import controller_unificados.GridListagemController;
import controller_unificados.MensagemValidacaoController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model_bean.InventarioItemModel;
import controller_dao.InventarioItemDao;
import java.io.InputStream;

public class ViewInventarioGerenciar extends javax.swing.JFrame {

    public ViewInventarioGerenciar() {
        initComponents();
        //Centralizar tela
        this.setLocationRelativeTo(null);

        //Mximização da tela
        this.setResizable(false);

        tamanhoColunasGridListagemNaoAgupado();
        dataPadrao();
        limparPesquisa();
    }

    public void parametroRelatorio(String tipoRelatorio, String tituloRelatorio, String descricaoRelatorio, int tipoImpressao) {

        DataValidacaoController data = new DataValidacaoController();
        GridListagemController gridListagem = new GridListagemController();
        InventarioItemDao invtItemDao = new InventarioItemDao();
        HashMap<String, Object> parametro = new HashMap<String, Object>();
        String dataInicio = data.converteDataDB(dcDataInicio);
        String dataFinal = data.converteDataDB(dcDataFinal);

        //Parametros de dados do relatorio
        parametro.put("dataInicial", dataInicio);
        parametro.put("dataFinal", dataFinal);
        parametro.put("tipoInventario", tipoRelatorio);
        parametro.put("tituloRelatorio", tituloRelatorio);
        parametro.put("infoPesquisa", data.converteDataLayout(dcDataInicio) + " a " + data.converteDataLayout(dcDataFinal));
        parametro.put("descricaoRelatorio", descricaoRelatorio);
        parametro.put("quantidadeItens", "Quantidade de Itens: " + Integer.toString(gridListagem.contadorGridListagem(tbGridListagemProd)));

        //1 - Listagem Itens da Entrada ou Saida
        if (tipoImpressao == 1) {
            InputStream nomeArqRelatorio = getClass().getResourceAsStream("/relatorios/InventarioItensNaoAgrupado.jasper");
            invtItemDao.parametrosImpressaoRelatorioItens(nomeArqRelatorio, parametro);
        }

        //2 - Listagem Itens Agrupados
        if (tipoImpressao == 2) {
            InputStream nomeArqRelatorio = getClass().getResourceAsStream("/relatorios/InventarioItensAgrupado.jasper");
            invtItemDao.parametrosImpressaoRelatorioItens(nomeArqRelatorio, parametro);
        }

    }

    private void limparPesquisa() {
        GridListagemController gridListagem = new GridListagemController();
        rbtEntrada.setSelected(true);
        rbtProdEntSaida.setSelected(true);
        rbtSaida.setSelected(false);
        rbtProdAgrupado.setSelected(false);
        dataPadrao();
        lbQtdeProdutoSelecionado.setText("Quantidade de Produtos");
        gridListagem.limparGridListagem(tbGridListagemProd);
    }

    public void dataPadrao() {
        DataValidacaoController data = new DataValidacaoController();
        dcDataInicio.setDate(data.dataAtual());
        ((JTextField) dcDataInicio.getDateEditor()).setEditable(false);
        dcDataFinal.setDate(data.dataAtual());
        ((JTextField) dcDataFinal.getDateEditor()).setEditable(false);

    }

    private void tamanhoColunasGridListagemNaoAgupado() {
        tbGridListagemProd.setAutoResizeMode(0); //Desabilita tamanho automatico coluna
        tbGridListagemProd.getColumnModel().getColumn(0).setPreferredWidth(150); //Codigo Radar
        tbGridListagemProd.getColumnModel().getColumn(1).setPreferredWidth(300); //nome produto
        tbGridListagemProd.getColumnModel().getColumn(2).setPreferredWidth(450); //Descrição do Produto
        tbGridListagemProd.getColumnModel().getColumn(3).setPreferredWidth(150); //Lote
        tbGridListagemProd.getColumnModel().getColumn(4).setPreferredWidth(150); //Local estoque
        tbGridListagemProd.getColumnModel().getColumn(5).setPreferredWidth(150); //Quantidade
        tbGridListagemProd.getColumnModel().getColumn(6).setPreferredWidth(150); //Data
        tbGridListagemProd.setAutoCreateRowSorter(true); //PERMITIR ORDENAÇÃO POR COLUNA
    }

    private void tamanhoColunasGridListagemAgupado() {
        tbGridListagemProd.setAutoResizeMode(0); //Desabilita tamanho automatico coluna
        tbGridListagemProd.getColumnModel().getColumn(0).setPreferredWidth(150); //Codigo Radar
        tbGridListagemProd.getColumnModel().getColumn(1).setPreferredWidth(300); //nome produto
        tbGridListagemProd.getColumnModel().getColumn(2).setPreferredWidth(450); //Descrição do Produto
        tbGridListagemProd.getColumnModel().getColumn(3).setPreferredWidth(150); //Local estoque
        tbGridListagemProd.getColumnModel().getColumn(4).setPreferredWidth(150); //Quantidade
        tbGridListagemProd.setAutoCreateRowSorter(true); //PERMITIR ORDENAÇÃO POR COLUNA
    }

    private void carregaGridListagem(String tipoInventario, int tipoRelatorio) {
        InventarioItemDao inventaItemDao = new InventarioItemDao();
        List<InventarioItemModel> lista = new ArrayList<InventarioItemModel>();
        DataValidacaoController data = new DataValidacaoController();
        GridListagemController gridListagem = new GridListagemController();
        MensagemValidacaoController msg = new MensagemValidacaoController();
        gridListagem.limparGridListagem(tbGridListagemProd);
        gridListagem.excluirColunaGridListagem(tbGridListagemProd);
        //1 - Tipo Relatorio: NAO AGRUPADO
        if (tipoRelatorio == 1) {
            gridListagem.inserirColunaGridListagem(tbGridListagemProd, 7);
            String[] vetTitulo = {
                "Cod. Radar",
                "Nome do Produto",
                "Descrição do Produto",
                "Lote",
                "Local de Estoque",
                "Quantidade",
                "Data Entrada"};
            gridListagem.tituloGridListagem(tbGridListagemProd, 7, vetTitulo);
            tamanhoColunasGridListagemNaoAgupado();
            lista = inventaItemDao.listarPeriodoItemNaoAgrupado(data.converteDataDB(dcDataInicio), data.converteDataDB(dcDataFinal), tipoInventario);
            DefaultTableModel linha = (DefaultTableModel) tbGridListagemProd.getModel();
            try {
                for (InventarioItemModel invtItemModel : lista) {
                    linha.addRow(new Object[]{
                        invtItemModel.getCodigoRadar(),
                        invtItemModel.getNomeProduto(),
                        invtItemModel.getDescricaoProduto(),
                        invtItemModel.getLote(),
                        invtItemModel.getLocalEstoque(),
                        invtItemModel.getQuantidade(),
                        invtItemModel.getDataInventario(),});
                }

            } catch (Exception e) {
                e.getMessage();
                System.out.println("Nao foi possivel carregar a Listagem n o grid");
            }

        }

        //2 - Tipo Relatorio: AGRUPADO
        if (tipoRelatorio == 2) {
            gridListagem.inserirColunaGridListagem(tbGridListagemProd, 5);
            String[] vetTitulo = {
                "Cod. Radar",
                "Nome do Produto",
                "Descrição do Produto",
                "Local de Estoque",
                "Quantidade"};
            gridListagem.tituloGridListagem(tbGridListagemProd, 5, vetTitulo);
            tamanhoColunasGridListagemAgupado();
            lista = inventaItemDao.listarPeriodoItemAgrupado(data.converteDataDB(dcDataInicio), data.converteDataDB(dcDataFinal), tipoInventario);
            DefaultTableModel linha = (DefaultTableModel) tbGridListagemProd.getModel();
            try {
                for (InventarioItemModel invtItemModel : lista) {
                    linha.addRow(new Object[]{
                        invtItemModel.getCodigoRadar(),
                        invtItemModel.getNomeProduto(),
                        invtItemModel.getDescricaoProduto(),
                        invtItemModel.getLocalEstoque(),
                        invtItemModel.getQuantidade(),});
                }

            } catch (Exception e) {
                e.getMessage();
                System.out.println("Nao foi possivel carregar a Listagem n o grid");
            }
        }

        if (lista.size() == 0) {
            msg.mensagemAtencao("Nenhum registro localizado.");
        }
    }

    private void pesquisa() {
        GridListagemController gridListagem = new GridListagemController();
        //E - Entrada
        if (rbtEntrada.isSelected()) {
            //1 - Itens por Inventario
            if (rbtProdEntSaida.isSelected()) {
                carregaGridListagem("E", 1);
            }
            //2 - Itens Agrupados
            if (rbtProdAgrupado.isSelected()) {
                carregaGridListagem("E", 2);
            }
        }

        //S - Saida
        if (rbtSaida.isSelected()) {
            //1 - Itens por Inventario
            if (rbtProdEntSaida.isSelected()) {
                carregaGridListagem("S", 1);
            }
            //2 - Itens Agrupados
            if (rbtProdAgrupado.isSelected()) {
                carregaGridListagem("S", 2);
            }
        }
        lbQtdeProdutoSelecionado.setText("Quantidade de Produtos: " + gridListagem.contadorGridListagem(tbGridListagemProd));
    }

    private void impressao() {

        GridListagemController gridListagem = new GridListagemController();

        if (gridListagem.contadorGridListagem(tbGridListagemProd) > 0) {
            //E - Entrada
            if (rbtEntrada.isSelected()) {
                //1 - Itens por Inventario
                if (rbtProdEntSaida.isSelected()) {
                    parametroRelatorio("E", "Relatorio de Entrada de Produtos", "Listagem de Itens Não Agrupados", 1);
                }
                //2 - Itens Agrupados
                if (rbtProdAgrupado.isSelected()) {
                    parametroRelatorio("E", "Relatorio de Entrada de Produtos", "Listagem de Itens Agrupados", 2);
                }
            }

            //S - Saida
            if (rbtSaida.isSelected()) {
                //1 - Itens por Inventario
                if (rbtProdEntSaida.isSelected()) {
                    parametroRelatorio("S", "Relatorio de Saída de Produtos", "Listagem de Itens Não Agrupados", 1);
                }
                //2 - Itens Agrupados
                if (rbtProdAgrupado.isSelected()) {
                    parametroRelatorio("S", "Relatorio de Saída de Produtos", "Listagem de Itens Agrupados", 2);
                }
            }
        }else{
            MensagemValidacaoController msg = new MensagemValidacaoController();
            msg.mensagemAtencao("Não há nenhum registro para impressão.");
        }  
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAcao = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        dcDataInicio = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        dcDataFinal = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        rbtEntrada = new javax.swing.JRadioButton();
        rbtSaida = new javax.swing.JRadioButton();
        pnlPesquisa = new javax.swing.JPanel();
        rbtProdEntSaida = new javax.swing.JRadioButton();
        rbtProdAgrupado = new javax.swing.JRadioButton();
        btLimparPesquisa = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbQtdeProdutoSelecionado = new javax.swing.JLabel();
        btImpressao = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGridListagemProd = new javax.swing.JTable();

        setTitle("Gerenciar Entrada e Saída de Produtos");

        pnlAcao.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Filtros de Pesquisa ...", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204))));

        dcDataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dcDataInicioKeyPressed(evt);
            }
        });

        jLabel10.setText("a");

        dcDataFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dcDataFinalKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dcDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dcDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204))));

        rbtEntrada.setText("Entrada de Produtos");
        rbtEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtEntradaActionPerformed(evt);
            }
        });

        rbtSaida.setText("Saída de Produtos");
        rbtSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtSaidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rbtEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(rbtSaida)
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtEntrada)
                    .addComponent(rbtSaida))
                .addGap(12, 12, 12))
        );

        pnlPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N

        rbtProdEntSaida.setText("Produtos por Entrada ou Saída");
        rbtProdEntSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtProdEntSaidaActionPerformed(evt);
            }
        });

        rbtProdAgrupado.setText("Produtos Agrupados");
        rbtProdAgrupado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rbtProdAgrupado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtProdAgrupadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPesquisaLayout = new javax.swing.GroupLayout(pnlPesquisa);
        pnlPesquisa.setLayout(pnlPesquisaLayout);
        pnlPesquisaLayout.setHorizontalGroup(
            pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtProdEntSaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(rbtProdAgrupado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlPesquisaLayout.setVerticalGroup(
            pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtProdEntSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtProdAgrupado, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        btLimparPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Clean3.png"))); // NOI18N
        btLimparPesquisa.setText("Limpar");
        btLimparPesquisa.setToolTipText("Limpar campo Pesquisa");
        btLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparPesquisaActionPerformed(evt);
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

        jLabel3.setText("Tipo de Inventário");

        jLabel4.setText("Data de Emissão");

        jLabel5.setText("Tipo de Listagem para Produtos");

        javax.swing.GroupLayout pnlAcaoLayout = new javax.swing.GroupLayout(pnlAcao);
        pnlAcao.setLayout(pnlAcaoLayout);
        pnlAcaoLayout.setHorizontalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAcaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAcaoLayout.createSequentialGroup()
                        .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(pnlPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(19, 19, 19))
        );
        pnlAcaoLayout.setVerticalGroup(
            pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAcaoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(186, 190, 198));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Gerenciar");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setText("Entrada e Saída de Produtos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(500, Short.MAX_VALUE))
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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204))));

        lbQtdeProdutoSelecionado.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbQtdeProdutoSelecionado.setText("Quantidade de produtos");

        btImpressao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/rgb (1).png"))); // NOI18N
        btImpressao.setText("Imprimir");
        btImpressao.setToolTipText("Clique para realizar a pesquisa.");
        btImpressao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImpressaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbQtdeProdutoSelecionado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btImpressao, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btImpressao, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbQtdeProdutoSelecionado))
                .addContainerGap())
        );

        tbGridListagemProd.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(204, 204, 204))); // NOI18N
        tbGridListagemProd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Radar", "Nome do Produto", "Descrição do Produto", "Lote", "Local Estoque", "Quantidade", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlAcao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlAcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dcDataInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dcDataInicioKeyPressed

    }//GEN-LAST:event_dcDataInicioKeyPressed

    private void dcDataFinalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dcDataFinalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dcDataFinalKeyPressed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        pesquisa();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void btLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparPesquisaActionPerformed
        limparPesquisa();
    }//GEN-LAST:event_btLimparPesquisaActionPerformed

    private void rbtProdAgrupadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtProdAgrupadoActionPerformed
        rbtProdEntSaida.setSelected(false);
    }//GEN-LAST:event_rbtProdAgrupadoActionPerformed

    private void rbtProdEntSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtProdEntSaidaActionPerformed
        rbtProdAgrupado.setSelected(false);
    }//GEN-LAST:event_rbtProdEntSaidaActionPerformed

    private void tbGridListagemProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGridListagemProdMouseClicked

    }//GEN-LAST:event_tbGridListagemProdMouseClicked

    private void rbtSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtSaidaActionPerformed
        rbtEntrada.setSelected(false);
    }//GEN-LAST:event_rbtSaidaActionPerformed

    private void rbtEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtEntradaActionPerformed
        rbtSaida.setSelected(false);
    }//GEN-LAST:event_rbtEntradaActionPerformed

    private void btImpressaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImpressaoActionPerformed
        impressao();
    }//GEN-LAST:event_btImpressaoActionPerformed

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
            java.util.logging.Logger.getLogger(ViewInventarioGerenciar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewInventarioGerenciar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewInventarioGerenciar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewInventarioGerenciar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewInventarioGerenciar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btImpressao;
    private javax.swing.JButton btLimparPesquisa;
    private javax.swing.JButton btPesquisar;
    private com.toedter.calendar.JDateChooser dcDataFinal;
    private com.toedter.calendar.JDateChooser dcDataInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbQtdeProdutoSelecionado;
    private javax.swing.JPanel pnlAcao;
    private javax.swing.JPanel pnlPesquisa;
    private javax.swing.JRadioButton rbtEntrada;
    private javax.swing.JRadioButton rbtProdAgrupado;
    private javax.swing.JRadioButton rbtProdEntSaida;
    private javax.swing.JRadioButton rbtSaida;
    private javax.swing.JTable tbGridListagemProd;
    // End of variables declaration//GEN-END:variables

}
