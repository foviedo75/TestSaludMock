package cordobarentar.com.testsaludmock.Api;

import java.util.List;

import cordobarentar.com.testsaludmock.POJO.ActividadesDisplayList;
import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.POJO.Empleado;
import cordobarentar.com.testsaludmock.POJO.HistoricoBody;
import cordobarentar.com.testsaludmock.POJO.LavadoBody;
import cordobarentar.com.testsaludmock.POJO.LoginBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {

    public static final String BASE_URL = "http://cordobarentar.com";

    //=======================================================================
    //          ACA DECLARAMOS LA PETICION POST PARA HACER EL LOGIN
    //=======================================================================

    @POST("/api/login")

    Call<Empleado> login(@Body LoginBody loginBody);

    //ANOTACIONES


    //=======================================================================
    //          ACA DECLARAMOS LA PETICION GET PARA OBTENER EL LISTA DE TODOS LOS AUTOS
    //=======================================================================
    @GET("/api/Autosapi")
    Call<List<AutosDisplayList>> getAutos(@Header("Authorization") String token);
    //=======================================================================
    //          ACA DECLARAMOS LA PETICION GET PARA OBTENER EL LISTA DE LOS AUTOS DE LA UBICACION DEL EMPLEADO
    //=======================================================================
    @GET("/api/AutosApixUbicacion")
    Call<List<AutosDisplayList>> getAutosxUbicacion(@Header("Authorization") String token, @Query("ubicacionid") String idUbicacion);




    //=======================================================================
    //          ACA DECLARAMOS LA PETICION POST PARA HACER EL INSERT DE LAVADOS
    //=======================================================================
    @POST("/api/LavadosApi")
    Call<LavadoBody> insertLavado  (@Header("Authorization") String token, @Body LavadoBody datos);

    //=======================================================================
    //          ACA DECLARAMOS LA PETICION POST PARA HACER EL INSERT DE HISTORICO
    //=======================================================================
    @POST("/api/historicosapi")
    Call<HistoricoBody> insertHistorico  (@Header("Authorization") String token, @Body HistoricoBody datos);

    //=======================================================================
    //          ACA DECLARAMOS LA PETICION PUT PARA HACER EL UPDATE DE AUTO
    //=======================================================================
    @PUT ("api/autosapi/{id}")
    Call <AutosDisplayList> updateAuto (@Path("id") String autoId,
            @Header("Authorization") String token, @Body AutosDisplayList datos);


    //=======================================================================
    //          ACA DECLARAMOS LA PETICION GET PARA TRAER LOS MANTENIMIENTOS de todas las ubicaciones
    //=======================================================================
    @GET("/api/actividadesapi")
    Call<List<ActividadesDisplayList>> getActividades(@Header("Authorization") String token);

    //=======================================================================
    //          ACA DECLARAMOS LA PETICION GET PARA TRAER LOS MANTENIMIENTOS de la ubicacion del empleado
    //=======================================================================

    @GET("/api/ActividadesApixUbicacion")

    Call<List<ActividadesDisplayList>> getActividadessxUbicacion(@Header("Authorization") String token, @Query("ubicacionid") String idUbicacion);


    //=======================================================================
    //          ACA DECLARAMOS LA PETICION PUT PARA ACTUALIZAR ESTADO DE MANTENIMIENTO
    //=======================================================================
    @PUT ("api/ActividadesApi/{id}")
    Call <ActividadesDisplayList> updateActividades (@Path("id") String actividadId,
          @Header("Authorization") String token, @Body ActividadesDisplayList datos);


    //=======================================================================
    //          ACA DECLARAMOS LA PETICION GET PARA TRAER LOS MANTENIMIENTOS
    //          DE UN DETERMINADO AUTO
    //=======================================================================
    @GET("/api/ActividadesApixAuto")
    Call<List<ActividadesDisplayList>> getActividadesXAuto(@Header("Authorization") String token, @Query("autoid") String tuid);



}
