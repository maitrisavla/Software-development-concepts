import java.util.*;

public class DependencyManager {

    //Adjaceny map for what a class is dependent upon
    Map<String, ArrayList<String>> dependsUpon;
    //Adjaceny map for what classes the current class will affect
    Map<String, ArrayList<String>> willAfect;

    //Indegree map showing the number of class the current class is dependent upon
    Map<String, Integer> indegree;

    //Constructor
    public DependencyManager() {
        dependsUpon = new HashMap<>();
        willAfect = new HashMap<>();
        indegree = new HashMap<>();
    }


    //Add class method that will initalize the entry for the class into relevent maps
    boolean addClass( String className ) throws IllegalArgumentException
    {
        if(!dependsUpon.containsKey(className))
        {
            dependsUpon.put(className, new ArrayList<>());
        }

        if(!willAfect.containsKey(className))
        {
            willAfect.put(className, new ArrayList<>());
        }

        if(!indegree.containsKey(className))
        {
            indegree.put(className,0);
        }

        return true;
    }

    //Add class method that will set up the adjacency maps
    boolean addClass( String className, Set<String> dependencies) throws
            IllegalArgumentException
    {
        addClass(className);
        Set<String> dependsUponSet = new HashSet<>(dependsUpon.get(className));
        for(String dependency : dependencies)
        {
            if(!dependsUponSet.contains(dependency))
            {
                indegree.put(className, indegree.getOrDefault(className, 0) + 1);
                addClass(dependency);
                dependsUpon.get(className).add(dependency);
                willAfect.get(dependency).add(className);
            }
        }

        return true;
    }

    //Depth First Search to Find out the right build order
    List<String> buildOrder() throws IllegalStateException{
        ArrayList<String> result = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : indegree.entrySet())
        {
            if(entry.getValue() == 0)
            {
                buildOrderDfs(result, entry.getKey(),new HashSet<>());
            }
        }

        if(result.size() != dependsUpon.size())
        {
            throw new IllegalStateException();
        }

        return result;
    }

    // Recursive helper method that helps buildOrder with depth first search
    public void buildOrderDfs(ArrayList<String> result, String currentClass, HashSet<String> visited)
    {
        result.add(currentClass);
        visited.add(currentClass);

        for(String dependent : willAfect.get(currentClass))
        {
            indegree.put(dependent, indegree.get(dependent) - 1);
            if(indegree.get(dependent) <= 0 && !visited.contains(dependent))
            {
                buildOrderDfs(result, dependent, visited);
            }
        }
    }

    //Breadth First Search to find the standalone modules
    Set<Set<String>> standaloneModules(){

        Set<Set<String>> result = new HashSet<>();
        ArrayList<String> allModules = new ArrayList<>(dependsUpon.keySet());
        Set<String> visitedModules = new HashSet<>();

        Queue<String> q = new LinkedList<>();
        for(String module : allModules)
        {
            if(!visitedModules.contains(module))
            {
                Set<String> currentStandAloneModule = new HashSet<String>();
                q.add(module);
                currentStandAloneModule.add(module);
                visitedModules.add(module);

                while(!q.isEmpty())
                {
                    String currentModule = q.poll();
                    for(String relatedModule : willAfect.getOrDefault(currentModule,new ArrayList<>()))
                    {
                        if(!visitedModules.contains(relatedModule) && !currentStandAloneModule.contains(relatedModule))
                        {
                            currentStandAloneModule.add(relatedModule);
                            visitedModules.add(relatedModule);
                            q.add(relatedModule);
                        }
                    }

                    for(String relatedModule : dependsUpon.getOrDefault(currentModule,new ArrayList<>()))
                    {
                        if(!visitedModules.contains(relatedModule) && !currentStandAloneModule.contains(relatedModule))
                        {
                            currentStandAloneModule.add(relatedModule);
                            visitedModules.add(relatedModule);
                            q.add(relatedModule);
                        }
                    }
                }
                result.add(currentStandAloneModule);
            }
        }

        return result;
    }

    //Since we are keeping track of how many classes are affected by a single class with "willAffect" map, sorting the values will give us the right result
    List<String> highUseClasses( int listMax ) throws IllegalArgumentException{

        if(listMax >= willAfect.size())
        {
            throw new IllegalArgumentException();
        }

        PriorityQueue<Map.Entry<String, ArrayList<String>>> pq = new PriorityQueue<>((a,b)->b.getValue().size() - a.getValue().size());
        pq.addAll(willAfect.entrySet());

        ArrayList<String> result = new ArrayList<>();
        for(int i = 0 ; i < listMax ; i ++)
        {
            result.add(pq.poll().getKey());
        }

        return result;
    }

    //BFS to find out if a repeated class is entered into the queue to find out dependency cycle
    boolean hasDependencyCycle (){
        Set<String> visited = new HashSet<>();
        Queue<String> q = new LinkedList<>();
        boolean noStartingPointFound = true;

        for(Map.Entry<String, Integer> entry : indegree.entrySet())
        {
            if(entry.getValue() == 0)
            {
                noStartingPointFound = false;
                q.add(entry.getKey());
                visited.add(entry.getKey());
            }
        }

        while(!q.isEmpty())
        {
            String currentNode = q.poll();
            for(String childNode : willAfect.get(currentNode))
            {
                if(visited.contains(childNode))
                {
                    return true;
                }
                q.add(childNode);
                visited.add(childNode);
            }
        }

        return noStartingPointFound;
    }



    public static void main(String[] args) {
        DependencyManager dm = new DependencyManager();
        HashSet<String> dep1 = new HashSet<String>( Arrays.asList("C","D","E"));
        dm.addClass("A",dep1);

        HashSet<String> dep2 = new HashSet<String>( Arrays.asList("D","E","F","G"));
        dm.addClass("B",dep2);

        HashSet<String> dep3 = new HashSet<String>( Arrays.asList("H"));
        dm.addClass("C",dep3);

        dm.addClass("D",dep3);

        ArrayList<String> result = (ArrayList<String>) dm.buildOrder();
        System.out.println(result);



        DependencyManager dm1 = new DependencyManager();
        HashSet<String> dep4 = new HashSet<String>( Arrays.asList("B","C"));
        dm1.addClass("A",dep4);

        HashSet<String> dep5 = new HashSet<String>( Arrays.asList("D"));
        dm1.addClass("C",dep5);

        HashSet<String> dep6 = new HashSet<String>( Arrays.asList("F","G"));
        dm1.addClass("E",dep6);

        Set<Set<String>> standAlone = dm1.standaloneModules();
        System.out.println(standAlone);



        DependencyManager dm2 = new DependencyManager();
        HashSet<String> dep7 = new HashSet<String>( Arrays.asList("B"));
        dm2.addClass("A",dep7);

        HashSet<String> dep8 = new HashSet<String>( Arrays.asList("C"));
        dm2.addClass("B",dep8);

        HashSet<String> dep9 = new HashSet<String>( Arrays.asList("A"));
        dm2.addClass("C",dep9);
        System.out.println(dm2.hasDependencyCycle());


    }
}
