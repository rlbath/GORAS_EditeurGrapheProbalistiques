package traitement;

import java.util.HashMap;
import exceptions.*;

public class FactoryManager {
    
    
    public HashMap<String, FactoryGraphe> factories;
    
    private static FactoryManager instance = new FactoryManager();
    
    public FactoryManager() {
        factories = new HashMap<> ();
        factories.put("Graphe simple", new FactoryGrapheSimpleNonOriente() );
        factories.put("TODO Graphe oriente", new FactoryGrapheSimpleNonOriente() );
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

}
