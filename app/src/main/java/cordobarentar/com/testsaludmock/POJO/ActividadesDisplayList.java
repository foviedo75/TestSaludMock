package cordobarentar.com.testsaludmock.POJO;

import java.util.Date;

public class ActividadesDisplayList {
    String actividadID;
    String actividadAutoPatente;
    String actividadServicioNombre;
    String actividadEmpleadoNombre;
    String actividadNotaAdicional;
    String actividadFecha;
    String actividadKmTablero;
    Boolean actividadEstado;
    String actividadKmExcedido;
    String autoID;
    String servicioID;
    String empleadoID;
    String ubicacionID;



    public ActividadesDisplayList(String actividadID, String actividadAutoPatente, String actividadServicioNombre,
                                  String actividadEmpleadoNombre,String actividadNotaAdicional,
                                  String actividadFecha,String actividadKmTablero,Boolean actividadEstado,
                                  String actividadKmExcedido, String autoID,String servicioID,String empleadoID,
                                  String ubicacionID)
    {
        this.actividadID=actividadID;
        this.actividadAutoPatente=actividadAutoPatente;
        this.actividadServicioNombre=actividadServicioNombre;
        this.actividadEmpleadoNombre=actividadEmpleadoNombre;
        this.actividadNotaAdicional=actividadNotaAdicional;
        this.actividadFecha=actividadFecha;
        this.actividadKmTablero=actividadKmTablero;
        this.actividadEstado=actividadEstado;
        this.actividadKmExcedido=actividadKmExcedido;
        this.autoID=autoID;
        this.servicioID=servicioID;
        this.empleadoID=empleadoID;
        this.ubicacionID=ubicacionID;
    }

    public String getActividadID(){return actividadID;}
    public void setActividadID(String actividadID) { this.actividadID = actividadID;}

    public String getactividadAutoPatente(){return actividadAutoPatente;}
    public void setactividadAutoPatente(String actividadAutoPatente) { this.actividadAutoPatente = actividadAutoPatente;}

    public String getactividadServicioNombre(){return actividadServicioNombre;}
    public void setactividadServicioNombre(String actividadServicioNombre) { this.actividadServicioNombre = actividadServicioNombre;}

    public String getactividadEmpleadoNombre(){return actividadEmpleadoNombre;}
    public void setactividadEmpleadoNombre(String actividadEmpleadoNombre) { this.actividadEmpleadoNombre = actividadEmpleadoNombre;}

    public String getactividadNotaAdicional(){return actividadNotaAdicional;}
    public void setactividadNotaAdicional(String actividadNotaAdicional) { this.actividadNotaAdicional = actividadNotaAdicional;}

    public String getactividadFecha(){return actividadFecha;}
    public void setactividadFecha(String actividadFecha) { this.actividadFecha = actividadFecha;}

    public String getactividadKmTablero(){return actividadKmTablero;}
    public void setactividadKmTablero(String actividadKmTablero) { this.actividadKmTablero = actividadKmTablero;}

    public Boolean getactividadEstado(){return actividadEstado;}
    public void setactividadEstado(Boolean actividadEstado) { this.actividadEstado = actividadEstado;}

    public String getactividadKmExcedido(){return actividadKmExcedido;}
    public void setactividadKmExcedido(String actividadKmExcedido) { this.actividadKmExcedido = actividadKmExcedido;}

    public String getautoID(){return autoID;}
    public void setautoID(String autoID) { this.autoID = autoID;}

    public String getservicioID(){return servicioID;}
    public void setservicioID(String servicioID) { this.servicioID = servicioID;}

    public String getempleadoID(){return empleadoID;}
    public void setempleadoID(String empleadoID) { this.empleadoID = empleadoID;}

    public String getubicacionID(){return ubicacionID;}
    public void setubicacionID(String ubicacionID) { this.ubicacionID = ubicacionID;}

}
