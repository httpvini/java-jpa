package app.dao;

import dominio.Pessoa;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class PessoaDao implements Dao<Pessoa>{


    private EntityManager entityManager; //=

    public PessoaDao (EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Pessoa> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Pessoa e");
        return query.getResultList();
    }

    @Override
    public Optional<Pessoa> get(long id) {
        return Optional.ofNullable(entityManager.find(Pessoa.class, id));
    }

    @Override
    public void save(Pessoa pessoa) {
        executeInsideTransaction(entityManager -> entityManager.persist(pessoa));
    }

    @Override
    public void update(Pessoa pessoa, String[] params) {
        pessoa.setNome(Objects.requireNonNull(params[0], "Nome não pode ser nulo"));
        pessoa.setEmail(Objects.requireNonNull(params[1], "Email não pode ser nulo"));
        executeInsideTransaction(entityManager -> entityManager.merge(pessoa));
    }

    @Override
    public void delete(Pessoa pessoa) {
        executeInsideTransaction(entityManager -> entityManager.remove(pessoa));
    }


    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
