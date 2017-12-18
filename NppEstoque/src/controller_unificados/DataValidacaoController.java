package controller_unificados;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataValidacaoController {

    public java.util.Date dataAtual() {
        java.util.Date data = new java.util.Date();
        java.util.Date dataDia = data;
        return dataDia;
    }

    public String converteDataDB(JDateChooser dcData) {
        //Converte valor do JDateChoose em data
        SimpleDateFormat conversao = new SimpleDateFormat("yyyy-MM-dd");//para converter DataValidacaoController e Hora ("dd/MM/yyyy HH:mm:ss");
        String dataBr = null;
        try {
            dataBr = conversao.format(dcData.getDate());
        } catch (Exception e) {
        }
        return dataBr;
    }

    public String converteDataHoraDB(JDateChooser dcData) {
        //Converte valor do JDateChoose em data
        SimpleDateFormat conversao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataBr = null;
        try {
            dataBr = conversao.format(dcData.getDate());
        } catch (Exception e) {
        }
        return dataBr;
    }

    public String converteDataSimplesDB(Date data) {
        //Converte valor em data
        SimpleDateFormat conversao = new SimpleDateFormat("dd-MM-YYYY");//para converter DataValidacaoController e Hora ("dd/MM/yyyy HH:mm:ss");
        String dataBr = null;
        try {
            dataBr = conversao.format(data);
        } catch (Exception e) {
        }
        return dataBr;

    }

    public String converteDataLayout(JDateChooser dcData) {
         //Converte valor do JDateChoose em data
        SimpleDateFormat conversao = new SimpleDateFormat("dd/MM/YYYY");//para converter DataValidacaoController e Hora ("dd/MM/yyyy HH:mm:ss");
        String dataBr = null;
        try {
            dataBr = conversao.format(dcData.getDate());
        } catch (Exception e) {
        }
        return dataBr;
    }
}
