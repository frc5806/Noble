#include <SoftwareSerial.h>
#include <SparkFunESP8266WiFi.h>

#define LEFT 1
#define RIGHT 0

bool good = false;
ESP8266Client client;
bool state = false;

void setup() {  
	Serial.begin(9600);
	//while (esp8266.begin(9600,ESP8266_HARDWARE_SERIAL) != true) {
    while (esp8266.begin() != true) {
    	Serial.print("Error connecting to ESP8266.\n");
    	delay(1000);
  	}
  	Serial.print(F("Connected to ESP8266"));
	
	int retVal = -10;
	do {
		retVal = esp8266.connect("basement", "barnesandnoble");
		if (retVal < 0) {
			Serial.print(F("Error connecting: "));
			Serial.println(retVal);
		}
	} while(retVal < 0);
	Serial.println(F("Connected to basement wifi network"));

	Serial.print(F("My IP is: ")); Serial.println(esp8266.localIP());
	
	do {
		retVal = client.connect("192.168.1.10",3000);
		Serial.println("Attempting to connect to driver station");
	} while (retVal<=0);
	Serial.println("Connected to driver station");

	motorInit();
}

void loop() {
	char currentChar = 'a';
	char meaningfulChars[16];
	meaningfulChars[15] = '\0';
	int meaningfulSize = sizeof(meaningfulChars)/sizeof(meaningfulChars[0]);
	bool reachedStart = false;
	bool reachedEnd = false;
	int counter = 0;
	
	while(true) {
		if(client.available()) {
			char c = client.read();
    		if(((c >= 48 && c <= 57) || c == ';' || c == '-') && reachedStart == true) {
    			for(int a = 1; a < meaningfulSize-1; a++) {
					meaningfulChars[a-1] = meaningfulChars[a];
    			}
    			meaningfulChars[meaningfulSize-2] = c;
    		}
    		if(c == ')') {
    			reachedEnd = true;
    		}
    		if(c == '(') {
    			reachedStart = true;
    		}
		} if(reachedEnd == true) {
			int startingPoint = 0;
			for(int a = 0; a < meaningfulSize; a++) {
				if(meaningfulChars[a] != 0) {
					startingPoint = a;
					break;
				}
			}
			for(int a = startingPoint; a < meaningfulSize; a++) {
				meaningfulChars[a-startingPoint] = meaningfulChars[a];
				meaningfulChars[a] = '\0';
			}
			//Serial.print("\n");
			int rightVal, leftVal;
			sscanf(meaningfulChars, "%d;%d", &leftVal, &rightVal);
			
			Serial.print(leftVal);
			Serial.print("; ");
			Serial.print(rightVal);
			Serial.print("\n");

			setMotor(LEFT,leftVal);
			setMotor(RIGHT,rightVal);

			//setMotor(LEFT,100);
			
			char buf[] = {1};
			client.write(buf);

			memset(meaningfulChars, 0, sizeof(meaningfulChars));
			meaningfulChars[meaningfulSize-1] = '\0';

			reachedEnd = false;
			reachedStart = false;
		}
		if(client.connected() == 0) {
			counter++;
			Serial.println("Not connected");
		} else {
			counter = 0;
		}

		if(counter > 30) {
			counter = 0;
			int retVal = -1;
			do {
				retVal = client.connect("192.168.1.10",3000);
				Serial.println("Attempting to connect to driver station");
			} while (retVal<=0);
			Serial.println("Connected to driver station");
		}
	}
}
