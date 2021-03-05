import java.util.*;

public class DirectedWeightedGraph {

    public static class Vertex <String>{
        String name;

        public Vertex(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?> vertex = (Vertex<?>) o;
            return name.equals(vertex.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Arch<?> arch = (Arch<?>) o;
            return from.equals(arch.from) &&
                    to.equals(arch.to) &&
                    weight.equals(arch.weight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, weight);
        }
    }

    Map<Vertex<String>, List<Arch<Integer>>> vertexMap = new HashMap<>();

    //есть ли вершина в графе
    public boolean hasVertex(String vertexName) { return vertexMap.containsKey(new Vertex<>(vertexName)); }

    //есть ли дуга в графе
    public boolean hasArch(String from, String to) {
        if (!hasVertex(from)) return false;
        List<Arch<Integer>> arches = vertexMap.get(new Vertex<>(from));

        for (Arch<Integer> arch: arches) {
            if (arch.to.equals(to))
                return true;
        }
        return false;
    }

    //добавление вершины
    public void addVertex(String name) {
        Vertex<String> v = new Vertex<>(name);
        List<Arch<Integer>> arches = new ArrayList<>();
        vertexMap.put(v, arches);
    }

    //добавление дуги
    public void addArch(String from, String to, int weight) {
        List<Arch<Integer>> fromArches = vertexMap.get(new Vertex<>(from));
        List<Arch<Integer>> toArches = vertexMap.get(new Vertex<>(to));
        if (fromArches == null) {
            this.addVertex(from);
            fromArches = vertexMap.get(new Vertex<>(from));
        }
        if (toArches == null) this.addVertex(to);
        fromArches.add(new Arch<>(from, to, weight));
    }

    //удаление дуги
    public void deleteArch(String from, String to, int weight) {
        if (hasArch(from, to)) vertexMap.get(new Vertex<>(from)).remove(new Arch<>(from, to, weight));
    }

    //удаление вершины
    public void deleteVertex(String name) {
        if (hasVertex(name)) vertexMap.remove(new Vertex<>(name));
    }

    //переименование вершины
    public void renameVertex (String name1, String name2) {
        Vertex<String> v = new Vertex<>(name2);
        List<Arch<Integer>> arches = vertexMap.get(new Vertex<>(name1));
        vertexMap.put(v, arches);
        this.deleteVertex(name1);
    }

    //изменение веса дуги
    public void changeWeightArch(String from, String to, int weight1, int weight2) {
        this.addArch(from, to, weight2);
        this.deleteArch(from, to, weight1);
    }

    //вывод списка вершин, которые исходят из вершины name
    public Set<String> getVertexOut(String name) {
        Set<String> result = new HashSet<>();
        List<Arch<Integer>> arches = vertexMap.get(new Vertex<>(name));
        for (Arch<Integer> element: arches) {
            result.add(element.to);
        }
        return result;
    }

    //вывод списка вершин, которые приходят в вершину name
    public Set<String> getVertexIn(String name) {
        Set<String> result = new HashSet<>();
        for (Vertex<String> element: vertexMap.keySet()) {
            if (hasArch(element.name, name)) result.add(element.name);
        }
        return result;
    }
}
