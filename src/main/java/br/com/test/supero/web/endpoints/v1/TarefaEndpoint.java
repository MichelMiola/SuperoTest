package br.com.test.supero.web.endpoints.v1;

import br.com.test.supero.core.dao.entity.Tarefa;
import br.com.test.supero.core.service.TarefaService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tarefa")
public class TarefaEndpoint {

    private TarefaService tarefaService;

    @Autowired
    public TarefaEndpoint(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping("{idTarefa}")
    public ResponseEntity filter(@PathVariable Integer idTarefa) {
        try {
            Tarefa tarefa = tarefaService.get(idTarefa);
            return ResponseEntity.ok().body(tarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping()
    public ResponseEntity list() {
        Iterable<Tarefa> list = tarefaService.list();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping()
    public ResponseEntity save(@RequestBody String json) {
        Tarefa tarefa = new Gson().fromJson(json, Tarefa.class);
        this.tarefaService.insert(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{idTarefa}")
    public ResponseEntity update(@PathVariable Integer idTarefa, @RequestBody String json) {
        try {
            tarefaService.get(idTarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Tarefa tarefa = new Gson().fromJson(json, Tarefa.class);
        tarefa.setIdTarefa(idTarefa);
        this.tarefaService.update(tarefa);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{idTarefa}")
    public ResponseEntity delete(@PathVariable Integer idTarefa) {
        this.tarefaService.delete(idTarefa);
        return ResponseEntity.ok().build();
    }

}
