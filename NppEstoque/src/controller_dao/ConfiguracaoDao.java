package controller_dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import model_bean.ConfiguracaoModel;

public class ConfiguracaoDao implements Serializable {

    //PastaPrincipal
    public String caminhoArquivoPastaPrincipal() {
        return ("C:\\nppEstoque\\");
    }

    public void salvar(ConfiguracaoModel configModel) {
        try {
            FileOutputStream caminhoArquivo = new FileOutputStream(caminhoArquivoPastaPrincipal() + "DB.txt");
            ObjectOutputStream dadosObjeto = new ObjectOutputStream(caminhoArquivo);
            dadosObjeto.writeObject(configModel);
            dadosObjeto.close();
            caminhoArquivo.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public ConfiguracaoModel consultarArquivoConexaoDB() {
        ConfiguracaoModel configModel = null;
        try {
            FileInputStream caminhoArquivo = new FileInputStream(caminhoArquivoPastaPrincipal() + "DB.txt");
            ObjectInputStream dadosObjeto = new ObjectInputStream(caminhoArquivo);
            configModel = (ConfiguracaoModel) dadosObjeto.readObject();
            dadosObjeto.close();
            caminhoArquivo.close();
        } catch (Exception exp) {
        }
        return configModel;
    }

}
