package cordobarentar.com.testsaludmock.POJO;

public class Empleado {
    private String empleadoID;
    private String empleadoNombre;
    private String empleadoEmail;
    private String empleadoDireccion;
    private String empleadoTelefono;
    private String empleadoCuil;
    private String empleadoCuentaBancaria;
    private String empleadoFechaIngreso;
    private String ubicacionID;
    private String ubicacion;
    private String tokenAcceso;

    public Empleado(String empleadoID,String empleadoNombre, String empleadoEmail, String empleadoDireccion,
                    String empleadoTelefono, String empleadoCuil, String empleadoCuentaBancaria,
                    String empleadoFechaIngreso, String ubicacionID, String ubicacion, String tokenAcceso ){

        this.empleadoID=empleadoID;
        this.empleadoNombre=empleadoNombre;
        this.empleadoEmail=empleadoEmail;
        this.empleadoDireccion=empleadoDireccion;
        this.empleadoTelefono=empleadoTelefono;
        this.empleadoCuil=empleadoCuil;
        this.empleadoCuentaBancaria=empleadoCuentaBancaria;
        this.empleadoFechaIngreso=empleadoFechaIngreso;
        this.ubicacionID=ubicacionID;
        this.ubicacion=ubicacion;
        this.tokenAcceso=tokenAcceso;
    }

    public String getempleadoID() {
        return empleadoID;
    }
    public void setempleadoID(String empleadoID) {
        this.empleadoID = empleadoID;
    }
//==================================================
    public String getempleadoNombre() {
        return empleadoNombre;
    }
    public void setempleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }
    //==================================================
    public String getempleadoEmail() {
        return empleadoEmail;
    }
    public void setempleadoEmail(String empleadoEmail) {
        this.empleadoEmail = empleadoEmail;
    }
    //==================================================
    public String getempleadoDireccion() {
        return empleadoDireccion;
    }
    public void setempleadoDireccion(String empleadoDireccion) {
        this.empleadoDireccion = empleadoDireccion;
    }

    //==================================================
    public String getempleadoTelefono() {
        return empleadoTelefono;
    }
    public void setempleadoTelefono(String empleadoTelefono) {
        this.empleadoTelefono = empleadoTelefono;
    }
    //==================================================
    public String getempleadoCuil() {
        return empleadoCuil;
    }
    public void setempleadoCuil(String empleadoCuil) {
        this.empleadoCuil = empleadoCuil;
    }
    //==================================================
    public String getempleadoCuentaBancaria() {
        return empleadoCuentaBancaria;
    }
    public void setempleadoCuentaBancaria(String empleadoCuentaBancaria) {
        this.empleadoCuentaBancaria = empleadoCuentaBancaria;
    }
    //==================================================
    public String getempleadoFechaIngreso() {
        return empleadoFechaIngreso;
    }
    public void setempleadoFechaIngreso(String empleadoFechaIngreso) {
        this.empleadoFechaIngreso = empleadoFechaIngreso;
    }

    //==================================================
    public String getubicacionID() {
        return ubicacionID;
    }
    public void setubicacionID(String ubicacionID) {
        this.ubicacionID = ubicacionID;
    }
    //==================================================
    public String getubicacion() {
        return ubicacion;
    }
    public void setubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    //==================================================
    public String gettokenAcceso() {
        return tokenAcceso;
    }
    public void settokenAcceso(String tokenAcceso) {
        this.tokenAcceso = tokenAcceso;
    }

}
