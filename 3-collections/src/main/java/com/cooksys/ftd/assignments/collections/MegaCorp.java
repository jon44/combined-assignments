package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	private HashSet<Capitalist> set = new HashSet<>();

    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	
    	if(capitalist == null){
    		return false;
    	}
        
    	if(set.contains(capitalist)){
    		return false;
    	}
    	
    	if(capitalist.hasParent()){
    		if(!set.contains(capitalist.getParent())){
    			if(add(capitalist.getParent())){
    				set.add(capitalist);
    				return true;
    			}
    		}else{
    			set.add(capitalist);
    			return true;
    		}
    	}else{
    		if(capitalist instanceof FatCat){
    			set.add(capitalist);
    			return true;
    		}
    	}
    	
    	return false;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	
    	if(capitalist == null){
    		return false;
    	}
        
    	if(set.contains(capitalist)){
    		return true;
    	}else{
    		return false;
    	}
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	
    	HashSet<Capitalist> currSet = new HashSet<>(set);
        
    	return currSet;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
        
    	HashSet<FatCat> parentSet = new HashSet<>();
    	
    	for(Capitalist i : set){
    		if(i instanceof FatCat){
    			parentSet.add((FatCat)i);
    		}
    	}
    	
    	return parentSet;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
        
    	HashSet<Capitalist> childSet = new HashSet<>();
    	
    	if(fatCat == null){
    		return childSet;
    	}
    	
    	if(set.contains(fatCat)){
    		
    		for(Capitalist i : set){
    			if(i.getParent() != null && i.getParent().equals(fatCat)){
    				childSet.add(i);
    			}
    		}
    	}
    	
    	return childSet;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
        
    	HashMap<FatCat, Set<Capitalist>> hierarchyMap = new HashMap<>();
    	
    	for(Capitalist i : set){
    		if(i instanceof FatCat){
    			HashSet<Capitalist> tempSet = new HashSet<>();
    			
    			for(Capitalist j : set){
    				if(j.getParent() == i){
    					tempSet.add(j);
    				}
    			}
    			hierarchyMap.put((FatCat)i, tempSet);
    		}
    	}
    	
    	return hierarchyMap;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
        
    	ArrayList<FatCat> parentChain = new ArrayList<>();
    	
    	if(capitalist == null){
    		return parentChain;
    	}
    	
    	FatCat tempParent = (FatCat)capitalist.getParent();
    	
    	while(tempParent != null){
    		if(!set.contains(tempParent)){
    			parentChain.clear();
    			return parentChain;
    		}
    		parentChain.add(tempParent);
    		tempParent = tempParent.getParent();
    	}
    	
    	return parentChain;
    }
}