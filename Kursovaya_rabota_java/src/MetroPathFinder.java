import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

/**
 * Класс MetroPathFinder поиск путей в метро
 */
public class    MetroPathFinder extends Application {
    private MetroGraph graph;
    /**
     * запуск приложения
     *
     * @param primaryStage основной этап приложения
     */
    @Override
    public void start(Stage primaryStage) {
        graph = new MetroGraph();
        CreateGraph();

        Label startLineLabel = new Label("Выберите ветку метро для отправления:");
        ComboBox<String> startLineComboBox = new ComboBox<>();
        startLineComboBox.getItems().addAll("Оранжевая", "Зеленая", "Красная", "Синяя", "Фиолетовая");

        Label endLineLabel = new Label("Выберите ветку метро для прибытия:");
        ComboBox<String> endLineComboBox = new ComboBox<>();
        endLineComboBox.getItems().addAll("Оранжевая", "Зеленая", "Красная", "Синяя", "Фиолетовая");

        ComboBox<String> startStationComboBox = new ComboBox<>();
        ComboBox<String> endStationComboBox = new ComboBox<>();

        Button findPathButton = new Button("Найти путь");
        ListView<String> pathListView = new ListView<>();
        Label resultLabel = new Label();

        startLineComboBox.setOnAction(e -> {
            String selectedLine = startLineComboBox.getValue();
            startStationComboBox.getItems().clear();
            startStationComboBox.getItems().addAll(graph.getStationsByLine(selectedLine));
        });

        endLineComboBox.setOnAction(e -> {
            String selectedLine = endLineComboBox.getValue();
            endStationComboBox.getItems().clear();
            endStationComboBox.getItems().addAll(graph.getStationsByLine(selectedLine));
        });

        findPathButton.setOnAction(e -> {
            String start = startStationComboBox.getValue();
            String end = endStationComboBox.getValue();
            if (start == null || end == null) {
                resultLabel.setText("Пожалуйста, выберите обе станции.");
                pathListView.getItems().clear();
                return;
            }
            MetroGraph.PathResult pathResult = graph.dijkstra(start, end);
            if (pathResult != null) {
                resultLabel.setText("Минимальное время: " + pathResult.distance + " минут");
                pathListView.getItems().clear();
                pathListView.getItems().addAll(formatPathWithDirections(pathResult));
            } else {
                resultLabel.setText("Путь не найден");
                pathListView.getItems().clear();
            }
        });

        VBox vbox = new VBox(10, startLineLabel, startLineComboBox, startStationComboBox, endLineLabel, endLineComboBox, endStationComboBox, findPathButton, resultLabel, pathListView);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Metro Path Finder");
        primaryStage.show();
    }
    /**
     * Форматирует путь с указаниями между станциями
     *
     * @param pathResult результат поиска пути
     * @return список строк с указаниями между станциями
     */
    private List<String> formatPathWithDirections(MetroGraph.PathResult pathResult) {
        List<String> formattedPath = new ArrayList<>();
        for (int i = 0; i < pathResult.path.size() - 1; i++) {
            String fromStation = pathResult.path.get(i);
            String toStation = pathResult.path.get(i + 1);
            int time = graph.getTravelTime(fromStation, toStation);
            formattedPath.add(fromStation + " → " + toStation);
        }
        return formattedPath;
    }
    /**
     * Создает граф метро с станциями и соединениями
     */
    private void CreateGraph() {
        graph = new MetroGraph();

        // Оранжевая ветка
        graph.addStation("Улица Дыбенко", "Оранжевая");
        graph.addStation("Проспект Большивиков", "Оранжевая");
        graph.addStation("Ладожская", "Оранжевая");
        graph.addStation("Новочеркасская", "Оранжевая");
        graph.addStation("Площадь Александра Невского 2", "Оранжевая");
        graph.addStation("Лиговский проспект", "Оранжевая");
        graph.addStation("Достоевская", "Оранжевая");
        graph.addStation("Спасская", "Оранжевая");
        //Зеленая ветка
        graph.addStation("Рыбацкое", "Зеленая");
        graph.addStation("Обухово", "Зеленая");
        graph.addStation("Пролетарская", "Зеленая");
        graph.addStation("Ломоносовская", "Зеленая");
        graph.addStation("Елизаровская", "Зеленая");
        graph.addStation("Площадь Александра Невского 1", "Зеленая");
        graph.addStation("Маяковская", "Зеленая");
        graph.addStation("Гостиный двор", "Зеленая");
        graph.addStation("Василеостровская", "Зеленая");
        graph.addStation("Приморская", "Зеленая");
        graph.addStation("Зенит", "Зеленая");
        graph.addStation("Беговая", "Зеленая");
        //красная ветка
        graph.addStation("Девяткино", "Красная");
        graph.addStation("Гражданский проспект", "Красная");
        graph.addStation("Академическая", "Красная");
        graph.addStation("Политехническая", "Красная");
        graph.addStation("Площадь мужества", "Красная");
        graph.addStation("Лесная", "Красная");
        graph.addStation("Выборгская", "Красная");
        graph.addStation("Площадь Ленина", "Красная");
        graph.addStation("Чернышевская", "Красная");
        graph.addStation("Площадь Восстания", "Красная");
        graph.addStation("Владимирская", "Красная");
        graph.addStation("Пушкинская", "Красная");
        graph.addStation("Технологический институт 1", "Красная");
        graph.addStation("Балтийская", "Красная");
        graph.addStation("Нарвская", "Красная");
        graph.addStation("Кировский завод", "Красная");
        graph.addStation("Автово", "Красная");
        graph.addStation("Ленинский проспект", "Красная");
        graph.addStation("Проспект ветеранов", "Красная");
        //синяя ветка
        graph.addStation("Парнас", "Синяя");
        graph.addStation("Проспект просвещения", "Синяя");
        graph.addStation("Озерки", "Синяя");
        graph.addStation("Удельная", "Синяя");
        graph.addStation("Пионерская", "Синяя");
        graph.addStation("Черная речка", "Синяя");
        graph.addStation("Петроградская", "Синяя");
        graph.addStation("Горьковская", "Синяя");
        graph.addStation("Невский проспект", "Синяя");
        graph.addStation("Сенная площадь", "Синяя");
        graph.addStation("Технологический институт 2", "Синяя");
        graph.addStation("Фрунзенская", "Синяя");
        graph.addStation("Московские ворота", "Синяя");
        graph.addStation("Электросила", "Синяя");
        graph.addStation("Парк победы", "Синяя");
        graph.addStation("Московская", "Синяя");
        graph.addStation("Звездная", "Синяя");
        graph.addStation("Купчино", "Синяя");
        // Фиолетовая ветка
        graph.addStation("Комендантский проспект", "Фиолетовая");
        graph.addStation("Старая деревня", "Фиолетовая");
        graph.addStation("Крестовский остров", "Фиолетовая");
        graph.addStation("Чкаловская", "Фиолетовая");
        graph.addStation("Спортивная", "Фиолетовая");
        graph.addStation("Адмиралтейская", "Фиолетовая");
        graph.addStation("Садовая", "Фиолетовая");
        graph.addStation("Звенигородская", "Фиолетовая");
        graph.addStation("Обводный канал", "Фиолетовая");
        graph.addStation("Волковская", "Фиолетовая");
        graph.addStation("Бухарестская", "Фиолетовая");
        graph.addStation("Международная", "Фиолетовая");
        graph.addStation("Проспект славы", "Фиолетовая");
        graph.addStation("Дунайская", "Фиолетовая");
        graph.addStation("Шушары", "Фиолетовая");
        // Оранжевая ветка
        graph.addConnection("Улица Дыбенко", "Проспект Большивиков", 3);
        graph.addConnection("Проспект Большивиков", "Ладожская", 3);
        graph.addConnection("Ладожская", "Новочеркасская", 3);
        graph.addConnection("Новочеркасская", "Площадь Александра Невского 2", 3);
        graph.addConnection("Площадь Александра Невского 2", "Лиговский проспект", 3);
        graph.addConnection("Лиговский проспект", "Достоевская", 3);
        graph.addConnection("Достоевская", "Спасская", 3);
        //Зеленая ветка
        graph.addConnection("Рыбацкое", "Обухово", 3);
        graph.addConnection("Обухово", "Пролетарская", 3);
        graph.addConnection("Пролетарская", "Ломоносовская", 3);
        graph.addConnection("Ломоносовская", "Елизаровская", 3);
        graph.addConnection("Елизаровская", "Площадь Александра Невского 1", 3);
        graph.addConnection("Площадь Александра Невского 1", "Маяковская", 3);
        graph.addConnection("Маяковская", "Гостиный двор", 3);
        graph.addConnection("Гостиный двор", "Василеостровская", 3);
        graph.addConnection("Василеостровская", "Приморская", 3);
        graph.addConnection("Приморская", "Зенит", 3);
        graph.addConnection("Зенит", "Беговая", 3);
        //Красная ветка
        graph.addConnection("Девяткино", "Гражданский проспект", 3);
        graph.addConnection("Гражданский проспект", "Академическая", 3);
        graph.addConnection("Академическая", "Политехническая", 3);
        graph.addConnection("Политехническая", "Площадь мужества", 3);
        graph.addConnection("Площадь мужества", "Лесная", 3);
        graph.addConnection("Лесная", "Выборгская", 3);
        graph.addConnection("Выборгская", "Площадь Ленина", 3);
        graph.addConnection("Площадь Ленина", "Чернышевская", 3);
        graph.addConnection("Чернышевская", "Площадь Восстания", 3);
        graph.addConnection("Площадь Восстания", "Владимирская", 3);
        graph.addConnection("Владимирская", "Пушкинская", 3);
        graph.addConnection("Пушкинская", "Технологический институт 1", 3);
        graph.addConnection("Технологический институт 1", "Балтийская", 3);
        graph.addConnection("Балтийская", "Нарвская", 3);
        graph.addConnection("Нарвская", "Кировский завод", 3);
        graph.addConnection("Кировский завод", "Автово", 3);
        graph.addConnection("Автово", "Ленинский проспект", 3);
        graph.addConnection("Ленинский проспект", "Проспект ветеранов", 3);
        //Синяя ветка
        graph.addConnection("Парнас", "Проспект просвещения", 3);
        graph.addConnection("Проспект просвещения", "Озерки", 3);
        graph.addConnection("Озерки", "Удельная", 3);
        graph.addConnection("Удельная", "Пионерская", 3);
        graph.addConnection("Пионерская", "Черная речка", 3);
        graph.addConnection("Черная речка", "Петроградская", 3);
        graph.addConnection("Петроградская", "Горьковская", 3);
        graph.addConnection("Горьковская", "Невский проспект", 3);
        graph.addConnection("Невский проспект", "Сенная площадь", 3);
        graph.addConnection("Сенная площадь", "Технологический институт 2", 3);
        graph.addConnection("Технологический институт 2", "Фрунзенская", 3);
        graph.addConnection("Фрунзенская", "Московские ворота", 3);
        graph.addConnection("Московские ворота", "Электросила", 3);
        graph.addConnection("Электросила", "Парк победы", 3);
        graph.addConnection("Парк победы", "Московская", 3);
        graph.addConnection("Московская", "Звездная", 3);
        graph.addConnection("Звездная", "Купчино", 3);
        //Фиолетоая ветка
        graph.addConnection("Комендантский проспект", "Старая деревня", 3);
        graph.addConnection("Старая деревня", "Крестовский остров", 3);
        graph.addConnection("Крестовский остров", "Чкаловская", 3);
        graph.addConnection("Чкаловская", "Спортивная", 3);
        graph.addConnection("Спортивная", "Адмиралтейская", 3);
        graph.addConnection("Адмиралтейская", "Садовая", 3);
        graph.addConnection("Садовая", "Звенигородская", 3);
        graph.addConnection("Звенигородская", "Обводный канал", 3);
        graph.addConnection("Обводный канал", "Волковская", 3);
        graph.addConnection("Волковская", "Бухарестская", 3);
        graph.addConnection("Бухарестская", "Международная", 3);
        graph.addConnection("Международная", "Проспект славы", 3);
        graph.addConnection("Проспект славы", "Дунайская", 3);
        graph.addConnection("Дунайская", "Шушары", 3);
        //Соединения
        graph.addConnection("Площадь Александра Невского 1", "Площадь Александра Невского 2", 2);
        graph.addConnection("Площадь Восстания", "Маяковская", 2);
        graph.addConnection("Владимирская", "Достоевская", 2);
        graph.addConnection("Невский проспект", "Гостиный двор", 2);
        graph.addConnection("Технологический институт 1", "Технологический институт 2", 2);
        graph.addConnection("Пушкинская", "Звенигородская", 2);
        graph.addConnection("Сенная площадь", "Спасская", 2);
        graph.addConnection("Сенная площадь", "Садовая", 2);
        graph.addConnection("Спасская", "Садовая", 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/**
 * Класс MetroGraph показывает граф метро с станциями и соединениями
 */
class MetroGraph {
    private final Map<String, List<Connection>> adjacencyList = new HashMap<>();
    private final Map<String, String> stationLines = new HashMap<>();
    /**
     * Добавляет станцию в граф метро
     *
     * @param station название станции
     * @param line    линия метро
     */
    public void addStation(String station, String line) {
        adjacencyList.putIfAbsent(station, new ArrayList<>());
        stationLines.put(station, line);
    }
    /**
     * Добавляет соединение между двумя станциями
     *
     * @param station1 первая станция
     * @param station2 вторая станция
     * @param time     время в пути между станциями
     */
    public void addConnection(String station1, String station2, int time) {
        adjacencyList.get(station1).add(new Connection(station2, time));
        adjacencyList.get(station2).add(new Connection(station1, time));
    }
    /**
     * Получает список станций по линии
     *
     * @param line линия метро
     * @return список станций, принадлежащих указанной линии
     */
    public List<String> getStationsByLine(String line) {
        List<String> stations = new ArrayList<>();
        for (Map.Entry<String, String> entry : stationLines.entrySet()) {
            if (entry.getValue().equals(line)) {
                stations.add(entry.getKey());
            }
        }
        return stations;
    }
    /**
     * Реализует алгоритм Дейкстера для поиска кратчайшего пути между станциями
     *
     * @param start начальная станция
     * @param end   конечная станция
     * @return результат поиска пути, содержащий расстояние и сам путь
     */
    public PathResult dijkstra(String start, String end) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousStations = new HashMap<>();
        PriorityQueue<Connection> queue = new PriorityQueue<>(Comparator.comparingInt(c -> c.time));

        for (String station : adjacencyList.keySet()) {
            distances.put(station, Integer.MAX_VALUE);
            previousStations.put(station, null);
        }
        distances.put(start, 0);
        queue.add(new Connection(start, 0));

        while (!queue.isEmpty()) {
            Connection current = queue.poll();
            if (current.station.equals(end)) {
                List<String> path = new ArrayList<>();
                for (String at = end; at != null; at = previousStations.get(at)) {
                    path.add(at);
                }
                Collections.reverse(path);
                return new PathResult(distances.get(end), path);
            }

            for (Connection neighbor : adjacencyList.get(current.station)) {
                int newDist = distances.get(current.station) + neighbor.time;
                if (newDist < distances.get(neighbor.station)) {
                    distances.put(neighbor.station, newDist);
                    previousStations.put(neighbor.station, current.station);
                    queue.add(new Connection(neighbor.station, newDist));
                }
            }
        }
        return null;
    }
    /**
     * Получает время в пути между двумя станциями
     *
     * @param fromStation начальная станция
     * @param toStation   конечная станция
     * @return время в пути между станциями
     */
    public int getTravelTime(String fromStation, String toStation) {
        for (Connection connection : adjacencyList.get(fromStation)) {
            if (connection.station.equals(toStation)) {
                return connection.time;
            }
        }
        return 0;
    }
    /**
    * Класс, представляющий результат поиска пути.
    */
    public static class PathResult {
        public int distance;
        public List<String> path;

        public PathResult(int distance, List<String> path) {
            this.distance = distance; this.path = path;
        }
    }
    /**
    * Класс, представляющий соединение между станциями
    */
    private static class Connection {
        String station;
        int time;

        Connection(String station, int time) {
            this.station = station;
            this.time = time;
        }
    }
}