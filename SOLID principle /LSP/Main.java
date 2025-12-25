// This is example of Liskov Substitution Principle which L from Solid principle
class Notification {
    public void sendNotification(){
        System.out.println("Email Notification");
    }
}

class TextNotification extends Notification{

    @Override
    public void sendNotification(){
        System.out.println("Text Notificatin");
    }
}

class WpNotification extends Notification{

    @Override
    public void sendNotification(){
        System.out.println("Wp Notificatin");
    }
}


public class Main {
    public static void main(String[] args) {
        Notification notification = new TextNotification();
        notification.sendNotification();
    }
}
