package app;

import app.dao.Dao;
import app.dao.PessoaDao;
import dominio.Pessoa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Programa {

    private static Dao<Pessoa> pessoaDao;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ejpa");
        pessoaDao = new PessoaDao(entityManagerFactory.createEntityManager());
        List<Pessoa> pessoas = criaPessoas();
        pessoas.forEach(p -> save(p));
        Pessoa pessoa = get(1);
        System.out.println(pessoa);
        update(pessoa, new String[]{"Marcus", "newemail@frinds.com"});
        save(new Pessoa("Rachel", "rachel@frineds.com"));
        delete(get(2));
        getAll().forEach(p -> System.out.println(p.getNome()));
    }

    public static Pessoa get(long id) {
        Optional<Pessoa> pessoa = pessoaDao.get(id);

        return pessoa.orElseGet(
                () -> new Pessoa("Pessoa não existente", "Não há email"));
    }

    public static List<Pessoa> getAll() {
        return pessoaDao.getAll();
    }

    public static void save(Pessoa pessoa) {
        pessoaDao.save(pessoa);
    }

    public static void update(Pessoa pessoa, String[] params) {
        pessoaDao.update(pessoa, params);
    }

    public static void delete(Pessoa pessoa) {
        pessoaDao.delete(pessoa);
    }

    public static List<Pessoa> criaPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();

        Pessoa p1 = new Pessoa("Marcus", "marcus@email.com");
        Pessoa p2 = new Pessoa("Vinicius", "vin@email.com");
        Pessoa p3 = new Pessoa("Oliveira", "oliv@email.com");

        pessoas.add(p1);
        pessoas.add(p2);
        pessoas.add(p3);

        return pessoas;
    }
}
