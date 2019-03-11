package com.favio.blackjackservidor;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.favio.blackjackservidor.modelos.Numero;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_enviar, btn_recibir, btn_nuevo;
    ImageView iv_carta1;
    TextView tv_resultado, tv_mensaje;
    Integer cantNum, resultado=0, intentos=0;
    Drawable carta, as, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, diez, jack, reina, rey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_enviar=findViewById(R.id.btn_enviar);
        btn_recibir=findViewById(R.id.btn_recibir);
        btn_nuevo=findViewById(R.id.btn_nuevo);
        iv_carta1=findViewById(R.id.iv_carta1);
        tv_resultado=findViewById(R.id.tv_resultado);
        tv_mensaje=findViewById(R.id.tv_mensaje);

        as=getResources().getDrawable(R.drawable.diamantes_as);
        dos=getResources().getDrawable(R.drawable.diamantes2);
        tres=getResources().getDrawable(R.drawable.diamantes3);
        cuatro=getResources().getDrawable(R.drawable.diamantes4);
        cinco=getResources().getDrawable(R.drawable.diamantes5);
        seis=getResources().getDrawable(R.drawable.diamantes6);
        siete=getResources().getDrawable(R.drawable.diamantes7);
        ocho=getResources().getDrawable(R.drawable.diamantes8);
        nueve=getResources().getDrawable(R.drawable.diamantes9);
        diez=getResources().getDrawable(R.drawable.diamantes10);
        jack=getResources().getDrawable(R.drawable.diamantes_jack);
        reina=getResources().getDrawable(R.drawable.diamantes_reina);
        rey=getResources().getDrawable(R.drawable.diamantes_rey);
        carta=getResources().getDrawable(R.drawable.carta);

        btn_enviar.setOnClickListener(this);
        btn_recibir.setOnClickListener(this);
        btn_nuevo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_recibir:

                if (intentos<5){

                    JsonObjectRequest peticion01=new JsonObjectRequest(
                            Request.Method.GET,
                            "http://nuevo.rnrsiilge-org.mx/baraja/numero",
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    Gson gson=new Gson();
                                    Numero numero=gson.fromJson(response.toString(),Numero.class);
                                    cantNum=numero.getCantidad();

                                    switch (cantNum){

                                        case 1:
                                            iv_carta1.setBackground(as);
                                            break;
                                        case 2:
                                            iv_carta1.setBackground(dos);
                                            break;
                                        case 3:
                                            iv_carta1.setBackground(tres);
                                            break;
                                        case 4:
                                            iv_carta1.setBackground(cuatro);
                                            break;
                                        case 5:
                                            iv_carta1.setBackground(cinco);
                                            break;
                                        case 6:
                                            iv_carta1.setBackground(seis);
                                            break;
                                        case 7:
                                            iv_carta1.setBackground(siete);
                                            break;
                                        case 8:
                                            iv_carta1.setBackground(ocho);
                                            break;
                                        case 9:
                                            iv_carta1.setBackground(nueve);
                                            break;
                                        case 10:
                                            iv_carta1.setBackground(diez);
                                            break;
                                        case 11:
                                            iv_carta1.setBackground(jack);
                                            break;
                                        case 12:
                                            iv_carta1.setBackground(reina);
                                            break;
                                        case 13:
                                            iv_carta1.setBackground(rey);
                                            break;
                                    }

                                    resultado+=cantNum;

                                    tv_resultado.setText(Integer.toString(resultado));
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }
                    );

                    VolleyS.getInstance(this).getRequestQueue().add(peticion01);

                    intentos++;
                }

                else {

                    btn_recibir.setEnabled(false);

                    Toast.makeText(MainActivity.this, "No puedes solicitar más cartas", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_enviar:

                JSONObject jsonObj=new JSONObject();
                try {
                    jsonObj.put("Numero","Favio: " + resultado);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest peticion02=new JsonObjectRequest(
                        Request.Method.POST,
                        "http://nuevo.rnrsiilge-org.mx/baraja/enviar",
                        jsonObj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                tv_mensaje.setText(response.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );

                VolleyS.getInstance(this).getRequestQueue().add(peticion02);

                break;

            case R.id.btn_nuevo:
                limpiar();
                break;
        }
    }

    public void limpiar(){
        cantNum=0;
        resultado=0;
        intentos=0;

        tv_resultado.setText("Resultado");
        tv_mensaje.setText("");
        iv_carta1.setBackground(carta);
        btn_recibir.setEnabled(true);
    }
}
