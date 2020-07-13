package br.com.almeida.casbeer;

import org.junit.Assert;
import org.junit.Test;

import br.com.almeida.casbeer.helper.BeerDAO;
import br.com.almeida.casbeer.model.Beer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeerDAOTest {

    @Test
    public void testInsertBeer(){

        Beer beer = new Beer();
        beer.setName("testName");
        beer.setTagline("testTagLine");
        beer.setDescription("testDescription");
        beer.setImage_url("testImageUrl");

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(true);

        Assert.assertTrue(beerDAOMock.insert(beer));

    }

    @Test
    public void testInsertEmptyBeer(){

        Beer beer = new Beer();

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(false);

        Assert.assertFalse(beerDAOMock.insert(beer));

    }

    @Test
    public void testInsertBeerOnlyWithName(){

        Beer beer = new Beer();
        beer.setName("beerTest");

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(false);

        Assert.assertFalse(beerDAOMock.insert(beer));

    }

    @Test
    public void testInsertBeerOnlyWithDescription(){

        Beer beer = new Beer();
        beer.setDescription("beerTestDescription");

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(false);

        Assert.assertFalse(beerDAOMock.insert(beer));

    }

    @Test
    public void testInsertBeerOnlyWithoutImageUrl(){

        Beer beer = new Beer();
        beer.setName("TestName");
        beer.setDescription("beerTestDescription");
        beer.setTagline("testTagLine");

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(false);

        Assert.assertFalse(beerDAOMock.insert(beer));

    }
}