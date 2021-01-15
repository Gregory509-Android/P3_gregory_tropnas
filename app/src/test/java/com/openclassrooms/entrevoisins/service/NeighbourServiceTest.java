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

import static org.junit.Assert.assertEquals;
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
    public void createNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        service.createNeighbour(new Neighbour());
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertEquals(expectedNeighbours.size() + 1, neighbours.size());
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        // intialemment la methode ci-dessous renvoie a une liste vide
        List<Neighbour> neighbourList = service.getFavoriteNeighbours();
        assertEquals(0, neighbourList.size());

        // ajout en favoris du 1er utilisateur de la liste
        Neighbour neighbour = service.getNeighbours().get(0);
        service.updateFavoriteNeighbour(neighbour);

        // la liste des favoris contient maintenant un neighbour
        neighbourList = service.getFavoriteNeighbours();
        assertEquals(1, neighbourList.size());
    }

    @Test
    public void updateNeighboursWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        assertEquals(false, neighbour.isFavorite());
        service.updateFavoriteNeighbour(neighbour);
        assertEquals(true, neighbour.isFavorite());
        service.updateFavoriteNeighbour(neighbour);
        assertEquals(false, neighbour.isFavorite());

    }

}



