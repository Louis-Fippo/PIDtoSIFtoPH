package de.bioquant.cytoscape.pidfileconverter.Model;

import cytoscape.CyNode;

public class NodeOfPattern {
	
	
	//Constructeur
	public NodeOfPattern(CyNode noeud,int type){
		
		this.Noeud=noeud;
		this.type=type;
		
	}
	
	//Attributs
	CyNode Noeud; //le noeud
	int type;     //le type d'interaction qui le lie au pattern
	
	//les méthodes 
	public CyNode getCyNode(){
		
		return this.Noeud;
	}
	
	public int getTypeInt(){
		
		return this.type;
	}
	
	
	
	
	

}
