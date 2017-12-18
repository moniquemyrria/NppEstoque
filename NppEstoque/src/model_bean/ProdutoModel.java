
package model_bean;

public class ProdutoModel {
    
    private int idProduto;
    private String codigoRadar;
    private String nomeProduto;
    private String descricaoProduto;
    private String localEstoque;
    private String situacaoDeletadoProduto;
    private ProdutoEstoqueModel produtoEstoqueModel;
    
    public ProdutoModel(){
        produtoEstoqueModel = new ProdutoEstoqueModel(); 
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getCodigoRadar() {
        return codigoRadar;
    }

    public void setCodigoRadar(String codigoRadar) {
        this.codigoRadar = codigoRadar;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getLocalEstoque() {
        return localEstoque;
    }

    public void setLocalEstoque(String localEstoque) {
        this.localEstoque = localEstoque;
    }

    public String getSituacaoDeletadoProduto() {
        return situacaoDeletadoProduto;
    }

    public void setSituacaoDeletadoProduto(String situacaoDeletadoProduto) {
        this.situacaoDeletadoProduto = situacaoDeletadoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    
    public Double getQuantidadeEstoque(){
        return this.produtoEstoqueModel.getQuantidadeEstoque();
    } 
    
    public void setQuantidadeEstoque(Double quantidadeEstoque){
        produtoEstoqueModel.setQuantidadeEstoque(quantidadeEstoque);
    }
    
    public String getDataUltimoInventario(){
        return this.produtoEstoqueModel.getDataUltimoInventario();
    } 
    
    public void setDataUltimoInventario(String dataUltimoInventario){
        produtoEstoqueModel.setDataUltimoInventario(dataUltimoInventario);
    }
    
}
