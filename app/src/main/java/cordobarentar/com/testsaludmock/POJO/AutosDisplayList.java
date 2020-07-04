package cordobarentar.com.testsaludmock.POJO;

public class AutosDisplayList {

    String autoID;
    String autoPatente ;
    String autoModelo ;
    String autoColor;
    String autoCodRadio;
    String autoTelefono;
    String autoKmtablero ;
    String autoKmBajados;
    String autoKmReales;
    Boolean autoActivoYN;
    String ubicacionID;
    Boolean autoActividadesPendientesYN;
    Boolean autoLuces;

    public AutosDisplayList(String autoID, String autoPatente, String autoModelo,
                           String autoColor, String autoCodRadio, String autoTelefono,
                           String autoKmtablero, String autoKmBajados,
                           String autoKmReales, Boolean autoActivoYN, String ubicacionID,
                            boolean autoActividadesPendientesYN, boolean autoLuces)
    {
        this.autoID = autoID;
        this.autoPatente = autoPatente ;
        this.autoModelo = autoModelo;
        this.autoColor = autoColor;
        this.autoCodRadio=autoCodRadio;
        this.autoTelefono=autoTelefono;
        this.autoKmtablero = autoKmtablero;
        this.autoKmBajados = autoKmBajados;
        this.autoKmReales = autoKmReales;
        this.autoActivoYN = autoActivoYN;
        this.ubicacionID = ubicacionID;
        this.autoActividadesPendientesYN = autoActividadesPendientesYN;
        this.autoLuces = autoLuces;

    }

    public String getAutoID(){return autoID;}
    public void setAutoID(String autoID) { this.autoID = autoID;}

    public String getAutoPatente(){return autoPatente;}
    public void setAutoPatente(String autoPatente) { this.autoPatente = autoPatente;}

    public String getAutoModelo(){return autoModelo;}
    public void setAutoModelo(String autoModelo) { this.autoModelo = autoModelo;}

    public String getAutoColor(){return autoColor;}
    public void setAutoColor(String autoColor) { this.autoColor = autoColor;}

    public String getAutoCodRadio(){return autoCodRadio;}
    public void setAutoCodRadio(String autoCodRadio) { this.autoCodRadio = autoCodRadio;}

    public String getAutoTelefono(){return autoTelefono;}
    public void setAutoTelefono(String autoTelefono) { this.autoTelefono = autoTelefono;}

    public String getAutoKmtablero(){return autoKmtablero;}
    public void setAutoKmtablero(String autoKmtablero) { this.autoKmtablero = autoKmtablero;}

    public String getAutoKmBajados(){return autoKmBajados;}
    public void setAutoKmBajados(String autoKmBajados) { this.autoKmBajados = autoKmBajados;}

    public String getAutoKmReales(){return autoKmReales;}
    public void setAutoKmReales(String autoKmReales) { this.autoKmReales = autoKmReales;}

    public Boolean getAutoActivoYN(){return autoActivoYN;}
    public void setAutoActivoYN(Boolean autoActivoYN) { this.autoActivoYN = autoActivoYN;}

    public String getUbicacionID(){return ubicacionID;}
    public void setUbicacionID(String ubicacionID) { this.ubicacionID = ubicacionID;}

    public Boolean getautoActividadesPendientesYN(){return autoActividadesPendientesYN;}
    public void setAutoautoActividadesPendientesYN(Boolean autoActividadesPendientesYN) { this.autoActividadesPendientesYN = autoActividadesPendientesYN;}

    public Boolean getautoautoLucesAutomaticasYN(){return autoLuces;}
    public void setAutoautoautoLucesAutomaticasYN(Boolean autoLuces) { this.autoLuces = autoLuces;}


}
