#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0x90, 0xA2, 0xDA, 0x0F, 0x50, 0x35 };

IPAddress arduino(192,168,0,50);
IPAddress server(192,168,0,15);

EthernetClient client;

String username[] = {"Admin","Skut","Arbek","Kubda"};
String password[] = {"12344","stra","124ssgra","!#¤%"};

String readString;

void setup() {
  Serial.begin(9600);
  Ethernet.begin(mac,arduino);
  Serial.println("Connecting...");
  if (client.connect(server, 8888)){
   Serial.println("Connected to Server");
  } else {
    Serial.println("Connecting failed");
  }
}

void clientRead() { 
  while (client.available()) {
  //delay(10);  
    if (client.available() >0) {
      char c = client.read();
      readString += c;
    }
}
}

boolean verify(String firstText, String secondText){
  for(int i = 0; i <= 3 ; i++){
    if((firstText.equals(username[i])) && (secondText.equals(password[i]))){ 
      return true; 
    }
  }
    return false;
   }

void loop(){
  if (client.available() > 0){
    readString = "";
    clientRead();
    int n = readString.length();
    int commaIndex = readString.indexOf(';');
    int lastIndex = readString.lastIndexOf(n);
    String firstText = readString.substring(0, commaIndex);
    String secondText = readString.substring(commaIndex + 1 , lastIndex);
    Serial.println(firstText);
    Serial.println(secondText);
    Serial.println(verify(firstText, secondText));
  }

 if (!client.connected()){
   Serial.println("Server disconnected!");
   client.stop();
   client.flush();
   while(true);
 
}
}
