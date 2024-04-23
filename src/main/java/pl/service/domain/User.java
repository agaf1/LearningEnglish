package pl.service.domain;

import java.util.List;

public record User(Integer id, String name, List<Phrase> phrases) {
    public User(Integer id, String name){
        this(id,name,List.of());
    }
}
