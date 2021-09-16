package zinovev_lab_3; /**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
import java.util.*;
public class AStarState
{
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    // Хэш-карта для открытых вершин
    Map<Location, Waypoint> open = new HashMap<Location, Waypoint>();

    // Хэш-карта для закрытых вершин
    Map<Location, Waypoint> closed = new HashMap<Location, Waypoint>();

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {

        return map;
    }

    /**
     * метод проверяет все точки в наборе открытых вершин
     * и после этого возвращает ссылку на вершину с наименьшей общей стоимостью.
     * Если набор открытых вершин пустой возвращается NULL. **/
    public Waypoint getMinOpenWaypoint()
    {
        //если набор открытых вершин пустой возвращается NULL.
        if (numOpenWaypoints() == 0)
            return null;

        //Лучший путь (с наименьшей общей стоимостью)
        Waypoint best = null;
        //Стоимость лучшего пути
        double best_cost = Double.MAX_VALUE;

        //перебор всех точек в наборе открытых вершин
        for (Location loc : open.keySet()) {
            //стоимость текущего пути
            double current_waypoint_cost = open.get(loc).getTotalCost();

            //если стоимость текущего пути меньше предыдущего лучшего, меняем лучший и запоминаем его
            if (current_waypoint_cost < best_cost) {
                best = open.get(loc);
                best_cost = current_waypoint_cost;
            }
        }

        //возвращаем путь с наименьшей общей стоимостью
        return best;
    }

    /**
     * Метод добавляет путевую точку в набор открытых (или обновляет имеющуюся)
     * Если такой вершины еще нет в наборе открытых вершин, то она туда добавляется.
     * Если вершина уже существует в наборе открытых вершин,
     * она заменяет старую только если значение стоимости до новой вершины
     * меньше, чем значение стоимости до текущей вершины из набора.
     * Возвращает true если вершина добавлена (или обновлена) и false - иначе
     **/
    public boolean addOpenWaypoint(Waypoint waypoint)
    {
        Location location = waypoint.getLocation();
        //Если точки еще нет в наборе открытых вершин, то она добавляется
        if (!open.containsKey(location)) {
            open.put(location, waypoint);
            return true;
        }
        else {
            Waypoint current_waypoint = open.get(location);
            //сравнение стоимости до входной вершины и до текущей из набора
            //если новая вершина имеет меньшую стоимость - заносим ее в набор
            if (waypoint.getPreviousCost() < current_waypoint.getPreviousCost()) {
                open.put(location, waypoint);
                return true;
            }
            return false;
        }
    }


    //метод возвращает количество точек в наборе открытых вершин
    public int numOpenWaypoints()
    {
        return open.size();
    }

    /**
     * Метод перемещает вершину из набора «открытых вершин» в набор
     * «закрытых вершин».
     **/
    public void closeWaypoint(Location loc)
    {
        if (open.containsKey(loc)) {
            closed.put(loc, open.get(loc));
            open.remove(loc);
        }
    }

    /**
     * метод возвращает true, если указанное местоположение встречается в наборе закрытых вершин,
     * false - иначе.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed.containsKey(loc);
    }
}