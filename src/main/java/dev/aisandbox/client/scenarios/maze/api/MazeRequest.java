package dev.aisandbox.client.scenarios.maze.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerRequest;
import lombok.Data;

/**
 * @author gde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MazeRequest implements ServerRequest {

    private Config config = new Config();
    private History history;
    private Position currentPosition;

}
