package tripath.com.qsalesprototypeapp;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by SSLAB on 2017-08-17.
 */

public class FileUtil {

    public static void saveCache(File cacheFile, JSONObject data) throws Exception{


        boolean canWrite = false;
        if(cacheFile.exists()){
            canWrite = true;
        }else {
            canWrite = cacheFile.createNewFile();
        }

        if(canWrite) {
            if (cacheFile.canWrite()) {
                OutputStream outputStream = new FileOutputStream(cacheFile);
                outputStream.write(data.toString().getBytes());
            }
        }else {
            throw new FileNotFoundException( "Can not Create File" );
        }
    }

    /**
     * 만약 비어있다면 생성되지 않았거나 .. 없는것,
     * @param targetFile
     * @return
     * @throws Exception
     */
    public static JSONObject getCacheData(File targetFile) throws Exception{

        boolean canRead = false;
        JSONObject jsonObject = null;
        if( targetFile.exists()){

            InputStream inputStream = new FileInputStream(targetFile);

            int read = 0;


            StringBuilder builder = new StringBuilder();
            while( (read  = inputStream.read())>0){

                builder.append((char)read);
            }

            jsonObject = new JSONObject( builder.toString());
            inputStream.close();

            return jsonObject;

        }else {
            return new JSONObject();
        }


    }
}
