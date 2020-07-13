package br.com.almeida.casbeer;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.almeida.casbeer.helper.BeerDAO;
import br.com.almeida.casbeer.model.Beer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        verify(beerDAOMock, times(1)).insert(beer);

    }

    @Test
    public void testInsertEmptyBeer(){

        Beer beer = new Beer();

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(false);

        Assert.assertFalse(beerDAOMock.insert(beer));
        verify(beerDAOMock, times(1)).insert(beer);
    }

    @Test
    public void testInsertBeerOnlyWithName(){

        Beer beer = new Beer();
        beer.setName("beerTest");

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(false);

        Assert.assertFalse(beerDAOMock.insert(beer));
        verify(beerDAOMock, times(1)).insert(beer);

    }

    @Test
    public void testInsertBeerOnlyWithDescription(){

        Beer beer = new Beer();
        beer.setDescription("beerTestDescription");

        BeerDAO beerDAOMock = mock(BeerDAO.class);

        when(beerDAOMock.insert(beer)).thenReturn(false);

        Assert.assertFalse(beerDAOMock.insert(beer));
        verify(beerDAOMock, times(1)).insert(beer);

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
        verify(beerDAOMock, times(1)).insert(beer);

    }

    @Test
    public void testListBeers(){

        Beer beer = new Beer();
        beer.setName("testName");
        beer.setTagline("testTagLine");
        beer.setDescription("testDescription");
        beer.setImage_url("testImageUrl");

        Beer beer2 = new Beer();
        beer2.setName("testName2");
        beer2.setTagline("testTagLine2");
        beer2.setDescription("testDescription2");
        beer2.setImage_url("testImageUrl2");

        Beer beer3 = new Beer();
        beer3.setName("testName3");
        beer3.setTagline("testTagLine3");
        beer3.setDescription("testDescription3");
        beer3.setImage_url("testImageUrl3");

        List<Beer> beerList = new ArrayList<>();
        BeerDAO beerDAOMock = mock(BeerDAO.class);
        beerDAOMock.insert(beer);
        beerDAOMock.insert(beer2);
        beerDAOMock.insert(beer3);

        when(beerDAOMock.list()).thenReturn(Arrays.asList(beer,beer2,beer3));

        //test
        List<Beer> empList = new ArrayList<>();
        empList = beerDAOMock.list();

        Assert.assertEquals(3, empList.size());
        verify(beerDAOMock, times(1)).insert(beer);

    }
}