#define LEFT 1
#define RIGHT 0

void setup() {  
	motorInit();
}

void loop() {
	setMotor(LEFT,100);
	setMotor(RIGHT,100);
	delay(1000); 

	setMotor(LEFT,100);
	setMotor(RIGHT,-100);
	delay(400);
}