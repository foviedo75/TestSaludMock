package cordobarentar.com.testsaludmock.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import cordobarentar.com.testsaludmock.POJO.Empleado;
/*
MANEJO DE PREFERENCIAS DEL USUARIO LOGUEADO*/

public class SessionPrefs {

    public static final String PREFS_NAME = "CRAC_PREFS";
    public static final String PREF_EMPLEADO_ID = "PREF_EMPLEADO_ID";
    public static final String PREF_EMPLEADO_NOMBRE = "PREF_EMPLEADO_NOMBRE";
    public static final String PREF_EMPLEADO_EMAIL = "PREF_EMPLEADO_EMAIL";
    public static final String PREF_EMPLEADO_DIRECCION = "PREF_EMPLEADO_DIRECCION";
    public static final String PREF_EMPLEADO_TELEFONO = "PREF_EMPLEADO_TELEFONO";
    public static final String PREF_EMPLEADO_CUIL = "PREF_EMPLEADO_CUIL";
    public static final String PREF_EMPLEADO_CUENTABANCARIA = "PREF_EMPLEADO_CUENTABANCARIA";
    public static final String PREF_EMPLEADO_FECHAINGRESO = "PREF_EMPLEADO_FECHAINGRESO";
    public static final String PREF_EMPLEADO_UBICACIONID = "PREF_EMPLEADO_UBICACIONID";
    public static final String PREF_EMPLEADO_UBICACION = "PREF_EMPLEADO_UBICACION";
    public static final String PREF_EMPLEADO_TOKENACCESO = "PREF_EMPLEADO_TOKENACCESO";

    private final SharedPreferences mPrefs;
    private boolean mIsLoggedIn = false;

    private static SessionPrefs INSTANCE;

    public static SessionPrefs get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPrefs(context);
        }
        return INSTANCE;
    }

    private SessionPrefs(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_EMPLEADO_TOKENACCESO, null));
    }

    public boolean isLoggedIn(){
        return mIsLoggedIn;
    }

    public void saveAffiliate(Empleado empleado) {
        if (empleado != null) {
            SharedPreferences.Editor editor = mPrefs.edit();

            editor.putString("PREF_EMPLEADO_ID", empleado.getempleadoID());
            editor.putString("PREF_EMPLEADO_NOMBRE",empleado.getempleadoNombre());
            editor.putString("PREF_EMPLEADO_EMAIL",empleado.getempleadoEmail());
            editor.putString("PREF_EMPLEADO_DIRECCION",empleado.getempleadoDireccion());
            editor.putString("PREF_EMPLEADO_TELEFONO",empleado.getempleadoTelefono());
            editor.putString("PREF_EMPLEADO_CUIL",empleado.getempleadoCuil());
            editor.putString("PREF_EMPLEADO_CUENTABANCARIA",empleado.getempleadoCuentaBancaria());
            editor.putString("PREF_EMPLEADO_FECHAINGRESO",empleado.getempleadoFechaIngreso());
            editor.putString("PREF_EMPLEADO_UBICACIONID",empleado.getubicacionID());
            editor.putString("PREF_EMPLEADO_UBICACION",empleado.getubicacion());
            editor.putString("PREF_EMPLEADO_TOKENACCESO",empleado.gettokenAcceso());


            editor.apply();

            mIsLoggedIn = true;
        }
    }

    public void logOut(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_EMPLEADO_ID, null);
        editor.putString(PREF_EMPLEADO_NOMBRE, null);
        editor.putString(PREF_EMPLEADO_EMAIL, null);
        editor.putString("PREF_EMPLEADO_DIRECCION",null);
        editor.putString("PREF_EMPLEADO_TELEFONO",null);
        editor.putString("PREF_EMPLEADO_CUIL",null);
        editor.putString("PREF_EMPLEADO_CUENTABANCARIA",null);
        editor.putString("PREF_EMPLEADO_FECHAINGRESO",null);
        editor.putString("PREF_EMPLEADO_UBICACIONID",null);
        editor.putString("PREF_EMPLEADO_UBICACION",null);
        editor.putString("PREF_EMPLEADO_TOKENACCESO",null);
        editor.apply();
    }

    public String getToken(){
        return mPrefs.getString(PREF_EMPLEADO_TOKENACCESO, null);
    }
    public String getNombreEmpleado()
    {
        return mPrefs.getString(PREF_EMPLEADO_NOMBRE, null);
    }
    public String getEmailEmpleado()
    {
        return mPrefs.getString(PREF_EMPLEADO_EMAIL, null);
    }
    public String getUbicacionIdEmpleado()
    {
        return mPrefs.getString(PREF_EMPLEADO_UBICACIONID, null);
    }
    public String getEmpleadoID()
    {
        return mPrefs.getString(PREF_EMPLEADO_ID, null);
    }




}
