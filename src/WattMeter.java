import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import processing.core.*;
import processing.serial.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class WattMeter extends PApplet {
	float power = 0;
	float battery = 0;	
	Serial myPort;

	
	public void postData(float battery_) throws IOException {
		String urlString = "http://bicyclemeter.herokuapp.com/add?";
        String postStr = "power="+power+"&battery="+battery_;//POSTするデータ
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
		frameRate(1);
  }

   public float serialEvent(){
     float x_;
     x_=myPort.read();
     return x_;
   }
	
	public void draw()
  {
		final float calib = (float) 1.0;//キャリブレーションの倍率
		float battery_ = serialEvent() * calib;
		try {
			postData(battery_);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
  }
  public static void main(String args[]){
	    PApplet.main(new String[] { "--present", "WattMeter" });
	}
}