package ma.xproce.videoservice.service;
import ma.xproce.videoservice.dao.entities.Creator;
import ma.xproce.videoservice.dao.repositories.CreatorRepository;
import ma.xproce.videoservice.dto.CreatorRequest;
import org.springframework.stereotype.Service;

@Service
public class CreatorManager {

    private CreatorRepository creatorRepository;

    public CreatorManager(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    public Creator saveCreator(CreatorRequest creatorRequest) {

        Creator creator = Creator.builder()
                .name(creatorRequest.getName())
                .email(creatorRequest.getEmail())
                .build();

        return creatorRepository.save(creator);
    }
}