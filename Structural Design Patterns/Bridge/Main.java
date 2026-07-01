import java.util.*;

interface VideoQuality {
    void load(String title);
}

class SDQuality implements VideoQuality {
    @Override
    public void load(String title) {
        System.out.println(title + " steaming in SD Quality");
    }
}

class HDQuality implements VideoQuality {
    @Override
    public void load(String title) {
        System.out.println(title + " steaming in HD Quality");
    }
}

class UHDQuality implements VideoQuality {
    @Override
    public void load(String title) {
        System.out.println(title + " steaming in UHD Quality");
    }
}

abstract class VideoPlayer {
    protected VideoQuality videoQuality;

    public VideoPlayer(VideoQuality videoQuality) {
        this.videoQuality = videoQuality;
    }

    public abstract void play(String title);
}

class WebPlayer extends VideoPlayer {
    public WebPlayer(VideoQuality videoQuality) {
        super(videoQuality);
    }

    @Override
    public void play(String title) {
        System.out.println("Web Player:");
        videoQuality.load(title);
    }
}

class MobilePlayer extends VideoPlayer {
    public MobilePlayer(VideoQuality videoQuality) {
        super(videoQuality);
    }

    @Override
    public void play(String title) {
        System.out.println("Mobile Player:");
        videoQuality.load(title);
    }
}

class SmartTVPlayer extends VideoPlayer {
    public SmartTVPlayer(VideoQuality videoQuality) {
        super(videoQuality);
    }

    @Override
    public void play(String title) {
        System.out.println("SmartTV Player:");
        videoQuality.load(title);
    }
}

class Main {
    public static void main(String[] args) {

    }
}
