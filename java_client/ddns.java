package ddns;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
public class ddns {
public static String my_ip="";
	public static void main(String[] args) {
begin();
	}

	static void begin() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("begin"+"\n");
                post_head();
            }
        }, 30, 60, TimeUnit.SECONDS);//30为首次运行滞后间隔。60为检测IP变化量间隔时间
        
	}
	public static void post_head() {
		String sr=wdnmd.sendPost("http://yourhost/index.php", "uname="+""+"&pwd="+"");
		 System.out.println(sr+"\n");
		if(sr.toString().equals(my_ip)) {
		}else {
			my_ip=sr.toString();
			String gg=wdnmd.sendPost("http://yourhost/ddns.php", "ip="+my_ip);
			 System.out.println(gg+"\n");
		}
	}

}

//post
class wdnmd {
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
         
            URLConnection connection = realUrl.openConnection();

            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            connection.connect();
          
            Map<String, List<String>> map = connection.getHeaderFields();
          
            for (String key : map.keySet()) {
            
            }
          
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
   
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
    
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
         
            URLConnection conn = realUrl.openConnection();
         
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
         
            conn.setDoOutput(true);
            conn.setDoInput(true);
          
            out = new PrintWriter(conn.getOutputStream());
         
            out.print(param);
           
            out.flush();
         
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
          
        }
    
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
           
            }
        }
        return result;
    }    
}