package br.com.almeida.casbeer.helper;

import java.util.List;

import br.com.almeida.casbeer.model.Beer;

public interface iBeerDAO {

    public boolean insert(Beer beer);
    public boolean update(Beer beer);
    public boolean delete(Beer beer);
    public List<Beer> list();
}
