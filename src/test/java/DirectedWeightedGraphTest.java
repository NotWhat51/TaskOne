import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class DirectedWeightedGraphTest {

    private DirectedWeightedGraph graph;

    @Before
    public void init  () {
        graph = new DirectedWeightedGraph();
    }

    // addVertex и deleteVertex тестирруются здесь же
    @Test
    public void hasVertex () {
        assertFalse(graph.hasVertex("ver1"));
        graph.addVertex("ver1");
        assertTrue(graph.hasVertex("ver1"));

        graph.deleteVertex("ver1");
        assertFalse(graph.hasVertex("ver1"));
    }

    @Test
    public void renameVertex () {
        graph.addVertex("ver1");
        assertTrue(graph.hasVertex("ver1"));

        graph.renameVertex("ver1", "ver2");
        assertFalse(graph.hasVertex("ver1"));
        assertTrue(graph.hasVertex("ver2"));

    }

    //addArch тестируется совместно
    @Test
    public void changeWeightArch () {
        graph.addArch("ver1", "ver2", 3);
        assertTrue(graph.hasArch("ver1", "ver2"));

        graph.changeWeightArch("ver1", "ver2", 3, 5);
        assertTrue(graph.vertexMap.get(new DirectedWeightedGraph.Vertex<>("ver1")).contains
                (new DirectedWeightedGraph.Arch<>("ver1", "ver2", 5)));
        assertFalse(graph.vertexMap.get(new DirectedWeightedGraph.Vertex<>("ver1")).contains
                (new DirectedWeightedGraph.Arch<>("ver1", "ver2", 3)));
    }

    //getVertexOut и getVertexIn тестируются совместно
    @Test
    public void getVertexOut () {
        graph.addArch("ver1", "ver2", 5);
        graph.addArch("ver1", "ver3", 4);
        graph.addArch("ver4", "ver1", 3);
        graph.addArch("ver5", "ver1", 2);
        Set<String> listOut = new HashSet<>();
        listOut.add("ver2");
        listOut.add("ver3");
        Set<String> listIn = new HashSet<>();
        listIn.add("ver4");
        listIn.add("ver5");
        assertEquals(listOut, graph.getVertexOut("ver1"));
        assertEquals(listIn, graph.getVertexIn("ver1"));
    }
}
