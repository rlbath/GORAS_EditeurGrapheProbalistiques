package traitement;

import java.util.HashMap;
import exceptions.*;

public class FactoryManager {

    private HashMap<String, FactoryGraphe> factories;
    
    private static FactoryManager instance = new FactoryManager();
    
    public FactoryManager() {
        factories = new HashMap<> ();
        factories.put("Graphe simple", new FactoryGrapheSimpleNonOriente() );
        factories.put("Graphe oriente", new FactoryGrapheSimpleOriente() );
        factories.put("Graphe probabiliste", new FactoryGrapheProbabiliste());
    }

    public static FactoryManager getInstance() {
        return instance;
    }
    
    public FactoryGraphe getFactoryGraphe(String type) throws TypeGrapheFactoryException { 
        if (factories.containsKey(type)) {
            return factories.get(type);
        } else {
            throw new TypeGrapheFactoryException();
        }
    }

    public HashMap<String, FactoryGraphe> getFactories() {
        return factories;
    }

}
