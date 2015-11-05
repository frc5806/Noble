#include <SoftwareSerial.h>
#include <SparkFunESP8266WiFi.h>

#define LEFT 1
#define RIGHT 0

bool good = false;
ESP8266Client client;
bool state = false;

void setup() {  
	Serial.begin(9600);
	
	pinMode(13,OUTPUT);

	if (esp8266.begin()) {
		Serial.println("We're good");
	} else {
		Serial.println("We're not");
	}
	
	int retVal = esp8266.connect("academic");
	if (retVal < 0) {
		Serial.print(F("Error connecting: "));
		Serial.println(retVal);
    } else {
		Serial.println("Stuff is good");
	}

	Serial.print(F("My IP is: ")); Serial.println(esp8266.localIP());

	retVal = client.connect("192.168.173.1",2000);
	if (retVal<=0) return;

	good = true;
	motorInit();
}

void loop() {
	while (client.available())
    	client.print(client.read());
}
