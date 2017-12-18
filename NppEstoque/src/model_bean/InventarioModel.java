
package model_bean;


public class InventarioModel extends ProdutoModel{
    private int idInventario;
    private String dataInventario;
    private String tipoInventario;
    private String situacaoDeletadoInventario;

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getDataInventario() {
        return dataInventario;
    }

    public void setDataInventario(String dataInventario) {
        this.dataInventario = dataInventario;
    }

    public String getTipoInventario() {
        return tipoInventario;
    }

    public void setTipoInventario(String tipoInventario) {
        this.tipoInventario = tipoInventario;
    }

    public String getSituacaoDeletadoInventario() {
        return situacaoDeletadoInventario;
    }

    public void setSituacaoDeletadoInventario(String situacaoDeletadoInventario) {
        this.situacaoDeletadoInventario = situacaoDeletadoInventario;
    } 
}
