package ma.xproce.videoservice.web;

import ma.xproce.videoservice.dao.entities.Creator;
import ma.xproce.videoservice.dao.entities.Video;
import ma.xproce.videoservice.dao.repositories.CreatorRepository;
import ma.xproce.videoservice.dao.repositories.VideoRepository;
import ma.xproce.videoservice.dto.CreatorRequest;
import ma.xproce.videoservice.dto.VideoRequest;

import ma.xproce.videoservice.service.CreatorManager;
import ma.xproce.videoservice.service.VideoManager;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Controller
public class VideoGraphQlController {

    private CreatorManager creatorManager;
    private VideoManager videoManager;
    private CreatorRepository creatorRepository;
    private VideoRepository videoRepository;

    public VideoGraphQlController(
            CreatorRepository creatorRepository,
            VideoRepository videoRepository,
            CreatorManager creatorManager,
            VideoManager videoManager)
    {
        this.creatorManager = creatorManager;
        this.videoManager = videoManager;
        this.creatorRepository = creatorRepository;
        this.videoRepository = videoRepository;
    }

    @QueryMapping
    public List<Video> videoList() {
        return videoRepository.findAll();
    }

    @QueryMapping
    public Creator creatorById(@Argument Long id) {

        return creatorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                String.format("Creator %s not found", id)
                        )
                );
    }

    @MutationMapping
    public Creator saveCreator(@Argument CreatorRequest creator) {

        Creator newCreator = Creator.builder()
                .name(creator.getName())
                .email(creator.getEmail())
                .build();

        return creatorRepository.save(newCreator);
    }

    @MutationMapping
    public Video saveVideo(@Argument VideoRequest video) {

        Creator creator = Creator.builder()
                .name(video.getCreator().getName())
                .email(video.getCreator().getEmail())
                .build();

        Creator savedCreator = creatorRepository.save(creator);

        Video newVideo = Video.builder()
                .name(video.getName())
                .url(video.getUrl())
                .description(video.getDescription())
                .datePublication(video.getDatePublication())
                .creator(savedCreator)
                .build();

        return videoRepository.save(newVideo);
    }
    @SubscriptionMapping
    public Flux<Video> notifyVideoChange() {
        return Flux.fromStream(
                Stream.generate(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Random random = new Random();
                    CreatorRequest creatorRequest = CreatorRequest.builder().name("x" +
                                    new Random().nextInt())
                            .email("x@gmail.com").build();
                    Creator creator = creatorManager.saveCreator(creatorRequest);
                    Video video = videoManager.findById(1L);
                    video.setCreator(creator);
                    videoManager.updateVideo(video);
                    return video;
                }));
    }
}