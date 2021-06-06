package br.com.test.supero.core.service;

import br.com.test.supero.core.dao.entity.Tarefa;
import br.com.test.supero.core.dao.repository.TarefaRepositoy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class TarefaService {

    private final TarefaRepositoy tarefaRepositoy;

    public TarefaService(TarefaRepositoy tarefaRepositoy) {
        this.tarefaRepositoy = tarefaRepositoy;
    }

    public int insert(Tarefa tarefa) {
        Tarefa tarefaInsert = this.tarefaRepositoy.save(tarefa);
        return tarefaInsert.getIdTarefa();
    }

    public void update(Tarefa tarefa) {
        Tarefa tarefaDatabase = this.get(tarefa.getIdTarefa());
        tarefaDatabase.setTitulo(tarefa.getTitulo());
        tarefaDatabase.setDataEdicao(LocalDateTime.now());
        tarefaDatabase.setStatusConcluido(tarefa.isStatusConcluido());
        tarefaDatabase.setDescricao(tarefa.getDescricao());
        this.tarefaRepositoy.save(tarefaDatabase);
    }

    public Tarefa get(Integer idTarefa) {
        Optional<Tarefa> tarefa = tarefaRepositoy.findByIdTarefaAndDataRemocaoIsNull(idTarefa);
        return tarefa.orElseThrow(IllegalArgumentException::new);
    }

    public Iterable<Tarefa> list() {
        return tarefaRepositoy.findByDataRemocaoIsNull();
    }

    public void delete(Integer idTarefa) {
        Tarefa tarefa = this.get(idTarefa);
        tarefa.setDataRemocao(LocalDateTime.now());
        this.tarefaRepositoy.save(tarefa);
    }

}
