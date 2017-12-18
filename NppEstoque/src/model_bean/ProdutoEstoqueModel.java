package model_bean;

public class ProdutoEstoqueModel{
    
    private Double quantidadeEstoque;
    private String dataUltimoInventario;

    public Double getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Double quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getDataUltimoInventario() {
        return dataUltimoInventario;
    }

    public void setDataUltimoInventario(String dataUltimoInventario) {
        this.dataUltimoInventario = dataUltimoInventario;
    }
    
}
