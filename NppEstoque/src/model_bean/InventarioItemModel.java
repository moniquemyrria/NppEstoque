package model_bean;

public class InventarioItemModel extends InventarioModel{
    private String lote;
    private Double quantidade;

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }    
}
