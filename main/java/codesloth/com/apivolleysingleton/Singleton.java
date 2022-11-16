package codesloth.com.apivolleysingleton;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {
    private final RequestQueue requestQueue;
    private static Singleton singleton;

    private Singleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized Singleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new Singleton(context);
        }
        return singleton;
    }
   public RequestQueue getRequestQueue(){
        return  requestQueue;
    }

    }

