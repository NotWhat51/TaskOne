import java.util.*;

public class DirectedWeightedGraph {

    public static class Vertex <String>{
        String name;

        public Vertex(String name) {
            this.name = name;
        }
    }

    public static class Arch <Integer>{
        String from;
        String to;
        Integer weight;

        public Arch(String from, String to, Integer weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    Map<Vertex<String>, List<Arch<Integer>>> vertexMap = new HashMap<>();

    //есть ли вершина в графе
    public boolean hasVertex(String vertexName) { return vertexMap.containsKey(vertexName); }

    //есть ли дуга в графе
    public boolean hasArch(String from, String to) {
        if (!hasVertex(from)) return false;
        List<Arch<Integer>> arches = vertexMap.get(from);
        return arches.contains(to);
    }

    //добавление вершины
    public void addVertex(String name) {
        Vertex<String> v = new Vertex(name);
        List<Arch<Integer>> arches = new ArrayList<>();
        vertexMap.put(v, arches);
    }

    //добавление дуги
    public void addArch(String from, String to, double weight) {
        List<Arch<Integer>> fromArches = vertexMap.get(from);
        List<Arch<Integer>> toArches = vertexMap.get(to);
        if (fromArches == null) this.addVertex(from);
        if (toArches == null) this.addVertex(to);
        fromArches.add(new Arch(from, to, weight));
    }

    //удаление дуги
    public void deleteArch(String from, String to, double weight) {
        if (hasArch(from, to)) vertexMap.get(from).remove(weight);
    }

    //удаление вершины
    public void deleteVertex(String name) {
        if (hasVertex(name)) vertexMap.get(name).clear();
    }

    //переименование вершины
    public void renameVertex (String name1, String name2) {
        Vertex<String> v = new Vertex(name2);
        List<Arch<Integer>> arches = vertexMap.get(name1);
        vertexMap.put(v, arches);
        this.deleteVertex(name1);
    }

    //изменение веса дуги
    public void changeWeightArch(String from, String to, double weight1, double weight2) {
        this.addArch(from, to, weight2);
        this.deleteArch(from, to, weight1);
    }

    //вывод списка вершин, в которе приходят дуги от name
    public List<String> getVertexOut(String name) {
        List<String> result = new ArrayList<>();
        List<Arch<Integer>> arches = vertexMap.get(name);
        for (Arch<Integer> element: arches) {
            result.add(element.to);
        }
        return result;
    }

    //вывод списка вершин, из которых дуги приходят в name
    public List<String> getVertexIn(String name) {
        List<String> result = new ArrayList<>();
        for (Vertex<String> element: vertexMap.keySet()) {
            if (hasArch(element.name, name)) result.add(element.name);
        }
        return result;
    }
}
