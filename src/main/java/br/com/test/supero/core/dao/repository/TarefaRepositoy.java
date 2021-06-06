package br.com.test.supero.core.dao.repository;

import br.com.test.supero.core.dao.entity.Tarefa;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface TarefaRepositoy extends CrudRepository<Tarefa, Integer> {

    Collection<Tarefa> findByDataRemocaoIsNull();

    Optional<Tarefa> findByIdTarefaAndDataRemocaoIsNull(Integer integer);
}
