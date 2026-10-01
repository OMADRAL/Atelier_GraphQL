package ma.xproce.videoservice;

import ma.xproce.videoservice.dao.entities.Creator;
import ma.xproce.videoservice.dao.entities.Video;
import ma.xproce.videoservice.dao.repositories.CreatorRepository;
import ma.xproce.videoservice.dao.repositories.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VideoServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(VideoServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(
            CreatorRepository creatorRepository,
            VideoRepository videoRepository) {
        return args -> {
            List<Creator> creators = List.of(
                    Creator.builder()
                            .name("ouma")
                            .email("ouma@example.com")
                            .build(),
                    Creator.builder()
                            .name("Fati")
                            .email("Fati@example.com")
                            .build()
            );
            creatorRepository.saveAll(creators);
            List<Video> videos = List.of(
                    Video.builder()
                            .name("graphQl lesson")
                            .url("http://test1.com/video1")
                            .description("Introduction à GraphQl")
                            .datePublication("28/09/2026")
                            .creator(creators.get(0))
                            .build(),
                    Video.builder()
                            .name("Rest lesson")
                            .url("http://test2.com/video2")
                            .description("Introduction à GraphQl")
                            .datePublication("30/09/2026")
                            .creator(creators.get(0))
                            .build()
            );
            videoRepository.saveAll(videos);
        };
    }


}
