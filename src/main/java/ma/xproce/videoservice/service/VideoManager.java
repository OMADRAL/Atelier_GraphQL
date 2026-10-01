package ma.xproce.videoservice.service;

import ma.xproce.videoservice.dao.entities.Video;
import ma.xproce.videoservice.dao.repositories.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoManager {

    private VideoRepository videoRepository;

    public VideoManager(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Video findById(Long id) {

        return videoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Video " + id + " not found")
                );
    }

    public Video updateVideo(Video video) {

        return videoRepository.save(video);
    }
}