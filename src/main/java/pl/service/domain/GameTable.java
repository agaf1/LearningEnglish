package pl.service.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//TODO mozna uzyc jednej adnotacji @Data
@Getter
@Setter
@NoArgsConstructor
public class GameTable {

    private Integer id;
    private Integer userId;
    private Integer gameId;
    private Phrase phrase;
}
