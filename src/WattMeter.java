import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import processing.core.*;
import processing.serial.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class WattMeter extends PApplet {
	float power = 100;
	float battery = 100;	
	Serial myPort;

	public void getValue(){
		power = 0;
		battery = 0;
	}
	
	public void postData() throws IOException {
		String urlString = "http://bicyclemeter.herokuapp.com/add?";
        String postStr = "power="+power+"&battery="+battery;//POSTするデータ
        String dataStr = urlString + postStr;
        //System.out.println(dataStr);

        URL url = new URL(dataStr);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("GET");
        http.connect();
        // サーバーからのレスポンスを標準出力へ出す
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        //String xml = "", line = "";
        //while((line = reader.readLine()) != null)
        //	xml += line;
        //System.out.println(xml);
        reader.close();
	
	}
	
	public void setup()
  {
		myPort=new Serial(this,"/dev/tty.usbmodemfd121",9600);
		size(400, 400);
		try {
			postData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

   public int serialEvent(){
     int x_;
     x_=myPort.read();
     //println(x_);
     return x_;
  }
	
	public void draw()
  {
    	  
  }
  public static void main(String args[]){
	    PApplet.main(new String[] { "--present", "WattMeter" });
	}
}