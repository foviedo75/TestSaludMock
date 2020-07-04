package cordobarentar.com.testsaludmock.POJO;

public class LavadoBody {


    private String LavadoNombre;
    private boolean LavadoPagadoYN;
    private String AutoPatente;
    private double LavadoPrecio;
    private String AutoID;
    private String EmpleadoID;


    public LavadoBody(String LavadoNombre, boolean LavadoPagadoYN, String AutoPatente,
                      double LavadoPrecio,String AutoID,String EmpleadoID)
    {
        this.LavadoNombre = LavadoNombre;
        this.LavadoPagadoYN = LavadoPagadoYN;
        this.AutoPatente = AutoPatente;
        this.LavadoPrecio = LavadoPrecio;
        this.AutoID = AutoID;
        this.EmpleadoID = EmpleadoID;

    }


}
