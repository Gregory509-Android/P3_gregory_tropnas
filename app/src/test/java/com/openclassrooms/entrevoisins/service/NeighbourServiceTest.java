package com.openclassrooms.entrevoisins.service;

import android.support.v4.app.Fragment;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }
    @Test
    public void getNeighbourFavorite() {
        List<Neighbour> neighbourList = service.getNeighboursFavorite();
        List<Neighbour> expectedNeighbours = new ArrayList<>();
        assertThat(neighbourList, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }
    @Test
    public void deleteNeighbourFavoriteWithSuccess() {
        service.createNeighbourFavorite(new Neighbour());
        Neighbour neighbourFavoriteToDelete = service.getNeighboursFavorite().get(0);
        service.deleteNeighbourFavorite(neighbourFavoriteToDelete);
        assertFalse(service.getNeighboursFavorite().contains(neighbourFavoriteToDelete));
    }



}



