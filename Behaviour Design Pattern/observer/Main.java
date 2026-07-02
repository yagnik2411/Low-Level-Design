import java.util.*;

// OBSERVER PATTERN
// -----------------
// The idea: one object (the "subject") holds a list of dependents (the
// "observers"), and whenever the subject's state changes, it pushes that
// change out to everyone on the list. The subject doesn't know or care
// what each observer actually DOES with the update — email, push notification,
// SMS, whatever. It just knows "these things want to be told."
//
// Classic real-world mapping: YouTube channel = subject, subscribers = observers.
// Upload a video -> every subscriber gets notified, in whatever form they signed up for.

// ===================================================================
// STEP 1: THE OBSERVER INTERFACE
// This is the contract every subscriber type has to follow — one method,
// "update", that the subject calls when something happens. The subject
// will only ever talk to subscribers through this interface.
// ===================================================================

interface Subscriber {
    void update(String videoTitle);
}

// ===================================================================
// STEP 2: CONCRETE OBSERVERS
// Same interface, completely different reactions. That's the flexibility
// you're buying — YouTubeChannel doesn't need an if/else for "is this an
// email subscriber or a mobile subscriber," it just calls update() and
// lets each class handle it their own way.
// ===================================================================

class EmailSubscriber implements Subscriber {
    private String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("Email sent to-" + email + ": New video uploaded with title:" + videoTitle);
    }
}

class MobileSubscriber implements Subscriber {
    private String username;

    public MobileSubscriber(String username) {
        this.username = username;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("Push Notification  sent to-" + username + ": New video uploaded with title:" + videoTitle);
    }
}

// ===================================================================
// STEP 3: THE SUBJECT INTERFACE
// This defines what any "observable" thing needs to support: a way to
// join the list, a way to leave it, and a way to broadcast to everyone
// currently on it.
// ===================================================================

interface Channel {
    void subscribe(Subscriber subscriber);

    void unsubscribe(Subscriber subscriber);

    void notifyAll(String videoTitle);
    // Heads up on naming: java.lang.Object already has a method called
    // notifyAll() (used for thread synchronization, totally unrelated to
    // this). This one takes a String so it's just an overload, not an
    // override, so it compiles fine — but the name collision can be
    // genuinely confusing to read later. Calling it notifySubscribers()
    // instead would be the safer real-world choice.
}

// ===================================================================
// STEP 4: THE CONCRETE SUBJECT
// This is where the actual list of observers lives, and where the
// "something happened, tell everyone" logic runs.
// ===================================================================

class YouTubeChannel implements Channel {
    private List<Subscriber> subscribers = new ArrayList<>();
    private String channelName;

    public YouTubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifyAll(String videoTitle) {
        // Loop through every observer and push the update. The channel
        // has no idea what each subscriber is going to do with this —
        // that's the point. Subject and observer are decoupled.
        for (Subscriber subscriber : subscribers) {
            subscriber.update(videoTitle);
        }
    }

    public void uploadNewVideo(String videoTitle) {
        // This is the "state change" that triggers the whole pattern.
        // In a real system this could be any event — someone reacts to it
        // by calling notifyAll() right after.
        System.out.println("new video uploaded:" + videoTitle);
        notifyAll(videoTitle);
    }
}

// ===================================================================
// STEP 5: DEMO
// Note: this demo never actually calls subscribe()! It creates a channel
// and uploads a video straight away, so the subscriber list is empty and
// the for-loop in notifyAll() just does nothing silently. Worth fixing
// below so you can actually see the notification fire.
// ===================================================================

class Main {
    public static void main(String[] args) {
        YouTubeChannel channel = new YouTubeChannel("yagnik");

        // Wire up some observers before triggering the event, otherwise
        // there's nobody to notify.
        Subscriber emailSub = new EmailSubscriber("yagnik@example.com");
        Subscriber mobileSub = new MobileSubscriber("yagnik_dev");

        channel.subscribe(emailSub);
        channel.subscribe(mobileSub);

        channel.uploadNewVideo("Design Patterns in Java");

        // unsubscribe works the same way in reverse — try it yourself:
        // channel.unsubscribe(mobileSub);
        // channel.uploadNewVideo("Another Upload");
        // and mobileSub should go quiet on the second notification.
    }
}

// ===================================================================
// QUICK RECAP FOR INTERVIEWS
// ===================================================================
// - Subject (Channel/YouTubeChannel) maintains the list of Observers
// (Subscriber/EmailSubscriber/MobileSubscriber) and pushes updates to
// all of them on a state change.
// - Subject and Observer only know each other through interfaces —
// YouTubeChannel never references EmailSubscriber or MobileSubscriber
// directly. You can add a new subscriber type (say, SmsSubscriber)
// without touching YouTubeChannel at all.
// - This is a "push" style Observer — the subject sends the actual data
// (videoTitle) in the update() call. A "pull" style would instead pass
// a reference to the subject itself and let the observer ask it for
// whatever data it needs. Push is simpler; pull scales better when
// different observers need different pieces of state.
// - Common follow-up question: what if notifyAll() throws an exception
// halfway through the loop? As written, one broken subscriber kills
// the notification for everyone after it in the list — worth knowing
// as a real-world gotcha even if this demo doesn't need to solve it.