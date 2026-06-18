package com.example.btl_qly_sinhvien.Controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.btl_qly_sinhvien.Adapter.Adapter_DK_hocphan;
import com.example.btl_qly_sinhvien.Adapter.Adapter_danhsach_congno;
import com.example.btl_qly_sinhvien.MainActivity;
import com.example.btl_qly_sinhvien.Model.model_danhsachcongno;
import com.example.btl_qly_sinhvien.URL.path;
import com.example.btl_qly_sinhvien.model_dkhp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class controll {
    public static void Login(Context context, String email, String pass){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = path.url+"get.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        int check = Integer.parseInt(response);
                        if(check > 0){
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,MainActivity.class);
                            intent.putExtra("email",email);
                            context.startActivity(intent);
                        }else{
                            Toast.makeText(context, "Ddăng nhập thất bại ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("email",email);
                data.put("pass",pass);
                return data;
            }
        };
        queue.add(stringRequest);
    }
    public static void turn_off_SSL(){
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void dangky_json(Context context, String tenmon, RecyclerView view, ArrayList<model_dkhp> listdkhp){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = path.url+"dangkyhocphan.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(null,response);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
                        view.setLayoutManager(linearLayoutManager);
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i ++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                listdkhp.add(new model_dkhp(obj.getString("ten_khoahoc"),obj.getString("so_tc"),obj.getString("lop_khoahoc"),obj.getString("phong_hoc"),obj.getString("han_dk"), obj.getString("gia_tc"), obj.getString("name") ));

                            }
                            Adapter_DK_hocphan adapter_dk_hocphan = new Adapter_DK_hocphan(listdkhp);
                            view.setAdapter(adapter_dk_hocphan);
                        }catch(Exception ex){
                            Toast.makeText(context, "Chưa có môn này trong hệ thống,\n Bạn có thể chọn môn khác", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(null,error.toString());
                    }
                }
        )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("tenmon",tenmon);
                return data;
            }
        };
        queue.add(request);
    }
    public static void Delete_dkhop(Context context,String tenmon,RecyclerView view){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = path.url+"delete_dkhp.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<model_dkhp> listdkhp = new ArrayList<>();
                        try{
                              JSONArray json = new JSONArray(response);
                              for (int i = 0 ; i < json.length(); i ++){
                                  JSONObject obj = json.getJSONObject(i);
                                  listdkhp.add(new model_dkhp(obj.getString("ten_khoahoc"),obj.getString("so_tc"),obj.getString("lop_khoahoc"),obj.getString("phong_hoc"),obj.getString("han_dk"), obj.getString("gia_tc"), obj.getString("name") ));
                              }
                              Adapter_DK_hocphan adapter_dk_hocphan = new Adapter_DK_hocphan(listdkhp);
                              view.setAdapter(adapter_dk_hocphan);
                        }catch(Exception e){
                            Toast.makeText(context, "Chưa xóa được vì không tồn tại tên môn"+tenmon, Toast.LENGTH_SHORT).show();
                            Log.d(null,e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(null,error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("tenmon",tenmon);
                return data;
            }
        };
        queue.add(request);
    }
    public static void setCongno(Context context, TextView tongtien, TextView dadong,TextView conno,String email){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = path.url+"congno.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj = new JSONObject(response);
                            tongtien.setText(obj.getString("tongtien")+" đ");
                            dadong.setText(obj.getString("bought") +" đ");
                            conno.setText(obj.getString("conno")+ " đ");
                        }catch(Exception e){
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d(null,e.toString());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(null,error.toString());
                    }
                }
        )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("email",email);
                return data;
            }
        }
        ;
        queue.add(request);
    }
    public static void setDanhsachCongNo(Context context,RecyclerView recycleview,String email){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = path.url+"chietietcongno.php";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<model_danhsachcongno> danhsach = new ArrayList<>();
                        try{
                            JSONArray arr = new JSONArray(response);
                            for (int thanh = 0; thanh < arr.length(); thanh ++){
                                JSONObject obj = arr.getJSONObject(thanh);
                                danhsach.add(new model_danhsachcongno(obj.getString("ten_khoahoc"),obj.getString("so_tc"),obj.getString("gia_tc"),obj.getString("Tich"),obj.getString("trangthai") ));
                            }
                            Adapter_danhsach_congno danhsach_congno = new Adapter_danhsach_congno(danhsach);
                            recycleview.setAdapter(danhsach_congno);
                        }catch(Exception e){
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d(null,e.toString());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(null,error.toString());
                    }
                }
        )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("email",email);
                return data;
            }
        };
        queue.add(request);
    }

}
