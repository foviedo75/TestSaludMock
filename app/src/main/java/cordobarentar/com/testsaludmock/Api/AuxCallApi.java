package cordobarentar.com.testsaludmock.Api;

public class AuxCallApi {

    /*public static  final String BASE_URL2 = "http://cordobarentar.com";*/

    public  static Api apiService(){

        return Cliente.getClient(Api.BASE_URL).create(Api.class);
    }


}
