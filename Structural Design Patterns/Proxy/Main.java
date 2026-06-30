import java.util.HashMap;
import java.util.Map;

interface VideoDownloader {
    public String downloadVideo(String videoUrl);
}

class RealVideoDownloader implements VideoDownloader {
    public String downloadVideo(String videoUrl) {
        System.out.println("Downloading video from URL: " + videoUrl);
        String content = "Video content from " + videoUrl;
        System.out.println("Downloaded Content: " + content);
        return content;
    }
}

class CachedVideoDownloader implements VideoDownloader {
    private RealVideoDownloader realVideoDownloader;
    private Map<String, String> cache;

    public CachedVideoDownloader() {
        realVideoDownloader = new RealVideoDownloader();
        cache = new HashMap<>();
    }

    @Override
    public String downloadVideo(String videoUrl) {
        if (cache.containsKey(videoUrl)) {
            System.out.println("Returning cached video for:" + videoUrl);
            return cache.get(videoUrl);
        }

        System.out.println("Cache miss.Downloading..");
        String videoContent = realVideoDownloader.downloadVideo(videoUrl);
        cache.put(videoUrl, videoContent);
        return videoContent;
    }
}

class Main {
    public static void main(String[] args) {
        
        VideoDownloader cacheVideoDownloader = new CachedVideoDownloader();
        System.out.println("User 1 tries to download the video.");
        cacheVideoDownloader.downloadVideo("https://video.com/proxy-pattern");

        System.out.println();

        System.out.println("User 2 tries to download the same video again.");
        cacheVideoDownloader.downloadVideo("https://video.com/proxy-pattern");
    }
}
