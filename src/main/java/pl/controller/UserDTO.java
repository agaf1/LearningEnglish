package pl.controller;

import pl.service.domain.Phrase;

import java.util.List;


record UserDTO(Integer id, String name, List<Phrase> phrases) {
}
