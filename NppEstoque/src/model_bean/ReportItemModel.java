
package model_bean;


public class ReportItemModel {
    
    private int idProduto;
    private String codigoRadar;
    private String nomeProduto;
    private String descricaoProduto;
    private String lote;
    private String localEstoque;
    private Double quantidade;
    private String dataInventario;

    /**
     * @return the idProduto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    /**
     * @return the codigoRadar
     */
    public String getCodigoRadar() {
        return codigoRadar;
    }

    /**
     * @param codigoRadar the codigoRadar to set
     */
    public void setCodigoRadar(String codigoRadar) {
        this.codigoRadar = codigoRadar;
    }

    /**
     * @return the nomeProduto
     */
    public String getNomeProduto() {
        return nomeProduto;
    }

    /**
     * @param nomeProduto the nomeProduto to set
     */
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    /**
     * @return the descricaoProduto
     */
    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    /**
     * @param descricaoProduto the descricaoProduto to set
     */
    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    /**
     * @return the lote
     */
    public String getLote() {
        return lote;
    }

    /**
     * @param lote the lote to set
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * @return the localEstoque
     */
    public String getLocalEstoque() {
        return localEstoque;
    }

    /**
     * @param localEstoque the localEstoque to set
     */
    public void setLocalEstoque(String localEstoque) {
        this.localEstoque = localEstoque;
    }

    /**
     * @return the quantidade
     */
    public Double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the dataInventario
     */
    public String getDataInventario() {
        return dataInventario;
    }

    /**
     * @param dataInventario the dataInventario to set
     */
    public void setDataInventario(String dataInventario) {
        this.dataInventario = dataInventario;
    }
    
    
}
