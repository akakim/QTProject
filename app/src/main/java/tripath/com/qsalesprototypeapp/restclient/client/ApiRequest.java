package tripath.com.qsalesprototypeapp.restclient.client;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import tripath.com.qsalesprototypeapp.restclient.entity.AuthCode;

/**
 * Created by SSLAB on 2017-06-23.
 */

public class ApiRequest <T> extends JsonRequest<T> {

    private Class<T> clazz;
    private int method;

    public ApiRequest(int method, String url, String jsonRequest, Response.Listener<T> listener,
                      Response.ErrorListener errorListener, Class<T> clazz) {
        super(method, url.replace(" ", "%20"), jsonRequest, listener, errorListener);
        this.clazz = clazz;
        this.method = method;
    }



    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();

        headers.put("Accept", "application/json");
        return headers;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString;

            if(method == Method.GET || method == Method.POST){
                jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            }else {
                JSONObject responseJson = new JSONObject();
                responseJson.put("statusCode", response.statusCode);
                String data = new String ( response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                Log.d(getClass().getSimpleName(),data);
                responseJson.put("data",data);
                jsonString = responseJson.toString();

                Log.d(getClass().getSimpleName(),jsonString);
            }
            Log.d(getClass().getSimpleName(),jsonString);
            // customizing Deserialization

            if(AuthCode.class.getCanonicalName().equals( clazz.getCanonicalName() )){


            }
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            return Response.success(mapper.readValue(jsonString, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (IOException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
}
