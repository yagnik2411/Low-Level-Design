import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

interface EmailTemplate extends Cloneable {
    EmailTemplate clone();

    void setContent(String content);

    void send(String to);
}

class WelcomeEmail implements EmailTemplate {
    private String subject;
    private String content;

    public WelcomeEmail() {
        this.subject = "Welcome";
        this.content = "hii, I am yagnik";
    }

    public EmailTemplate clone() {
        try {
            return (WelcomeEmail) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone Failed", e);
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void send(String to) {
        System.out.println("Sending to " + to + ": [" + subject + "] " + content);
    }

}

class EmailTemplateRegistry {
    private static final Map<String, EmailTemplate> template = new HashMap<>();

    static {
        template.put("welcome", new WelcomeEmail());
    }

    public static EmailTemplate getTemplate(String type) {
        return template.get(type).clone();
    }
}

public class Main {
    public static void main(String[] args) {
        EmailTemplate welcomeEmail = EmailTemplateRegistry.getTemplate("welcome");
    }
}
