public class Human {
	
	String name;
	int age;
	int heightInInches;
	String eyeColor;
	
	public Human() { //Constructor
		
	}
	
	public void speak(){ //M�todo 1
		System.out.println("Hello, my name is "+ name);
		System.out.println("I am "+ heightInInches +" inches tall");
		System.out.println("I am "+ age +" years old");
		System.out.println("My eye color is "+ eyeColor);
	}
	

	public void eat(){ //M�todo 2
		System.out.println("eating...");
	}
	
	public void walk(){ //M�todo 3
		System.out.println("walking...");
	}

	public void work(){ //M�todo 4
		System.out.println("working...");
	}
}

