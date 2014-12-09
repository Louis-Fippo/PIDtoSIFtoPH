package de.bioquant.cytoscape.pidfileconverter.Model;

import java.util.ArrayList;

public class Pattern {
	
	//construteur
	public Pattern(){
		this.numPattern=-1;
		this.compPattern=new ArrayList<NodeOfPattern>();
	}
	
	public Pattern(Pattern P){
		
		this.numPattern=P.getNumberOfPattern();
		this.compPattern.addAll(P.getNodeOfPattern());
		
	}
	
	//les attributs
	//le numéro du pattern
	int numPattern;
	
	//les composants du pattern
	ArrayList<NodeOfPattern> compPattern;
	
	//les méthodes
	
	public void setNumberOfPattern(int numb){
		this.numPattern = numb;
	}
	
	public void addNode(NodeOfPattern noeud){
		
		this.compPattern.add(noeud);
	}
	
	public int getNumberOfPattern(){
		
		return this.numPattern;
	}
	
	public ArrayList<NodeOfPattern> getNodeOfPattern(){
		
		return this.compPattern;
	}

}
