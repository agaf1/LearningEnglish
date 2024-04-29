package pl.service.domain;

import lombok.Data;

@Data
public class GameTable {

    private Integer id;
    private Integer userId;
    private Integer gameId;
    private Phrase phrase;
}
