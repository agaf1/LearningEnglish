package pl.repository;

import org.springframework.data.repository.CrudRepository;

interface GameTableJpa extends CrudRepository<GameTableEntity,Integer> {
}
