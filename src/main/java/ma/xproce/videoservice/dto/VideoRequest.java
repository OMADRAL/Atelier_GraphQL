package ma.xproce.videoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRequest {

    private String name;
    private String url;
    private String description;
    private String datePublication;
    private CreatorRequest creator;
}